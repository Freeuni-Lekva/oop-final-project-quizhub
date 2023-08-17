package Usernames_DAO.models;

import DATABASE_DAO.QuizDatabases.QuizDatabase;
import DATABASE_DAO.UsernameDatabases.*;
import Questions_DAO.Quiz;
import javafx.util.Pair;

import javax.persistence.criteria.CriteriaBuilder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class User {
    static String username;
    private FriendsDatabase friends_db;
    private RankingsDatabase rating_db;
    private ReviewDatabase review_db;
    private AchievementDatabase achievement_db;
    private UserQuizDatabase userQuiz_db;
    private QuizDatabase quiz_db;
    private static UsersDatabase user_db;
    private boolean is_admin;
    public User(String username,boolean is_admin) throws SQLException {
        this.username = username;
        rating_db = new RankingsDatabase();
        review_db = new ReviewDatabase();
        achievement_db = new AchievementDatabase();
        userQuiz_db = new UserQuizDatabase();
        quiz_db = new QuizDatabase();
        this.is_admin = is_admin;
        user_db = new UsersDatabase();
        friends_db = new FriendsDatabase();
    }
    public static User getUser(String username) throws SQLException {
        boolean is_admin = user_db.isAdministrator(username);
        return new User(username, is_admin);
    }


    public String getUsername() {
        return username;
    }
    public void demoteFromAdmin() throws SQLException {
        is_admin = false;
        user_db.removeAdministrator(username);
    }
    public List<String> getFriends() throws SQLException {
        List<String> friends = friends_db.getFriends(username);
        return friends;
    }
    public boolean isAdmin(){
        return is_admin;
    }

    public void promoteToAdmin() throws SQLException {
        user_db.promoteAdministrator(username);
        is_admin = true;
    }
    public List<Achievement> getAchievement() throws SQLException {
        List<Integer> list =  achievement_db.getAchievements(username);
        List<Achievement> ans = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            ans.add(new Achievement(username,list.get(i)));
        }
        return ans;
    }

    public List<Pair<Integer, String>> getRecentCreatedQuizes() throws SQLException {
        List<Integer> ls1 = userQuiz_db.getQuizes(username);
        List<Pair<Integer, String>> ls = new ArrayList<>();
        for (Integer integer : ls1) {
            ls.add(new Pair<>(integer, quiz_db.getQuizName(integer)));
        }
        return ls;
    }

    public List<Pair<Integer, String>> getRecentTakenQuizes() throws SQLException {
        List<Integer> ls1 = rating_db.getRecentTakenQuizes(username);
        List<Pair<Integer, String>> ls = new ArrayList<>();
        for (Integer integer : ls1) {
            ls.add(new Pair<>(integer, quiz_db.getQuizName(integer)));
        }
        return ls;
    }
    public ArrayList<Pair<String,Pair<Boolean,Pair<String,Integer>>>> FriendsActivity() throws SQLException {
        ArrayList<Pair<String,Pair<Boolean,Pair<String,Integer>>>> ans = new ArrayList<>();
        List<UserAction>list =  rating_db.retrieveFriendsActions(username);
        for(int i=0;i<list.size();i++){
            UserAction ua = list.get(i);
            String username = ua.getUsername();
            boolean isCreated;
            if(ua.getSource() == "Rankings")isCreated = false;
            else isCreated=true;
            int quiz_id = (int)ua.quizId();
            Quiz quiz1 = quiz_db.getQuiz(quiz_id);
            String quiz_name = quiz1.getQuizName();
            ans.add(new Pair<>(username,new Pair<>(isCreated,new Pair<>(quiz_name,quiz_id))));
        }
        return ans;
    }
}
