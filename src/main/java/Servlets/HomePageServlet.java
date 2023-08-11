package Servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HomePageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("recentSwitch") == null) {
            request.getSession().setAttribute("recentSwitch", "Recent");
        } else {
            request.getSession().setAttribute("recentSwitch", null);
        }
        request.getRequestDispatcher("Homepage.jsp").forward(request, response);
    }
}