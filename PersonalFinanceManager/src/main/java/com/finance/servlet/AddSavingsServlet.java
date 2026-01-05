package com.finance.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.finance.dao.GoalDAO;
import com.finance.db.DBConnection;
import com.finance.model.User;

@WebServlet("/AddSavings")
public class AddSavingsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("currentUser") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        User user = (User) session.getAttribute("currentUser");

        int goalId;
        double amount;

        // Parse input
        try {
            goalId = Integer.parseInt(request.getParameter("goalId"));
            amount = Double.parseDouble(request.getParameter("amount"));

            if (amount <= 0) {
                response.getWriter().println("❌ Invalid amount.");
                return;
            }

        } catch (Exception e) {
            response.getWriter().println("❌ Invalid input.");
            return;
        }

        Connection conn = null;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            // Fetch current saved & target
            double saved = 0;
            double target = 0;

            String fetchSql = """
                SELECT saved_amount, target_amount
                FROM goals
                WHERE goal_id = ? AND user_id = ?
            """;

            try (PreparedStatement ps = conn.prepareStatement(fetchSql)) {
                ps.setInt(1, goalId);
                ps.setInt(2, user.getUserId());
                ResultSet rs = ps.executeQuery();

                if (!rs.next()) {
                    conn.rollback();
                    response.getWriter().println("❌ Goal not found.");
                    return;
                }

                saved = rs.getDouble("saved_amount");
                target = rs.getDouble("target_amount");
            }

            double remaining = target - saved;

            // Oversave prevention
            if (remaining <= 0) {
                conn.rollback();
                response.getWriter().println("❌ Goal already achieved.");
                return;
            }

            if (amount > remaining) {
                conn.rollback();
                response.getWriter().println("❌ You can add at most = " + remaining);
                return;
            }

            // Add savings safely
            GoalDAO goalDAO = new GoalDAO(conn);
            boolean success = goalDAO.addSavings(goalId, user.getUserId(), amount);

            if (success) {
                conn.commit();
                response.sendRedirect("goals.jsp");
            } else {
                conn.rollback();
                response.getWriter().println("❌ Failed to add savings.");
            }

        } catch (Exception e) {
            try { if (conn != null) conn.rollback(); } catch (Exception ignored) {}
            e.printStackTrace();
            response.getWriter().println("❌ Server error.");

        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (Exception ignored) {}
        }
    }
}
