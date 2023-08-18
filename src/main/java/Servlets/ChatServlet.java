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

public class ChatServlet extends HttpServlet {

    private final int SENTINEL = -1;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sender = (String) request.getSession().getAttribute("user");
        String reciever = (String) request.getSession().getAttribute("currUser");
        String text = request.getParameter("sms");
        boolean isChallenge = false;
        int quiz_id = SENTINEL;

        try {
            MessageManager manager = new MessageManager();
            manager.sendMessage(new Message(sender, reciever, text, isChallenge, quiz_id));
            profile profile = new profile(reciever);
            profile.setNotification("message", true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        response.sendRedirect("Inbox.jsp?username=" + reciever);
    }

}