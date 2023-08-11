package QuestionTests;

import Questions_DAO.QuestionMultiChoiceMultiAnswer;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Collections;

public class TestQuestionMultiChoiceMultiAnswer extends TestCase {
    public void testVariables() {
        QuestionMultiChoiceMultiAnswer qmcma1 = new QuestionMultiChoiceMultiAnswer("x^2 - 5x + 6 = 0. x = ?",
                                                    "1//2//3//6", "2//3", false, true);
        assertEquals("x^2 - 5x + 6 = 0. x = ?", qmcma1.getQuestion());
        assertFalse(qmcma1.isOrdered());
        assertTrue(qmcma1.isCaseSensitive());
        assertEquals(2, qmcma1.getMaxScore());

        QuestionMultiChoiceMultiAnswer qmcma2 = new QuestionMultiChoiceMultiAnswer("x^2 - 8x + 12 = 0. x = ?",
                                                    "1//2//3//6", "2//6", true, true);
        assertEquals("x^2 - 8x + 12 = 0. x = ?", qmcma2.getQuestion());
        assertTrue(qmcma2.isOrdered());
        assertTrue(qmcma2.isCaseSensitive());
        assertEquals(2, qmcma2.getMaxScore());
    }

    public void testGetPossibleAnswers() {
        QuestionMultiChoiceMultiAnswer qmcma1 = new QuestionMultiChoiceMultiAnswer("x^2 - 5x + 6 = 0. x = ?",
                                                    "2//3//1//6", "2//3", false, true);
        ArrayList<String> ls1 = qmcma1.getPossibleAnswers();
        Collections.sort(ls1);
        assertEquals(4, ls1.size());
        assertEquals("1", ls1.get(0));
        assertEquals("2", ls1.get(1));
        assertEquals("3", ls1.get(2));
        assertEquals("6", ls1.get(3));

        QuestionMultiChoiceMultiAnswer qmcma2 = new QuestionMultiChoiceMultiAnswer("x^2 - 8x + 12 = 0. x = ?",
                                                     "2//3//1//6", "2//6", true, true);
        ArrayList<String> ls2 = qmcma2.getPossibleAnswers();
        assertEquals(4, ls2.size());
        assertEquals("2", ls2.get(0));
        assertEquals("3", ls2.get(1));
        assertEquals("1", ls2.get(2));
        assertEquals("6", ls2.get(3));
    }

    public void testGetAnswers() {
        QuestionMultiChoiceMultiAnswer qmcma1 = new QuestionMultiChoiceMultiAnswer("x^2 - 5x + 6 = 0. x = ?",
                                                    "2//3//1//6", "3//2", false, true);
        ArrayList<String> ls1 = qmcma1.getAnswers();
        assertEquals(2, ls1.size());
        assertEquals("2", ls1.get(0));
        assertEquals("3", ls1.get(1));

        QuestionMultiChoiceMultiAnswer qmcma2 = new QuestionMultiChoiceMultiAnswer("x^2 - 8x + 12 = 0. x = ?",
                                                    "2//3//1//6", "2//6", true, true);
        ArrayList<String> ls2 = qmcma2.getAnswers();
        assertEquals(2, ls2.size());
        assertEquals("2", ls2.get(0));
        assertEquals("6", ls2.get(1));
    }

    public void testCheckAnswer() {
        QuestionMultiChoiceMultiAnswer qmcma1 = new QuestionMultiChoiceMultiAnswer("x^2 - 5x + 6 = 0. x = ?",
                                                    "2//3//1//6", "3//2", false, true);
        ArrayList<String> ls1 = new ArrayList<>();
        assertEquals(0, qmcma1.checkAnswer(ls1));
        ls1.add("3");
        assertEquals(1, qmcma1.checkAnswer(ls1));
        ls1.add("6");
        assertEquals(1, qmcma1.checkAnswer(ls1));

        QuestionMultiChoiceMultiAnswer qmcma2 = new QuestionMultiChoiceMultiAnswer("x^2 - 8x + 12 = 0. x = ?",
                                                    "2//3//1//6", "2//6", true, true);
        ArrayList<String> ls2 = new ArrayList<>();
        assertEquals(0, qmcma2.checkAnswer(ls2));
        ls2.add("6");
        assertEquals(1, qmcma2.checkAnswer(ls2));
        ls2.add("2");
        assertEquals(2, qmcma2.checkAnswer(ls2));
    }
}
