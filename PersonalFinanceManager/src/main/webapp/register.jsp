<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="css/style.css">
<meta charset="UTF-8">
<title>User Registration</title>
</head>
<body>

<jsp:include page="includes/messages.jsp" />

<div class="page-form">
    <h2>Register</h2>

    <form action="register" method="post">
        <label>Name</label>
        <input type="text" name="name" required>

        <label>Email</label>
        <input type="email" name="email" required>

        <label>Password</label>
        <input type="password" name="password" minlength="6" required>

        <label>Role</label>
        <select name="role" required>
            <option value="USER">User</option>
            <option value="ADMIN">Admin</option>
        </select>

        <input type="submit" value="Register">
    </form>

    <p style="text-align:center;">
        Already have an account?
        <a href="index.jsp">Login here</a>
    </p>
</div>

</body>
</html>
