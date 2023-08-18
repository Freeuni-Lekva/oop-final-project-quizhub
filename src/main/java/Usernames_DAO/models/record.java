package Usernames_DAO.models;

import DATABASE_DAO.QuizDatabases.QuizDatabase;
import DATABASE_DAO.UsernameDatabases.RankingsDatabase;

import java.sql.SQLException;

public class record {
    public String username;
    public int quiz_id;
    public int rating;
    public String quizName;
    private QuizDatabase quiz_db;
    private RankingsDatabase rating_db;
    public record(String username,int quiz_id) throws SQLException {
        this.quiz_db = new QuizDatabase();
        this.rating_db = new RankingsDatabase();
        this.username = username;
        this.quiz_id = quiz_id;
        this.rating = getRating(username,quiz_id);
        quizName = getQuizName(quiz_id);
    }

    private int getRating(String username, int quizId) throws SQLException {
        return rating_db.UserMaxScore(username,quizId);
    }

    private String  getQuizName(int quiz_id) throws SQLException {
        return quiz_db.getQuizName(quiz_id);
    }
}
