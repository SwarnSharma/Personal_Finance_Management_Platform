<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.finance.model.User"%>
<%@ page import="com.finance.dao.BudgetDAO"%>
<%@ page import="com.finance.model.Budget"%>
<%@ page import="java.util.List"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Budgets</title>
<link rel="stylesheet" href="css/style.css">
</head>

<body>

<jsp:include page="includes/messages.jsp" />

<%
    if(session == null || session.getAttribute("currentUser") == null){
        response.sendRedirect("index.jsp");
        return;
    }

    User user = (User) session.getAttribute("currentUser");
    BudgetDAO budgetDAO = new BudgetDAO();
    List<Budget> budgets = budgetDAO.getBudgetsByUser(user.getUserId());
%>

<h2>Budgets</h2>

<!-- ================= ADD BUDGET FORM ================= -->
<form action="addBudget"
      method="post"
      class="page-form"
      onsubmit="return validateBudgetForm();">

    Category:
    <input type="text" name="category" required>

    Amount:
    <input type="number" id="budgetAmount" name="amount" step="0.01" min="1" required>

    Start Date:
    <input type="date" name="startDate" required>

    End Date:
    <input type="date" name="endDate" required>

    <input type="submit" value="Add Budget">

</form>

<script src="js/validation.js"></script>

<!-- ================= BUDGET TABLE ================= -->
<h3>All Budgets</h3>

<table border="1" width="80%" cellpadding="5" cellspacing="0">
    <tr>
        <th>ID</th>
        <th>Category</th>
        <th>Amount (₹)</th>
        <th>Start Date</th>
        <th>End Date</th>
        <th>Action</th>
    </tr>

<%
    for(Budget b : budgets){
%>
    <tr>
        <td><%= b.getBudgetId() %></td>
        <td><%= b.getCategory() %></td>
        <td>₹ <%= b.getAmount() %></td>
        <td><%= b.getStartDate() %></td>
        <td><%= b.getEndDate() %></td>

        <td class="action-cell">
            <form action="DeleteBudget" method="post" class="action-form">
                <input type="hidden" name="budgetId" value="<%= b.getBudgetId() %>">
                <input type="submit"
                       value="Delete"
                       class="btn-action btn-delete"
                       onclick="return confirm('Are you sure you want to delete this budget?');">
            </form>
        </td>
    </tr>
<%
    }
%>
</table>

<p><a href="dashboard.jsp">Back to Dashboard</a></p>

</body>
</html>
