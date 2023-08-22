package DatabaseTests;

import DATABASE_DAO.QuizDatabases.TagsQuizDatabase;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TagsQuizDatabaseTest extends TestCase {

    private TagsQuizDatabase database;

    @BeforeEach
    public void setUp() throws SQLException {
        database = new TagsQuizDatabase();
        database.clearAllTables();
        init();
    }

    private void init() throws SQLException{
        database.addQuiz("Math" , 1);
        database.addQuiz("Math" , 4);
        database.addQuiz("Math" , 5);
        database.addQuiz("History" , 1);
        database.addQuiz("Biology" , 5);
        database.addQuiz("Sport" , 5);
        database.addQuiz("Math" , 6);
        database.addQuiz("Biology" , 1);
        database.addQuiz("Sport" , 6);
    }

    @Test
    public void TestCase() throws SQLException {

        List<String> list = database.getTags(1);
        Collections.sort(list);
        assertEquals(list, Arrays.asList("Biology", "History", "Math"));
        assertNotEquals(list, Arrays.asList("History"));

        list = database.getTags(2);
        assertEquals(list , Arrays.asList());
        assertNotEquals(list, Arrays.asList("History"));

        list = database.getTags(3);
        assertEquals(list , Arrays.asList());
        assertNotEquals(list, Arrays.asList("Math"));

        list = database.getTags(4);
        assertEquals(list, Arrays.asList("Math"));
        assertNotEquals(list, Arrays.asList("History"));

        list = database.getTags(5);
        Collections.sort(list);
        assertEquals(list, Arrays.asList("Biology", "Math", "Sport"));
        assertNotEquals(list, Arrays.asList("Math", "History", "Biology"));

        list = database.getTags(6);
        Collections.sort(list);
        assertEquals(list, Arrays.asList("Math", "Sport"));
        assertNotEquals(list, Arrays.asList("Math", "Biology", "Sport"));

        // let's remove the last quiz
        database.delete(6, TagsQuizDatabase.tablename);
        // check whether it was deleted correctly
        assertTrue(database.getTags(6).isEmpty());

    }

    @Test
    public void testRetrieve() throws SQLException{
        ArrayList<Integer> quizes = database.getFoundQuizByTag("Math");
        Collections.sort(quizes);
        assertEquals(quizes, Arrays.asList(1,4,5,6));
        quizes = database.getFoundQuizByTag("History");
        assertEquals(quizes, Collections.singletonList(1));
        quizes = database.getFoundQuizByTag("Biology");
        Collections.sort(quizes);
        assertEquals(quizes, Arrays.asList(1,5));
        quizes = database.getFoundQuizByTag("Sport");
        Collections.sort(quizes);
        assertEquals(quizes, Arrays.asList(5,6));
    }



}
