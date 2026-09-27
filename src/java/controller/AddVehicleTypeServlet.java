package controller;

import dal.VehicleTypeDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.VehicleType;

@WebServlet("/add-vehicle-type")
public class AddVehicleTypeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String typeName = request.getParameter("typeName");
        String priceRaw = request.getParameter("pricePerHour");
        String status = request.getParameter("status");

        try {
            double pricePerHour = Double.parseDouble(priceRaw);

            VehicleType vt = new VehicleType();
            vt.setTypeName(typeName);
            vt.setPricePerHour(pricePerHour);
            vt.setStatus(status);

            VehicleTypeDAO dao = new VehicleTypeDAO();
            boolean ok = dao.insertVehicleType(vt);

            if (ok) {
                response.sendRedirect("vehicle-type-list?success=add");
            } else {
                response.sendRedirect("vehicle-type-list?error=add_failed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("vehicle-type-list?error=system");
        }
    }
}
