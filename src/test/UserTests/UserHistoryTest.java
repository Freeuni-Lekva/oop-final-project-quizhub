import DATABASE_DAO.UsernameDatabases.UserQuizDatabase;
import Usernames_DAO.models.UserHistory;
import junit.framework.TestCase;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;

public class UserHistoryTest extends TestCase {
    UserHistory hist;
    Timestamp tmp;
    public UserHistoryTest() throws SQLException {
        UserQuizDatabase  q_db = new UserQuizDatabase();
        tmp = q_db.getCurrentDate();
        hist = new UserHistory(tmp,"1",1);
    }
    public void testGetters(){
        assertEquals("1",hist.getScore());
        assertEquals(tmp,hist.getFinishDate());
        assertEquals(new Integer(1),hist.getAtionTime());
    }
}
