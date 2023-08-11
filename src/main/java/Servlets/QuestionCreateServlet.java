package Servlets;

import Questions_DAO.*;
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

public class QuestionCreateServlet extends HttpServlet {
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
        if (request.getParameter("finish-button") != null) {
            UserCreatesQuiz quiz = (UserCreatesQuiz) request.getSession().getAttribute("new quiz");
            try {
                quiz.FinishAndPublish();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            request.getRequestDispatcher("Homepage.jsp").forward(request, response);
            return;
        }
        boolean selector = request.getSession().getAttribute("pageType").equals("selector");
        if (selector) {
            String type = request.getParameter("Qtype");
            switch (type) {
                case "questionResponse":
                    request.getSession().setAttribute("QuestionType", 1);
                    break;
                case "multipleChoice":
                    request.getSession().setAttribute("QuestionType", 3);
                    break;
                case "multiAnswer":
                    request.getSession().setAttribute("QuestionType", 5);
                    break;
                case "fillBlank":
                    request.getSession().setAttribute("QuestionType", 2);
                    break;
                case "pictureResponse":
                    request.getSession().setAttribute("QuestionType", 4);
                    break;
                case "matching":
                    request.getSession().setAttribute("rowNum", -1);
                    request.getSession().setAttribute("QuestionType", 7);
                    break;
                default:
                    request.getSession().setAttribute("QuestionType", 6);
                    break;
            }
            request.getSession().setAttribute("pageType", "question");
            request.getRequestDispatcher("QuestionCreate.jsp").forward(request, response);
        } else {
            if (request.getSession().getAttribute("rowNum") != null && (Integer) (request.getSession().getAttribute("rowNum")) == -1) {
                if (request.getParameter("rowNum").equals("")){
                    request.getSession().setAttribute("rowNum", 4);
                } else {
                    request.getSession().setAttribute("rowNum", Integer.parseInt(request.getParameter("rowNum")));
                }
                request.getRequestDispatcher("QuestionCreate.jsp").forward(request, response);
                return;
            }
            int type = (Integer) request.getSession().getAttribute("QuestionType");
            UserCreatesQuiz quiz = (UserCreatesQuiz) request.getSession().getAttribute("new quiz");
            if (type == 1) {
                quiz.addQuestion(new QuestionResponse(request.getParameter("question"), request.getParameter("answer"),
                        false, request.getParameter("caseSensitive") != null));
            } else if (type == 2) {
                quiz.addQuestion(new QuestionFillBlank(request.getParameter("question"), request.getParameter("answer"),
                        true, request.getParameter("caseSensitive") != null));
            } else if (type == 3) {
                quiz.addQuestion(new QuestionMultiChoice(request.getParameter("question"), request.getParameter("possibleAnswers"),
                        request.getParameter("answer"), request.getParameter("randomize") == null, true));
            } else if (type == 4) {
                quiz.addQuestion(new QuestionPictureResponse(request.getParameter("question"), request.getParameter("url"),
                        request.getParameter("answer"), false, request.getParameter("caseSensitive") != null));
            } else if (type == 5) {
                quiz.addQuestion(new QuestionMultiAnswer(request.getParameter("question"), request.getParameter("answer"),
                        request.getParameter("randomize") == null, request.getParameter("caseSensitive") != null));
            } else if (type == 6) {
                quiz.addQuestion(new QuestionMultiChoiceMultiAnswer(request.getParameter("question"), request.getParameter("possibleAnswers"),
                        request.getParameter("answer"), request.getParameter("randomize") == null, true));
            } else {
                int rowNum = (Integer) request.getSession().getAttribute("rowNum");
                String possibleAnswers = "";
                for (int i = 0; i < rowNum; i++) {
                    possibleAnswers += request.getParameter("left" + Integer.toString(i));
                    possibleAnswers += "//";
                }
                for (int i = 0; i < rowNum - 1; i++) {
                    possibleAnswers += request.getParameter("right" + Integer.toString(i));
                    possibleAnswers += "//";
                }
                possibleAnswers += request.getParameter("right" + Integer.toString(rowNum - 1));
                String answers = "";
                for (int i = 0; i < rowNum - 1; i++) {
                    answers += request.getParameter("left" + Integer.toString(i));
                    answers += "//";
                    answers += request.getParameter("right" + Integer.toString(i));
                    answers += "//";
                }
                answers += request.getParameter("left" + Integer.toString(rowNum - 1));
                answers += "//";
                answers += request.getParameter("right" + Integer.toString(rowNum - 1));
                quiz.addQuestion(new QuestionMatching("Match these two columns:", possibleAnswers, answers,request.getParameter("randomize") == null, false));
            }
            request.getSession().setAttribute("pageType", "selector");
            request.getSession().setAttribute("new quiz", quiz);
            request.getSession().setAttribute("rowNum", null);
            request.getRequestDispatcher("QuestionCreate.jsp").forward(request, response);
        }
    }
}