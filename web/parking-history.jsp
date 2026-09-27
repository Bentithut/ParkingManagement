<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.User" %>
<%@ page import="model.ParkingHistoryItem" %>
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
        <title>Tra cứu lịch sử theo biển số</title>
        <link rel="stylesheet" href="css/style.css">
    </head>
    <body>
        <div class="container">
            <h2>Tra cứu lịch sử theo biển số</h2>

            <p>Xin chào, <strong><%= currentUser.getFullName() %></strong> - Role: <strong><%= currentUser.getRole() %></strong></p>

            <form action="parking-history" method="get">
                Biển số xe <br>
                <input type="text" name="licensePlate" value="<%= request.getAttribute("searchedPlate") != null ? request.getAttribute("searchedPlate") : "" %>" required>
                <br><br>
                <button type="submit">Tìm kiếm</button>
            </form>

            <hr>

            <%
                List<ParkingHistoryItem> historyList = (List<ParkingHistoryItem>) request.getAttribute("historyList");
                String searchedPlate = (String) request.getAttribute("searchedPlate");
            %>

            <% if (searchedPlate != null) { %>
            <h3>Kết quả tìm kiếm cho biển số: <%= searchedPlate %></h3>

            <% if (historyList != null && !historyList.isEmpty()) { %>
            <table border="1" cellpadding="8" cellspacing="0">
                <tr>
                    <th>Session ID</th>
                    <th>Biển số xe</th>
                    <th>Loại xe</th>
                    <th>Mã vé</th>
                    <th>Thời gian vào</th>
                    <th>Thời gian ra</th>
                    <th>Tổng tiền</th>
                    <th>Trạng thái</th>
                </tr>

                <% for (ParkingHistoryItem item : historyList) { %>
                <tr>
                    <td><%= item.getSessionId() %></td>
                    <td><%= item.getLicensePlate() %></td>
                    <td><%= item.getVehicleTypeName() %></td>
                    <td><%= item.getTicketCode() %></td>
                    <td><%= item.getEntryTime() %></td>
                    <td><%= item.getExitTime() == null ? "" : item.getExitTime() %></td>
                    <td><%= item.getTotalAmount() == null ? "" : item.getTotalAmount() %></td>
                    <td><%= item.getStatus() %></td>
                </tr>
                <% } %>
            </table>
            <% } else { %>
            <p>Không tìm thấy lịch sử gửi xe cho biển số này.</p>
            <% } %>
            <% } %>

            <br>
            <a href="<%= dashboardLink %>">Back Dashboard</a>
        </div>
    </body>
</html>