package com.finance.servlet;

import java.io.IOException;
import java.sql.Connection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.finance.dao.UserDAO;
import com.finance.db.DBConnection;
import com.finance.model.User;
import com.finance.util.MessageUtil;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        // 🔐 Server-side validation
        if (name == null || email == null || password == null || role == null ||
            name.isEmpty() || email.isEmpty() || password.isEmpty() || role.isEmpty()) {

            MessageUtil.setError(session, "All fields are required");
            response.sendRedirect("register.jsp");
            return;
        }

        Connection conn = null;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); // 🔴 TRANSACTION START

            User user = new User(name, email, password, role);
            UserDAO userDAO = new UserDAO(conn);

            boolean success = userDAO.registerUser(user);

            if (success) {
                conn.commit(); // ✅ SUCCESS
                MessageUtil.setSuccess(session, "Registration successful. Please login.");
                response.sendRedirect("index.jsp");
            } else {
                conn.rollback(); // ❌ FAILURE
                MessageUtil.setError(session, "User already exists or registration failed");
                response.sendRedirect("register.jsp");
            }

        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback();
            } catch (Exception ignored) {}

            e.printStackTrace();
            MessageUtil.setError(session, "Server error occurred");
            response.sendRedirect("register.jsp");

        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true);
            } catch (Exception ignored) {}
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("register.jsp");
    }
}
