<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.User" %>
<%@ page import="model.Role" %>
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
        <title>User Management</title>
        <link rel="stylesheet" href="css/style.css">
    </head>
    <body>
        <div class="container">
            <h2>Quản lý tài khoản</h2>

            <p>Xin chào, <strong><%= currentUser.getFullName() %></strong> - Role: <strong><%= currentUser.getRole() %></strong></p>

            <%
                String success = request.getParameter("success");
                String error = request.getParameter("error");
            %>

            <% if ("add".equals(success)) { %>
            <p class="success">Thêm tài khoản thành công.</p>
            <% } else if ("edit".equals(success)) { %>
            <p class="success">Cập nhật tài khoản thành công.</p>
            <% } else if ("status_updated".equals(success)) { %>
            <p class="success">Cập nhật trạng thái tài khoản thành công.</p>
            <% } else if ("delete".equals(success)) { %>
            <p class="success">Xóa tài khoản thành công.</p>
            <% } %>

            <% if ("username_exists".equals(error)) { %>
            <p class="error">Username đã tồn tại.</p>
            <% } else if ("cannot_lock_admin".equals(error)) { %>
            <p class="error">Không được khóa tài khoản admin.</p>
            <% } else if ("cannot_delete_admin".equals(error)) { %>
            <p class="error">Không được xóa tài khoản admin.</p>
            <% } else if (error != null) { %>
            <p class="error">Lỗi: <%= error %></p>
            <% } %>

            <h3>Thêm tài khoản mới</h3>

            <form action="add-user" method="post">
                Username <br>
                <input type="text" name="username" required>
                <br><br>

                Password <br>
                <input type="text" name="password" required>
                <br><br>

                Full Name <br>
                <input type="text" name="fullName" required>
                <br><br>

                Role <br>
                <select name="roleId">
                    <%
                        List<Role> roleList = (List<Role>) request.getAttribute("roleList");
                        if (roleList != null) {
                            for (Role role : roleList) {
                    %>
                    <option value="<%= role.getRoleId() %>"><%= role.getRoleName() %></option>
                    <%
                            }
                        }
                    %>
                </select>
                <br><br>

                Status <br>
                <select name="status">
                    <option value="ACTIVE">ACTIVE</option>
                    <option value="INACTIVE">INACTIVE</option>
                </select>
                <br><br>

                <button type="submit">Thêm tài khoản</button>
            </form>

            <hr>

            <h3>Danh sách tài khoản</h3>

            <%
                List<User> userList = (List<User>) request.getAttribute("userList");
                if (userList != null && !userList.isEmpty()) {
            %>
            <table border="1" cellpadding="8" cellspacing="0">
                <tr>
                    <th>ID</th>
                    <th>Username</th>
                    <th>Họ tên</th>
                    <th>Role</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>
                <% for (User u : userList) { %>
                <tr>
                    <td><%= u.getUserId() %></td>
                    <td><%= u.getUsername() %></td>
                    <td><%= u.getFullName() %></td>
                    <td><%= u.getRole() %></td>
                    <td><%= u.getStatus() %></td>
                    <td>
                        <a href="edit-user?id=<%= u.getUserId() %>">Sửa</a>

                        <% if (!"admin".equalsIgnoreCase(u.getUsername())) { %>
                        |
                        <a href="toggle-user-status?id=<%= u.getUserId() %>"
                           onclick="return confirm('Bạn có chắc muốn đổi trạng thái tài khoản này?');">
                            <%= "ACTIVE".equalsIgnoreCase(u.getStatus()) ? "Khóa" : "Mở khóa" %>
                        </a>
                        |
                        <a href="delete-user?id=<%= u.getUserId() %>"
                           onclick="return confirm('Bạn có chắc muốn xóa tài khoản này?');">
                            Xóa
                        </a>
                        <% } %>
                    </td>
                </tr>
                <% } %>
            </table>
            <% } else { %>
            <p>Không có tài khoản nào.</p>
            <% } %>

            <br>
            <a href="admin-dashboard.jsp">Back Dashboard</a>
        </div>
    </body>
</html>