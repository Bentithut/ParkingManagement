<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.User" %>
<%@ page import="model.Role" %>
<%@ page import="java.util.List" %>
<%
    User currentUser = (User) session.getAttribute("user");
    if (currentUser == null) {
        response.sendRedirect("login.jsp?error=not_logged_in");
        return;
    }

    User editUser = (User) request.getAttribute("editUser");
    List<Role> roleList = (List<Role>) request.getAttribute("roleList");

    if (editUser == null) {
        response.sendRedirect("user-list?error=not_found");
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Edit User</title>
        <link rel="stylesheet" href="css/style.css">
    </head>
    <body>
        <div class="container">
            <h2>Sửa tài khoản</h2>

            <form action="edit-user" method="post">
                <input type="hidden" name="userId" value="<%= editUser.getUserId() %>">

                Username <br>
                <input type="text" value="<%= editUser.getUsername() %>" readonly>
                <br><br>

                Password <br>
                <input type="text" name="password" value="<%= editUser.getPassword() %>" required>
                <br><br>

                Full Name <br>
                <input type="text" name="fullName" value="<%= editUser.getFullName() %>" required>
                <br><br>

                Role <br>
                <select name="roleId">
                    <%
                        if (roleList != null) {
                            for (Role role : roleList) {
                    %>
                    <option value="<%= role.getRoleId() %>"
                            <%= editUser.getRoleId() == role.getRoleId() ? "selected" : "" %>>
                        <%= role.getRoleName() %>
                    </option>
                    <%
                            }
                        }
                    %>
                </select>
                <br><br>

                Status <br>
                <select name="status">
                    <option value="ACTIVE" <%= "ACTIVE".equalsIgnoreCase(editUser.getStatus()) ? "selected" : "" %>>ACTIVE</option>
                    <option value="INACTIVE" <%= "INACTIVE".equalsIgnoreCase(editUser.getStatus()) ? "selected" : "" %>>INACTIVE</option>
                </select>
                <br><br>

                <button type="submit">Lưu</button>
            </form>

            <br>
            <a href="user-list">Quay lại</a>
        </div>
    </body>
</html>