package Servlets;

import Questions_DAO.Question;
import Questions_DAO.Quiz;
import Usernames_DAO.UserQuiz.SummaryQuiz;
import Usernames_DAO.UserQuiz.UserTakesQuiz;
import Usernames_DAO.manager.accountManager;
import Usernames_DAO.models.User;
import javafx.scene.SubScene;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class MultiPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("button").equals("exitButton")) {
            request.getRequestDispatcher("Homepage.jsp").forward(request, response);
            return;
        }
        UserTakesQuiz quiz = (UserTakesQuiz) request.getSession().getAttribute("quiz");
        if (request.getSession().getAttribute("correction") != null) {
            if (request.getParameter("button").equals("nextButton")) {
                request.getSession().setAttribute("correction", null);
                try {
                    goToNextPage(request, response, quiz);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    goToSubmitPage(request, response, quiz);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            return;
        }

        OnePageServlet.readAnswersAndSubmitQuestion(request, response, quiz, quiz.getQuiz().getCurrentQuestion(), "");

        if (request.getParameter("button").equals("nextButton")) {
            if (!quiz.getQuiz().isPracticeMode() && quiz.getQuiz().hasImmediateCorrection()) {
                request.getSession().setAttribute("quiz", quiz);
                request.getRequestDispatcher("CorrectionPage.jsp").forward(request, response);
            } else {
                try {
                    goToNextPage(request, response, quiz);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            try {
                goToSubmitPage(request, response, quiz);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void goToSubmitPage(HttpServletRequest request, HttpServletResponse response, UserTakesQuiz quiz) throws ServletException, IOException, SQLException {
        ArrayList<String> ls1 = new ArrayList<>();
        while (quiz.getQuiz().hasNextQuestion()) {
            quiz.getQuiz().goToNextQuestion();
            quiz.submitQuestion(ls1);
        }
        request.getSession().setAttribute("correction", null);
        quiz.finish();
        request.getSession().setAttribute("quiz", quiz);
        request.getRequestDispatcher("ResultPage.jsp").forward(request, response);
    }

    private void goToNextPage(HttpServletRequest request, HttpServletResponse response, UserTakesQuiz quiz) throws ServletException, IOException, SQLException {
        if (quiz.getQuiz().hasNextQuestion()) {
            quiz.getQuiz().goToNextQuestion();
            request.getSession().setAttribute("quiz", quiz);
            request.getRequestDispatcher("QuizPage.jsp").forward(request, response);
        } else {
            quiz.finish();
            if (quiz.getQuiz().isPracticeMode()) {
                request.getSession().setAttribute("quiz", null);
                request.getRequestDispatcher("QuizSummary.jsp?quizID="+Integer.toString(quiz.getQuizId())).forward(request, response);
            } else {
                request.getSession().setAttribute("quiz", quiz);
                request.getRequestDispatcher("ResultPage.jsp").forward(request, response);
            }
        }
    }
}