package QuestionTests;

import Questions_DAO.QuestionResponse;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class TestQuestionResponse extends TestCase {
    @Test
    public void testVariables() {
        QuestionResponse qr1 = new QuestionResponse("What is 3 x 4?", "12", false, false);
        assertEquals("What is 3 x 4?", qr1.getQuestion());
        assertFalse(qr1.isOrdered());
        assertFalse(qr1.isCaseSensitive());
        assertEquals(1, qr1.getMaxScore());

        QuestionResponse qr2 = new QuestionResponse("Capital city of Georgia:", "Tbilisi", false, true);
        assertEquals("Capital city of Georgia:", qr2.getQuestion());
        assertFalse(qr2.isOrdered());
        assertTrue(qr2.isCaseSensitive());
        assertEquals(1, qr2.getMaxScore());

        assertNull(qr1.getTexts());
        assertNull(qr2.getImage());
        assertNull(qr1.getPossibleAnswers());
    }

    @Test
    public void testGetAnswers() {
        QuestionResponse qr1 = new QuestionResponse("What is 3 x 4?", "12", false, false);
        ArrayList<String> ls1 = qr1.getAnswers();
        assertEquals(1, ls1.size());
        assertEquals("12", ls1.get(0));

        QuestionResponse qr2 = new QuestionResponse("Capital city of Georgia:", "Tbilisi", false, true);
        ArrayList<String> ls2 = qr2.getAnswers();
        assertEquals(1, ls2.size());
        assertEquals("Tbilisi", ls2.get(0));

        QuestionResponse qr3 = new QuestionResponse("Capital city of Georgia:", "Tbilisi", false, false);
        ArrayList<String> ls3 = qr3.getAnswers();
        assertEquals(1, ls3.size());
        assertEquals("TBILISI", ls3.get(0));
    }

    @Test
    public void testCheckAnswer() {
        QuestionResponse qr1 = new QuestionResponse("What is 3 x 4?", "12", false, false);
        ArrayList<String> ls1 = new ArrayList<>();
        assertEquals(0, qr1.checkAnswer(ls1));
        ls1.add("12");
        assertEquals(1, qr1.checkAnswer(ls1));
        ls1.add("24");
        assertEquals(0, qr1.checkAnswer(ls1));

        QuestionResponse qr2 = new QuestionResponse("Capital city of Georgia:", "Tbilisi", false, true);
        ArrayList<String> ls2 = new ArrayList<>();
        assertEquals(0, qr2.checkAnswer(ls2));
        ls2.add("tBilIsi");
        assertEquals(0, qr2.checkAnswer(ls2));
        ls2.clear();
        ls2.add("Tbilisi");
        assertEquals(1, qr2.checkAnswer(ls2));

        QuestionResponse qr3 = new QuestionResponse("Capital city of Georgia:", "Tbilisi", false, false);
        ArrayList<String> ls3 = new ArrayList<>();
        assertEquals(0, qr3.checkAnswer(ls3));
        ls3.add("tBilIsi");
        assertEquals(1, qr3.checkAnswer(ls3));
        ls3.clear();
        ls3.add("Tbilisi");
        assertEquals(1, qr3.checkAnswer(ls3));
    }
}
