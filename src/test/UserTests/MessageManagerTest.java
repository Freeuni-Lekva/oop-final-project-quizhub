package UserTests;

import DATABASE_DAO.QuizDatabases.QuestionsDatabase;
import DATABASE_DAO.QuizDatabases.QuizDatabase;
import DATABASE_DAO.QuizDatabases.QuizQuestionDatabase;
import DATABASE_DAO.QuizDatabases.TagsQuizDatabase;
import DATABASE_DAO.UsernameDatabases.*;
import Usernames_DAO.manager.FriendshipManager;
import Usernames_DAO.message.FriendRequest;
import Usernames_DAO.message.Message;
import Usernames_DAO.message.MessageManager;
import junit.framework.TestCase;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;

public class MessageManagerTest extends TestCase {
    private MessageManager mm;
    MessageDatabase msg_db;
    public MessageManagerTest() throws SQLException {
        mm = new MessageManager();
        msg_db = new MessageDatabase();
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
    public void testAdd() throws SQLException {
        FriendsDatabase fr_db = new FriendsDatabase();
        fr_db.add("me","you");
        mm.sendMessage(new Message("me","you","text",false,-1));
        mm.sendMessage(new Message("mee","you","text",false,-1));
        mm.sendMessage(new Message("meee","you","text",false,-1));
        assertEquals(mm.getMessages("me","you").get(0).getText(),"text");
        assertEquals(mm.getMessages("mee","you").get(0).getText(),"text");
        assertEquals(mm.getMessages("meee","you").get(0).getText(),"text");
        assertEquals(1,mm.getMessagesSearch("me","you").size());
        msg_db.clearTable(MessageDatabase.tablename);
    }

    @Test
    public void testGet() throws SQLException {
        mm.sendMessage(new Message("me","you","text",false,-1));
        mm.sendMessage(new Message("mee","you","text",false,-1));
        ArrayList<String> result3 = mm.getRecievedUsers("you");
        ArrayList<Message>result = mm.getMessages("me","you");
        ArrayList<Message>result2 = mm.getMessages("mee","you");
        assertEquals("text",result.get(0).getText());
        assertEquals("text",result2.get(0).getText());
        assertEquals("me",result3.get(0));
        assertEquals("mee",result3.get(1));
        msg_db.clearTable(MessageDatabase.tablename);
    }

    @Test
    public void testFriendRequest() throws SQLException {
        FriendshipManager manager = new FriendshipManager();
        manager.addFriendshipRequest("vako","luka");
        ArrayList<String> req = mm.getFriendRequests("luka");
        assertEquals("vako",req.get(0));
        msg_db.clearTable(MessageDatabase.tablename);
        msg_db.clearTable(FriendRequestsDatabase.tablename);
    }
}
