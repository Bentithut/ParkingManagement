package controller;

import dal.VehicleTypeDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.VehicleType;

@WebServlet("/delete-vehicle-type")
public class DeleteVehicleTypeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idRaw = request.getParameter("id");

        try {
            int typeId = Integer.parseInt(idRaw);

            VehicleTypeDAO dao = new VehicleTypeDAO();
            VehicleType vt = dao.findById(typeId);

            if (vt == null) {
                response.sendRedirect("vehicle-type-list?error=not_found");
                return;
            }

            // Không cho xóa nếu loại xe đang được dùng trong bảng vehicles
            if (dao.isVehicleTypeUsed(typeId)) {
                response.sendRedirect("vehicle-type-list?error=type_in_use");
                return;
            }

            boolean ok = dao.deleteVehicleType(typeId);

            if (ok) {
                response.sendRedirect("vehicle-type-list?success=delete");
            } else {
                response.sendRedirect("vehicle-type-list?error=delete_failed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("vehicle-type-list?error=system");
        }
    }
}