package Servlets;

import Usernames_DAO.UserQuiz.UserCreatesQuiz;
import Usernames_DAO.manager.accountManager;
import Usernames_DAO.models.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class QuizCreateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("exit-button") != null) {
            request.getRequestDispatcher("Homepage.jsp").forward(request, response);
            return;
        }

        UserCreatesQuiz newQuiz = (UserCreatesQuiz)  request.getSession().getAttribute("new quiz");
        if (newQuiz == null) {
            User user = (User) request.getSession().getAttribute("user");
            try {
                newQuiz = new UserCreatesQuiz(user);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            request.getSession().setAttribute("new quiz", newQuiz);
        }

        newQuiz.setOnePage(request.getParameter("page").equals("SinglePage"));
        newQuiz.setRandom(request.getParameter("ordering").equals("Randomized"));
        if (!newQuiz.isOnePage()) {
            newQuiz.setImmediateCorrection(request.getParameter("feedback") != null);
            newQuiz.setPracticeMode(request.getParameter("PracticeMode") != null);
        }
        newQuiz.setQuizName(request.getParameter("name"));
        newQuiz.setCategory(request.getParameter("category"));
        newQuiz.setTags(request.getParameter("tag1"), request.getParameter("tag2"), request.getParameter("tag3"));
        newQuiz.setDescription(request.getParameter("description"));

        request.getSession().setAttribute("new quiz", newQuiz);
        request.getSession().setAttribute("pageType", "selector");
        request.getRequestDispatcher("QuestionCreate.jsp").forward(request, response);
    }
}