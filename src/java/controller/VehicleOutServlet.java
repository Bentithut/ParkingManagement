package controller;

import dal.ParkingSessionDAO;
import dal.TicketDAO;
import dal.VehicleDAO;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ParkingSession;
import model.Ticket;
import model.Vehicle;

@WebServlet("/vehicle-out")
public class VehicleOutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String ticketCode = request.getParameter("ticketCode");

        TicketDAO ticketDAO = new TicketDAO();
        ParkingSessionDAO sessionDAO = new ParkingSessionDAO();
        VehicleDAO vehicleDAO = new VehicleDAO();

        try {
            if (ticketCode == null || ticketCode.trim().isEmpty()) {
                response.sendRedirect("vehicle-out.jsp?error=empty_code");
                return;
            }

            ticketCode = ticketCode.trim();

            Ticket ticket = ticketDAO.findByTicketCode(ticketCode);
            if (ticket == null) {
                response.sendRedirect("vehicle-out.jsp?error=code_not_found");
                return;
            }

            ParkingSession session = sessionDAO.getActiveSessionByTicketId(ticket.getTicketId());
            if (session == null) {
                response.sendRedirect("vehicle-out.jsp?error=session_not_found");
                return;
            }

            Vehicle vehicle = vehicleDAO.findById(session.getVehicleId());
            if (vehicle == null) {
                response.sendRedirect("vehicle-out.jsp?error=vehicle_not_found");
                return;
            }

            Timestamp exitTime = new Timestamp(System.currentTimeMillis());
            Timestamp entryTime = session.getEntryTime();

            long diffMillis = exitTime.getTime() - entryTime.getTime();
            long totalMinutes = diffMillis / (1000 * 60);
            long hours = totalMinutes / 60;
            long minutes = totalMinutes % 60;

            double pricePerHour = vehicle.getPricePerHour();

            double chargedHours = Math.ceil(totalMinutes / 60.0);
            if (chargedHours <= 0) {
                chargedHours = 1;
            }

            double totalAmount = chargedHours * pricePerHour;

            session.setExitTime(exitTime);
            session.setTotalAmount(totalAmount);
            session.setStatus("COMPLETED");

            boolean updatedSession = sessionDAO.updateSessionWhenVehicleOut(session);
            if (!updatedSession) {
                response.sendRedirect("vehicle-out.jsp?error=update_session_failed");
                return;
            }

            boolean updatedVehicle = vehicleDAO.updateStatus(vehicle.getVehicleId(), "OUT");
            if (!updatedVehicle) {
                response.sendRedirect("vehicle-out.jsp?error=update_vehicle_failed");
                return;
            }

            boolean updatedTicket = ticketDAO.updateTicketStatus(ticket.getTicketId(), "USED");
            if (!updatedTicket) {
                response.sendRedirect("vehicle-out.jsp?error=update_ticket_failed");
                return;
            }

            String encodedPlate = URLEncoder.encode(vehicle.getLicensePlate(), StandardCharsets.UTF_8);
            String encodedType = URLEncoder.encode(vehicle.getTypeName(), StandardCharsets.UTF_8);
            String encodedEntry = URLEncoder.encode(entryTime.toString(), StandardCharsets.UTF_8);
            String encodedExit = URLEncoder.encode(exitTime.toString(), StandardCharsets.UTF_8);

            response.sendRedirect(
                    "vehicle-out.jsp?success=1"
                    + "&plate=" + encodedPlate
                    + "&type=" + encodedType
                    + "&entryTime=" + encodedEntry
                    + "&exitTime=" + encodedExit
                    + "&hours=" + hours
                    + "&minutes=" + minutes
                    + "&amount=" + totalAmount
            );

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("vehicle-out.jsp?error=system");
        }
    }
}
