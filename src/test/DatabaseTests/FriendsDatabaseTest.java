package DatabaseTests;

import DATABASE_DAO.UsernameDatabases.FriendsDatabase;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FriendsDatabaseTest extends TestCase {

    private FriendsDatabase database;

    @BeforeEach
    public void setUp() throws SQLException {
        database = new FriendsDatabase();
        database.clearAllTables();
    }

    @Test
    public void testAdd() throws SQLException {
        database.add("lmebo", "dushki");
        database.add("lmebo", "aikay");
        database.add("ana", "deme");
        database.add("ana", "admin");
        assertTrue(database.areFriends("lmebo", "dushki"));
        assertTrue(database.areFriends("lmebo", "aikay"));
        assertFalse(database.areFriends("dushki", "aikay"));
    }

    @Test
    public void testRemove() throws SQLException {
        database.add("lmebo", "dushki");
        database.add("lmebo", "aikay");
        assertTrue(database.areFriends("lmebo", "dushki"));
        assertTrue(database.areFriends("lmebo", "aikay"));
        assertFalse(database.areFriends("dushki", "aikay"));

        database.removeOneFriend("lmebo", "dushki");
        assertFalse(database.areFriends("lmebo", "dushki"));
        database.add("dushki", "aikay");
        assertTrue(database.areFriends("dushki", "aikay"));

    }

    @Test
    public void testDelete() throws SQLException {
        database.add("lmebo", "dushki");
        database.add("lmebo", "aikay");
        database.add("dushki" , "aikay");
        assertTrue(database.areFriends("lmebo", "dushki"));
        assertTrue(database.areFriends("lmebo", "aikay"));
        assertTrue(database.areFriends("dushki", "aikay"));

        database.remove("lmebo");
        assertFalse(database.areFriends("lmebo", "dushki"));
        assertFalse(database.areFriends("lmebo", "aikay"));
        assertTrue(database.areFriends("dushki", "aikay"));
    }

    @Test
    public void testFriends() throws SQLException{
        database.add("lmebo", "dushki");
        database.add("lmebo", "aikay");
        database.add("dushki" , "aikay");
        database.add("lmebo", "vako");
        List<String> friends = database.getFriends("lmebo");
        assertEquals(friends, Arrays.asList("dushki", "aikay", "vako"));
        friends = database.getFriends("dushki");
        assertEquals(friends, Arrays.asList("lmebo", "aikay"));
        friends = database.getFriends("aikay");
        assertEquals(friends, Arrays.asList("lmebo", "dushki"));
        friends = database.getFriends("vako");
        assertEquals(friends, Collections.singletonList("lmebo"));

        database.add("lmebo","andro");
        assertEquals(database.getUsernamesLike("a", "lmebo"), Arrays.asList("aikay", "vako", "andro"));
        assertEquals(database.getUsernamesLike("d", "lmebo"), Arrays.asList("dushki", "andro"));

        database.add("dato", "lmebo");
        assertEquals(database.getUsernamesLike("d", "lmebo"), Arrays.asList("dushki", "andro", "dato"));
    }

}
