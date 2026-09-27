package controller;

import dal.ReportDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ReportData;

@WebServlet("/report")
public class ReportServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ReportDAO reportDAO = new ReportDAO();
        ReportData reportData = reportDAO.getDashboardReport();

        request.setAttribute("reportData", reportData);
        request.getRequestDispatcher("report.jsp").forward(request, response);
    }
}
