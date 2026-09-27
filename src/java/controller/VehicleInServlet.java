package controller;

import dal.ParkingSessionDAO;
import dal.TicketDAO;
import dal.VehicleDAO;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Random;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ParkingSession;
import model.Ticket;
import model.Vehicle;

@WebServlet("/vehicle-in")
public class VehicleInServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String licensePlate = request.getParameter("licensePlate");
        String typeIdRaw = request.getParameter("typeId");

        VehicleDAO vehicleDAO = new VehicleDAO();
        TicketDAO ticketDAO = new TicketDAO();
        ParkingSessionDAO sessionDAO = new ParkingSessionDAO();

        try {
            if (licensePlate == null || licensePlate.trim().isEmpty()
                    || typeIdRaw == null || typeIdRaw.trim().isEmpty()) {
                response.sendRedirect("vehicle-in.jsp?error=missing_data");
                return;
            }

            int typeId = Integer.parseInt(typeIdRaw);
            licensePlate = licensePlate.trim().toUpperCase();

            Vehicle vehicle = vehicleDAO.findByLicensePlate(licensePlate);

            if (vehicle != null && "IN".equalsIgnoreCase(vehicle.getStatus())) {
                response.sendRedirect("vehicle-in.jsp?error=plate_exists");
                return;
            }

            if (vehicle == null) {
                vehicle = new Vehicle();
                vehicle.setLicensePlate(licensePlate);
                vehicle.setCustomerId(null);
                vehicle.setTypeId(typeId);
                vehicle.setStatus("IN");

                boolean inserted = vehicleDAO.insertVehicle(vehicle);
                if (!inserted) {
                    response.sendRedirect("vehicle-in.jsp?error=insert_vehicle_failed");
                    return;
                }

                vehicle = vehicleDAO.findByLicensePlate(licensePlate);
                if (vehicle == null) {
                    response.sendRedirect("vehicle-in.jsp?error=vehicle_not_found");
                    return;
                }
            } else {
                boolean updated = vehicleDAO.updateStatus(vehicle.getVehicleId(), "IN");
                if (!updated) {
                    response.sendRedirect("vehicle-in.jsp?error=update_vehicle_failed");
                    return;
                }
            }

            Random random = new Random();
            String ticketCode;
            do {
                int number = random.nextBoolean()
                        ? 1000 + random.nextInt(9000)
                        : 10000 + random.nextInt(90000);
                ticketCode = String.valueOf(number);
            } while (ticketDAO.findByTicketCode(ticketCode) != null);

            Timestamp now = new Timestamp(System.currentTimeMillis());

            Ticket ticket = new Ticket();
            ticket.setTicketCode(ticketCode);
            ticket.setVehicleId(vehicle.getVehicleId());
            ticket.setTicketType("DAILY");
            ticket.setIssueTime(now);
            ticket.setExpireTime(null);
            ticket.setStatus("ACTIVE");

            int ticketId = ticketDAO.createTicket(ticket);
            if (ticketId == -1) {
                response.sendRedirect("vehicle-in.jsp?error=create_ticket_failed");
                return;
            }

            ParkingSession session = new ParkingSession();
            session.setVehicleId(vehicle.getVehicleId());
            session.setTicketId(ticketId);
            session.setLotId(null);
            session.setEntryTime(now);
            session.setExitTime(null);
            session.setTotalAmount(null);
            session.setStatus("PARKING");

            int sessionId = sessionDAO.createSession(session);
            if (sessionId == -1) {
                response.sendRedirect("vehicle-in.jsp?error=create_session_failed");
                return;
            }

            response.sendRedirect("vehicle-in.jsp?success=1&code=" + ticketCode);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("vehicle-in.jsp?error=system");
        }
    }
}