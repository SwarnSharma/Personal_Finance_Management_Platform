package com.finance.servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.finance.dao.UserDAO;
import com.finance.model.User;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1️⃣ Get email and password from form
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // 2️⃣ Authenticate user
        UserDAO dao = new UserDAO();
        User user = dao.login(email, password);

        // 3️⃣ Handle login result
        if (user != null) {
            // Create session
            HttpSession session = request.getSession();
            session.setAttribute("currentUser", user);

            // ✅ Redirect to dashboard.jsp
            response.sendRedirect("dashboard.jsp");
        } else {
            // Show error message and link back to login
            response.getWriter().println("❌ Invalid email or password! <a href='index.jsp'>Try Again</a>");
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.getWriter().println("LoginServlet is running.");
    }
}
