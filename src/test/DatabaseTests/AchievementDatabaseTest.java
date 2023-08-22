package DatabaseTests;

import DATABASE_DAO.UsernameDatabases.AchievementDatabase;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AchievementDatabaseTest extends TestCase {

    private AchievementDatabase database;

    @BeforeEach
    public void setUp() throws SQLException {
        database = new AchievementDatabase();
        database.clearAllTables();
    }

    @Test
    public void testCase() throws SQLException {
        database.add("lmebo", 1);
        database.add("lmebo", 2);
        database.add("lmebo", 2);
        database.add("aikay", 2);
        database.add("aikay", 2);
        database.add("dushki", 3);
        database.add("lmebo", 4);
        database.add("aikay", 1);
        database.add("vako", 1);
        database.add("aikay", 2);

        List<Integer> list = database.getAchievements("lmebo");
        Collections.sort(list);
        assertEquals(list, Arrays.asList(1,2,4));
        list = database.getAchievements("dushki");
        Collections.sort(list);
        assertEquals(database.getAchievements("dushki"), Collections.singletonList(3));
        list = database.getAchievements("aikay");
        Collections.sort(list);
        assertEquals(list, Arrays.asList(1,2));
        list = database.getAchievements("vako");
        Collections.sort(list);
        assertEquals(list, Collections.singletonList(1));

        assertTrue(database.hasAchievement("lmebo", 1));
        assertTrue(database.hasAchievement("lmebo", 2));
        assertTrue(database.hasAchievement("lmebo", 4));
        assertFalse(database.hasAchievement("lmebo", 3));

        assertTrue(database.hasAchievement("dushki", 3));
        assertFalse(database.hasAchievement("dushki", 1));

        assertTrue(database.hasAchievement("aikay", 1));
        assertTrue(database.hasAchievement("aikay", 2));
        assertFalse(database.hasAchievement("aikay", 3));

        assertTrue(database.hasAchievement("vako", 1));
        assertFalse(database.hasAchievement("vako", 2));

    }



}
