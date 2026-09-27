package controller;

import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

@WebServlet("/toggle-user-status")
public class ToggleUserStatusServlet extends HttpServlet {

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

            if ("admin".equalsIgnoreCase(user.getUsername())) {
                response.sendRedirect("user-list?error=cannot_lock_admin");
                return;
            }

            String newStatus = "ACTIVE".equalsIgnoreCase(user.getStatus()) ? "INACTIVE" : "ACTIVE";
            boolean ok = userDAO.updateStatus(userId, newStatus);

            if (ok) {
                response.sendRedirect("user-list?success=status_updated");
            } else {
                response.sendRedirect("user-list?error=status_failed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("user-list?error=system");
        }
    }
}