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

import com.finance.dao.ExpenseDAO;
import com.finance.db.DBConnection;
import com.finance.model.Expense;
import com.finance.model.User;
import com.finance.util.MessageUtil;

@WebServlet("/Expenses")
public class ExpenseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("currentUser") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        User user = (User) session.getAttribute("currentUser");

        String amountStr = request.getParameter("amount");
        String category = request.getParameter("category");
        String dateStr = request.getParameter("expenseDate");
        String description = request.getParameter("description");

        // 🔐 Server-side validation
        if (amountStr == null || category == null || dateStr == null ||
            amountStr.isEmpty() || category.isEmpty() || dateStr.isEmpty()) {

            MessageUtil.setError(session, "All required fields must be filled");
            response.sendRedirect("expenses.jsp");
            return;
        }

        Connection conn = null;

        try {
            double amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                MessageUtil.setError(session, "Amount must be greater than zero");
                response.sendRedirect("expenses.jsp");
                return;
            }

            java.util.Date expenseDate =
                    new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);

            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); // 🔴 TRANSACTION START

            Expense expense = new Expense(
                    user.getUserId(),
                    amount,
                    category,
                    expenseDate,
                    description
            );

            ExpenseDAO expenseDAO = new ExpenseDAO(conn);
            boolean success = expenseDAO.addExpense(expense);

            if (success) {
                conn.commit(); // ✅ SUCCESS
                MessageUtil.setSuccess(session, "Expense added successfully");
            } else {
                conn.rollback(); // ❌ FAILURE
                MessageUtil.setError(session, "Failed to add expense");
            }

            response.sendRedirect("expenses.jsp");

        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback();
            } catch (Exception ignored) {}

            e.printStackTrace();
            MessageUtil.setError(session, "Server error occurred");
            response.sendRedirect("expenses.jsp");

        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true);
            } catch (Exception ignored) {}
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("expenses.jsp");
    }
}
