package UserTests;

import DATABASE_DAO.QuizDatabases.QuestionsDatabase;
import DATABASE_DAO.QuizDatabases.QuizDatabase;
import DATABASE_DAO.QuizDatabases.QuizQuestionDatabase;
import DATABASE_DAO.QuizDatabases.TagsQuizDatabase;
import DATABASE_DAO.UsernameDatabases.*;
import Usernames_DAO.UserQuiz.UserCreatesQuiz;
import Usernames_DAO.manager.FriendshipManager;
import Usernames_DAO.message.Message;
import Usernames_DAO.message.MessageManager;
import Usernames_DAO.models.User;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.sql.SQLException;
import java.util.ArrayList;

public class MessageManagerTest extends TestCase {
    private MessageManager mm;
    MessageDatabase msg_db;

    @BeforeEach
    protected void setUp() throws SQLException {
        mm = new MessageManager();
        msg_db = new MessageDatabase();
        QuizDatabase database = new QuizDatabase();
        database.clearAllTables();
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

    @Test
    public void testChallenge() throws SQLException {
        User user = new User("vako", false);
        UserCreatesQuiz quiz = new UserCreatesQuiz(user);
        quiz.setQuizName("name");
        int id = quiz.FinishAndPublish();
        assertEquals(id, 1);
        Message sms = new Message("vako", "luka", "name//content",true, id);
        String name = sms.getQuizName();
        assertEquals("name", name);
    }
}
