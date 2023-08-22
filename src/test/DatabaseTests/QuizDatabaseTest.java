package DatabaseTests;

import DATABASE_DAO.QuizDatabases.QuestionsDatabase;
import DATABASE_DAO.QuizDatabases.QuizDatabase;
import DATABASE_DAO.QuizDatabases.QuizQuestionDatabase;
import DATABASE_DAO.QuizDatabases.TagsQuizDatabase;
import Questions_DAO.*;
import javafx.util.Pair;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class QuizDatabaseTest extends TestCase {

    private QuizDatabase database;


    private Quiz quiz;
    private ArrayList<String> tags;
    private ArrayList<Question> questions;
    private QuestionsDatabase questionsDatabase;

    private QuizQuestionDatabase quizQuestionDatabase;
    private TagsQuizDatabase tagsQuizDatabase;
    private ArrayList<ArrayList<String>> correctAnswers;

    public QuizDatabaseTest() throws SQLException {
    }


    @BeforeEach
    public void setUp() throws SQLException {
        database = new QuizDatabase();
        quizQuestionDatabase = new QuizQuestionDatabase();
        tagsQuizDatabase = new TagsQuizDatabase();
        questionsDatabase = new QuestionsDatabase();
        database.clearAllTables();
    }

    private void addQuestion() throws SQLException {
        questionsDatabase.insertQuestion(database.getMinId(QuestionsDatabase.tablename),questions.get(questions.size() - 1).getType(), questions.get(questions.size() - 1).getQuestion(), questions.get(questions.size() - 1).getPossibleAnswers(), questions.get(questions.size() - 1).getAnswers(), questions.get(questions.size() - 1).isOrdered(), questions.get(questions.size() - 1).isCaseSensitive() );

    }

    @Test
    public void testAdd() throws SQLException {
        tags = new ArrayList<>();
        tags.add("Math");
        tags.add("Geography");
        tags.add("Biology");


        questions = new ArrayList<>();
        questions.add(new QuestionFillBlank("3 x ___ = 12 and ___ is a capital city of Georgia", "4//Tbilisi",
                true, false));
        addQuestion();
        questions.add(new QuestionMatching("Match these two columns:", "3x4//7-2//36/6//6//5//12",
                "3x4//12//7-2//5//36/6//6", false, false));
        addQuestion();
        questions.add(new QuestionMatching("What eats what?", "Rabbit//Chicken//Wolf//Corn//Meat//Carrot",
                "Rabbit//Carrot//Chicken//Corn//Wolf//Meat", true, false));
        addQuestion();
        questions.add(new QuestionMultiAnswer("Name 5 Europian City", "London//Paris//Tbilisi//Kiev//Madrid",
                false, false));
        addQuestion();
        questions.add(new QuestionMultiChoice("Capital city of Georgia:", "Qutaisi//Tbilisi//Batumi",
                "Tbilisi", true, true));
        addQuestion();
        questions.add(new QuestionMultiChoiceMultiAnswer("x^2 - 5x + 6 = 0. x = ?",
                "2//3//1//6", "3//2", false, true));
        addQuestion();
        questions.add(new QuestionResponse("What is 3 x 4?", "12", false, true));
        addQuestion();

        quiz = new Quiz("TestQuiz", "L.Mebonia", "All","this is test quiz, feel free to take it",
                tags, questions, false, false, true, false);

        database.addQuiz(1, quiz.getQuizName(), quiz.getCreatorName(), quiz.getCategory(), quiz.getDescription(), quiz.isRandom(), quiz.isOnePage(), quiz.hasImmediateCorrection(), quiz.isPracticeMode());
        // checking if a quiz was added properly
        assertEquals(database.getQuizName(1), "TestQuiz");

        // adding second quiz
        questions.clear();
        tags.add("Sport");
        questions.add(new QuestionResponse("What is 3 x 4?", "12", false, false));
        addQuestion();
        questions.add(new QuestionFillBlank("3 x ___ = 12 and ___ is a capital city of Georgia", "4//Tbilisi",
                true, false));
        addQuestion();
        questions.add(new QuestionMultiChoice("What is 3 x 4?", "10//11//12//13", "12",
                false, true));
        addQuestion();
        quiz = new Quiz("RealQuiz", "L.Mebonia", "All", "this is the real quiz", tags, questions, false, false, true, false);
        database.addQuiz(database.getMinId(QuizDatabase.tablename), quiz.getQuizName().toString(), quiz.getCreatorName(), quiz.getCategory(), quiz.getDescription(), quiz.isRandom(), quiz.isOnePage(), quiz.hasImmediateCorrection(), quiz.isPracticeMode());
        // checking wheter the second quiz was added properly
        assertEquals(database.getQuizName(2), "RealQuiz");
        // removing the first quiz
        database.delete(1,QuizDatabase.tablename);
        // new quiz should be added with the id of 1
        quiz = new Quiz("QuizAddedAfterDeletion", "L.Mebonia", "All", "this is the real quiz", tags, questions, false, false, true, false);
        database.addQuiz(database.getMinId(QuizDatabase.tablename), quiz.getQuizName().toString(), quiz.getCreatorName(), quiz.getCategory(), quiz.getDescription(), quiz.isRandom(), quiz.isOnePage(), quiz.hasImmediateCorrection(), quiz.isPracticeMode());
        // checking whether quiz was added correctly after the deletion of the first quiz
        assertEquals(database.getQuizName(1), "QuizAddedAfterDeletion");
        Quiz quiz1 = database.getQuiz(1);
        assertEquals(quiz.getQuizName(), quiz1.getQuizName());
        assertEquals(quiz.getCreatorName(), quiz1.getCreatorName());
        assertEquals(quiz.getCategory(), quiz1.getCategory());
        assertEquals(quiz.getDescription(), quiz1.getDescription());
        assertEquals(quiz.isRandom(), quiz1.isRandom());
        assertEquals(quiz.isOnePage(), quiz1.isOnePage());
        assertEquals(quiz.isPracticeMode(), quiz1.isPracticeMode());
    }

    @Test
    public void testRetrieveQuiz() throws SQLException {
        tags = new ArrayList<>();
        tags.add("Math");
        tags.add("Geography");
        tags.add("Biology");

        tagsQuizDatabase.addQuiz("Math", 1);
        tagsQuizDatabase.addQuiz("Geography", 1);
        tagsQuizDatabase.addQuiz("Biology", 1);

        quizQuestionDatabase.addQuestion(1, 1);
        quizQuestionDatabase.addQuestion(2, 2);
        questions = new ArrayList<>();
        questions.add(new QuestionFillBlank("3 x ___ = 12 and ___ is a capital city of Georgia", "4//Tbilisi",
                true, false));
        addQuestion();
        questions.add(new QuestionMatching("Match these two columns:", "3x4//7-2//36/6//6//5//12",
                "3x4//12//7-2//5//36/6//6", false, false));
        addQuestion();
        quiz = new Quiz("TestQuiz", "L.Mebonia", "All","this is test quiz, feel free to take it",
                tags, questions, false, false, true, false);
        database.addQuiz(database.getMinId(QuizDatabase.tablename), quiz.getQuizName(), quiz.getCreatorName(), quiz.getCategory(), quiz.getDescription(), quiz.isRandom(), quiz.isOnePage(), quiz.hasImmediateCorrection(), quiz.isPracticeMode());

        quiz = new Quiz("TestQuiz1", "L.Mebonia", "All","feel free to take it",
                tags, questions, false, false, true, false);

        database.addQuiz(database.getMinId(QuizDatabase.tablename), quiz.getQuizName(), quiz.getCreatorName(), quiz.getCategory(), quiz.getDescription(), quiz.isRandom(), quiz.isOnePage(), quiz.hasImmediateCorrection(), quiz.isPracticeMode());

        ArrayList<Integer> quizes = database.getAllQuizes();
        assertEquals(quizes, Arrays.asList(1, 2));

        ArrayList<Pair<Quiz,Integer>> result = database.getFoundQuizByName("Test"); // should get both quizes
        assertEquals(result.get(0).getKey().getQuizName(), "TestQuiz");
        assertEquals(result.get(1).getKey().getQuizName(), "TestQuiz1");
    }


}
