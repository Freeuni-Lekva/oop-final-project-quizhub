package DatabaseTests;

import DATABASE_DAO.UsernameDatabases.AchievementDatabase;
import DATABASE_DAO.UsernameDatabases.AnnouncementDatabase;
import Usernames_DAO.models.Announcement;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;

public class AnnouncementDatabaseTest extends TestCase {
    private AnnouncementDatabase database;

    @BeforeEach
    public void setUp() throws SQLException {
        database = new AnnouncementDatabase();
        database.clearAllTables();
    }

    @Test
    public void testCase() throws SQLException {
        int id = database.getMinId(AnnouncementDatabase.tablename);
        database.add(id, "luka", "Math", "Math is very cool");
        id = database.getMinId(AnnouncementDatabase.tablename);
        database.add(id, "demetre", "Math", "Math is indeed very cool");
        id = database.getMinId(AnnouncementDatabase.tablename);
        database.add(id, "ana", "Games", "Dota is the best game");
        id = database.getMinId(AnnouncementDatabase.tablename);
        database.add(id, "luka", "Games", "Fifa is the best game");
        id = database.getMinId(AnnouncementDatabase.tablename);
        database.add(id, "demetre", "Games", "LOL is the best game");

        ArrayList<Announcement> result = database.getAnnouncements();
        result.sort(Comparator.comparing(Announcement::getName));
        Announcement announcement = result.get(0);
        assertEquals(announcement.getName() , "ana");
        assertEquals(announcement.getSubject() , "Games");
        assertEquals(announcement.getText() , "Dota is the best game");

        announcement = result.get(1);
        assertEquals(announcement.getName() , "demetre");
        assertEquals(announcement.getSubject() , "Math");
        assertEquals(announcement.getText() , "Math is indeed very cool");

        announcement = result.get(2);
        assertEquals(announcement.getName() , "demetre");
        assertEquals(announcement.getSubject() , "Games");
        assertEquals(announcement.getText() , "LOL is the best game");

        announcement = result.get(3);
        assertEquals(announcement.getName() , "luka");
        assertEquals(announcement.getSubject() , "Math");
        assertEquals(announcement.getText() , "Math is very cool");

        announcement = result.get(4);
        assertEquals(announcement.getName() , "luka");
        assertEquals(announcement.getSubject() , "Games");
        assertEquals(announcement.getText() , "Fifa is the best game");



    }


}
