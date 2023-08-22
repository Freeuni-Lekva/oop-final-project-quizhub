package UserTests;

import DATABASE_DAO.QuizDatabases.QuestionsDatabase;
import DATABASE_DAO.QuizDatabases.QuizDatabase;
import DATABASE_DAO.QuizDatabases.QuizQuestionDatabase;
import DATABASE_DAO.QuizDatabases.TagsQuizDatabase;
import DATABASE_DAO.UsernameDatabases.*;
import junit.framework.TestCase;
import Usernames_DAO.manager.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;


public class accountManagerTest extends TestCase {
    accountManager manager;
    private UsersDatabase userDB;

    @BeforeEach
    protected void setUp() throws Exception {
        super.setUp();
        manager = new accountManager();
        userDB = new UsersDatabase();
        clearTables();
    }

    public void clearTables() throws SQLException {
        UsersDatabase UserDB = new UsersDatabase();
        UserDB.clearTable(UsersDatabase.tablename);
        UserDB.clearTable(RankingsDatabase.tablename);
        UserDB.clearTable(FriendsDatabase.tablename);
        UserDB.clearTable(QuizDatabase.tablename);
        UserDB.clearTable(QuizQuestionDatabase.tablename);
        UserDB.clearTable(QuestionsDatabase.tablename);
        UserDB.clearTable(UserQuizDatabase.tablename);
        UserDB.clearTable(AchievementDatabase.tablename);
        UserDB.clearTable(AnnouncementDatabase.tablename);
        UserDB.clearTable(FriendRequestsDatabase.tablename);
        UserDB.clearTable(MessageDatabase.tablename);
        UserDB.clearTable(TagsQuizDatabase.tablename);
    }
    @Test
    public void testAdd() throws Exception {
        assertTrue( manager.addAcc("name", "pass") );
        assertTrue( manager.addAcc("name1", "pass1") );
        assertTrue( manager.addAcc("name2", "pass") );
        assertTrue( manager.addAcc("name3", "pass2") );

        assertFalse( manager.addAcc("name", "pass") );

        assertFalse( manager.addAcc("", "pass") );
        assertFalse( manager.addAcc("name5", "") );
        assertFalse( manager.addAcc("", "") );

        assertFalse( manager.addAcc("name6", null) );
        assertFalse( manager.addAcc(null, "pass") );
        assertFalse( manager.addAcc(null, null) );
    }
    @Test
    public void testMatch() throws Exception {
        assertFalse( manager.matchesPassword("name", "pass") );
        assertFalse( manager.matchesPassword("name", null) );
        assertFalse( manager.matchesPassword(null, "pass") );
        assertFalse( manager.matchesPassword(null, null) );

        manager.addAcc("name1", "pass1");
        manager.addAcc("name2", "pass2");
        manager.addAcc("name3", "pass3");
        manager.addAcc("name4", "pass1");

        assertTrue(manager.matchesPassword("name1", "pass1") );
        assertTrue(manager.matchesPassword("name2", "pass2") );
        assertTrue(manager.matchesPassword("name3", "pass3") );
        assertTrue(manager.matchesPassword("name4", "pass1") );

        assertFalse( manager.matchesPassword("name1", "pass100") );
        assertFalse( manager.matchesPassword("name2", "pass100") );
        assertFalse( manager.matchesPassword("name3", "pass100") );
        assertFalse( manager.matchesPassword("name4", "pass100") );

        assertFalse( manager.matchesPassword("name1", null) );
        assertFalse( manager.matchesPassword(null, "pass1") );
        assertFalse( manager.matchesPassword(null, null) );
    }

    @Test
    public void testExists() throws Exception {
        assertFalse(manager.ContainsKey(null));
        assertFalse(manager.ContainsKey("user"));

        manager.addAcc("name1", "pass1");
        manager.addAcc("name2", "pass2");
        manager.addAcc("name3", "pass3");
        manager.addAcc("name4", "pass1");

        assertTrue(manager.ContainsKey("name1"));
        assertTrue(manager.ContainsKey("name2"));
        assertTrue(manager.ContainsKey("name3"));
        assertTrue(manager.ContainsKey("name4"));

        assertFalse(manager.ContainsKey(null));
        assertFalse(manager.ContainsKey("name5"));
    }
}
