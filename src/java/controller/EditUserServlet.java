package controller;

import dal.RoleDAO;
import dal.UserDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Role;
import model.User;

@WebServlet("/edit-user")
public class EditUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idRaw = request.getParameter("id");

        try {
            int userId = Integer.parseInt(idRaw);

            UserDAO userDAO = new UserDAO();
            RoleDAO roleDAO = new RoleDAO();

            User user = userDAO.findById(userId);
            List<Role> roleList = roleDAO.getAllRoles();

            request.setAttribute("editUser", user);
            request.setAttribute("roleList", roleList);
            request.getRequestDispatcher("user-form.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("user-list?error=invalid_id");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String userIdRaw = request.getParameter("userId");
        String password = request.getParameter("password");
        String fullName = request.getParameter("fullName");
        String roleIdRaw = request.getParameter("roleId");
        String status = request.getParameter("status");

        try {
            int userId = Integer.parseInt(userIdRaw);
            int roleId = Integer.parseInt(roleIdRaw);

            User user = new User();
            user.setUserId(userId);
            user.setPassword(password);
            user.setFullName(fullName);
            user.setRoleId(roleId);
            user.setStatus(status);

            UserDAO userDAO = new UserDAO();
            boolean ok = userDAO.updateUser(user);

            if (ok) {
                response.sendRedirect("user-list?success=edit");
            } else {
                response.sendRedirect("user-list?error=edit_failed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("user-list?error=system");
        }
    }
}
