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

@WebServlet("/deleteUser")
public class DeleteUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        User admin = (User) session.getAttribute("currentUser");
        if (admin == null || !"ADMIN".equals(admin.getRole())) {
            response.sendRedirect("index.jsp");
            return;
        }

        String userIdStr = request.getParameter("id");
        if (userIdStr == null || userIdStr.isEmpty()) {
            response.getWriter().println("❌ Invalid user ID.");
            return;
        }

        int userId;
        try {
            userId = Integer.parseInt(userIdStr);
        } catch (NumberFormatException e) {
            response.getWriter().println("❌ Invalid user ID format.");
            return;
        }

        Connection conn = null;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); // 🔴 TRANSACTION START

            UserDAO dao = new UserDAO(conn);
            boolean success = dao.deleteUser(userId);

            if (success) {
                conn.commit(); // ✅ SUCCESS
                response.sendRedirect("admin_dashboard.jsp");
            } else {
                conn.rollback(); // ❌ FAILURE
                response.getWriter().println("❌ Failed to delete user.");
            }

        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback();
            } catch (Exception ignored) {}
            e.printStackTrace();
            response.getWriter().println("❌ Server error occurred.");
        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true);
            } catch (Exception ignored) {}
        }
    }
}
