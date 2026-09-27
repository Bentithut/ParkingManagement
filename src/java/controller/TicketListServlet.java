package controller;

import dal.TicketDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Ticket;

@WebServlet("/ticket-list")
public class TicketListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        TicketDAO ticketDAO = new TicketDAO();
        List<Ticket> ticketList = ticketDAO.getAllTickets();

        request.setAttribute("ticketList", ticketList);
        request.getRequestDispatcher("ticket.jsp").forward(request, response);
    }
}