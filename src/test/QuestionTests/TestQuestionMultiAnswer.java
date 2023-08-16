package QuestionTests;

import Questions_DAO.QuestionMultiAnswer;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class TestQuestionMultiAnswer extends TestCase {
    @Test
    public void testVariables() {
        QuestionMultiAnswer qma1 = new QuestionMultiAnswer("Name 5 Europian City", "London//Paris//Tbilisi//Kiev//Madrid",
                                                                                                false, false);
        assertEquals("Name 5 Europian City", qma1.getQuestion());
        assertFalse(qma1.isOrdered());
        assertFalse(qma1.isCaseSensitive());
        assertEquals(5, qma1.getMaxScore());

        QuestionMultiAnswer qma2 = new QuestionMultiAnswer("Name 5 Europian City", "London//Paris//Tbilisi//Kiev//Madrid",
                                                                                                true, true);
        assertEquals("Name 5 Europian City", qma2.getQuestion());
        assertTrue(qma2.isOrdered());
        assertTrue(qma2.isCaseSensitive());
        assertEquals(5, qma2.getMaxScore());

        QuestionMultiAnswer qma3 = new QuestionMultiAnswer("Name 3 Largest Countries", "Russia//Canada//USA",
                                                                                                        true, false);
        assertEquals("Name 3 Largest Countries", qma3.getQuestion());
        assertTrue(qma3.isOrdered());
        assertFalse(qma3.isCaseSensitive());
        assertEquals(3, qma3.getMaxScore());

        assertNull(qma1.getImage());
        assertNull(qma2.getTexts());
    }

    @Test
    public void testGetAnswers() {
        QuestionMultiAnswer qma1 = new QuestionMultiAnswer("Name 5 Europian City", "London//Paris//Tbilisi//Kiev//Madrid",
                false, false);
        ArrayList<String> ls1 = qma1.getAnswers();
        assertEquals(5, ls1.size());
        assertEquals("KIEV", ls1.get(0));
        assertEquals("LONDON", ls1.get(1));
        assertEquals("MADRID", ls1.get(2));
        assertEquals("PARIS", ls1.get(3));
        assertEquals("TBILISI", ls1.get(4));

        QuestionMultiAnswer qma2 = new QuestionMultiAnswer("Name 5 Europian City", "London//Paris//Tbilisi//Kiev//Madrid",
                true, true);
        ArrayList<String> ls2 = qma2.getAnswers();
        assertEquals(5, ls2.size());
        assertEquals("London", ls2.get(0));
        assertEquals("Paris", ls2.get(1));
        assertEquals("Tbilisi", ls2.get(2));
        assertEquals("Kiev", ls2.get(3));
        assertEquals("Madrid", ls2.get(4));

        QuestionMultiAnswer qma3 = new QuestionMultiAnswer("Name 3 Largest Countries", "Russia//Canada//USA",
                true, false);
        ArrayList<String> ls3 = qma3.getAnswers();
        assertEquals(3, ls3.size());
        assertEquals("RUSSIA", ls3.get(0));
        assertEquals("CANADA", ls3.get(1));
        assertEquals("USA", ls3.get(2));
    }

    @Test
    public void testCheckAnswer() {
        QuestionMultiAnswer qma1 = new QuestionMultiAnswer("Name 5 Europian City", "London//Paris//Tbilisi//Kiev//Madrid",
                false, false);
        ArrayList<String> ls1 = new ArrayList<>();
        assertEquals(0, qma1.checkAnswer(ls1));
        ls1.add("paRis");
        assertEquals(1, qma1.checkAnswer(ls1));
        ls1.add("lisboN");
        assertEquals(1, qma1.checkAnswer(ls1));
        ls1.add("madrid");
        assertEquals(2, qma1.checkAnswer(ls1));
        ls1.add("LONDON");
        assertEquals(3, qma1.checkAnswer(ls1));
        ls1.add("talin");
        assertEquals(3, qma1.checkAnswer(ls1));
        ls1.add("Berlin");
        assertEquals(0, qma1.checkAnswer(ls1));

        QuestionMultiAnswer qma2 = new QuestionMultiAnswer("Name 5 Europian City", "London//Paris//Tbilisi//Kiev//Madrid",
                true, true);
        ArrayList<String> ls2 = new ArrayList<>();
        assertEquals(0, qma2.checkAnswer(ls2));
        ls2.add("paRis");
        assertEquals(0, qma2.checkAnswer(ls2));
        ls2.add("Paris");
        assertEquals(1, qma2.checkAnswer(ls2));
        ls2.add("Tbilisi");
        assertEquals(2, qma2.checkAnswer(ls2));
        ls2.add("Madrid");
        assertEquals(2, qma2.checkAnswer(ls2));

        QuestionMultiAnswer qma3 = new QuestionMultiAnswer("Name 3 Largest Countries", "Russia//Canada//USA",
                true, false);
        ArrayList<String> ls3 = new ArrayList<>();
        assertEquals(0, qma3.checkAnswer(ls3));
        ls3.add("russia");
        assertEquals(1, qma3.checkAnswer(ls3));
        ls3.add("china");
        assertEquals(1, qma3.checkAnswer(ls3));
        ls3.add("usA");
        assertEquals(2, qma3.checkAnswer(ls3));
        ls3.add("Canada");
        assertEquals(0, qma3.checkAnswer(ls3));
    }
}
