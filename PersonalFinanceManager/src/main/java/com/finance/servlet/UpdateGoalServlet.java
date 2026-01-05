package com.finance.servlet;

import java.io.IOException;
import java.sql.Connection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.finance.dao.GoalDAO;
import com.finance.db.DBConnection;
import com.finance.model.User;

@WebServlet("/UpdateGoal")
public class UpdateGoalServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("currentUser") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        User user = (User) session.getAttribute("currentUser");

        String action = request.getParameter("action");
        String goalIdStr = request.getParameter("goalId");

        if (action == null || goalIdStr == null) {
            response.getWriter().println("❌ Invalid request.");
            return;
        }

        // ✅ Optional safety check
        if (!"achieve".equals(action) && !"delete".equals(action)) {
            response.getWriter().println("❌ Invalid action.");
            return;
        }

        int goalId;
        try {
            goalId = Integer.parseInt(goalIdStr);
        } catch (NumberFormatException e) {
            response.getWriter().println("❌ Invalid goal ID.");
            return;
        }

        Connection conn = null;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            GoalDAO goalDAO = new GoalDAO(conn);
            boolean result = false;

            if ("achieve".equals(action)) {
                result = goalDAO.markGoalAsAchieved(goalId, user.getUserId());
            } else if ("delete".equals(action)) {
                result = goalDAO.deleteGoal(goalId, user.getUserId());
            }

            if (result) {
                conn.commit();
                response.sendRedirect("goals.jsp");
            } else {
                conn.rollback();
                response.getWriter().println("❌ Operation failed.");
            }

        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback();
            } catch (Exception ignored) {}

            e.printStackTrace();
            response.getWriter().println("❌ Server error occurred.");
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close(); // ✅ optional but professional
                }
            } catch (Exception ignored) {}
        }
    }
}
