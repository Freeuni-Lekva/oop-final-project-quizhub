package QuestionTests;

import Questions_DAO.QuestionMatching;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import java.util.Collections;

public class TestQuestionMatching extends TestCase {
    @Test
    public void testVariables() {
        QuestionMatching qm1 = new QuestionMatching("Match these two columns:", "3x4//7-2//36/6//6//5//12",
                                                            "3x4//12//7-2//5//36/6//6", false, false);
        assertEquals("Match these two columns:", qm1.getQuestion());
        assertEquals(7, qm1.getType());
        assertFalse(qm1.isOrdered());
        assertFalse(qm1.isCaseSensitive());
        assertEquals(3, qm1.getMaxScore());

        QuestionMatching qm2 = new QuestionMatching("What eats what?", "Rabbit//Chicken//Wolf//Corn//Meat//Carrot",
                                            "Rabbit//Carrot//Chicken//Corn//Wolf//Meat", true, false);
        assertEquals("What eats what?", qm2.getQuestion());
        assertEquals(7, qm2.getType());
        assertTrue(qm2.isOrdered());
        assertFalse(qm2.isCaseSensitive());
        assertEquals(3, qm2.getMaxScore());

        assertNull(qm1.getTexts());
        assertNull(qm2.getImage());
    }

    @Test
    public void testGetPossibleAnswers() {
        QuestionMatching qm1 = new QuestionMatching("Match these two columns:", "3x4//7-2//36/6//6//5//12",
                                                            "3x4//12//7-2//5//36/6//6", false, false);
        assertEquals(6, qm1.getPossibleAnswers().size());
        ArrayList<String> ls = qm1.getPossibleAnswers();
        Collections.sort(ls);
        assertEquals("12", ls.get(0));
        assertEquals("36/6", ls.get(1));
        assertEquals("7-2", ls.get(5));

        QuestionMatching qm2 = new QuestionMatching("What eats what?", "Rabbit//Chicken//Wolf//Corn//Meat//Carrot",
                "Rabbit//Carrot//Chicken//Corn//Wolf//Meat", true, false);
        assertEquals(6, qm2.getPossibleAnswers().size());
        assertEquals("Rabbit", qm2.getPossibleAnswers().get(0));
        assertEquals("Chicken", qm2.getPossibleAnswers().get(1));
        assertEquals("Wolf", qm2.getPossibleAnswers().get(2));
        assertEquals("Corn", qm2.getPossibleAnswers().get(3));
        assertEquals("Meat", qm2.getPossibleAnswers().get(4));
        assertEquals("Carrot", qm2.getPossibleAnswers().get(5));
    }

    @Test
    public void testGetAnswers() {
        QuestionMatching qm1 = new QuestionMatching("Match these two columns:", "3x4//7-2//36/6//6//5//12",
                "3x4//12//7-2//5//36/6//6", false, false);
        ArrayList<String> ls1 = new ArrayList<>();
        ls1.add("3x4//12");
        ls1.add("7-2//5");
        ls1.add("36/6//6");
        Collections.sort(ls1);
        assertEquals(ls1, qm1.getAnswers());

        QuestionMatching qm2 = new QuestionMatching("What eats what?", "Rabbit//Chicken//Wolf//Corn//Meat//Carrot",
                "Rabbit//Carrot//Chicken//Corn//Wolf//Meat", true, false);
        ArrayList<String> ls2 = new ArrayList<>();
        ls2.add("Rabbit//Carrot");
        ls2.add("Chicken//Corn");
        ls2.add("Wolf//Meat");
        Collections.sort(ls2);
        assertEquals(ls2, qm2.getAnswers());
    }

    @Test
    public void testCheckAnswer() {
        QuestionMatching qm1 = new QuestionMatching("Match these two columns:", "3x4//7-2//36/6//6//5//12",
                "3x4//12//7-2//5//36/6//6", false, false);
        ArrayList<String> ls1 = new ArrayList<>();
        ls1.add("36/6//6");
        ls1.add("3x4//12");
        ls1.add("7-2//5");
        assertEquals(3, qm1.checkAnswer(ls1));
        ls1.set(1, "3x4//5");
        ls1.set(2, "7-2//12");
        assertEquals(1, qm1.checkAnswer(ls1));

        QuestionMatching qm2 = new QuestionMatching("What eats what?", "Rabbit//Chicken//Wolf//Corn//Meat//Carrot",
                "Rabbit//Carrot//Chicken//Corn//Wolf//Meat", true, false);
        ArrayList<String> ls2 = new ArrayList<>();
        ls2.add("Rabbit//Corn");
        ls2.add("Wolf//Carrot");
        ls2.add("Chicken//Meat");
        assertEquals(0, qm2.checkAnswer(ls2));
    }
}