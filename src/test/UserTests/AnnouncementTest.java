package UserTests;

import DATABASE_DAO.QuizDatabases.QuestionsDatabase;
import DATABASE_DAO.QuizDatabases.QuizDatabase;
import DATABASE_DAO.QuizDatabases.QuizQuestionDatabase;
import DATABASE_DAO.QuizDatabases.TagsQuizDatabase;
import DATABASE_DAO.UsernameDatabases.*;
import Usernames_DAO.manager.AdminManager;
import Usernames_DAO.manager.HomepageManager;
import junit.framework.TestCase;

import java.sql.SQLException;

public class AnnouncementTest extends TestCase {
    private AdminManager manager;
    public AnnouncementTest() throws SQLException {
        clearTables();
        manager = new AdminManager("admin");
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
