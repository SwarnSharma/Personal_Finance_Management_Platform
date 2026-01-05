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

import com.finance.dao.BudgetDAO;
import com.finance.db.DBConnection;
import com.finance.model.Budget;
import com.finance.model.User;
import com.finance.util.MessageUtil;

@WebServlet("/addBudget")
public class BudgetServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("currentUser") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        User user = (User) session.getAttribute("currentUser");

        String category = request.getParameter("category");
        String amountStr = request.getParameter("amount");
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");

        // Validate required fields
        if (category == null || amountStr == null || startDateStr == null || endDateStr == null ||
            category.isEmpty() || amountStr.isEmpty() || startDateStr.isEmpty() || endDateStr.isEmpty()) {

            MessageUtil.setError(session, "All required fields must be filled");
            response.sendRedirect("budgets.jsp");
            return;
        }

        Connection conn = null;

        try {
            double amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                MessageUtil.setError(session, "Budget amount must be greater than zero");
                response.sendRedirect("budgets.jsp");
                return;
            }

            java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDateStr);
            java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDateStr);

            if (endDate.before(startDate)) {
                MessageUtil.setError(session, "End date cannot be before start date");
                response.sendRedirect("budgets.jsp");
                return;
            }

            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); // Start transaction

            Budget budget = new Budget(user.getUserId(), category, amount, startDate, endDate);
            BudgetDAO budgetDAO = new BudgetDAO(conn);

            boolean success = budgetDAO.addBudget(budget);

            if (success) {
                conn.commit(); // Commit transaction
                MessageUtil.setSuccess(session, "Budget added successfully");
            } else {
                conn.rollback(); // Rollback on failure
                MessageUtil.setError(session, "Failed to add budget");
            }

            response.sendRedirect("budgets.jsp");

        } catch (Exception e) {
            try { if (conn != null) conn.rollback(); } catch (Exception ignored) {}
            e.printStackTrace();
            MessageUtil.setError(session, "Server error occurred");
            response.sendRedirect("budgets.jsp");

        } finally {
            try { if (conn != null) conn.setAutoCommit(true); } catch (Exception ignored) {}
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("budgets.jsp");
    }
}
