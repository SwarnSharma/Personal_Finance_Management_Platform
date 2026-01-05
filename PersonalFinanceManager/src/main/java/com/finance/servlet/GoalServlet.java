package com.finance.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.finance.dao.GoalDAO;
import com.finance.db.DBConnection;
import com.finance.model.Goal;
import com.finance.model.User;
import com.finance.util.MessageUtil;

@WebServlet("/addGoal")
public class GoalServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("currentUser") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        User user = (User) session.getAttribute("currentUser");

        String description = request.getParameter("description");
        String targetAmountStr = request.getParameter("targetAmount");
        String deadlineStr = request.getParameter("deadline");

        // 🔐 Server-side validation
        if (description == null || targetAmountStr == null || deadlineStr == null ||
            description.isEmpty() || targetAmountStr.isEmpty() || deadlineStr.isEmpty()) {

            MessageUtil.setError(session, "All required fields must be filled");
            response.sendRedirect("goals.jsp");
            return;
        }

        Connection conn = null;

        try {
            double targetAmount = Double.parseDouble(targetAmountStr);
            if (targetAmount <= 0) {
                MessageUtil.setError(session, "Target amount must be greater than zero");
                response.sendRedirect("goals.jsp");
                return;
            }

            java.util.Date deadline =
                    new SimpleDateFormat("yyyy-MM-dd").parse(deadlineStr);

            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); // 🔴 TRANSACTION START

            Goal goal = new Goal(
                    user.getUserId(),
                    description,
                    targetAmount,
                    deadline,
                    false
            );

            GoalDAO goalDAO = new GoalDAO(conn);
            boolean success = goalDAO.addGoal(goal);

            if (success) {
                conn.commit(); // ✅ SUCCESS
                MessageUtil.setSuccess(session, "Goal added successfully");
            } else {
                conn.rollback(); // ❌ FAILURE
                MessageUtil.setError(session, "Failed to add goal");
            }

            response.sendRedirect("goals.jsp");

        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback();
            } catch (Exception ignored) {}

            e.printStackTrace();
            MessageUtil.setError(session, "Server error occurred");
            response.sendRedirect("goals.jsp");

        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true);
            } catch (Exception ignored) {}
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("goals.jsp");
    }
}
