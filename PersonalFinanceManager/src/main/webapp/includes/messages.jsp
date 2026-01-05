<%
    String success = (String) session.getAttribute("successMessage");
    String error = (String) session.getAttribute("errorMessage");
%>

<% if (success != null) { %>
    <div style="background:#d4edda;color:#155724;padding:10px;margin:10px auto;width:80%;border-radius:5px;">
        <%= success %>
    </div>
    <%
        session.removeAttribute("successMessage");
    %>
<% } %>

<% if (error != null) { %>
    <div style="background:#f8d7da;color:#721c24;padding:10px;margin:10px auto;width:80%;border-radius:5px;">
        <%= error %>
    </div>
    <%
        session.removeAttribute("errorMessage");
    %>
<% } %>
