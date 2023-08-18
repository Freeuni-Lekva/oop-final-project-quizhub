package Usernames_DAO.models;

import DATABASE_DAO.QuizDatabases.QuizDatabase;
import DATABASE_DAO.QuizDatabases.QuizQuestionDatabase;
import DATABASE_DAO.QuizDatabases.TagsQuizDatabase;
import DATABASE_DAO.UsernameDatabases.FriendsDatabase;
import DATABASE_DAO.UsernameDatabases.RankingsDatabase;
import DATABASE_DAO.UsernameDatabases.UserQuizDatabase;
import DATABASE_DAO.UsernameDatabases.UsersDatabase;
import Questions_DAO.Question;
import Questions_DAO.Quiz;
import javafx.util.Pair;

import javax.persistence.criteria.CriteriaBuilder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class profile {
    private User u;
    private RankingsDatabase rank_db;
    private UserQuizDatabase uq_db;
    private FriendsDatabase f_db;
    private QuizDatabase q_db;

    private String username;
    private UsersDatabase usersDatabase;

    private QuizQuestionDatabase quizQuestionDatabase;
    private TagsQuizDatabase tagsQuizDatabase;
    public profile(String username) throws SQLException {
        u = User.getUser(username);
        this.username = username;
        uq_db = new UserQuizDatabase();
        rank_db = new RankingsDatabase();
        f_db = new FriendsDatabase();
        q_db = new QuizDatabase();
        quizQuestionDatabase = new QuizQuestionDatabase();
        tagsQuizDatabase = new TagsQuizDatabase();
        usersDatabase = new UsersDatabase();
    }
    public int getCompletedQuizes() throws SQLException {
        return rank_db.distinctQuizes(u.getUsername());
    }
    public int getCreatedQuizes() throws SQLException {
        return uq_db.numberOfCreatedQuizes(u.getUsername());
    }
    public int getFriendCount() throws SQLException {
        return f_db.getFriends(u.getUsername()).size();
    }
    public int getHighestScore() throws SQLException {
        Pair<Integer,Integer> p= rank_db.getHighestScore(u.getUsername());
        int maxScore = p.getKey();
        int quiz_id = p.getValue();
        if(maxScore == 0)return 0;
        Quiz quiz1 = q_db.getQuiz(quiz_id);
        ArrayList<String> tags = tagsQuizDatabase.getTags(quiz_id);
        ArrayList<Question> questions = quizQuestionDatabase.getQuestions(quiz_id);
        Quiz quiz = new Quiz(quiz1.getQuizName(), quiz1.getCreatorName(), quiz1.getCategory(), quiz1.getDescription(),
                tags, questions, quiz1.isRandom(), quiz1.isOnePage(), quiz1.hasImmediateCorrection(), quiz1.isPracticeMode());
        return toPercent((double) maxScore,quiz.getMaxScore());
    }
    public static Integer toPercent(double score, int max_score) {
        return Math.toIntExact(Math.round((score / max_score) * 100));
    }
    public String ShortestTime() throws SQLException {
        return timeToString(rank_db.UserMinTime(u.getUsername()));
    }

    public int AwardCount() throws SQLException {
        return u.getAchievement().size();
    }
    public List<Achievement> getAchievment() throws SQLException {
        return u.getAchievement();
    }
    public List<String> getFriends() throws SQLException {
        return u.getFriends();
    }

    public List<Pair<Quiz,Pair<Integer,Integer>>> getUserHistory(String username) throws SQLException {
        List<UserAction> list = rank_db.retrieveUserActions(username);
        List<Pair<Quiz,Pair<Integer,Integer>>> ans = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            UserAction ua = list.get(i);
            int quiz_id = (int) ua.quizId();
            Quiz quiz1 = q_db.getQuiz(quiz_id);
            ArrayList<String> tags = tagsQuizDatabase.getTags(quiz_id);
            ArrayList<Question> questions = quizQuestionDatabase.getQuestions(quiz_id);
            Quiz q = new Quiz(quiz1.getQuizName(), quiz1.getCreatorName(), quiz1.getCategory(), quiz1.getDescription(),
                    tags, questions, quiz1.isRandom(), quiz1.isOnePage(), quiz1.hasImmediateCorrection(), quiz1.isPracticeMode());
            int score;
            Pair<Quiz,Pair<Integer,Integer>> p;
            if(ua.getSource() == "Rankings"){
                score = toPercent((double) Integer.parseInt(ua.getScore()),q.getMaxScore());
                p = new Pair<>(q,new Pair<>((int)ua.quizId(),score));
            }else{
                score = -1;
                p = new Pair<>(q,new Pair<>((int)ua.quizId(),score));
            }
            ans.add(p);
        }
        return ans;
    }

    private String getUsername(){
        return this.username;
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

    public void setNotification(String notification, Boolean flag) throws SQLException {
        usersDatabase.setNotification(getUsername(), notification, flag);
    }

    public Boolean getNotification(String notification) throws SQLException {
        return usersDatabase.getNotification(getUsername(), notification);
    }


}
