<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Ticket" %>
<%@ page import="model.User" %>

<%
    User currentUser = (User) session.getAttribute("user");
    if (currentUser == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String dashboardLink = "ADMIN".equalsIgnoreCase(currentUser.getRole())
            ? "admin-dashboard.jsp"
            : "staff-dashboard.jsp";
%>

<!DOCTYPE html>
<html>
    <head>
        <title>Ticket Manager</title>
        <link rel="stylesheet" href="css/style.css">
    </head>
    <body>
        <div class="container">
            <h2>Danh sách vé hiện tại</h2>
            <p>Xin chào, <strong><%= currentUser.getFullName() %></strong> - Role: <strong><%= currentUser.getRole() %></strong></p>

            <%
                List<Ticket> ticketList = (List<Ticket>) request.getAttribute("ticketList");
                if (ticketList != null && !ticketList.isEmpty()) {
            %>

            <table border="1" cellpadding="8" cellspacing="0">
                <tr>
                    <th>Mã code</th>
                    <th>Biển số xe</th>
                    <th>Loại xe</th>
                    <th>Thời gian vào</th>
                </tr>

                <% for (Ticket t : ticketList) { %>
                <tr>
                    <td><%= t.getTicketCode() %></td>
                    <td><%= t.getLicensePlate() %></td>
                    <td><%= t.getVehicleTypeName() %></td>
                    <td><%= t.getEntryTime() %></td>
                </tr>
                <% } %>
            </table>

            <% } else { %>
            <p>Không có vé nào.</p>
            <% } %>

            <br>
            <a href="<%= dashboardLink %>">Back Dashboard</a>
        </div>
    </body>
</html>