package Usernames_DAO.manager;

import DATABASE_DAO.QuizDatabases.QuizDatabase;
import DATABASE_DAO.UsernameDatabases.*;
import Usernames_DAO.models.Announcement;
import javafx.util.Pair;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HomepageManager {
    private final RankingsDatabase rating_db;
    private final UserQuizDatabase userQuiz_db;
    private final QuizDatabase quiz_db;
    public HomepageManager() throws SQLException {
        rating_db = new RankingsDatabase();
        userQuiz_db = new UserQuizDatabase();
        quiz_db = new QuizDatabase();
    }

    public ArrayList<Announcement> getAnnouncements() throws SQLException {
        AnnouncementDatabase ann_db = new AnnouncementDatabase();
        return ann_db.getAnnouncements();
    }

    public List<Pair<Pair<Integer, Integer>, String>> getPopularQuizes() throws SQLException {
        List<Pair<Integer, Integer>> ls1 = rating_db.getPopularQuizes();
        List<Pair<Pair<Integer, Integer>, String>> ls = getTriples(ls1);
        List<Integer> allQuizes = quiz_db.getAllQuizes();
        for (Integer id : allQuizes) {
            if (rating_db.quizNumberOfPerformers(id) == 0) {
                ls.add(new Pair<>(new Pair<>(id, 0), quiz_db.getQuizName(id)));
            }
        }
        return ls;
    }

    public List<Pair<Pair<Integer, Integer>, String>> getRecentQuizes() throws SQLException {
        List<Pair<Integer, Integer>> ls1 = userQuiz_db.getRecentCreatedQuizes();
        return getTriples(ls1);
    }

    private List<Pair<Pair<Integer, Integer>, String>> getTriples(List<Pair<Integer, Integer>> ls1) throws SQLException {
        List<Pair<Pair<Integer, Integer>, String>> ls = new ArrayList<>();
        for (Pair<Integer, Integer> pair : ls1) {
            ls.add(new Pair<>(pair, quiz_db.getQuizName(pair.getKey())));
        }
        return ls;
    }
}
