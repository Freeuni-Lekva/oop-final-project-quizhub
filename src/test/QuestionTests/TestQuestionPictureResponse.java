package QuestionTests;

import Questions_DAO.QuestionPictureResponse;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class TestQuestionPictureResponse extends TestCase {
    @Test
    public void testVariables() {
        QuestionPictureResponse qpr1 = new QuestionPictureResponse("Name of the president?", "president.png",
                                                                                    "Donald Trump", false, true);
        assertEquals("Name of the president?", qpr1.getQuestion());
        assertEquals("president.png", qpr1.getImage());
        assertFalse(qpr1.isOrdered());
        assertTrue(qpr1.isCaseSensitive());
        assertEquals(1, qpr1.getMaxScore());

        QuestionPictureResponse qpr2 = new QuestionPictureResponse("Number of Triangles?", "triangles.png",
                                                                                    "30", false, false);
        assertEquals(4, qpr2.getType());
        assertEquals("Number of Triangles?", qpr2.getQuestion());
        assertEquals("triangles.png", qpr2.getImage());
        assertFalse(qpr2.isOrdered());
        assertFalse(qpr2.isCaseSensitive());
        assertEquals(1, qpr2.getMaxScore());
        assertNull(qpr1.getTexts());
        assertNull(qpr2.getTexts());
        assertNull(qpr1.getPossibleAnswers());
    }

    @Test
    public void testGetAnswers() {
        QuestionPictureResponse qpr1 = new QuestionPictureResponse("Name of the president?", "president.png",
                                                                            "Donald Trump", false, true);
        ArrayList<String> ls1 = qpr1.getAnswers();
        assertEquals(1, ls1.size());
        assertEquals("Donald Trump", ls1.get(0));

        QuestionPictureResponse qpr2 = new QuestionPictureResponse("Name of the president?", "president.png",
                                                                            "Donald Trump", false, false);
        ArrayList<String> ls2 = qpr2.getAnswers();
        assertEquals(1, ls2.size());
        assertEquals("DONALD TRUMP", ls2.get(0));

        QuestionPictureResponse qpr3 = new QuestionPictureResponse("Number of Triangles?", "triangles.png",
                                                                                        "30", false, false);
        ArrayList<String> ls3 = qpr3.getAnswers();
        assertEquals(1, ls3.size());
        assertEquals("30", ls3.get(0));
    }

    @Test
    public void testCheckAnswer() {
        QuestionPictureResponse qpr1 = new QuestionPictureResponse("Name of the president?", "president.png",
                                                                            "Donald Trump", false, true);
        ArrayList<String> ls1 = new ArrayList<>();
        assertEquals(0, qpr1.checkAnswer(ls1));
        ls1.add("Donald TrumP");
        assertEquals(0, qpr1.checkAnswer(ls1));
        ls1.set(0, "Donald Trump");
        assertEquals(1, qpr1.checkAnswer(ls1));
        ls1.add("Donald");
        assertEquals(0, qpr1.checkAnswer(ls1));

        QuestionPictureResponse qpr2 = new QuestionPictureResponse("Name of the president?", "president.png",
                                                                        "Donald Trump", false, false);
        ArrayList<String> ls2 = new ArrayList<>();
        assertEquals(0, qpr2.checkAnswer(ls2));
        ls2.add("Donald TrumP");
        assertEquals(1, qpr2.checkAnswer(ls2));
        ls2.set(0, "Donald Trump");
        assertEquals(1, qpr2.checkAnswer(ls2));
        ls2.add("Donald");
        assertEquals(0, qpr2.checkAnswer(ls2));

        QuestionPictureResponse qpr3 = new QuestionPictureResponse("Number of Triangles?", "triangles.png",
                "30", false, false);
        ArrayList<String> ls3 = new ArrayList<>();
        assertEquals(0, qpr3.checkAnswer(ls3));
        ls3.add("A");
        assertEquals(0, qpr3.checkAnswer(ls3));
        ls3.add("30");
        assertEquals(0, qpr3.checkAnswer(ls3));
        ls3.remove(0);
        assertEquals(1, qpr3.checkAnswer(ls3));
    }
}
