package UserTests;

import DATABASE_DAO.QuizDatabases.QuestionsDatabase;
import DATABASE_DAO.QuizDatabases.QuizDatabase;
import DATABASE_DAO.QuizDatabases.QuizQuestionDatabase;
import DATABASE_DAO.QuizDatabases.TagsQuizDatabase;
import DATABASE_DAO.UsernameDatabases.*;
import Usernames_DAO.manager.AdminManager;
import Usernames_DAO.manager.HomepageManager;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class HomePageTest extends TestCase {
    private  AnnouncementDatabase ann;
    private HomepageManager hmp;

    @BeforeEach
    protected void setUp() throws SQLException {
        ann = new AnnouncementDatabase();
        ann.clearTable(AnnouncementDatabase.tablename);
        hmp = new HomepageManager();
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
    public void testGetAnnouncements() throws SQLException {
        AdminManager manager = new AdminManager("admin");
        manager.addAnnouncement("vako","1","text");

        assertEquals(1,hmp.getAnnouncements().size());
        ann.clearTable(AnnouncementDatabase.tablename);
    }
    @Test
    public void testgetPopularQuizes() throws SQLException {
        QuizDatabase q_db = new QuizDatabase();
        q_db.addQuiz(1,"12","vako","1","1",false,false,false,false);
        assertEquals(1,hmp.getPopularQuizes().size());
        UserQuizDatabase us_db = new UserQuizDatabase();
        us_db.add("vako",1,null);
        assertEquals(1,hmp.getRecentQuizes().size());
        q_db.clearTable(QuizDatabase.tablename);
        us_db.clearTable(UserQuizDatabase.tablename);
    }
}
