package Usernames_DAO.UserQuiz;

import DATABASE_DAO.QuizDatabases.QuizDatabase;
import DATABASE_DAO.QuizDatabases.QuizQuestionDatabase;
import DATABASE_DAO.QuizDatabases.TagsQuizDatabase;
import DATABASE_DAO.UsernameDatabases.RankingsDatabase;
import Questions_DAO.Question;
import Questions_DAO.Quiz;
import Usernames_DAO.models.User;
import Usernames_DAO.models.UserAction;
import Usernames_DAO.models.UserHistory;
import javafx.util.Pair;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class SummaryQuiz {
    private final RankingsDatabase rankingsDatabase;
    private final Quiz quiz;
    private final int quiz_id;
    public SummaryQuiz (int quiz_id) throws SQLException {
        QuizDatabase quizDatabase = new QuizDatabase();
        rankingsDatabase = new RankingsDatabase();
        TagsQuizDatabase tagsQuizDatabase = new TagsQuizDatabase();
        QuizQuestionDatabase quizQuestionDatabase = new QuizQuestionDatabase();

        Quiz quiz1 = quizDatabase.getQuiz(quiz_id);
        ArrayList<String> tags = tagsQuizDatabase.getTags(quiz_id);
        ArrayList<Question> questions = quizQuestionDatabase.getQuestions(quiz_id);
        quiz = new Quiz(quiz1.getQuizName(), quiz1.getCreatorName(), quiz1.getCategory(), quiz1.getDescription(),
                tags, questions, quiz1.isRandom(), quiz1.isOnePage(), quiz1.hasImmediateCorrection(), quiz1.isPracticeMode());

        this.quiz_id = quiz_id;
    }
    public int getQuiz_id() { return quiz_id; }
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
        return toPercent(rankingsDatabase.quizAverageScore(quiz_id), quiz.getMaxScore());
    }
    public Integer getBestScore() throws SQLException {
        return toPercent((double) rankingsDatabase.quizMaxScore(quiz_id), quiz.getMaxScore());
    }
    public ArrayList<Pair<String, Pair<Integer, String>>> getRecentPerfomers() throws SQLException {
        ArrayList<UserAction> ls1 = rankingsDatabase.quizGetPerformers(quiz_id, "Recent");
        return getPairs(ls1);
    }

    public ArrayList<Pair<String, Pair<Integer, String>>> getTopPerfomers(String date) throws SQLException {
        ArrayList<UserAction> ls1 = rankingsDatabase.quizGetPerformers(quiz_id, date);
        return getPairs(ls1);
    }

    public ArrayList<Pair<String, Pair<Integer, String>>> getMyHistory(User u,String order) throws SQLException {
        ArrayList<Pair<String, Pair<Integer, String>>> ans = new ArrayList<>();
        List<UserHistory> list = rankingsDatabase.getUserQuizHistory(u.getUsername(),quiz_id,order);
        for(int i=0;i< list.size();i++){
            String date = list.get(i).getFinishDate().toString();
            int score = toPercent(Double.parseDouble(list.get(i).getScore()),quiz.getMaxScore());
            int time = list.get(i).getAtionTime();
            ans.add(new Pair<>(date,new Pair<>(score,timeToString(time))));
        }
        return ans;
    }

    public ArrayList<Pair<String, Pair<Integer, String>>> getFriendPerformers(User u) throws SQLException {
        List<String> friends = u.getFriends();
        ArrayList<Pair<String, Pair<Integer, String>>> ans = new ArrayList<>();
        for(int i=0;i< friends.size();i++){
            String name = friends.get(i);
            UserHistory uh = rankingsDatabase.FriendMaxScore(name,quiz_id);
            if(uh == null)
                return ans;
            int score = toPercent(Double.parseDouble(uh.getScore()),quiz.getMaxScore());
            String time = timeToString(uh.getAtionTime());
            ans.add(new Pair<>(name,new Pair<>(score,time)));
        }
        return ans;
    }

    private ArrayList<Pair<String, Pair<Integer, String>>> getPairs(ArrayList<UserAction> ls1) {
        ArrayList<Pair<String, Pair<Integer, String>>> ls = new ArrayList<>();
        for (UserAction element : ls1) {
            String username = element.getUsername();
            int score = Integer.parseInt(element.getScore());
            int time = (int)(element.getActionTime().getTime());
            ls.add(new Pair<>(username, new Pair<>(toPercent((double) score, quiz.getMaxScore()), timeToString(time))));
        }
        return ls;

    }

    public static Integer toPercent(double score, int max_score) {
        return Math.toIntExact(Math.round((score / max_score) * 100));
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