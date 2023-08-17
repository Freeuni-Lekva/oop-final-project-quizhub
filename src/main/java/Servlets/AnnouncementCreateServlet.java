package Servlets;

import Usernames_DAO.manager.AdminManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class AnnouncementCreateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("annCreated") != null && request.getParameter("saveButton") != null){
            String name = (String) request.getSession().getAttribute("user");
            String subject =  request.getParameter("name");
            String text = request.getParameter("desc");
            try {
                AdminManager admin = new AdminManager(name);
                admin.addAnnouncement(name,subject,text);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            request.getSession().setAttribute("annCreated", null);
        }
        request.getRequestDispatcher("Homepage.jsp").forward(request, response);
    }
}