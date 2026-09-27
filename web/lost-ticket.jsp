<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.User" %>
<%@ page import="model.LostTicketInfo" %>
<%
    User currentUser = (User) session.getAttribute("user");
    if (currentUser == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String dashboardLink = "ADMIN".equalsIgnoreCase(currentUser.getRole())
            ? "admin-dashboard.jsp"
            : "staff-dashboard.jsp";

    String success = request.getParameter("success");
    String pageError = request.getParameter("error");
    if (pageError == null) {
        pageError = (String) request.getAttribute("error");
    }

    String searchedPlate = (String) request.getAttribute("searchedPlate");
    LostTicketInfo info = (LostTicketInfo) request.getAttribute("lostTicketInfo");
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Xử lý mất vé</title>
        <link rel="stylesheet" href="css/style.css">
    </head>
    <body>
        <div class="container">
            <h2>Xử lý mất vé</h2>

            <p>Xin chào, <strong><%= currentUser.getFullName() %></strong> - Role: <strong><%= currentUser.getRole() %></strong></p>

            <form action="lost-ticket" method="get">
                Biển số xe <br>
                <input type="text" name="licensePlate" value="<%= searchedPlate != null ? searchedPlate : "" %>" required>
                <br><br>
                <button type="submit">Tìm xe đang gửi</button>
            </form>

            <hr>

            <% if ("1".equals(success)) { %>
            <p class="success">Xử lý mất vé thành công. Xe đã được cho ra khỏi bãi.</p>
            <% } %>

            <% if ("not_found".equals(pageError)) { %>
            <p class="error">Không tìm thấy xe đang gửi với biển số này.</p>
            <% } else if ("update_session_failed".equals(pageError)) { %>
            <p class="error">Lỗi cập nhật phiên gửi xe.</p>
            <% } else if ("update_vehicle_failed".equals(pageError)) { %>
            <p class="error">Lỗi cập nhật trạng thái xe.</p>
            <% } else if ("update_ticket_failed".equals(pageError)) { %>
            <p class="error">Lỗi cập nhật trạng thái vé.</p>
            <% } else if ("system".equals(pageError)) { %>
            <p class="error">Lỗi hệ thống.</p>
            <% } %>

            <% if (info != null) { %>
            <h3>Thông tin xe mất vé</h3>

            <table border="1" cellpadding="8" cellspacing="0">
                <tr><th>Biển số xe</th><td><%= info.getLicensePlate() %></td></tr>
                <tr><th>Loại xe</th><td><%= info.getVehicleTypeName() %></td></tr>
                <tr><th>Mã vé</th><td><%= info.getTicketCode() %></td></tr>
                <tr><th>Thời gian vào</th><td><%= info.getEntryTime() %></td></tr>
                <tr><th>Thời gian ra</th><td><%= info.getExitTime() %></td></tr>
                <tr><th>Tổng thời gian gửi</th><td><%= info.getTotalHours() %> giờ <%= info.getTotalMinutes() %> phút</td></tr>
                <tr><th>Phí gửi xe</th><td><%= info.getParkingFee() %> VND</td></tr>
                <tr><th>Phụ phí mất vé</th><td><%= info.getLostTicketFee() %> VND</td></tr>
                <tr><th>Tổng tiền</th><td><strong><%= info.getTotalAmount() %> VND</strong></td></tr>
            </table>

            <br>

            <form action="lost-ticket" method="post">
                <input type="hidden" name="sessionId" value="<%= info.getSessionId() %>">
                <input type="hidden" name="vehicleId" value="<%= info.getVehicleId() %>">
                <input type="hidden" name="ticketId" value="<%= info.getTicketId() %>">
                <input type="hidden" name="totalAmount" value="<%= info.getTotalAmount() %>">

                <button type="submit" onclick="return confirm('Xác nhận xử lý mất vé và cho xe ra?');">
                    Xác nhận cho xe ra
                </button>
            </form>
            <% } %>

            <br>
            <a href="<%= dashboardLink %>">Back Dashboard</a>
        </div>
    </body>
</html>