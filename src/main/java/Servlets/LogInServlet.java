package Servlets;

import Usernames_DAO.manager.HomepageManager;
import Usernames_DAO.manager.accountManager;
import Usernames_DAO.models.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class LogInServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        accountManager AccountManager = (accountManager) request.getServletContext().getAttribute("accManager");
        boolean loginIsSuccessful = false;

        try {
            if (!password.equals("") && AccountManager.matchesPassword(username, password)){
                loginIsSuccessful = true;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (loginIsSuccessful) {
            User user = null;
            try {
                user = new User(username, false);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            request.getSession().setAttribute("user", user);
            try {
                request.getSession().setAttribute("homepage", new HomepageManager());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            request.getRequestDispatcher("Homepage.jsp").forward(request, response);
        }
        else
            request.getRequestDispatcher("LogInTryAgain.jsp").forward(request, response);
    }
}