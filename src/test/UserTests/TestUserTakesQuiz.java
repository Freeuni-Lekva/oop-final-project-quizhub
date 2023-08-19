package UserTests;

import Questions_DAO.*;
import Usernames_DAO.UserQuiz.SummaryQuiz;
import Usernames_DAO.UserQuiz.UserCreatesQuiz;
import Usernames_DAO.UserQuiz.UserTakesQuiz;
import Usernames_DAO.models.User;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class TestUserTakesQuiz extends TestCase {
    private UserTakesQuiz userTakesQuiz1;
    private UserTakesQuiz userTakesQuiz2;
    private int quiz_id1;
    private int quiz_id2;
    private Timestamp start_time1;
    private Timestamp start_time2;

    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();
        User user = new User("D.Gelashvili", false);
        start_time1 = new Timestamp(System.currentTimeMillis());
        start_time2 = new Timestamp(System.currentTimeMillis());
        quiz_id1 = CreateFirstQuiz(user);
        quiz_id2 = CreateSecondQuiz(user);
        userTakesQuiz1 = new UserTakesQuiz(user, quiz_id1, start_time1, true);
        userTakesQuiz2 = new UserTakesQuiz(user, quiz_id2, start_time2, false);
    }

    private int CreateFirstQuiz(User user) throws SQLException {
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
    private int CreateSecondQuiz(User user) throws SQLException {
        UserCreatesQuiz userCreatesQuiz = new UserCreatesQuiz(user);
        userCreatesQuiz.setQuizName("Test Quiz 2");
        userCreatesQuiz.setCategory("Geography");
        userCreatesQuiz.setTags("Hard", "", "For You");
        userCreatesQuiz.setDescription("This is a second test quiz for beginners. Enjoy it <3");
        userCreatesQuiz.setOnePage(true);
        userCreatesQuiz.setRandom(false);
        userCreatesQuiz.setImmediateCorrection(false);
        userCreatesQuiz.setPracticeMode(false);

        userCreatesQuiz.addQuestion(new QuestionFillBlank("3 x ___ = 12 and ___ is a capital city of Georgia", "4//Tbilisi",
                true, false));
        userCreatesQuiz.addQuestion(new QuestionMultiChoice("Capital city of Georgia:", "Qutaisi//Tbilisi//Batumi",
                "Tbilisi", true, true));
        userCreatesQuiz.addQuestion(new QuestionPictureResponse("What is it?", "assets/corgi.jpg",
                "corgi", true, false));

        return userCreatesQuiz.FinishAndPublish();
    }

    @Test
    public void testVariables() {
        assertEquals("D.Gelashvili", userTakesQuiz1.getUser().getUsername());
        assertEquals("D.Gelashvili", userTakesQuiz2.getUser().getUsername());
        assertEquals((Integer) quiz_id1, userTakesQuiz1.getQuizId());
        assertEquals((Integer) quiz_id2, userTakesQuiz2.getQuizId());
        assertEquals("Test Quiz", userTakesQuiz1.getQuiz().getQuizName());
        assertEquals("Test Quiz 2", userTakesQuiz2.getQuiz().getQuizName());
        assertEquals("Science", userTakesQuiz1.getQuiz().getCategory());
        assertEquals("Geography", userTakesQuiz2.getQuiz().getCategory());
        assertEquals(2, userTakesQuiz1.getQuiz().getTotalNumberOfQuestions());
        assertEquals(3, userTakesQuiz2.getQuiz().getTotalNumberOfQuestions());
    }

    @Test
    public void testSubmitQuestion() {
        ArrayList<String> userAns1 = new ArrayList<>();
        ArrayList<String> ans1 = new ArrayList<>();
        ArrayList<String> userAns2 = new ArrayList<>();

        userAns1.add("3x4//12"); userAns1.add("7-2//6"); userAns1.add("36/6//5");
        ans1.add("36/6//6"); ans1.add("3x4//12"); ans1.add("7-2//5");
        assertEquals(ans1, userTakesQuiz1.submitQuestion(userAns1));
        assertEquals(1, userTakesQuiz1.getQuiz().getUserScore());
        userTakesQuiz1.getQuiz().goToNextQuestion();

        userAns2.add("4"); userAns2.add("tbilisi");
        assertNull(userTakesQuiz2.submitQuestion(userAns2));
        assertEquals(2, userTakesQuiz2.getQuiz().getUserScore());
        userTakesQuiz2.getQuiz().goToNextQuestion();

        userAns1.clear();
        ans1.clear();
        userAns2.clear();

        userAns1.add("12");
        ans1.add("12");
        assertEquals(ans1, userTakesQuiz1.submitQuestion(userAns1));
        assertEquals(2, userTakesQuiz1.getQuiz().getUserScore());

        userAns2.add("Batumi");
        assertNull(userTakesQuiz2.submitQuestion(userAns2));
        assertEquals(2, userTakesQuiz2.getQuiz().getUserScore());
        userTakesQuiz2.getQuiz().goToNextQuestion();

        userAns1.clear();
        ans1.clear();
        userAns2.clear();

        userAns2.add("Corgi");
        assertNull(userTakesQuiz2.submitQuestion(userAns2));
        assertEquals(3, userTakesQuiz2.getQuiz().getUserScore());
    }

    @Test
    public void testFinish() throws SQLException {
        ArrayList<String> userAns1 = new ArrayList<>();
        ArrayList<String> userAns2 = new ArrayList<>();

        userAns1.add("3x4//12"); userAns1.add("7-2//6"); userAns1.add("36/6//5");
        userTakesQuiz1.submitQuestion(userAns1);
        userTakesQuiz1.getQuiz().goToNextQuestion();
        userAns1.clear();
        userAns1.add("12");
        userTakesQuiz1.submitQuestion(userAns1);
        assertTrue(userTakesQuiz1.getQuiz().hasNextQuestion());
        userTakesQuiz1.getQuiz().goToNextQuestion();
        userAns1.clear();

        userAns1.add("3x4//12"); userAns1.add("7-2//5"); userAns1.add("36/6//6");
        userTakesQuiz1.submitQuestion(userAns1);
        userTakesQuiz1.getQuiz().goToNextQuestion();
        userAns1.clear();
        userAns1.add("12");
        userTakesQuiz1.submitQuestion(userAns1);
        assertTrue(userTakesQuiz1.getQuiz().hasNextQuestion());
        userTakesQuiz1.getQuiz().goToNextQuestion();
        userAns1.clear();

        userAns1.add("3x4//12"); userAns1.add("7-2//5"); userAns1.add("36/6//6");
        userTakesQuiz1.submitQuestion(userAns1);
        userTakesQuiz1.getQuiz().goToNextQuestion();
        userAns1.clear();
        userAns1.add("12");
        userTakesQuiz1.submitQuestion(userAns1);
        assertTrue(userTakesQuiz1.getQuiz().hasNextQuestion());
        userTakesQuiz1.getQuiz().goToNextQuestion();
        userAns1.clear();

        userAns1.add("3x4//12"); userAns1.add("7-2//5"); userAns1.add("36/6//6");
        userTakesQuiz1.submitQuestion(userAns1);
        assertFalse(userTakesQuiz1.getQuiz().hasNextQuestion());

        Timestamp end_time1 = userTakesQuiz1.finish();
        LocalDateTime startDateTime1 = start_time1.toLocalDateTime();
        LocalDateTime endDateTime1 = end_time1.toLocalDateTime();
        Duration duration1 = Duration.between(startDateTime1, endDateTime1);
        assertEquals(SummaryQuiz.timeToString((int)(duration1.toMillis() / 1000)), userTakesQuiz1.getTime());


        userAns2.add("4"); userAns2.add("tbilisi");
        userTakesQuiz2.submitQuestion(userAns2);
        userTakesQuiz2.getQuiz().goToNextQuestion();
        userAns2.clear();
        userAns2.add("Tbilisi");
        userTakesQuiz2.submitQuestion(userAns2);
        userTakesQuiz2.getQuiz().goToNextQuestion();
        userAns2.clear();
        userAns2.add("Corgi");
        userTakesQuiz2.submitQuestion(userAns2);
        assertFalse(userTakesQuiz2.getQuiz().hasNextQuestion());

        Timestamp end_time2 = userTakesQuiz2.finish();
        LocalDateTime startDateTime2 = start_time2.toLocalDateTime();
        LocalDateTime endDateTime2 = end_time2.toLocalDateTime();
        Duration duration2 = Duration.between(startDateTime2, endDateTime2);
        assertEquals(SummaryQuiz.timeToString((int)(duration2.toMillis() / 1000)), userTakesQuiz2.getTime());
    }
}
