<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.VehicleType" %>
<%@ page import="model.User" %>
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
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Vehicle Type Management</title>
        <link rel="stylesheet" href="css/style.css">
    </head>
    <body>
        <div class="container">
            <h2>Quản lý loại xe / giá vé</h2>

            <p>Xin chào, <strong><%= currentUser.getFullName() %></strong> - Role: <strong><%= currentUser.getRole() %></strong></p>

            <%
                String success = request.getParameter("success");
                String error = request.getParameter("error");
            %>

            <% if ("add".equals(success)) { %>
            <p class="success">Thêm loại xe thành công.</p>
            <% } else if ("edit".equals(success)) { %>
            <p class="success">Cập nhật loại xe thành công.</p>
            <% } else if ("delete".equals(success)) { %>
            <p class="success">Xóa loại xe thành công.</p>
            <% } %>

            <% if ("type_in_use".equals(error)) { %>
            <p class="error">Không thể xóa vì loại xe này đang được sử dụng trong danh sách xe.</p>
            <% } else if (error != null) { %>
            <p class="error">Lỗi: <%= error %></p>
            <% } %>

            <h3>Thêm loại xe mới</h3>
            <form action="add-vehicle-type" method="post">
                Tên loại xe <br>
                <input type="text" name="typeName" required>
                <br><br>

                Giá / giờ <br>
                <input type="number" step="0.01" name="pricePerHour" required>
                <br><br>

                Trạng thái <br>
                <select name="status">
                    <option value="ACTIVE">ACTIVE</option>
                    <option value="INACTIVE">INACTIVE</option>
                </select>
                <br><br>

                <button type="submit">Thêm</button>
            </form>

            <hr>

            <h3>Danh sách loại xe</h3>

            <%
                List<VehicleType> vehicleTypeList = (List<VehicleType>) request.getAttribute("vehicleTypeList");
                if (vehicleTypeList != null && !vehicleTypeList.isEmpty()) {
            %>
            <table border="1" cellpadding="8" cellspacing="0">
                <tr>
                    <th>ID</th>
                    <th>Tên loại xe</th>
                    <th>Giá / giờ</th>
                    <th>Trạng thái</th>
                    <th>Action</th>
                </tr>

                <% for (VehicleType vt : vehicleTypeList) { %>
                <tr>
                    <td><%= vt.getTypeId() %></td>
                    <td><%= vt.getTypeName() %></td>
                    <td><%= vt.getPricePerHour() %></td>
                    <td><%= vt.getStatus() %></td>
                    <td>
                        <a href="edit-vehicle-type?id=<%= vt.getTypeId() %>">Sửa</a>
                        |
                        <a href="delete-vehicle-type?id=<%= vt.getTypeId() %>"
                           onclick="return confirm('Bạn có chắc muốn xóa loại xe này?');">
                            Xóa
                        </a>
                    </td>
                </tr>
                <% } %>
            </table>
            <% } else { %>
            <p>Không có loại xe nào.</p>
            <% } %>

            <br>
            <a href="admin-dashboard.jsp">Back Dashboard</a>
        </div>
    </body>
</html>