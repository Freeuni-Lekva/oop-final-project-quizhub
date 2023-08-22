package UserTests;

import DATABASE_DAO.QuizDatabases.QuestionsDatabase;
import DATABASE_DAO.QuizDatabases.QuizDatabase;
import DATABASE_DAO.QuizDatabases.QuizQuestionDatabase;
import DATABASE_DAO.QuizDatabases.TagsQuizDatabase;
import DATABASE_DAO.UsernameDatabases.*;
import Usernames_DAO.manager.AdminManager;
import Usernames_DAO.manager.HomepageManager;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.sql.SQLException;

public class AnnouncementTest extends TestCase {
    private AdminManager manager;

    @BeforeEach
    protected void setUp() throws SQLException {
        QuizDatabase database = new QuizDatabase();
        database.clearAllTables();
        manager = new AdminManager("admin");
    }

    @Test
    public void testGetters() throws SQLException {
        manager.addAnnouncement("admin","subject","text");
        HomepageManager hmp = new HomepageManager();
        assertEquals("admin",hmp.getAnnouncements().get(0).getName());
        assertEquals("subject",hmp.getAnnouncements().get(0).getSubject());
        assertEquals("text",hmp.getAnnouncements().get(0).getText());
        AnnouncementDatabase ann = new AnnouncementDatabase();
        ann.clearTable(AnnouncementDatabase.tablename);
    }
}
