<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="css/style.css">
<meta charset="UTF-8">
<title>Login</title>
</head>
<body>

<jsp:include page="includes/messages.jsp" />

<div class="page-form">
    <h2>Login</h2>

    <form action="login" method="post">
        <label>Email</label>
        <input type="email" name="email" required>

        <label>Password</label>
        <input type="password" name="password" required>

        <input type="submit" value="Login">
    </form>

    <p style="text-align:center;">
        Don't have an account?
        <a href="register.jsp">Register here</a>
    </p>
</div>

</body>
</html>
