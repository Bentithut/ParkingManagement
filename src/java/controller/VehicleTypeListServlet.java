package controller;

import dal.VehicleTypeDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.VehicleType;

@WebServlet("/vehicle-type-list")
public class VehicleTypeListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        VehicleTypeDAO dao = new VehicleTypeDAO();
        List<VehicleType> list = dao.getAllVehicleTypes();

        request.setAttribute("vehicleTypeList", list);
        request.getRequestDispatcher("vehicle-type-management.jsp").forward(request, response);
    }
}
