package controller;

import dal.VehicleTypeDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.VehicleType;

@WebServlet("/toggle-vehicle-type-status")
public class ToggleVehicleTypeStatusServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idRaw = request.getParameter("id");

        try {
            int id = Integer.parseInt(idRaw);

            VehicleTypeDAO dao = new VehicleTypeDAO();
            VehicleType vt = dao.findById(id);

            if (vt == null) {
                response.sendRedirect("vehicle-type-list?error=not_found");
                return;
            }

            String newStatus = "ACTIVE".equalsIgnoreCase(vt.getStatus()) ? "INACTIVE" : "ACTIVE";
            boolean ok = dao.updateStatus(id, newStatus);

            if (ok) {
                response.sendRedirect("vehicle-type-list?success=status_updated");
            } else {
                response.sendRedirect("vehicle-type-list?error=update_status_failed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("vehicle-type-list?error=system");
        }
    }
}
