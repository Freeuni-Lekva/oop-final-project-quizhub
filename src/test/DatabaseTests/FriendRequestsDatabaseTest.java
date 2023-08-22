package DatabaseTests;

import DATABASE_DAO.UsernameDatabases.FriendRequestsDatabase;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class FriendRequestsDatabaseTest extends TestCase {

    private FriendRequestsDatabase database;

    @BeforeEach
    public void setUp() throws SQLException {
        database = new FriendRequestsDatabase();
        database.clearAllTables();
    }

    @Test
    public void testAdd() throws SQLException {
        database.add("lmebo", "dushki");
        assertTrue(database.friendRequestSent("lmebo", "dushki"));
        assertFalse(database.friendRequestSent("dushki", "lmebo"));
        database.add("vardohan", "lmebo");
        assertTrue(database.friendRequestSent("vardohan", "lmebo"));
        assertFalse(database.friendRequestSent("vardohan", "dushki"));
        database.add("ana", "vako");
    }

    @Test
    public void testRemove() throws SQLException {
        database.add("lmebo", "dushki");
        assertTrue(database.friendRequestSent("lmebo", "dushki"));
        assertFalse(database.friendRequestSent("dushki", "lmebo"));
        database.remove("lmebo", "dushki");
        assertFalse(database.friendRequestSent("lmebo", "dushki"));

        database.add("vardohan", "lmebo");
        assertTrue(database.friendRequestSent("vardohan", "lmebo"));
        assertFalse(database.friendRequestSent("vardohan", "dushki"));
        database.remove("vardohan", "lmebo");
        database.remove("dushki", "vardohan"); // should do nothing
        assertFalse(database.friendRequestSent("vardohan", "lmebo"));
        assertFalse(database.friendRequestSent("dushki", "vardohan"));
    }

    @Test
    public void testCase() throws SQLException{
        database.add("lmebo", "dushki");
        database.add("vardohan", "lmebo");
        database.add("ana", "vardohan");

        assertTrue(database.friendRequestSent("lmebo", "dushki"));
        assertTrue(database.friendRequestSent("vardohan", "lmebo"));
        assertTrue(database.friendRequestSent("ana", "vardohan"));
        assertFalse(database.friendRequestSent("dushki", "lmebo"));
        assertFalse(database.friendRequestSent("dushki", "ana"));

        database.add("ana", "dushki");
        database.add("vardohan", "dushki");
        ArrayList<String> recieved = database.friendRequestsRecieved("dushki");
        Collections.sort(recieved);
        assertEquals(recieved, Arrays.asList("ana", "lmebo", "vardohan"));

        database.delete("lmebo");
        assertFalse(database.friendRequestSent("lmebo", "dushki"));
        assertFalse(database.friendRequestSent("vardohan", "lmebo"));

        recieved = database.friendRequestsRecieved("dushki");
        Collections.sort(recieved);
        assertEquals(recieved, Arrays.asList("ana", "vardohan"));
    }




}
