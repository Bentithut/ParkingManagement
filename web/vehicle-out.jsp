<%@ page contentType="text/html;charset=UTF-8" %>
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
        <title>Vehicle Out</title>
        <link rel="stylesheet" href="css/style.css">
    </head>
    <body>
        <div class="container">
            <h2>Vehicle Exit</h2>
            <p>Xin chào, <strong><%= currentUser.getFullName() %></strong> - Role: <strong><%= currentUser.getRole() %></strong></p>

            <form action="vehicle-out" method="post">
                Ticket Code <br>
                <input type="text" name="ticketCode" required>
                <br><br>
                <button type="submit">Vehicle Out</button>
            </form>

            <%
                String success = request.getParameter("success");
                String plate = request.getParameter("plate");
                String type = request.getParameter("type");
                String entryTime = request.getParameter("entryTime");
                String exitTime = request.getParameter("exitTime");
                String hours = request.getParameter("hours");
                String minutes = request.getParameter("minutes");
                String amount = request.getParameter("amount");
                String error = request.getParameter("error");
            %>

            <% if ("1".equals(success)) { %>
            <h3 class="success">Xe ra thành công</h3>
            <p><strong>Biển số xe:</strong> <%= plate %></p>
            <p><strong>Loại xe:</strong> <%= type %></p>
            <p><strong>Thời gian vào:</strong> <%= entryTime %></p>
            <p><strong>Thời gian ra:</strong> <%= exitTime %></p>
            <p><strong>Tổng thời gian gửi:</strong> <%= hours %> giờ <%= minutes %> phút</p>
            <p><strong>Tổng tiền:</strong> <%= amount %> VND</p>
            <% } %>

            <% if ("empty_code".equals(error)) { %>
            <p class="error">Vui lòng nhập mã vé.</p>
            <% } else if ("code_not_found".equals(error)) { %>
            <p class="error">Không tìm thấy mã vé.</p>
            <% } else if ("session_not_found".equals(error)) { %>
            <p class="error">Không tìm thấy phiên gửi xe đang hoạt động.</p>
            <% } else if ("vehicle_not_found".equals(error)) { %>
            <p class="error">Không tìm thấy xe.</p>
            <% } else if ("update_session_failed".equals(error)) { %>
            <p class="error">Lỗi cập nhật phiên gửi xe.</p>
            <% } else if ("update_vehicle_failed".equals(error)) { %>
            <p class="error">Lỗi cập nhật trạng thái xe.</p>
            <% } else if ("update_ticket_failed".equals(error)) { %>
            <p class="error">Lỗi cập nhật trạng thái vé.</p>
            <% } else if ("system".equals(error)) { %>
            <p class="error">Lỗi hệ thống.</p>
            <% } %>

            <br>
            <a href="<%= dashboardLink %>">Back Dashboard</a>
        </div>
    </body>
</html>