package Servlets;

import Usernames_DAO.manager.accountManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

@WebListener
public class AccManagerListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        accountManager AccountManager = null;
        try {
            AccountManager = new accountManager();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        servletContextEvent.getServletContext().setAttribute("accManager", AccountManager);
    }
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
