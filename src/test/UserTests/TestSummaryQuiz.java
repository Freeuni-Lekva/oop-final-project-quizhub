package UserTests;

import Questions_DAO.*;
import Usernames_DAO.UserQuiz.SummaryQuiz;
import Usernames_DAO.UserQuiz.UserCreatesQuiz;
import Usernames_DAO.UserQuiz.UserTakesQuiz;
import Usernames_DAO.models.User;
import Usernames_DAO.models.UserAction;
import javafx.util.Pair;
import junit.framework.TestCase;
import junit.framework.TestResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class TestSummaryQuiz extends TestCase {
    private SummaryQuiz summaryQuiz;
    private int quiz_id1;
    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();
        User user = new User("D.Gelashvili", false);
        quiz_id1 = CreateQuiz(user);
        summaryQuiz = new SummaryQuiz(quiz_id1);
    }

    private int CreateQuiz(User user) throws SQLException {
        UserCreatesQuiz userCreatesQuiz = new UserCreatesQuiz(user);
        userCreatesQuiz.setQuizName("Test Quiz");
        userCreatesQuiz.setCategory("Science");
        userCreatesQuiz.setTags("Easy", "Short", "");
        userCreatesQuiz.setDescription("This is a test quiz for beginners. Enjoy it <3");
        userCreatesQuiz.setOnePage(false);
        userCreatesQuiz.setRandom(false);
        userCreatesQuiz.setImmediateCorrection(true);
        userCreatesQuiz.setPracticeMode(true);

        userCreatesQuiz.addQuestion(new QuestionMatching("Match these two columns:", "3x4//7-2//36/6//6//5//12",
                "3x4//12//7-2//5//36/6//6", false, false));
        userCreatesQuiz.addQuestion(new QuestionResponse("What is 3 x 4?", "12", false, true));

        return userCreatesQuiz.FinishAndPublish();
    }

    @Test
    public void testVariables() {
        assertEquals(quiz_id1, summaryQuiz.getQuiz_id());
        assertEquals("Test Quiz", summaryQuiz.getQuizName());
        assertEquals("D.Gelashvili", summaryQuiz.getCreatorName());
        assertEquals("Science", summaryQuiz.getCategory());
        assertEquals("This is a test quiz for beginners. Enjoy it <3", summaryQuiz.getDescription());
        assertTrue(summaryQuiz.isPracticeMode());

        ArrayList<String> ls = new ArrayList<>();
        ls.add("Easy"); ls.add("Short");
        assertEquals(ls, summaryQuiz.getTags());
    }

    @Test
    public void testStatistics() throws SQLException {
        assertEquals((Integer) 0, summaryQuiz.getNumberOfPerformers());
        assertEquals("0s", summaryQuiz.getAverageTime());
        assertEquals("0s", summaryQuiz.getBestTime());
        assertEquals((Integer) 0, summaryQuiz.getAverageScore());
        assertEquals((Integer) 0, summaryQuiz.getBestScore());

        assertEquals(0, summaryQuiz.getRecentPerfomers().size());
        assertEquals(0, summaryQuiz.getTopPerfomers("All Time").size());
        assertEquals(0, summaryQuiz.getMyHistory(new User("Deme", false), "Score").size());
        assertEquals(0, summaryQuiz.getFriendPerformers(new User("Deme", false)).size());

        ArrayList<UserAction> ls = new ArrayList<>();
        ls.add(new UserAction("Take", 3, "Deme", new Timestamp(30), "3"));
        ArrayList<Pair<String, Pair<Integer, String>>> ls1 =  new ArrayList<>();
        ls1.add(new Pair<>("Deme", new Pair<>(75, "30s")));
        assertEquals(ls1, summaryQuiz.getPairs(ls));
    }

    @Test
    public void testTimeToString() {
        assertEquals("1d 3h 2m", SummaryQuiz.timeToString(97320));
        assertEquals("1d 0s", SummaryQuiz.timeToString(86400));
        assertEquals("4h 3m 0s", SummaryQuiz.timeToString(14580));
        assertEquals("1d 23h", SummaryQuiz.timeToString(169200));
    }
}
