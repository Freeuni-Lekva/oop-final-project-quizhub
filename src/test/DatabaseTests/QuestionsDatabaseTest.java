package DatabaseTests;

import DATABASE_DAO.QuizDatabases.QuestionsDatabase;
import Questions_DAO.*;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class QuestionsDatabaseTest extends TestCase {

    private QuestionsDatabase database;

    private ArrayList<Question> questions;

    @BeforeEach
    public void setUp() throws SQLException {
        database = new QuestionsDatabase();
        database.clearAllTables();
        init();

    }

    private void addQuestion() throws SQLException {
        database.insertQuestion(database.getMinId(QuestionsDatabase.tablename),questions.get(questions.size() - 1).getType(), questions.get(questions.size() - 1).getQuestion(), questions.get(questions.size() - 1).getPossibleAnswers(), questions.get(questions.size() - 1).getAnswers(), questions.get(questions.size() - 1).isOrdered(), questions.get(questions.size() - 1).isCaseSensitive() );

    }


    private void initFirstTest() throws SQLException {
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
        questions.add(new QuestionMatching("Match these two columns:", "3x4//7-2//36/6//6//5//12",
                "3x4//12//7-2//5//36/6//6", true, false));
        addQuestion();
        questions.add(new QuestionPictureResponse("Name of the president?", "president.png",
                "Donald Trump", false, true));
        addQuestion();
    }

    private void initSecondTest() throws SQLException {
        questions.add(new QuestionFillBlank("3 x ___ = 12 and ___ is a capital city of Georgia", "4//Tbilisi",
                true, false));
        addQuestion();
        questions.add(new QuestionMatching("Match these two columns:", "3x4//7-2//36/6//6//5//12",
                "3x4//12//7-2//5//36/6//6", false, false));
        addQuestion();
        questions.add(new QuestionMatching("What eats what?", "Rabbit//Chicken//Wolf//Corn//Meat//Carrot",
                "Rabbit//Carrot//Chicken//Corn//Wolf//Meat", true, false));
    }

    private void init() throws SQLException {
        database.clearTable(QuestionsDatabase.tablename);
        questions = new ArrayList<>();
    }

    @Test
    public void testQuestions() throws SQLException {
        initFirstTest();
        // 1-st question
        Question question = database.getQuestion(1);
        assertEquals(question.getType(), 2);
        assertEquals(question.getQuestion(), "3 x ___ = 12 and ___ is a capital city of Georgia");
        assertNull(question.getPossibleAnswers());
        assertEquals(question.getAnswers(), Arrays.asList("4","TBILISI"));
        assertTrue(question.isOrdered());
        assertFalse(question.isCaseSensitive());

        // 2-nd question
        question = database.getQuestion(2);
        assertEquals(question.getType(), 7);
        assertEquals(question.getQuestion(), "Match these two columns:");

        // if the possible answers are not ordered, any kind of permutation of possible answers is acceptable. Therefore we have to sort them to compare.
        Collections.sort(question.getPossibleAnswers());
        List<String> list = Arrays.asList("3x4","7-2","36/6","6","5","12");
        Collections.sort(list);
        assertEquals(question.getPossibleAnswers(), list);

        assertEquals(question.getAnswers(), Arrays.asList("36/6//6" , "3x4//12", "7-2//5"));
        assertFalse(question.isOrdered());
        assertFalse(question.isCaseSensitive());

        // 3-rd question
        question = database.getQuestion(3);
        assertEquals(question.getType(), 7);
        assertEquals(question.getQuestion(), "What eats what?");

        // possible answers are ordered therefore we don't have to sort them to compare.
        assertEquals(question.getPossibleAnswers(), Arrays.asList("Rabbit","Chicken","Wolf","Corn","Meat","Carrot"));

        assertEquals(question.getAnswers(), Arrays.asList("Chicken//Corn","Rabbit//Carrot","Wolf//Meat"));
        assertTrue(question.isOrdered());
        assertFalse(question.isCaseSensitive());

        // 4-th question
        question = database.getQuestion(4);
        assertEquals(question.getType(), 5);
        assertEquals(question.getQuestion(), "Name 5 Europian City");


        assertNull(question.getPossibleAnswers());

        assertEquals(question.getAnswers(), Arrays.asList("KIEV","LONDON","MADRID","PARIS","TBILISI"));
        assertFalse(question.isOrdered());
        assertFalse(question.isCaseSensitive());

        // 5-th question
        question = database.getQuestion(5);
        assertEquals(question.getType(), 3);
        assertEquals(question.getQuestion(), "Capital city of Georgia:");


        assertEquals(question.getPossibleAnswers(), Arrays.asList("Qutaisi","Tbilisi","Batumi"));
        // checking ordered
        assertNotEquals(question.getPossibleAnswers(), Arrays.asList("Tbilisi","Qutaisi","Batumi"));

        assertEquals(question.getAnswers(), Arrays.asList("Tbilisi"));
        // checking case sensitive
        assertNotEquals(question.getAnswers(), Arrays.asList("TBILISI"));
        assertTrue(question.isOrdered());
        assertTrue(question.isCaseSensitive());

        // 6-th question
        question = database.getQuestion(6);
        assertEquals(question.getType(), 6);
        assertEquals(question.getQuestion(), "x^2 - 5x + 6 = 0. x = ?");

        Collections.sort(question.getPossibleAnswers());
        assertEquals(question.getPossibleAnswers(), Arrays.asList("1","2","3","6"));


        assertEquals(question.getAnswers(), Arrays.asList("2","3"));

        assertFalse(question.isOrdered());
        assertTrue(question.isCaseSensitive());

        // 7-th question
        question = database.getQuestion(7);
        assertEquals(question.getType(), 1);
        assertEquals(question.getQuestion(), "What is 3 x 4?");


        assertNull(question.getPossibleAnswers());


        assertEquals(question.getAnswers(), Arrays.asList("12"));

        assertFalse(question.isOrdered());
        assertTrue(question.isCaseSensitive());

        // 8-th question
        question = database.getQuestion(8);
        assertEquals(question.getType(), 7);
        assertEquals(question.getQuestion(), "Match these two columns:");


        assertEquals(question.getPossibleAnswers(), Arrays.asList("3x4","7-2","36/6","6","5","12"));


        assertEquals(question.getAnswers(), Arrays.asList("36/6//6","3x4//12","7-2//5"));

        assertTrue(question.isOrdered());
        assertFalse(question.isCaseSensitive());


        questions.add(new QuestionPictureResponse("Name of the president?", "president.png",
                "Donald Trump", false, true));
        addQuestion();

        // 9-th question
        question = database.getQuestion(9);
        assertEquals(question.getType(), 4);
        assertEquals(question.getQuestion(), "Name of the president?");


        assertEquals(question.getPossibleAnswers(), Arrays.asList("president.png"));


        assertEquals(question.getAnswers(), Arrays.asList("Donald Trump"));

        assertFalse(question.isOrdered());
        assertTrue(question.isCaseSensitive());


    }

    @Test
    public void TestRemove() throws SQLException {
        initSecondTest();
        database.delete(2, QuestionsDatabase.tablename);
        // id of this question should be 2 because after deleting the second question, second spot is free for a question to be added.
        questions.add(new QuestionMultiAnswer("Name 5 Europian City", "London,Paris,Tbilisi,Kiev,Madrid",
                false, false));
        addQuestion();
        assertEquals(database.getQuestion(2).getQuestion(), "Name 5 Europian City");
    }



}
