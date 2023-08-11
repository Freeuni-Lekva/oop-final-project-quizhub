package Servlets;

import Questions_DAO.Question;
import Usernames_DAO.UserQuiz.UserTakesQuiz;
import Usernames_DAO.manager.accountManager;
import Usernames_DAO.models.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class OnePageServlet extends HttpServlet {
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
        ArrayList<Question> questions = quiz.getQuiz().getQuestionList();
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            readAnswersAndSubmitQuestion(request, response, quiz, question, Integer.toString(i));
            if (i != questions.size() - 1) {
                quiz.getQuiz().goToNextQuestion();
            }
        }
        try {
            quiz.finish();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        request.getRequestDispatcher("ResultPage.jsp").forward(request, response);
    }

    public static void readAnswersAndSubmitQuestion(HttpServletRequest request, HttpServletResponse response,
                                                    UserTakesQuiz quiz, Question question, String s) {
        ArrayList<String> ls = new ArrayList<>();
        if (question.getType() == 1) {
            ls.add(request.getParameter(s + "questionResponseAns"));
        } else if (question.getType() == 2) {
            ArrayList<String> answers = question.getAnswers();
            for (int i = 0; i < answers.size(); i++) {
                ls.add(request.getParameter(s + "fillInBlank" + Integer.toString(i)));
            }
        } else if (question.getType() == 3) {
            ArrayList<String> possibleAnswers = question.getPossibleAnswers();
            if (request.getParameter(s + "multiChoice") != null) {
                ls.add(possibleAnswers.get(Integer.parseInt(request.getParameter(s + "multiChoice"))));
            }
        } else if (question.getType() == 4) {
            ls.add(request.getParameter(s + "pictureResponseAns"));
        } else if (question.getType() == 5) {
            ArrayList<String> answers = question.getAnswers();
            for (int i = 0; i < answers.size(); i++) {
                ls.add(request.getParameter(s + "multiAnswer" + Integer.toString(i)));
            }
        } else if (question.getType() == 6) {
            ArrayList<String> possibleAnswers = question.getPossibleAnswers();
            for (int i = 0; i < possibleAnswers.size(); i++) {
                if (request.getParameter(s + "multiChoiceMultiAns" + Integer.toString(i)) != null) {
                    ls.add(possibleAnswers.get(i));
                }
            }
        } else {
            ArrayList<String> possibleAnswers = question.getPossibleAnswers();
            for (int i = 0; i < possibleAnswers.size() / 2; i++) {
                if (request.getParameter(s + "matching" + Integer.toString(i)).equals("")) {
                    ls.add("$//$");
                } else {
                    ls.add(possibleAnswers.get(i) + "//" +
                            possibleAnswers.get(possibleAnswers.size() / 2 +
                                    Integer.parseInt(request.getParameter(s + "matching" + Integer.toString(i))) - 1));
                }
            }
        }

        quiz.submitQuestion(ls);
    }
}