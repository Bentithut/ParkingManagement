package controller;

import dal.VehicleTypeDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.VehicleType;

@WebServlet("/edit-vehicle-type")
public class EditVehicleTypeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idRaw = request.getParameter("id");

        try {
            int id = Integer.parseInt(idRaw);
            VehicleTypeDAO dao = new VehicleTypeDAO();
            VehicleType vt = dao.findById(id);

            request.setAttribute("vehicleType", vt);
            request.getRequestDispatcher("vehicle-type-form.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("vehicle-type-list?error=invalid_id");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String idRaw = request.getParameter("typeId");
        String typeName = request.getParameter("typeName");
        String priceRaw = request.getParameter("pricePerHour");
        String status = request.getParameter("status");

        try {
            int typeId = Integer.parseInt(idRaw);
            double pricePerHour = Double.parseDouble(priceRaw);

            VehicleType vt = new VehicleType();
            vt.setTypeId(typeId);
            vt.setTypeName(typeName);
            vt.setPricePerHour(pricePerHour);
            vt.setStatus(status);

            VehicleTypeDAO dao = new VehicleTypeDAO();
            boolean ok = dao.updateVehicleType(vt);

            if (ok) {
                response.sendRedirect("vehicle-type-list?success=edit");
            } else {
                response.sendRedirect("vehicle-type-list?error=edit_failed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("vehicle-type-list?error=system");
        }
    }
}
