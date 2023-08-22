package DatabaseTests;


import DATABASE_DAO.UsernameDatabases.MessageDatabase;
import Usernames_DAO.message.Message;
import junit.framework.TestCase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class MessageDatabaseTest extends TestCase {

    private MessageDatabase database;

    @BeforeEach
    public void setUp() throws SQLException {
        database = new MessageDatabase();
        database.clearAllTables();
        init();
    }

    private void init() throws SQLException {
        database.add(new Message("luka", "demetre", "hello", false, -1));
        database.add(new Message("demetre", "luka", "Hello", false, -1));
        database.add(new Message("luka", "demetre", "how are you", false, -1));
        database.add(new Message("demetre", "luka", "good, you?", false, -1));
        database.add(new Message("luka", "demetre", "thanks, me too", false, -1));

        database.add(new Message("luka", "ana", "hello", false, -1));
        database.add(new Message("ana", "luka", "Hello", false, -1));
        database.add(new Message("ana", "luka", "goodbye", true, 1));
        database.add(new Message("luka", "ana", "bye", false, -1));
    }

    @Test
    public void testAdd() throws SQLException {
        assertEquals(database.getRecievedMessageSenders("luka"), Arrays.asList("ana", "demetre"));
        assertEquals(database.getRecievedMessageSenders("demetre"), Collections.singletonList("luka"));
        assertEquals(database.getRecievedMessageSenders("ana"), Collections.singletonList("luka"));
    }

    @Test
    public void testMessages() throws SQLException{
        ArrayList<Message> messages = database.getMessagesBetween("luka", "demetre");

        messages.sort(Comparator.comparing(Message::getText));

        assertEquals(messages.get(0).getFrom(), "demetre");
        assertEquals(messages.get(0).getTo(), "luka");
        assertEquals(messages.get(0).getText(), "Hello");
        assertEquals(messages.get(0).getQuiz_id(), -1);
        assertFalse(messages.get(0).isChallenge());

        assertEquals(messages.get(1).getFrom(), "demetre");
        assertEquals(messages.get(1).getTo(), "luka");
        assertEquals(messages.get(1).getText(), "good, you?");
        assertEquals(messages.get(1).getQuiz_id(), -1);
        assertFalse(messages.get(1).isChallenge());

        assertEquals(messages.get(2).getFrom(), "luka");
        assertEquals(messages.get(2).getTo(), "demetre");
        assertEquals(messages.get(2).getText(), "hello");
        assertEquals(messages.get(2).getQuiz_id(), -1);
        assertFalse(messages.get(2).isChallenge());

        assertEquals(messages.get(3).getFrom(), "luka");
        assertEquals(messages.get(3).getTo(), "demetre");
        assertEquals(messages.get(3).getText(), "how are you");
        assertEquals(messages.get(3).getQuiz_id(), -1);
        assertFalse(messages.get(3).isChallenge());

        assertEquals(messages.get(4).getFrom(), "luka");
        assertEquals(messages.get(4).getTo(), "demetre");
        assertEquals(messages.get(4).getText(), "thanks, me too");
        assertEquals(messages.get(4).getQuiz_id(), -1);
        assertFalse(messages.get(4).isChallenge());


        messages = database.getMessagesBetween("luka", "ana");

        messages.sort(Comparator.comparing(Message::getText));
        assertEquals(messages.get(0).getFrom(), "ana");
        assertEquals(messages.get(0).getTo(), "luka");
        assertEquals(messages.get(0).getText(), "Hello");
        assertEquals(messages.get(0).getQuiz_id(), -1);
        assertFalse(messages.get(0).isChallenge());

        assertEquals(messages.get(1).getFrom(), "luka");
        assertEquals(messages.get(1).getTo(), "ana");
        assertEquals(messages.get(1).getText(), "bye");
        assertEquals(messages.get(1).getQuiz_id(), -1);
        assertFalse(messages.get(1).isChallenge());

        assertEquals(messages.get(2).getFrom(), "ana");
        assertEquals(messages.get(2).getTo(), "luka");
        assertEquals(messages.get(2).getText(), "goodbye");
        assertEquals(messages.get(2).getQuiz_id(), 1);
        assertTrue(messages.get(2).isChallenge());

        assertEquals(messages.get(3).getFrom(), "luka");
        assertEquals(messages.get(3).getTo(), "ana");
        assertEquals(messages.get(3).getText(), "hello");
        assertEquals(messages.get(3).getQuiz_id(), -1);
        assertFalse(messages.get(3).isChallenge());






    }

    @Test
    public void testDelete() throws SQLException {
        database.delete("luka");
        ArrayList<Message> messages = database.getMessagesBetween("luka", "demetre");
        assertTrue(messages.isEmpty());
    }

}