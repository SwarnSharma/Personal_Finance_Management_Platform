<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.finance.model.User"%>
<%@ page import="com.finance.dao.UserDAO"%>
<%@ page import="java.util.List"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Dashboard</title>
<link rel="stylesheet" href="css/style.css">
</head>

<body>

<%
    if(session == null || session.getAttribute("currentUser") == null){
        response.sendRedirect("index.jsp");
        return;
    }

    User admin = (User) session.getAttribute("currentUser");
    if(!"ADMIN".equals(admin.getRole())){
        response.sendRedirect("index.jsp");
        return;
    }

    UserDAO dao = new UserDAO();
    List<User> users = dao.getAllUsers();
%>

<h2>Admin Dashboard</h2>
<p>Welcome, <%= admin.getName() %>!</p>

<h3>All Users</h3>

<table border="1" width="80%" cellpadding="5" cellspacing="0">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Email</th>
        <th>Role</th>
        <th>Action</th>
    </tr>

<%
    for(User u : users){
%>
    <tr>
        <td><%= u.getUserId() %></td>
        <td><%= u.getName() %></td>
        <td><%= u.getEmail() %></td>
        <td><%= u.getRole() %></td>

        <td class="action-cell">
            <% if(u.getUserId() != admin.getUserId()){ %>
                <form action="deleteUser" method="get" class="action-form">
                    <input type="hidden" name="id" value="<%= u.getUserId() %>">
                    <input type="submit"
                           value="Delete"
                           class="btn-action btn-delete"
                           onclick="return confirm('Delete this user?');">
                </form>
            <% } else { %>
                <em>Cannot delete yourself</em>
            <% } %>
        </td>
    </tr>
<%
    }
%>
</table>

<p><a href="dashboard.jsp">Back to Dashboard</a></p>

</body>
</html>
