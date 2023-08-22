package DatabaseTests;

import DATABASE_DAO.UsernameDatabases.FriendsDatabase;
import DATABASE_DAO.UsernameDatabases.RankingsDatabase;
import DATABASE_DAO.UsernameDatabases.UserQuizDatabase;
import Usernames_DAO.models.UserAction;
import Usernames_DAO.models.UserHistory;
import javafx.util.Pair;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RankingsDatabaseTest extends TestCase {

    private RankingsDatabase database;

    @BeforeEach
    public void setUp() throws SQLException {
        database = new RankingsDatabase();
        database.clearAllTables();
        init();
    }

    private void init() throws SQLException {
        database.add(database.getMinId(RankingsDatabase.tablename), "luka", 1, 15, database.getCurrentDate(), database.getCurrentDate());
        database.add(database.getMinId(RankingsDatabase.tablename), "luka", 1, 20, database.getCurrentDate(), database.getCurrentDate());
        database.add(database.getMinId(RankingsDatabase.tablename), "luka", 1, 25, database.getCurrentDate(), database.getCurrentDate());
        database.add(database.getMinId(RankingsDatabase.tablename), "luka", 1, 30, database.getCurrentDate(), database.getCurrentDate());
        database.add(database.getMinId(RankingsDatabase.tablename), "demetre", 1, 15, database.getCurrentDate(), database.getCurrentDate());
        database.add(database.getMinId(RankingsDatabase.tablename), "demetre", 1, 1, database.getCurrentDate(), database.getCurrentDate());
        database.add(database.getMinId(RankingsDatabase.tablename), "demetre", 1, 3, database.getCurrentDate(), database.getCurrentDate());
        database.add(database.getMinId(RankingsDatabase.tablename), "demetre", 1, 150, database.getCurrentDate(), database.getCurrentDate());
        database.add(database.getMinId(RankingsDatabase.tablename), "ana", 1, 151, database.getCurrentDate(), database.getCurrentDate());
        database.add(database.getMinId(RankingsDatabase.tablename), "ana", 1, 5, database.getCurrentDate(), database.getCurrentDate());
        database.add(database.getMinId(RankingsDatabase.tablename), "ana", 1, 1, database.getCurrentDate(), database.getCurrentDate());
        database.add(database.getMinId(RankingsDatabase.tablename), "ana", 1, 3, database.getCurrentDate(), database.getCurrentDate());
        database.add(database.getMinId(RankingsDatabase.tablename), "ana", 1, 11, database.getCurrentDate(), database.getCurrentDate());
        database.add(database.getMinId(RankingsDatabase.tablename), "luka", 2, 151, database.getCurrentDate(), database.getCurrentDate());
        database.add(database.getMinId(RankingsDatabase.tablename), "demetre", 2, 153, database.getCurrentDate(), database.getCurrentDate());
        database.add(database.getMinId(RankingsDatabase.tablename), "luka", 2, 159, database.getCurrentDate(), database.getCurrentDate());
        database.add(database.getMinId(RankingsDatabase.tablename), "luka", 2, 160, database.getCurrentDate(), database.getCurrentDate());
        database.add(database.getMinId(RankingsDatabase.tablename), "demetre", 1, 150, database.getCurrentDate(), database.getCurrentDate());
    }

    @Test
    public void testCase1() throws SQLException {
        List<Integer> list = database.getRecords("luka");
        assertEquals(list, Arrays.asList(1, 1, 1, 1, 2, 2, 2));
        list = database.getRecords("demetre");
        assertEquals(list, Arrays.asList(1, 1, 1, 1, 2, 1));
        list = database.getRecords("ana");
        assertEquals(list, Arrays.asList(1, 1, 1, 1, 1));
        list = database.getRecentTakenQuizes("luka");
        Collections.sort(list);
        assertEquals(list, Arrays.asList(1, 2));
        list = database.getRecentTakenQuizes("demetre");
        Collections.sort(list);
        assertEquals(list, Arrays.asList(1, 2));
        list = database.getRecentTakenQuizes("ana");
        Collections.sort(list);
        assertEquals(list, Collections.singletonList(1));
        assertEquals((int) database.distinctQuizes("luka"), 2);
        assertEquals((int) database.distinctQuizes("demetre"), 2);
        assertEquals((int) database.distinctQuizes("ana"), 1);
        assertEquals((int) database.UserMaxScore("luka", 1), 30);
        assertEquals((int) database.UserMaxScore("luka", 2), 160);
        assertEquals((int) database.UserMaxScore("demetre", 1), 150);
        assertEquals((int) database.UserMaxScore("demetre", 2), 153);
        assertEquals((int) database.UserMaxScore("ana", 1), 151);
        assertEquals((int) database.quizMaxScore(1), 151);
        assertEquals((int) database.quizMaxScore(2), 160);
        assertEquals((int) database.quizMinTime(1), 0);
        assertEquals((int) database.quizMinTime(2), 0);
        assertEquals(database.quizAverageScore(1), 41.4286);
        assertEquals(database.quizAverageScore(2), 155.75);
        assertEquals((int) database.quizMinTime(1), 0);
        assertEquals((int) database.quizMinTime(2), 0);
        assertEquals((int) database.quizAverageTime(1), 0);
        assertEquals((int) database.quizAverageTime(2), 0);
    }

    @Test
    public void testCase2() throws SQLException {
        FriendsDatabase friendsDatabase = new FriendsDatabase();
        friendsDatabase.add("luka", "demetre");
        friendsDatabase.add("luka", "ana");
        UserHistory history = database.FriendMaxScore("luka", 1);
        int tmp = Integer.parseInt(history.getScore());
        assertEquals(tmp, 30);
        history = database.FriendMaxScore("demetre", 1);
        tmp = Integer.parseInt(history.getScore());
        assertEquals(tmp, 150);

        List<UserAction> friendsActions = database.retrieveFriendsActions("luka");
        assertEquals(friendsActions.get(0).getScore(), "15");


    }

    @Test
    public void testCase3() throws SQLException {
        UserQuizDatabase userQuizDatabase = new UserQuizDatabase();
        userQuizDatabase.add("luka", 3, database.getCurrentDate());
        List<UserAction> list = database.retrieveUserActions("luka");
        assertEquals(list.size(), 8);
        List<Pair<Integer, Integer>> popularQuizes = database.getPopularQuizes();
        assertEquals((int)popularQuizes.get(0).getKey() , 1);
        assertEquals((int)popularQuizes.get(0).getValue() , 3);
        assertEquals((int)popularQuizes.get(1).getKey() , 2);
        assertEquals((int)popularQuizes.get(1).getValue() , 2);

        list = database.quizGetPerformers(1, "Recent");
        assertEquals(list.size(), 3);

        list = database.quizGetPerformers(1, "All Time");
        assertEquals(list.get(0).getUsername(), "ana");
        list = database.quizGetPerformers(1, "");
        assertEquals(list.get(0).getUsername(), "ana");

        List<UserHistory> result = database.getUserQuizHistory("luka", 1, "score");
        assertEquals(result.get(0).getScore(), "30");

        result = database.getUserQuizHistory("luka", 1, "time");
        assertEquals(result.get(0).getScore(), "30");

        result = database.getUserQuizHistory("luka", 1, "date");
        assertEquals(result.get(0).getScore(), "30");

        Pair<Integer, Integer> maxScore = database.getHighestScore("luka");
        assertEquals((int)maxScore.getKey(), 30);
        assertEquals((int)maxScore.getValue(), 1);

        assertEquals((int)database.UserMinTime("luka"), 0);
        assertEquals((int)database.numberOfTakenQuizes(), 18);

        database.delete(1);
        assertTrue(database.quizGetPerformers(1, "Recent").isEmpty());

    }

}