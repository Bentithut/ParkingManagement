package controller;

import dal.TicketDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import model.Ticket;

public class TicketServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        TicketDAO dao = new TicketDAO();
        List<Ticket> list = dao.getAllTickets();

        request.setAttribute("tickets", list);
        request.getRequestDispatcher("ticket.jsp").forward(request, response);
    }
}