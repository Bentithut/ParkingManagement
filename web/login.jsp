<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Login</title>
        <link rel="stylesheet" href="css/style.css">
    </head>
    <body>

        <div class="container">
            <h2>Đăng nhập</h2>

            <form action="login" method="post" autocomplete="off">
                <input type="text" style="display:none">
                <input type="password" style="display:none">

                Username <br>
                <input type="text" name="username" required autocomplete="off">
                <br><br>

                Password <br>
                <input type="password" name="password" required autocomplete="new-password">
                <br><br>

                <button type="submit">Login</button>
            </form>

            <%
                String error = request.getParameter("error");
            %>

            <% if ("1".equals(error)) { %>
            <p class="error">Sai tài khoản hoặc mật khẩu.</p>
            <% } %>
        </div>

    </body>
</html>