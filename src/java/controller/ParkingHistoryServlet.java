package controller;

import dal.ParkingHistoryDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ParkingHistoryItem;

@WebServlet("/parking-history")
public class ParkingHistoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String licensePlate = request.getParameter("licensePlate");

        if (licensePlate != null) {
            licensePlate = licensePlate.trim();
        }

        if (licensePlate != null && !licensePlate.isEmpty()) {
            ParkingHistoryDAO dao = new ParkingHistoryDAO();
            List<ParkingHistoryItem> historyList = dao.searchByLicensePlate(licensePlate);

            request.setAttribute("historyList", historyList);
            request.setAttribute("searchedPlate", licensePlate);
        }

        request.getRequestDispatcher("parking-history.jsp").forward(request, response);
    }
}
