package DatabaseTests;

import DATABASE_DAO.UsernameDatabases.RankingsDatabase;
import DATABASE_DAO.UsernameDatabases.UserQuizDatabase;
import javafx.util.Pair;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class UserQuizDatabaseTest extends TestCase {

    private UserQuizDatabase database;



    @BeforeEach
    public void setUp() throws SQLException {
        database = new UserQuizDatabase();
        database.clearAllTables();
        init();
    }

    private void init() throws SQLException {
        String username = "lmebo";
        database.add(username, 1, new Timestamp(System.currentTimeMillis()));
        database.add(username, 2, new Timestamp(System.currentTimeMillis()));
        database.add(username, 3, new Timestamp(System.currentTimeMillis()));

        username = "dushki";
        database.add(username, 4, new Timestamp(System.currentTimeMillis()));
        database.add(username, 5, new Timestamp(System.currentTimeMillis()));
        database.add(username, 6, new Timestamp(System.currentTimeMillis()));
    }

    @Test
    public void testAdd() throws SQLException {
        List<Integer> list = database.getQuizes("lmebo");
        Collections.sort(list);
        assertEquals(list, Arrays.asList(1,2,3));
        list = database.getQuizes("dushki");
        Collections.sort(list);
        assertEquals(list, Arrays.asList(4,5,6));
    }

    @Test
    public void testRemove() throws SQLException {
        database.remove("lmebo");
        assertEquals(Arrays.asList(), database.getQuizes("lmebo"));
        List<Integer> list = database.getQuizes("dushki");
        Collections.sort(list);
        assertEquals(Arrays.asList(4, 5, 6), list);

    }

    @Test
    public void testCase() throws SQLException{
        assertEquals((int)database.numberOfCreatedQuizes("lmebo"), 3);
        List<Pair<Integer, Integer>> result = database.getRecentCreatedQuizes();
        result.sort(Comparator.comparing(Pair::getKey));
        for(int i = 1; i <= 6; i++){
            assertEquals((int)result.get(i - 1).getKey() , i);
            assertEquals((int)result.get(i - 1).getValue() , 0);
        }
    }


}
