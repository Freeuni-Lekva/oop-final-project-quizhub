package Servlets;

import Usernames_DAO.manager.AdminManager;
import Usernames_DAO.manager.FriendshipManager;
import Usernames_DAO.message.MessageManager;
import Usernames_DAO.models.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class FriendRequestsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        FriendshipManager manager = null;
        try {
            manager = new FriendshipManager();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String user = (String) request.getSession().getAttribute("user");
        MessageManager mm = null;
        try {
            mm = new MessageManager();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ArrayList<String> requests = null;
        try {
            requests = mm.getFriendRequests(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for(String username : requests){
            if(request.getParameter("accButton" + username)!= null){
                try {
                    manager.confirmFriendshipRequest(user, username);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }else if(request.getParameter("rejButton" + username)!=null){
                try {
                    manager.rejectFriendshipRequest(user, username);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        request.getRequestDispatcher("Inbox.jsp").forward(request, response);
    }
}