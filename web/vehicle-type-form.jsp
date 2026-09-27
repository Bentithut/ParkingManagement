<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.VehicleType" %>
<%@ page import="model.User" %>
<%
    User currentUser = (User) session.getAttribute("user");
    if (currentUser == null) {
        response.sendRedirect("login.jsp?error=not_logged_in");
        return;
    }

    VehicleType vt = (VehicleType) request.getAttribute("vehicleType");
    if (vt == null) {
        response.sendRedirect("vehicle-type-list?error=not_found");
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Edit Vehicle Type</title>
        <link rel="stylesheet" href="css/style.css">
    </head>
    <body>
        <div class="container">
            <h2>Sửa loại xe / giá vé</h2>

            <form action="edit-vehicle-type" method="post">
                <input type="hidden" name="typeId" value="<%= vt.getTypeId() %>">

                Tên loại xe <br>
                <input type="text" name="typeName" value="<%= vt.getTypeName() %>" required>
                <br><br>

                Giá / giờ <br>
                <input type="number" step="0.01" name="pricePerHour" value="<%= vt.getPricePerHour() %>" required>
                <br><br>

                Trạng thái <br>
                <select name="status">
                    <option value="ACTIVE" <%= "ACTIVE".equalsIgnoreCase(vt.getStatus()) ? "selected" : "" %>>ACTIVE</option>
                    <option value="INACTIVE" <%= "INACTIVE".equalsIgnoreCase(vt.getStatus()) ? "selected" : "" %>>INACTIVE</option>
                </select>
                <br><br>

                <button type="submit">Lưu</button>
            </form>

            <br>
            <a href="vehicle-type-list">Quay lại</a>
        </div>      
    </body>
</html>