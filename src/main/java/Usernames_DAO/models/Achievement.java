package Usernames_DAO.models;

import DATABASE_DAO.QuizDatabases.QuizDatabase;
import DATABASE_DAO.UsernameDatabases.AchievementDatabase;
import DATABASE_DAO.UsernameDatabases.RankingsDatabase;
import DATABASE_DAO.UsernameDatabases.UserQuizDatabase;

import java.sql.SQLException;

public class Achievement {
    public static final int AMATEUR_AUTHOR = 1;
    public static final int PROLIFIC_AUTHOR = 2;
    public static final int PRODIGIOUS_AUTHOR = 3;
    public static final int QUIZ_MACHINE = 4;
    public static final int GREATEST = 5;
    public static final int PRACTICE_MODE = 6;
    private final AchievementDatabase Achievement_db;
    private final UserQuizDatabase quiz_db;
    private final String username;
    private final int achievment_id;
    private final RankingsDatabase rating_db;
    public void alertCreateQuiz(String username) throws SQLException {
        int numQuizes = quiz_db.numberOfCreatedQuizes(username);
        switch(numQuizes){
            case 1: Achievement_db.add(username,AMATEUR_AUTHOR);
                    break;
            case 5: Achievement_db.add(username,PROLIFIC_AUTHOR);
                    break;
            case 10: Achievement_db.add(username,PRODIGIOUS_AUTHOR);
                    break;
            default: break;
        }
    }
    public Achievement(String username,int achievment_id) throws SQLException {
        quiz_db = new UserQuizDatabase();
        Achievement_db = new AchievementDatabase();
        rating_db = new RankingsDatabase();
        this.username = username;
        this.achievment_id = achievment_id;
    }
    public void alertPractice(String username) throws SQLException {
        Achievement_db.add(username,PRACTICE_MODE);
    }

    public void alertQuizTaken(String username) throws SQLException {
        int numQuizTaken = rating_db.distinctQuizes(username);
        if(numQuizTaken == 10) Achievement_db.add(username,QUIZ_MACHINE);
    }

    public void alertHighestscore(String username) throws SQLException {
        Achievement_db.add(username,GREATEST);
    }

    public int getAchievment_id() {
        return achievment_id;
    }
}
