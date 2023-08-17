package Servlets;

import Usernames_DAO.manager.AdminManager;
import Usernames_DAO.manager.FriendshipManager;
import Usernames_DAO.models.User;
import Usernames_DAO.models.profile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class ProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        FriendshipManager fr = null;
        try {
            fr = new FriendshipManager();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String user = (String) request.getSession().getAttribute("user");
        String page = (String) request.getSession().getAttribute("profilePage");
        if(request.getParameter("logOutButton") != null){
            request.getSession().setAttribute("user",null);
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }
        if(request.getParameter("removeUser") != null) {
            try {
                AdminManager admin = new AdminManager(user);
                admin.removeUser(page);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            request.getRequestDispatcher("Homepage.jsp").forward(request, response);
            return;
        }
        if(request.getParameter("cancelButton")!= null){
            try {
                fr.cancelFriendRequest(user,page);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else if(request.getParameter("accFriend")!= null){
            try {
                fr.confirmFriendshipRequest(user,page);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else if(request.getParameter("rejFriend")!=null){
            try {
                fr.rejectFriendshipRequest(user,page);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else if(request.getParameter("addFriend")!= null){
            try {
                profile profile = new profile(page);
                profile.setNotification("request", true);
                fr.addFriendshipRequest(user,page);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else if(request.getParameter("removeButton")!=null){
            try {
                fr.removeFriend(user,page);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else if(request.getParameter("addAsAdmin") !=null) {
            try {
                User.getUser(page).promoteToAdmin();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else{
                try {
                    User.getUser(page).demoteFromAdmin();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        request.getRequestDispatcher("Profile.jsp?username=" + page).forward(request, response);
    }
}