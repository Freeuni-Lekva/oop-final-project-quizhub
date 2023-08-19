package UserTests;

import DATABASE_DAO.QuizDatabases.QuestionsDatabase;
import DATABASE_DAO.QuizDatabases.QuizDatabase;
import DATABASE_DAO.QuizDatabases.QuizQuestionDatabase;
import DATABASE_DAO.QuizDatabases.TagsQuizDatabase;
import Questions_DAO.*;
import Usernames_DAO.UserQuiz.UserCreatesQuiz;
import Usernames_DAO.models.User;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class TestUserCreatesQuiz extends TestCase {
    private UserCreatesQuiz userCreatesQuiz;
    private UserCreatesQuiz userCreatesQuiz1;
    private QuizDatabase quizDatabase;
    private QuizQuestionDatabase quizQuestionDatabase;
    private TagsQuizDatabase tagsQuizDatabase;

    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();
        User user = new User("D.Gelashvili", false);
        userCreatesQuiz = new UserCreatesQuiz(user);
        userCreatesQuiz1 = new UserCreatesQuiz(user);
        quizDatabase = new QuizDatabase();
        quizQuestionDatabase = new QuizQuestionDatabase();
        tagsQuizDatabase = new TagsQuizDatabase();
    }

    @Test
    public void testPage1() {
        userCreatesQuiz.setQuizName("Test Quiz");
        userCreatesQuiz.setCategory("Science");
        userCreatesQuiz.setTags("Easy", "Short", "");
        userCreatesQuiz.setDescription("This is a test quiz for beginners. Enjoy it <3");
        userCreatesQuiz.setOnePage(true);
        userCreatesQuiz.setRandom(true);
        userCreatesQuiz.setImmediateCorrection(true);
        userCreatesQuiz.setPracticeMode(false);

        assertEquals("D.Gelashvili", userCreatesQuiz.getCreator_name());
        assertEquals("Test Quiz", userCreatesQuiz.getQuizName());
        assertEquals("Science", userCreatesQuiz.getCategory());
        assertEquals(Arrays.asList("Easy", "Short"), userCreatesQuiz.getTags());
        assertEquals("This is a test quiz for beginners. Enjoy it <3", userCreatesQuiz.getDescription());
        assertTrue(userCreatesQuiz.isOnePage());
        assertTrue(userCreatesQuiz.isRandom());
        assertTrue(userCreatesQuiz.hasImmediateCorrection());
        assertFalse(userCreatesQuiz.isPracticeMode());
    }

    @Test
    public void testAddQuestion() {
        userCreatesQuiz.addQuestion(new QuestionMatching("Match these two columns:", "3x4//7-2//36/6//6//5//12",
                "3x4//12//7-2//5//36/6//6", false, false));
        assertEquals(1, userCreatesQuiz.getNumberOfQuestions());
        assertEquals(3, userCreatesQuiz.getMaxScore());

        userCreatesQuiz.addQuestion(new QuestionMultiChoiceMultiAnswer("x^2 - 5x + 6 = 0. x = ?",
                "2//3//1//6", "3//2", false, true));
        assertEquals(2, userCreatesQuiz.getNumberOfQuestions());
        assertEquals(5, userCreatesQuiz.getMaxScore());

        userCreatesQuiz.addQuestion(new QuestionResponse("What is 3 x 4?", "12", false, true));
        assertEquals(3, userCreatesQuiz.getNumberOfQuestions());
        assertEquals(6, userCreatesQuiz.getMaxScore());
    }

    @Test
    public void testFinishAndPublish1() throws SQLException {
        userCreatesQuiz.setQuizName("Test Quiz");
        userCreatesQuiz.setCategory("Science");
        userCreatesQuiz.addQuestion(new QuestionMatching("Match these two columns:", "3x4//7-2//36/6//6//5//12",
                "3x4//12//7-2//5//36/6//6", false, false));
        userCreatesQuiz.setTags("Easy", "Short", "");
        userCreatesQuiz.setDescription("This is a test quiz for beginners. Enjoy it <3");
        userCreatesQuiz.setOnePage(false);
        userCreatesQuiz.addQuestion(new QuestionMultiChoiceMultiAnswer("x^2 - 5x + 6 = 0. x = ?",
                "2//3//1//6", "3//2", false, true));
        userCreatesQuiz.setRandom(false);
        userCreatesQuiz.setImmediateCorrection(true);
        userCreatesQuiz.setPracticeMode(true);
        userCreatesQuiz.addQuestion(new QuestionResponse("What is 3 x 4?", "12", false, true));

        int quiz_id = userCreatesQuiz.FinishAndPublish();

        Quiz quiz1 = quizDatabase.getQuiz(quiz_id);
        ArrayList<String> tags = tagsQuizDatabase.getTags(quiz_id);
        ArrayList<Question> questions = quizQuestionDatabase.getQuestions(quiz_id);
        Quiz quiz = new Quiz(quiz1.getQuizName(), quiz1.getCreatorName(), quiz1.getCategory(), quiz1.getDescription(),
                tags, questions, quiz1.isRandom(), quiz1.isOnePage(), quiz1.hasImmediateCorrection(), quiz1.isPracticeMode());

        assertEquals("Test Quiz", quiz.getQuizName());
        assertEquals("D.Gelashvili", quiz.getCreatorName());
        assertEquals("Science", quiz.getCategory());
        assertEquals(Arrays.asList("Easy", "Short"), quiz.getTags());
        assertEquals("This is a test quiz for beginners. Enjoy it <3", quiz.getDescription());
        assertFalse(quiz.isRandom());
        assertFalse(quiz.isOnePage());
        assertTrue(quiz.hasImmediateCorrection());
        assertTrue(quiz.isPracticeMode());
        assertEquals(6, quiz.getMaxScore());
        assertEquals(3, quiz.getTotalNumberOfQuestions());
        assertEquals("Match these two columns:", quiz.getCurrentQuestion().getQuestion());
    }

    @Test
    public void testFinishAndPublish2() throws SQLException {
        userCreatesQuiz1.setQuizName("Test Quiz 2");
        userCreatesQuiz1.setCategory("Geography");
        userCreatesQuiz1.addQuestion(new QuestionFillBlank("3 x ___ = 12 and ___ is a capital city of Georgia", "4//Tbilisi",
                true, false));
        userCreatesQuiz1.setTags("Hard", "", "For You");
        userCreatesQuiz1.setDescription("This is a second test quiz for beginners. Enjoy it <3");
        userCreatesQuiz1.setOnePage(true);
        userCreatesQuiz1.addQuestion(new QuestionMultiAnswer("Name 5 Europian City", "London//Paris//Tbilisi//Kiev//Madrid",
                false, false));
        userCreatesQuiz1.setRandom(true);
        userCreatesQuiz1.setImmediateCorrection(false);
        userCreatesQuiz1.setPracticeMode(false);
        userCreatesQuiz1.addQuestion(new QuestionMultiChoice("Capital city of Georgia:", "Qutaisi//Tbilisi//Batumi",
                "Tbilisi", true, true));
        userCreatesQuiz1.addQuestion(new QuestionPictureResponse("What is it?", "assets/corgi.jpg",
                "corgi", true, false));

        int quiz_id = userCreatesQuiz1.FinishAndPublish();

        Quiz quiz1 = quizDatabase.getQuiz(quiz_id);
        ArrayList<String> tags = tagsQuizDatabase.getTags(quiz_id);
        ArrayList<Question> questions = quizQuestionDatabase.getQuestions(quiz_id);
        Quiz quiz = new Quiz(quiz1.getQuizName(), quiz1.getCreatorName(), quiz1.getCategory(), quiz1.getDescription(),
                tags, questions, quiz1.isRandom(), quiz1.isOnePage(), quiz1.hasImmediateCorrection(), quiz1.isPracticeMode());

        assertEquals("Test Quiz 2", quiz.getQuizName());
        assertEquals("D.Gelashvili", quiz.getCreatorName());
        assertEquals("Geography", quiz.getCategory());
        assertEquals(Arrays.asList("Hard", "For You"), quiz.getTags());
        assertEquals("This is a second test quiz for beginners. Enjoy it <3", quiz.getDescription());
        assertTrue(quiz.isRandom());
        assertTrue(quiz.isOnePage());
        assertFalse(quiz.hasImmediateCorrection());
        assertFalse(quiz.isPracticeMode());
        assertEquals(9, quiz.getMaxScore());
        assertEquals(4, quiz.getTotalNumberOfQuestions());
    }
}
