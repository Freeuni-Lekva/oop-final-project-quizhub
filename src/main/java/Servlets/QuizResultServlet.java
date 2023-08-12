package Servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class QuizResultServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(request.getParameter("TopOrFriends") != null){
            if (request.getParameter("TopOrFriends").equals("top")) {
                request.getSession().setAttribute("TopFriendsSwitch", "friends");
            } else {
                request.getSession().setAttribute("TopFriendsSwitch", "top");
            }
        }else{
            if(request.getParameter("ordering").equals("date")){
                request.getSession().setAttribute("order", "date");
            } else if (request.getParameter("ordering").equals("score")){
                request.getSession().setAttribute("order", "score");
            } else {
                request.getSession().setAttribute("order", "time");
            }
        }
        request.getRequestDispatcher("ResultPage.jsp").forward(request, response);
    }
}