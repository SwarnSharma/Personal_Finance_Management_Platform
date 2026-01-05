<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.finance.model.User"%>
<%@ page import="com.finance.dao.GoalDAO"%>
<%@ page import="com.finance.model.Goal"%>
<%@ page import="java.util.List"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Goals</title>
<link rel="stylesheet" href="css/style.css">
</head>

<body>

<%
    if (session == null || session.getAttribute("currentUser") == null) {
        response.sendRedirect("index.jsp");
        return;
    }

    User user = (User) session.getAttribute("currentUser");
    GoalDAO goalDAO = new GoalDAO();
    List<Goal> goals = goalDAO.getGoalsByUser(user.getUserId());
%>

<h2>Financial Goals</h2>

<form action="addGoal" method="post" class="page-form">
    Description:
    <input type="text" name="description" required>

    Target Amount:
    <input type="number" step="0.01" name="targetAmount" required>

    Deadline:
    <input type="date" name="deadline" required>

    <input type="submit" value="Add Goal">
</form>

<h3>My Goals</h3>

<table border="1" width="90%" cellpadding="6">
<tr>
    <th>Description</th>
    <th>Target (₹)</th>
    <th>Saved (₹)</th>
    <th>Deadline</th>
    <th>Progress</th>
    <th>Status</th>
    <th>Action</th>
</tr>

<%
    for (Goal g : goals) {

        double progress = 0;
        if (g.getTargetAmount() > 0) {
            progress = (g.getSavedAmount() / g.getTargetAmount()) * 100;
            if (progress > 100) progress = 100;
        }

        boolean achieved = g.getSavedAmount() >= g.getTargetAmount();

        double remaining = g.getTargetAmount() - g.getSavedAmount();
        if (remaining < 0) remaining = 0;

        String color;

        if (progress < 50) {
            color = "#4CAF50";   // Green
        } else if (progress < 80) {
            color = "#FFC107";   // Yellow
        } else if (progress < 100) {
            color = "#FF9800";   // Orange
        } else {
            color = "#F44336";   // Red (ONLY at 100%)
        }

%>

<tr>
    <td><%= g.getDescription() %></td>
    <td>₹ <%= g.getTargetAmount() %></td>
    <td>₹ <%= g.getSavedAmount() %></td>
    <td><%= g.getDeadline() %></td>

    <td>
        <div class="progress-bar">
            <div class="progress-fill"
                 style="width:<%= progress %>%; background:<%= color %>;">
            </div>
        </div>
        <small><%= (int) progress %>%</small>
    </td>

    <td>
        <b><%= achieved ? "Achieved" : "Active" %></b>
    </td>

    <td class="action-cell">

    <% if (!achieved) { %>

        <!-- Add Savings -->
        <form action="AddSavings" method="post" class="action-form">
            <input type="hidden" name="goalId" value="<%= g.getGoalId() %>">
            <input type="number"
                   name="amount"
                   min="1"
                   placeholder="₹"
                   style="width:70px;"
                   required>
            <input type="submit"
                   value="Add"
                   class="btn-action btn-achieve"
                   onclick="disableButton(this)">
        </form>

        <!-- Remaining Amount -->
        <div class="remaining-amount">
            Remaining: ₹ <%= String.format("%.2f", remaining) %>
        </div>

        <!-- Savings History -->
        <%
            List<Double> history = goalDAO.getSavingsHistory(g.getGoalId());
            if (!history.isEmpty()) {
        %>
            <small>Recent savings:</small><br>
            <%
                for (int i = 0; i < Math.min(3, history.size()); i++) {
            %>
                <small>+ ₹<%= history.get(i) %></small><br>
            <%
                }
            %>
        <%
            }
        %>

    <% } else { %>

        <div class="goal-complete">🎉 Goal Completed</div>
        <span class="achieved-label">✔ Achieved</span>

    <% } %>

        <!-- Delete -->
        <form action="UpdateGoal" method="post" class="action-form">
            <input type="hidden" name="goalId" value="<%= g.getGoalId() %>">
            <input type="hidden" name="action" value="delete">
            <input type="submit"
                   value="Delete"
                   class="btn-action btn-delete"
                   onclick="return confirm('Delete this goal?') && disableButton(this);">
        </form>

    </td>
</tr>

<%
    }
%>
</table>

<p><a href="dashboard.jsp">Back to Dashboard</a></p>

<script>
function disableButton(btn) {
    btn.disabled = true;
    btn.value = "Processing...";
    btn.form.submit();
}
</script>

</body>
</html>
