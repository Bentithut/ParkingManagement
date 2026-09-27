package controller;

import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

@WebServlet("/delete-user")
public class DeleteUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idRaw = request.getParameter("id");

        try {
            int userId = Integer.parseInt(idRaw);

            UserDAO userDAO = new UserDAO();
            User user = userDAO.findById(userId);

            if (user == null) {
                response.sendRedirect("user-list?error=not_found");
                return;
            }

            // Không cho xóa tài khoản admin
            if ("admin".equalsIgnoreCase(user.getUsername())) {
                response.sendRedirect("user-list?error=cannot_delete_admin");
                return;
            }

            boolean ok = userDAO.deleteUser(userId);

            if (ok) {
                response.sendRedirect("user-list?success=delete");
            } else {
                response.sendRedirect("user-list?error=delete_failed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("user-list?error=system");
        }
    }
}
