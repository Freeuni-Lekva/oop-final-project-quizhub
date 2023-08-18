package Servlets;

import Usernames_DAO.message.Message;
import Usernames_DAO.message.MessageManager;
import Usernames_DAO.models.profile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ChallengeFriendServlet extends HttpServlet {

    private final int SENTINEL = -1;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sender = (String) request.getSession().getAttribute("user");
        String text = (String) request.getSession().getAttribute("challenge");
        boolean isChallenge = true;
        int quiz_id = (int) request.getSession().getAttribute("challengeID");
        profile profile;
        try {
            profile = new profile(sender);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<String> friends = null;
        try {
            friends = profile.getFriends();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String reciever = "";
        for(String username : friends){
            if(request.getParameter("button" + username)!= null){
                reciever = username;
            }
        }
        try {
            MessageManager manager = new MessageManager();
            manager.sendMessage(new Message(sender, reciever, text, isChallenge, quiz_id));
            profile = new profile(reciever);
            profile.setNotification("challenge", true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        response.sendRedirect("Inbox.jsp?username=" + reciever);
    }

}