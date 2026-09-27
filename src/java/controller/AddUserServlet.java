package controller;

import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

@WebServlet("/add-user")
public class AddUserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String fullName = request.getParameter("fullName");
        String roleIdRaw = request.getParameter("roleId");
        String status = request.getParameter("status");

        try {
            int roleId = Integer.parseInt(roleIdRaw);

            UserDAO userDAO = new UserDAO();

            if (userDAO.existsByUsername(username)) {
                response.sendRedirect("user-list?error=username_exists");
                return;
            }

            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setFullName(fullName);
            user.setRoleId(roleId);
            user.setStatus(status);

            boolean ok = userDAO.insertUser(user);

            if (ok) {
                response.sendRedirect("user-list?success=add");
            } else {
                response.sendRedirect("user-list?error=add_failed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("user-list?error=system");
        }
    }
}
