<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.User" %>
<%@ page import="dal.VehicleTypeDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="model.VehicleType" %>
<%
    User currentUser = (User) session.getAttribute("user");
    if (currentUser == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String dashboardLink = "ADMIN".equalsIgnoreCase(currentUser.getRole())
            ? "admin-dashboard.jsp"
            : "staff-dashboard.jsp";

    VehicleTypeDAO vehicleTypeDAO = new VehicleTypeDAO();
    List<VehicleType> activeTypes = vehicleTypeDAO.getActiveVehicleTypes();
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Vehicle In</title>
        <link rel="stylesheet" href="css/style.css">
    </head>
    <body>
        <div class="container">

            <h2>Vehicle Entry</h2>
            <p>Xin chào, <strong><%= currentUser.getFullName() %></strong> - Role: <strong><%= currentUser.getRole() %></strong></p>

            <form action="vehicle-in" method="post">
                <label>License Plate</label><br>
                <input type="text" name="licensePlate" required>
                <br><br>

                <label>Vehicle Type</label><br>
                <select name="typeId">
                    <% if (activeTypes != null) {
                for (VehicleType vt : activeTypes) { %>
                    <option value="<%= vt.getTypeId() %>"><%= vt.getTypeName() %></option>
                    <%  }
               } %>
                </select>
                <br><br>

                <button type="submit">Vehicle In</button>
            </form>

            <hr>

            <%
                String success = request.getParameter("success");
                String code = request.getParameter("code");
                String error = request.getParameter("error");
            %>

            <% if ("1".equals(success)) { %>
            <div class="success">Xe vào thành công!</div>
            <div>Mã vé: <strong><%= code %></strong></div>
            <% } %>

            <% if ("plate_exists".equals(error)) { %>
            <div class="error">Biển số này đã có trong bãi.</div>
            <% } else if ("missing_data".equals(error)) { %>
            <div class="error">Vui lòng nhập đầy đủ thông tin.</div>
            <% } else if ("create_ticket_failed".equals(error)) { %>
            <div class="error">Lỗi tạo vé.</div>
            <% } else if ("create_session_failed".equals(error)) { %>
            <div class="error">Lỗi tạo phiên gửi xe.</div>
            <% } else if ("system".equals(error)) { %>
            <div class="error">Lỗi hệ thống.</div>
            <% } %>

            <br>
            <a href="<%= dashboardLink %>">Back Dashboard</a>

        </div>
    </body>
</html>