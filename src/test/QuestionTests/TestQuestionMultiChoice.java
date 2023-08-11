package QuestionTests;

import Questions_DAO.QuestionMultiChoice;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Collections;

public class TestQuestionMultiChoice extends TestCase {
    public void testVariables() {
        QuestionMultiChoice qmc1 = new QuestionMultiChoice("What is 3 x 4?", "10//11//12//13", "12",
                                                                                                false, true);
        assertEquals("What is 3 x 4?", qmc1.getQuestion());
        assertFalse(qmc1.isOrdered());
        assertTrue(qmc1.isCaseSensitive());
        assertEquals(1, qmc1.getMaxScore());

        QuestionMultiChoice qmc2 = new QuestionMultiChoice("Capital city of Georgia:", "Qutaisi//Tbilisi//Batumi",
                                                                                "Tbilisi", true, true);
        assertEquals("Capital city of Georgia:", qmc2.getQuestion());
        assertTrue(qmc2.isOrdered());
        assertTrue(qmc2.isCaseSensitive());
        assertEquals(1, qmc2.getMaxScore());
    }

    public void testGetPossibleAnswers() {
        QuestionMultiChoice qmc1 = new QuestionMultiChoice("What is 3 x 4?", "10//11//12//13", "12",
                                                                                                false, true);
        ArrayList<String> ls1 = qmc1.getPossibleAnswers();
        Collections.sort(ls1);
        assertEquals(4, ls1.size());
        assertEquals("10", ls1.get(0));
        assertEquals("11", ls1.get(1));
        assertEquals("12", ls1.get(2));
        assertEquals("13", ls1.get(3));

        QuestionMultiChoice qmc2 = new QuestionMultiChoice("Capital city of Georgia:", "Qutaisi//Tbilisi//Batumi",
                                                                                "Tbilisi", true, true);
        ArrayList<String> ls2 = qmc2.getPossibleAnswers();
        assertEquals(3, ls2.size());
        assertEquals("Qutaisi", ls2.get(0));
        assertEquals("Tbilisi", ls2.get(1));
        assertEquals("Batumi", ls2.get(2));
    }

    public void testGetAnswers() {
        QuestionMultiChoice qmc1 = new QuestionMultiChoice("What is 3 x 4?", "10//11//12//13", "12",
                                                                                                false, true);
        ArrayList<String> ls1 = qmc1.getAnswers();
        assertEquals(1, ls1.size());
        assertEquals("12", ls1.get(0));

        QuestionMultiChoice qmc2 = new QuestionMultiChoice("Capital city of Georgia:", "Qutaisi//Tbilisi//Batumi",
                                                                                "Tbilisi", true, true);
        ArrayList<String> ls2 = qmc2.getAnswers();
        assertEquals(1, ls2.size());
        assertEquals("Tbilisi", ls2.get(0));
    }

    public void testCheckAnswer() {
        QuestionMultiChoice qmc1 = new QuestionMultiChoice("What is 3 x 4?", "10//11//12//13", "12",
                                                                                                false, true);
        ArrayList<String> ls1 = new ArrayList<>();
        assertEquals(0, qmc1.checkAnswer(ls1));
        ls1.add("11");
        assertEquals(0, qmc1.checkAnswer(ls1));
        ls1.set(0, "12");
        assertEquals(1, qmc1.checkAnswer(ls1));

        QuestionMultiChoice qmc2 = new QuestionMultiChoice("Capital city of Georgia:", "Qutaisi//Tbilisi//Batumi",
                                                                                "Tbilisi", true, true);
        ArrayList<String> ls2 = new ArrayList<>();
        ls2.add("Tbilisi");
        assertEquals(1, qmc2.checkAnswer(ls2));
    }
}
