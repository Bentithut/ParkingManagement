package controller;

import dal.LostTicketDAO;
import dal.ParkingSessionDAO;
import dal.TicketDAO;
import dal.VehicleDAO;
import java.io.IOException;
import java.sql.Timestamp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.LostTicketInfo;
import model.ParkingSession;

@WebServlet("/lost-ticket")
public class LostTicketServlet extends HttpServlet {

    private static final double DEFAULT_LOST_TICKET_FEE = 100000.0;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String licensePlate = request.getParameter("licensePlate");

        if (licensePlate != null && !licensePlate.trim().isEmpty()) {
            licensePlate = licensePlate.trim().toUpperCase();

            LostTicketDAO lostTicketDAO = new LostTicketDAO();
            LostTicketInfo info = lostTicketDAO.findActiveSessionByLicensePlate(licensePlate);

            if (info == null) {
                request.setAttribute("error", "not_found");
            } else {
                Timestamp exitTime = new Timestamp(System.currentTimeMillis());
                Timestamp entryTime = info.getEntryTime();

                long diffMillis = exitTime.getTime() - entryTime.getTime();
                long totalMinutes = diffMillis / (1000 * 60);
                long hours = totalMinutes / 60;
                long minutes = totalMinutes % 60;

                double parkingFee;
                if ("Ô tô".equalsIgnoreCase(info.getVehicleTypeName())) {
                    parkingFee = Math.ceil(totalMinutes / 60.0) * 20000;
                } else if ("Xe đạp".equalsIgnoreCase(info.getVehicleTypeName())) {
                    parkingFee = Math.ceil(totalMinutes / 60.0) * 3000;
                } else {
                    parkingFee = Math.ceil(totalMinutes / 60.0) * 5000;
                }

                if (parkingFee <= 0) {
                    parkingFee = 5000;
                }

                double lostTicketFee = DEFAULT_LOST_TICKET_FEE;
                double totalAmount = parkingFee + lostTicketFee;

                info.setExitTime(exitTime);
                info.setTotalHours(hours);
                info.setTotalMinutes(minutes);
                info.setParkingFee(parkingFee);
                info.setLostTicketFee(lostTicketFee);
                info.setTotalAmount(totalAmount);

                request.setAttribute("lostTicketInfo", info);
            }

            request.setAttribute("searchedPlate", licensePlate);
        }

        request.getRequestDispatcher("lost-ticket.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String sessionIdRaw = request.getParameter("sessionId");
        String vehicleIdRaw = request.getParameter("vehicleId");
        String ticketIdRaw = request.getParameter("ticketId");
        String totalAmountRaw = request.getParameter("totalAmount");

        try {
            int sessionId = Integer.parseInt(sessionIdRaw);
            int vehicleId = Integer.parseInt(vehicleIdRaw);
            int ticketId = Integer.parseInt(ticketIdRaw);
            double totalAmount = Double.parseDouble(totalAmountRaw);

            ParkingSessionDAO sessionDAO = new ParkingSessionDAO();
            VehicleDAO vehicleDAO = new VehicleDAO();
            TicketDAO ticketDAO = new TicketDAO();

            ParkingSession session = new ParkingSession();
            session.setSessionId(sessionId);
            session.setExitTime(new Timestamp(System.currentTimeMillis()));
            session.setTotalAmount(totalAmount);
            session.setStatus("COMPLETED");

            boolean updatedSession = sessionDAO.updateSessionWhenVehicleOut(session);
            if (!updatedSession) {
                response.sendRedirect("lost-ticket.jsp?error=update_session_failed");
                return;
            }

            boolean updatedVehicle = vehicleDAO.updateStatus(vehicleId, "OUT");
            if (!updatedVehicle) {
                response.sendRedirect("lost-ticket.jsp?error=update_vehicle_failed");
                return;
            }

            boolean updatedTicket = ticketDAO.updateTicketStatus(ticketId, "USED");
            if (!updatedTicket) {
                response.sendRedirect("lost-ticket.jsp?error=update_ticket_failed");
                return;
            }

            response.sendRedirect("lost-ticket.jsp?success=1");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("lost-ticket.jsp?error=system");
        }
    }
}
