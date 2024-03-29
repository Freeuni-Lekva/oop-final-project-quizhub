package UserTests;

import DATABASE_DAO.UsernameDatabases.UserQuizDatabase;
import Usernames_DAO.models.UserAction;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Timestamp;

public class UserActionTest extends TestCase {
    private UserAction userAction;
    private Timestamp tmp;

    @BeforeEach
    protected void setUp() throws SQLException {
        UserQuizDatabase uq_db = new UserQuizDatabase();
        tmp = uq_db.getCurrentDate();
        userAction = new UserAction("Ranking",1,"vako",tmp,"10");
    }

    @Test
    public void testGetters(){
        assertEquals("Ranking",userAction.getSource());
        assertEquals(1,userAction.quizId());
        assertEquals("vako", userAction.getUsername());
        assertEquals("10",userAction.getScore());
        assertEquals("Source: " + "Ranking" + ", ID: " + 1 + ", Username: " + "vako" + ", Action Time: " + tmp + ", Score: " + "10",userAction.toString());
        assertEquals(tmp,userAction.getActionTime());
    }
}
