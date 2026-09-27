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

@WebServlet("/user-list")
public class UserListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserDAO userDAO = new UserDAO();
        RoleDAO roleDAO = new RoleDAO();

        List<User> userList = userDAO.getAllUsers();
        List<Role> roleList = roleDAO.getAllRoles();

        request.setAttribute("userList", userList);
        request.setAttribute("roleList", roleList);
        request.getRequestDispatcher("user-management.jsp").forward(request, response);
    }
}