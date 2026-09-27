<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.User" %>
<%@ page import="model.ReportData" %>
<%@ page import="model.ParkingSessionView" %>
<%@ page import="java.util.List" %>
<%
    User currentUser = (User) session.getAttribute("user");
    if (currentUser == null) {
        response.sendRedirect("login.jsp?error=not_logged_in");
        return;
    }

    if (!"ADMIN".equalsIgnoreCase(currentUser.getRole())) {
        response.sendRedirect("staff-dashboard.jsp");
        return;
    }

    ReportData reportData = (ReportData) request.getAttribute("reportData");
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Report</title>
        <link rel="stylesheet" href="css/style.css">
    </head>
    <body>
        <div class="container">
            <h2>Báo cáo hệ thống</h2>

            <p>Xin chào, <strong><%= currentUser.getFullName() %></strong> - Role: <strong><%= currentUser.getRole() %></strong></p>

            <% if (reportData != null) { %>
            <h3>Tổng quan</h3>
            <table border="1" cellpadding="10" cellspacing="0">
                <tr>
                    <th>Số xe đang gửi</th>
                    <th>Số lượt xe vào hôm nay</th>
                    <th>Số lượt xe ra hôm nay</th>
                    <th>Doanh thu hôm nay</th>
                    <th>Doanh thu tháng này</th>
                </tr>
                <tr>
                    <td><%= reportData.getCurrentParkingCount() %></td>
                    <td><%= reportData.getTodayVehicleInCount() %></td>
                    <td><%= reportData.getTodayVehicleOutCount() %></td>
                    <td><%= reportData.getTodayRevenue() %> VND</td>
                    <td><%= reportData.getMonthRevenue() %> VND</td>
                </tr>
            </table>

            <br>

            <h3>20 phiên gửi xe gần đây</h3>

            <%
                List<ParkingSessionView> recentSessions = reportData.getRecentSessions();
                if (recentSessions != null && !recentSessions.isEmpty()) {
            %>
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

                <% for (ParkingSessionView item : recentSessions) { %>
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
            <p>Chưa có dữ liệu phiên gửi xe.</p>
            <% } %>

            <% } else { %>
            <p>Không tải được dữ liệu báo cáo.</p>
            <% } %>

            <br>
            <a href="admin-dashboard.jsp">Back Dashboard</a>
        </div>
    </body>
</html>