package Servlets;

import Usernames_DAO.manager.AdminManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class QuizSummaryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(request.getParameter("removeQuiz") != null){
            try {
                AdminManager admin = new AdminManager((String) request.getSession().getAttribute("user"));
                admin.removeQuiz((int)request.getSession().getAttribute("quiz_idForAdmin"));
                request.getSession().setAttribute("quiz_idForAdmin", null);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            request.getRequestDispatcher("Homepage.jsp").forward(request, response);
            return;
        }
        if(request.getParameter("clearHistory") != null){
            try {
                AdminManager admin = new AdminManager((String) request.getSession().getAttribute("user"));
                admin.clearHistory(((int)request.getSession().getAttribute("quiz_idForAdmin")));
                request.getSession().setAttribute("quiz_idForAdmin", null);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else if(request.getParameter("AllTimeOrLastDay") != null){
            if (request.getParameter("AllTimeOrLastDay").equals("AllTime")) {
                request.getSession().setAttribute("LastDaySwitch", "Last Day");
            } else {
                request.getSession().setAttribute("LastDaySwitch", "AllTime");
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
        request.getRequestDispatcher("QuizSummary.jsp").forward(request, response);
    }
}