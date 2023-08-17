package QuestionTests;

import Questions_DAO.QuestionFillBlank;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class TestQuestionFillBlank extends TestCase {
    @Test
    public void testVariables() {
        QuestionFillBlank qfb1 = new QuestionFillBlank("3 x ___ = 12", "4", true, false);
        assertEquals("3 x ___ = 12", qfb1.getQuestion());
        assertTrue(qfb1.isOrdered());
        assertFalse(qfb1.isCaseSensitive());
        assertEquals(1, qfb1.getMaxScore());

        QuestionFillBlank qfb2 = new QuestionFillBlank("___ is a capital city of Georgia", "Tbilisi",
                                                                                    true, true);
        assertEquals("___ is a capital city of Georgia", qfb2.getQuestion());
        assertTrue(qfb2.isOrdered());
        assertTrue(qfb2.isCaseSensitive());
        assertEquals(1, qfb1.getMaxScore());
        assertNull(qfb1.getImage());
        assertNull(qfb2.getPossibleAnswers());
    }

    @Test
    public void testGetAnswers() {
        QuestionFillBlank qfb1 = new QuestionFillBlank("3 x ___ = 12", "4", true, true);
        ArrayList<String> ls1 = qfb1.getAnswers();
        assertEquals(1, ls1.size());
        assertEquals("4", ls1.get(0));

        QuestionFillBlank qfb2 = new QuestionFillBlank("___ is a capital city of Georgia", "Tbilisi",
                                                                                    true, false);
        ArrayList<String> ls2 = qfb2.getAnswers();
        assertEquals(1, ls2.size());
        assertEquals("TBILISI", ls2.get(0));

        QuestionFillBlank qfb3 = new QuestionFillBlank("3 x ___ = 12 and ___ is a capital city of Georgia", "4//Tbilisi",
                                                                                    true, true);
        ArrayList<String> ls3 = qfb3.getAnswers();
        assertEquals(2, ls3.size());
        assertEquals("4", ls3.get(0));
        assertEquals("Tbilisi", ls3.get(1));
    }

    @Test
    public void testCheckAnswer() {
        QuestionFillBlank qfb1 = new QuestionFillBlank("3 x ___ = 12 and ___ is a capital city of Georgia", "4//Tbilisi",
                                                                                    true, true);
        ArrayList<String> ls1 = new ArrayList<>();
        assertEquals(0, qfb1.checkAnswer(ls1));
        ls1.add("4");
        assertEquals(1, qfb1.checkAnswer(ls1));
        ls1.add("TbiliSi");
        assertEquals(1, qfb1.checkAnswer(ls1));
        ls1.set(1, "Tbilisi");
        assertEquals(2, qfb1.checkAnswer(ls1));
        ls1.add("extra");
        assertEquals(0, qfb1.checkAnswer(ls1));

        QuestionFillBlank qfb2 = new QuestionFillBlank("3 x ___ = 12 and ___ is a capital city of Georgia", "4//Tbilisi",
                true, false);
        ArrayList<String> ls2 = new ArrayList<>();
        assertEquals(0, qfb2.checkAnswer(ls2));
        ls2.add("4");
        assertEquals(1, qfb2.checkAnswer(ls2));
        ls2.add("TbiliSi");
        assertEquals(2, qfb2.checkAnswer(ls2));
        ls2.set(1, "Tbilisi");
        assertEquals(2, qfb1.checkAnswer(ls2));
        ls2.add("extra");
        assertEquals(0, qfb1.checkAnswer(ls2));
    }

    @Test
    public void testGetTexts() {
        QuestionFillBlank qfb1 = new QuestionFillBlank("3 x ___ = 12 and ___ is a capital city of Georgia", "4//Tbilisi",
                true, true);
        ArrayList<String> cuts = new ArrayList<>();
        cuts.add("3 x ");
        cuts.add(" = 12 and ");
        cuts.add(" is a capital city of Georgia");
        assertEquals(cuts, qfb1.getTexts());
    }
}
