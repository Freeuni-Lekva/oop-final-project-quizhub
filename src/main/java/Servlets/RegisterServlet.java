package Servlets;

import Usernames_DAO.manager.accountManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class RegisterServlet extends HttpServlet {
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
        boolean registerIsSuccessful = false;

        try {
            if (!password.equals("") && !AccountManager.ContainsKey(username)) {
                registerIsSuccessful = true;
                AccountManager.addAcc(username, password);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (registerIsSuccessful)
            request.getRequestDispatcher("index.jsp").forward(request, response);
        else
            request.getRequestDispatcher("RegisterTryAgain.jsp").forward(request, response);
    }
}