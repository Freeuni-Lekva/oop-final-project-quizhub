package UserTests;

import DATABASE_DAO.QuizDatabases.QuestionsDatabase;
import DATABASE_DAO.QuizDatabases.QuizDatabase;
import DATABASE_DAO.QuizDatabases.QuizQuestionDatabase;
import DATABASE_DAO.QuizDatabases.TagsQuizDatabase;
import DATABASE_DAO.UsernameDatabases.*;
import Usernames_DAO.models.Achievement;
import Usernames_DAO.models.User;
import junit.framework.TestCase;
import org.junit.Test;

import java.sql.SQLException;

public class AchievementTest extends TestCase {
    private AchievementDatabase ach_db;
    private UserQuizDatabase quiz_db;
    private RankingsDatabase rank_db;

    public AchievementTest() throws SQLException {
        ach_db = new AchievementDatabase();
        ach_db.clearTable(AchievementDatabase.tablename);
        quiz_db = new UserQuizDatabase();
        quiz_db.clearTable(UserQuizDatabase.tablename);
        rank_db = new RankingsDatabase();
        rank_db.clearTable(RankingsDatabase.tablename);
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
    public void testAddAch() throws SQLException {
        User u = new User("vako",false);
        Achievement ach = new Achievement("vako",1);
        int id = ach.getAchievment_id();
        assertEquals(1,id);
        ach.alertHighestscore("vako");
        assertEquals(1,u.getAchievement().size());
        ach.alertPractice("vako");
        assertEquals(2,u.getAchievement().size());
        for(int i=0;i<10;i++){
            quiz_db.add("vako",i,null);
            ach.alertCreateQuiz("vako");
            if(i == 1)
                assertEquals(3,u.getAchievement().size());
            if(i == 5)
                assertEquals(4,u.getAchievement().size());
            if(i == 10)
                assertEquals(5,u.getAchievement().size());
        }
        for(int i = 0;i<10;i++){
            rank_db.add(i,"vako",i,1,null,null);
            ach.alertQuizTaken("vako");
        }
        assertEquals(6,u.getAchievement().size());
        ach_db.clearTable(AchievementDatabase.tablename);
        quiz_db.clearTable(UserQuizDatabase.tablename);
        rank_db.clearTable(RankingsDatabase.tablename);
    }
}
