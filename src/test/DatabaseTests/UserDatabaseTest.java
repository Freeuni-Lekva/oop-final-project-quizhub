package DatabaseTests;

import DATABASE_DAO.UsernameDatabases.UsersDatabase;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

public class UserDatabaseTest extends TestCase {

    private UsersDatabase database;

    @BeforeEach
    public void setUp() throws SQLException {
        database = new UsersDatabase();
        database.clearAllTables();
    }

    @Test
    public void testAdd() throws SQLException {
        String username = "lmebo";
        String password = "lmebo17";
        database.add(username, password, true);

        username = "dushki";
        password = "dushki21";
        database.add(username, password, true);

        username = "vardohan";
        password = "vardohan21";
        database.add(username, password, true);

        username = "aikay";
        password = "aikay21";
        database.add(username, password, true);

        assertTrue(database.isAdministrator("lmebo"));
        assertTrue(database.isAdministrator("dushki"));
        assertTrue(database.isAdministrator("vardohan"));
        assertTrue(database.isAdministrator("aikay"));

        assertEquals((int)database.numberOfUsers(), 4);
        List<String> users = database.getUsers("d");
        assertTrue(users.contains("dushki"));
        assertTrue(users.contains("vardohan"));
        assertFalse(users.contains("lmebo"));

        database.removeAdministrator("lmebo");
        assertFalse(database.isAdministrator("lmebo"));
        database.promoteAdministrator("lmebo");
        assertTrue(database.isAdministrator("lmebo"));
    }

    @Test
    public void testContains() throws SQLException {
        String username = "lmebo";
        String password = "lmebo17";
        database.add(username, password, true);

        assertTrue(database.contains("lmebo"));
        assertFalse(database.contains("dushki"));

        database.delete("lmebo", UsersDatabase.tablename);
        assertFalse(database.contains("lmebo"));
    }


    @Test public void testNotifications() throws SQLException{
        String username = "lmebo";
        String password = "lmebo17";
        database.add(username, password, true);
        database.setNotification("lmebo", "message", true);
        assertTrue(database.getNotification("lmebo", "message"));
        assertFalse(database.getNotification("lmebo", "challenge"));
        database.setNotification("lmebo", "message", false);
        assertFalse(database.getNotification("lmebo", "message"));
        database.setNotification("lmebo", "request", true);
        database.setNotification("lmebo", "challenge", true);
        assertTrue(database.getNotification("lmebo", "request"));
        assertTrue(database.getNotification("lmebo", "challenge"));

    }

}
