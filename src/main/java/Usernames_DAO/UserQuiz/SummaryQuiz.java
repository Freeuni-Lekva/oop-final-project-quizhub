package Usernames_DAO.UserQuiz;

import DATABASE_DAO.QuizDatabases.QuizDatabase;
import DATABASE_DAO.QuizDatabases.QuizQuestionDatabase;
import DATABASE_DAO.QuizDatabases.TagsQuizDatabase;
import DATABASE_DAO.UsernameDatabases.RankingsDatabase;
import Questions_DAO.Question;
import Questions_DAO.Quiz;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class SummaryQuiz {
    private QuizDatabase quizDatabase;
    private RankingsDatabase rankingsDatabase;
    private TagsQuizDatabase tagsQuizDatabase;
    private QuizQuestionDatabase quizQuestionDatabase;
    private Quiz quiz;
    private int quiz_id;
    public SummaryQuiz (int quiz_id) throws SQLException {
        quizDatabase = new QuizDatabase();
        rankingsDatabase = new RankingsDatabase();
        tagsQuizDatabase = new TagsQuizDatabase();
        quizQuestionDatabase = new QuizQuestionDatabase();

        Quiz quiz1 = quizDatabase.getQuiz(quiz_id);
        ArrayList<String> tags = tagsQuizDatabase.getTags(quiz_id);
        ArrayList<Question> questions = quizQuestionDatabase.getQuestions(quiz_id);
        quiz = new Quiz(quiz1.getQuizName(), quiz1.getCreatorName(), quiz1.getCategory(), quiz1.getDescription(),
                tags, questions, quiz1.isRandom(), quiz1.isOnePage(), quiz1.hasImmediateCorrection(), quiz1.isPracticeMode());

        this.quiz_id = quiz_id;
    }
    public String getQuizName() { return quiz.getQuizName(); }
    public String getCreatorName() { return quiz.getCreatorName(); }
    public String getCategory() { return quiz.getCategory(); }
    public String getDescription() { return quiz.getDescription(); }
    public ArrayList<String> getTags() {
        return quiz.getTags();
    }
    public Boolean isPracticeMode() { return quiz.isPracticeMode(); }
    public Integer getNumberOfPerformers() throws SQLException {
        return rankingsDatabase.quizNumberOfPerformers(quiz_id);
    }
    public String getAverageTime() throws SQLException {
        return timeToString(rankingsDatabase.quizAverageTime(quiz_id));
    }
    public String getBestTime() throws SQLException {
        return timeToString(rankingsDatabase.quizMinTime(quiz_id));
    }
    public Integer getAverageScore() throws SQLException {
        return Math.toIntExact(Math.round((rankingsDatabase.quizAverageScore(quiz_id) / quiz.getMaxScore()) * 100));
    }
    public Integer getBestScore() throws SQLException {
        return Math.toIntExact(Math.round(((double) rankingsDatabase.quizMaxScore(quiz_id) / quiz.getMaxScore()) * 100));
    }

    public static String timeToString(int t) {
        String time = "";

        if (t / 86400 > 0) {
            time += Integer.toString(t / 86400);
            time += "d";
        }
        t -= (t / 86400) * 86400;

        if (t / 3600 > 0) {
            if (time.length() > 0) {
                time += " ";
            }
            time += Integer.toString(t / 3600);
            time += "h";
            if (time.length() > 5) {
                return time;
            }
        }
        t -= (t / 3600) * 3600;

        if (t / 60 > 0) {
            if (time.length() > 0) {
                time += " ";
            }
            time += Integer.toString(t / 60);
            time += "m";
            if (time.length() > 5) {
                return time;
            }
        }
        t -= (t / 60) * 60;

        if (time.length() > 0) {
            time += " ";
        }
        time += Integer.toString(t);
        time += "s";
        if (time.length() > 5) {
            return time;
        }

        return time;
    }
}
