<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.User" %>
<%
    User currentUser = (User) session.getAttribute("user");
    if (currentUser == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    if (!"ADMIN".equalsIgnoreCase(currentUser.getRole())) {
        response.sendRedirect("staff-dashboard.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Admin Dashboard</title>
        <link rel="stylesheet" href="css/style.css">
    </head>
    <body>
        <div class="container">
            <h2>Admin Dashboard</h2>

            <p>Xin chào, <strong><%= currentUser.getFullName() %></strong> - Role: <strong><%= currentUser.getRole() %></strong></p>

            <ul>
                <li><a href="user-list">Quản lý tài khoản</a></li>
                <li><a href="vehicle-type-list">Quản lý loại xe / giá vé</a></li>
                <li><a href="report">Xem báo cáo</a></li>
                <li><a href="parking-history">Tra cứu lịch sử theo biển số</a></li>
                <li><a href="lost-ticket.jsp">Xử lý mất vé</a></li>
                <li><a href="ticket-list">Ticket Manager</a></li>
                <li><a href="vehicle-in.jsp">Vehicle In</a></li>
                <li><a href="vehicle-out.jsp">Vehicle Out</a></li>
                <li><a href="logout">Logout</a></li>
            </ul>
        </div>
    </body>
</html>