package UserTests;

import DATABASE_DAO.QuizDatabases.QuestionsDatabase;
import DATABASE_DAO.QuizDatabases.QuizDatabase;
import DATABASE_DAO.QuizDatabases.QuizQuestionDatabase;
import DATABASE_DAO.QuizDatabases.TagsQuizDatabase;
import DATABASE_DAO.UsernameDatabases.*;
import Usernames_DAO.manager.accountManager;
import Usernames_DAO.models.User;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class UserTest extends TestCase {
    private User u;

    @BeforeEach
    protected void setUp() throws SQLException {
        QuizDatabase database = new QuizDatabase();
        database.clearAllTables();
        u = new User("vako",true);
    }


    @Test
    public void testGetters() throws Exception {
        accountManager accManager = new accountManager();
        accManager.addAcc("vako","123");
        accManager.addAcc("luka","123");
        assertEquals("vako",u.getUsername());
        u.demoteFromAdmin();
        assertEquals(false,u.isAdmin());
        FriendsDatabase fr_db = new FriendsDatabase();
        fr_db.add("vako","luka");
        assertEquals(1,u.getFriends().size());
        u.promoteToAdmin();
        assertEquals(true,u.isAdmin());
        assertEquals(0,u.getAchievement().size());
        AchievementDatabase ach_db = new AchievementDatabase();
        ach_db.add("vako",1);
        assertEquals(1,u.getAchievement().size());
        QuizDatabase database = new QuizDatabase();
        database.clearAllTables();
    }

    @Test
    public void testGetActivity() throws Exception {
        QuizDatabase database = new QuizDatabase();
        database.clearAllTables();
        accountManager accManager = new accountManager();
        accManager.addAcc("vako","123");
        accManager.addAcc("luka","123");
        assertEquals("vako",User.getUser("vako").getUsername());
        FriendsDatabase fr_db = new FriendsDatabase();
        fr_db.add("vako","luka");
        RankingsDatabase rank_db = new RankingsDatabase();
        rank_db.add(1,"luka",1,1,rank_db.getCurrentDate(),rank_db.getCurrentDate());
        UserQuizDatabase U_db = new UserQuizDatabase();
        U_db.add("luka",1,U_db.getCurrentDate());
        QuizDatabase q_db = new QuizDatabase();
        q_db.addQuiz(1,"123","vako","123","123",false,false,false,false);
        assertEquals(2,u.FriendsActivity().size());
        database.clearAllTables();
    }

    @Test
    public void testRecents() throws SQLException {
        UserQuizDatabase uq_db = new UserQuizDatabase();
        uq_db.add("vako",1,uq_db.getCurrentDate());
        assertEquals(1,u.getRecentCreatedQuizes().size());
        RankingsDatabase rank_db = new RankingsDatabase();
        rank_db.add(1,"vako",1,1,rank_db.getCurrentDate(),rank_db.getCurrentDate());
        assertEquals(1,u.getRecentTakenQuizes().size());
    }
}
