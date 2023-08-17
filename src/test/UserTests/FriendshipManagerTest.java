package UserTests;

import DATABASE_DAO.QuizDatabases.QuestionsDatabase;
import DATABASE_DAO.QuizDatabases.QuizDatabase;
import DATABASE_DAO.QuizDatabases.QuizQuestionDatabase;
import DATABASE_DAO.QuizDatabases.TagsQuizDatabase;
import DATABASE_DAO.UsernameDatabases.*;
import Usernames_DAO.manager.FriendshipManager;
import Usernames_DAO.manager.accountManager;
import Usernames_DAO.models.User;
import junit.framework.TestCase;
import org.junit.Test;

import java.sql.SQLException;

public class FriendshipManagerTest extends TestCase {
    private FriendshipManager manager;
    private FriendRequestsDatabase frReq_db;
    private FriendsDatabase fr_db;
    private UsersDatabase U_db;
    public FriendshipManagerTest() throws SQLException {
        User u = new User("vako",false);
        manager = new FriendshipManager();
        frReq_db = new FriendRequestsDatabase();
        fr_db = new FriendsDatabase();
        U_db = new UsersDatabase();
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
    public void testAddFriendshipRequest() throws Exception {
        User u = new User("vako",false);
        U_db.add("vako","123",false);
        U_db.add("luka","123",false);
        manager.addFriendshipRequest("vako","luka");
        assertTrue(manager.FriendRequestSent("vako","luka"));
        manager.addFriendshipRequest("luka","vako");
        FriendsDatabase fr_db = new FriendsDatabase();
        fr_db.add("vako","luka");
        assertTrue(manager.areFriend("vako","luka"));
        fr_db.clearTable(FriendsDatabase.tablename);
        frReq_db.clearTable(FriendRequestsDatabase.tablename);
        U_db.clearTable(UsersDatabase.tablename);
        clearTables();
    }
    @Test
    public void testReject() throws SQLException {
        manager.addFriendshipRequest("vako","luka");
        manager.rejectFriendshipRequest("luka","vako");
        assertFalse(manager.FriendRequestSent("vako","luka"));
        fr_db.clearTable(FriendsDatabase.tablename);
        frReq_db.clearTable(FriendRequestsDatabase.tablename);
        U_db.clearTable(UsersDatabase.tablename);
    }
    @Test
    public void testCancel() throws SQLException {
        manager.addFriendshipRequest("vako","luka");
        manager.cancelFriendRequest("vako","luka");
        assertFalse(manager.FriendRequestSent("vako","luka"));
        fr_db.clearTable(FriendsDatabase.tablename);
        frReq_db.clearTable(FriendRequestsDatabase.tablename);
        U_db.clearTable(UsersDatabase.tablename);
    }

    @Test
    public void testRemove() throws SQLException {
        manager.addFriendshipRequest("vako","luka");
        manager.confirmFriendshipRequest("luka","vako");
        manager.removeFriend("vako","luka");
        assertFalse(manager.areFriend("vako","luka"));
        fr_db.clearTable(FriendsDatabase.tablename);
        frReq_db.clearTable(FriendRequestsDatabase.tablename);
        U_db.clearTable(UsersDatabase.tablename);
    }
}
