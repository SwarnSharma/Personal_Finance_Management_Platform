package com.finance.servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.finance.dao.ExpenseDAO;
import com.finance.model.User;

@WebServlet("/DeleteExpense")
public class DeleteExpenseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (User) (session != null ? session.getAttribute("currentUser") : null);

        if (user == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        try {
            int expenseId = Integer.parseInt(request.getParameter("expenseId"));
            int userId = user.getUserId();

            ExpenseDAO dao = new ExpenseDAO();
            boolean deleted = dao.deleteExpense(expenseId, userId);

            if (deleted) {
                response.sendRedirect("expenses.jsp");
            } else {
                response.getWriter().println("❌ Failed to delete expense. It may not belong to you.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("❌ Error occurred while deleting expense.");
        }
    }
}
