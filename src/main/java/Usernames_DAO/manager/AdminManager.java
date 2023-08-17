package Usernames_DAO.manager;
import DATABASE_DAO.QuizDatabases.QuestionsDatabase;
import DATABASE_DAO.QuizDatabases.QuizDatabase;
import DATABASE_DAO.QuizDatabases.QuizQuestionDatabase;
import DATABASE_DAO.QuizDatabases.TagsQuizDatabase;
import DATABASE_DAO.UsernameDatabases.*;
import Usernames_DAO.models.Announcement;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminManager{
    private String username;
    private UsersDatabase user_db;
    private AchievementDatabase ach_db;
    private FriendsDatabase fr_db;
    private FriendRequestsDatabase frReq_db;
    private UserQuizDatabase userQuiz_db;
    private QuestionsDatabase Q_db;
    private QuizDatabase quiz_db;
    private QuizQuestionDatabase quizQ_db;
    private TagsQuizDatabase tag_db;
    private RankingsDatabase rank_db;
    private MessageDatabase msg_db;
    private AnnouncementDatabase ann_db;
    public AdminManager(String username) throws SQLException {
        this.username = username;
        this.user_db = new UsersDatabase();
        this.ach_db = new AchievementDatabase();
        this.fr_db = new FriendsDatabase();
        this.frReq_db = new FriendRequestsDatabase();
        this.userQuiz_db = new UserQuizDatabase();
        this.Q_db = new QuestionsDatabase();
        this.quiz_db = new QuizDatabase();
        this.quizQ_db = new QuizQuestionDatabase();
        this.tag_db = new TagsQuizDatabase();
        this.rank_db = new RankingsDatabase();
        this.msg_db = new MessageDatabase();
        this.ann_db = new AnnouncementDatabase();
    }

    public void removeUser(String username) throws SQLException {
        ach_db.delete(username,"Achievements");
        frReq_db.delete(username);
        fr_db.delete(username);
        msg_db.delete(username);
        rank_db.delete(username,"Rankings");
        ArrayList<Integer> deletedQuizId = (ArrayList<Integer>) userQuiz_db.getQuizes(username);
        userQuiz_db.delete(username,"UserQuiz");
        user_db.delete(username,"Users");
        ann_db.delete(username);
        for (int quiz_id : deletedQuizId) {
            removeQuiz(quiz_id);
        }
    }
    public int numOfUsers() throws SQLException {
        return user_db.numberOfUsers();
    }
    public int numOfQuizes() throws SQLException {
        return rank_db.numberOfTakenQuizes();
    }
    public void removeQuiz(int quiz_id) throws SQLException {
        ArrayList<Integer> questions = quizQ_db.getQuestionIds(quiz_id);
        for(Integer id : questions){
            Q_db.delete(id, "QuestionsTable");
        }
        quiz_db.delete(quiz_id,"QuizTable");
        quizQ_db.delete(quiz_id,"Quiz_QuestionTable");
        tag_db.delete(quiz_id,"Tags_QuizTable");
        rank_db.delete(quiz_id);
        userQuiz_db.delete(quiz_id, "UserQuiz");
    }
    public void addAnnouncement(String username,String subject,String text) throws SQLException {
        int id = ann_db.getMinId("Announcements");
        ann_db.add(id,username,subject,text);
    }

    public void clearHistory(int quiz_id){
        rank_db.delete(quiz_id);
    }
}