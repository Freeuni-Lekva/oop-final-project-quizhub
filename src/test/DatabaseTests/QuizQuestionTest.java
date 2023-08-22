package DatabaseTests;

import DATABASE_DAO.QuizDatabases.QuestionsDatabase;
import DATABASE_DAO.QuizDatabases.QuizDatabase;
import DATABASE_DAO.QuizDatabases.QuizQuestionDatabase;
import Questions_DAO.*;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class QuizQuestionTest extends TestCase {

    private QuizQuestionDatabase database;

    private ArrayList<Question> questions;

    private QuizDatabase quizDatabase;
    private QuestionsDatabase questionsDatabase;

    @BeforeEach
    public void setUp() throws SQLException {
        database = new QuizQuestionDatabase();
        quizDatabase = new QuizDatabase();
        questionsDatabase = new QuestionsDatabase();
        database.clearAllTables();
        questions = new ArrayList<>();
    }


    @Test
    public void testAdd() throws SQLException {
        Question qfb1 = new QuestionFillBlank("3 x ___ = 12 and ___ is a capital city of Georgia", "4//Tbilisi",
                true, true);
        questions.add(qfb1);
        Question qm1 = new QuestionMatching("Match these two columns:", "3x4//7-2//36/6//6//5//12",
                "3x4//12//7-2//5//36/6//6", true, false);
        questions.add(qm1);
        Quiz quiz = new Quiz("First Quiz", "L.Mebonia", "All", "This is the first quiz", null, questions, false, false, true, false);
        int quizId = database.getMinId(QuizDatabase.tablename);
        // adding quiz into quiz database
        quizDatabase.addQuiz(quizId, quiz.getQuizName(), quiz.getCreatorName(), quiz.getCategory(), quiz.getDescription(), quiz.isRandom(), quiz.isOnePage(), quiz.hasImmediateCorrection(), quiz.isPracticeMode());
        for (Question question : questions) {
            int questionId = database.getMinId(QuestionsDatabase.tablename);
            // adding question in question database
            questionsDatabase.insertQuestion(questionId, question.getType(), question.getQuestion(), question.getPossibleAnswers(), question.getAnswers(), question.isOrdered(), question.isCaseSensitive());
            // attaching quiz and questions to each other in quizQuestion database
            database.addQuestion(quizId, questionId);
        }

        // now let's check whether the questions were attached to the quiz correctly
        ArrayList<Question> questionsInDatabase = new ArrayList<>();
        questionsInDatabase.add(qfb1);
        questionsInDatabase.add(qm1);
        ArrayList<Question> result = database.getQuestions(1);
        for(int i = 0; i < result.size(); i++){
            Question res = result.get(i);
            Question quest = questionsInDatabase.get(i);
            assertEquals(res.getQuestion(), quest.getQuestion());
            assertEquals(res.isCaseSensitive(), quest.isCaseSensitive());
            assertEquals(res.isOrdered(), quest.isOrdered());
            assertEquals(res.getType(), quest.getType());
            assertEquals(res.getAnswers(), quest.getAnswers());
            assertEquals(res.getPossibleAnswers(), quest.getPossibleAnswers());
        }

        // let's remove this quiz.
        database.delete(1, QuizQuestionDatabase.tablename);
        // and check whether it was deleted from the database. removing questions and tags from other databases will be tested in "TestEverythingCombined.java" file.
        assertTrue(database.getQuestions(1).isEmpty());
    }

    @Test
    public void testRetrieve() throws SQLException {
        Question qfb1 = new QuestionFillBlank("3 x ___ = 12 and ___ is a capital city of Georgia", "4//Tbilisi",
                true, true);
        questions.add(qfb1);
        Question qm1 = new QuestionMatching("Match these two columns:", "3x4//7-2//36/6//6//5//12",
                "3x4//12//7-2//5//36/6//6", true, false);
        questions.add(qm1);
        Quiz quiz = new Quiz("First Quiz", "L.Mebonia", "All", "This is the first quiz", null, questions, false, false, true, false);
        for (Question question : questions) {
            int questionId = database.getMinId(QuestionsDatabase.tablename);
            // adding question in question database
            questionsDatabase.insertQuestion(questionId, question.getType(), question.getQuestion(), question.getPossibleAnswers(), question.getAnswers(), question.isOrdered(), question.isCaseSensitive());
            // attaching quiz and questions to each other in quizQuestion database
            database.addQuestion(1, questionId);
        }
        quizDatabase.addQuiz(1, quiz.getQuizName(), quiz.getCreatorName(), quiz.getCategory(), quiz.getDescription(), quiz.isRandom(), quiz.isOnePage(), quiz.hasImmediateCorrection(), quiz.isPracticeMode());

        ArrayList<Question> result = database.getQuestions(1);
        Question question = result.get(0);
        assertEquals(question.getQuestion(), "3 x ___ = 12 and ___ is a capital city of Georgia");
        question = result.get(1);
        assertEquals(question.getQuestion(), "Match these two columns:");
        assertEquals(result.size(), 2);
    }

}
