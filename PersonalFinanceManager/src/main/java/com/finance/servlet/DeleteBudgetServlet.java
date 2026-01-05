package com.finance.servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.finance.dao.BudgetDAO;
import com.finance.model.User;

@WebServlet("/DeleteBudget")
public class DeleteBudgetServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("currentUser");

        if (user == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        try {
            int budgetId = Integer.parseInt(request.getParameter("budgetId"));

            BudgetDAO dao = new BudgetDAO();
            dao.deleteBudget(budgetId, user.getUserId()); // Delete user-specific budget

            response.sendRedirect("budgets.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error deleting budget.");
        }
    }
}
