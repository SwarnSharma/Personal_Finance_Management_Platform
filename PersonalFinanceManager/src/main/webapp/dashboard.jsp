<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.finance.model.User"%>
<%@ page import="com.finance.dao.ExpenseDAO"%>
<%@ page import="com.finance.dao.BudgetDAO"%>
<%@ page import="com.finance.dao.GoalDAO"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dashboard</title>
<link rel="stylesheet" href="css/style.css">
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>

<jsp:include page="includes/messages.jsp" />

<%
    if (session == null || session.getAttribute("currentUser") == null) {
        response.sendRedirect("index.jsp");
        return;
    }

    User user = (User) session.getAttribute("currentUser");

    ExpenseDAO expenseDAO = new ExpenseDAO();
    BudgetDAO budgetDAO = new BudgetDAO();
    GoalDAO goalDAO = new GoalDAO();

    double totalExpenses = expenseDAO.getTotalExpensesByUser(user.getUserId());
    double totalBudget = budgetDAO.getTotalBudgetByUser(user.getUserId());
    int totalGoals = goalDAO.getGoalsByUser(user.getUserId()).size();

    double remaining = totalBudget - totalExpenses;
%>

<!-- 🔹 MAIN DASHBOARD CONTAINER -->
<div style="
    max-width: 1100px;
    margin: 30px auto;
    background: #ffffff;
    padding: 25px 30px;
    border-radius: 10px;
    box-shadow: 0 0 12px rgba(0,0,0,0.08);
">

    <h2>Welcome, <%= user.getName() %> 👋</h2>
    <p><b>Role:</b> <%= user.getRole() %></p>

    <!-- 🔹 SUMMARY CARDS -->
<div style="
    display: flex;
    gap: 20px;
    margin-top: 25px;
    flex-wrap: wrap;
">

    <!-- Expenses -->
    <div style="
        flex: 1;
        min-width: 250px;
        background: #fff5f5;
        border-left: 6px solid #f44336;
        padding: 18px;
        border-radius: 8px;
    ">
        <h3 style="margin:0;">💸 Total Expenses</h3>
        <p style="font-size:22px; margin-top:10px;">
            ₹ <%= totalExpenses %>
        </p>
    </div>

    <!-- Budget -->
    <div style="
        flex: 1;
        min-width: 250px;
        background: #f5faff;
        border-left: 6px solid #2196f3;
        padding: 18px;
        border-radius: 8px;
    ">
        <h3 style="margin:0;">💰 Total Budget</h3>
        <p style="font-size:22px; margin-top:10px;">
            ₹ <%= totalBudget %>
        </p>
    </div>

    <!-- Goals -->
    <div style="
        flex: 1;
        min-width: 250px;
        background: #f1fff5;
        border-left: 6px solid #4caf50;
        padding: 18px;
        border-radius: 8px;
    ">
        <h3 style="margin:0;">🎯 Total Goals</h3>
        <p style="font-size:22px; margin-top:10px;">
            <%= totalGoals %>
        </p>
    </div>

</div>


    <!-- 🚨 BUDGET STATUS -->
    <div style="
        margin-top:20px;
        padding:15px;
        border-radius:8px;
        <% if (remaining < 0) { %>
            background-color:#ffe5e5; color:#b30000;
        <% } else { %>
            background-color:#e6ffea; color:#006600;
        <% } %>
    ">
        <% if (remaining < 0) { %>
            ⚠️ <b>Budget Exceeded!</b><br>
            You exceeded your budget by ₹ <%= Math.abs(remaining) %>
        <% } else { %>
            ✅ <b>Budget Safe</b><br>
            Remaining budget: ₹ <%= remaining %>
        <% } %>
    </div>

    <!-- 📊 CHART -->
    <div style="margin-top:30px;">
        <h3>Finance Overview</h3>
        <div style="height:320px;">
            <canvas id="financeChart"></canvas>
        </div>
    </div>

    <!-- 🔗 ACTIONS -->
    <div style="margin-top:30px;">
        <h3>Actions</h3>
        <ul>
            <li><a href="expenses.jsp">Manage Expenses</a></li>
            <li><a href="budgets.jsp">Manage Budgets</a></li>
            <li><a href="goals.jsp">Manage Goals</a></li>
            <% if ("ADMIN".equals(user.getRole())) { %>
                <li><a href="admin_dashboard.jsp">Admin Dashboard</a></li>
            <% } %>
            <li><a href="logout.jsp">Logout</a></li>
        </ul>
    </div>

</div>

<script>
    const ctx = document.getElementById('financeChart');
    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: ['Total Expenses', 'Total Budget'],
            datasets: [{
                data: [<%= totalExpenses %>, <%= totalBudget %>],
                backgroundColor: ['#ff6384', '#36a2eb']
            }]
        },
        options: {
            maintainAspectRatio: false,
            responsive: true,
            plugins: {
                legend: { display: false }
            },
            scales: {
                y: { beginAtZero: true }
            }
        }
    });
</script>

</body>
</html>
