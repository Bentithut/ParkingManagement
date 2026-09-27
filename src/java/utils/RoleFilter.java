package utils;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter(urlPatterns = {
    "/admin-dashboard.jsp",
    "/user-list",
    "/user-management.jsp",
    "/user-form.jsp",
    "/add-user",
    "/edit-user",
    "/toggle-user-status",
    "/delete-user",
    "/vehicle-type-management.jsp",
    "/vehicle-type-form.jsp",
    "/vehicle-type-list",
    "/add-vehicle-type",
    "/edit-vehicle-type",
    "/toggle-vehicle-type-status",
    "/delete-vehicle-type",
    "/report",
    "/report.jsp"
})
public class RoleFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("role") == null) {
            res.sendRedirect(req.getContextPath() + "/login.jsp?error=not_logged_in");
            return;
        }

        String role = String.valueOf(session.getAttribute("role"));

        if ("ADMIN".equalsIgnoreCase(role)) {
            chain.doFilter(request, response);
        } else {
            res.sendRedirect(req.getContextPath() + "/access-denied.jsp");
        }
    }

    @Override
    public void destroy() {
    }
}
