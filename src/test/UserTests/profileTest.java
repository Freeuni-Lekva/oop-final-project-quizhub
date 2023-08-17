package UserTests;

import DATABASE_DAO.QuizDatabases.QuestionsDatabase;
import DATABASE_DAO.QuizDatabases.QuizDatabase;
import DATABASE_DAO.QuizDatabases.QuizQuestionDatabase;
import DATABASE_DAO.QuizDatabases.TagsQuizDatabase;
import DATABASE_DAO.UsernameDatabases.*;
import Questions_DAO.Quiz;
import Usernames_DAO.manager.accountManager;
import Usernames_DAO.models.User;
import Usernames_DAO.models.profile;
import junit.framework.TestCase;

import java.security.Timestamp;
import java.sql.SQLException;
import java.util.ArrayList;

public class profileTest extends TestCase {
    private UserQuizDatabase uq_db;
    private RankingsDatabase rank_db;
    private FriendsDatabase fr_db;
    private QuizDatabase q_db;
    private QuizQuestionDatabase qq_db;
    private QuestionsDatabase Question_db;
    public profileTest() throws SQLException {
        uq_db = new UserQuizDatabase();
        rank_db = new RankingsDatabase();
        fr_db = new FriendsDatabase();
        q_db = new QuizDatabase();
        qq_db = new QuizQuestionDatabase();
        Question_db = new QuestionsDatabase();
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
    public void testProfile1() throws Exception {
        User u = new User("vako",false);
        profile prof = new profile("vako");
        accountManager accManager = new accountManager();
        uq_db.add("vako",1,uq_db.getCurrentDate());
        rank_db.add(1,"vako",1,1,uq_db.getCurrentDate(),uq_db.getCurrentDate());
        fr_db.add("vako","luka");
        q_db.addQuiz(1,"12","vako","","",false,false,false,false);
        qq_db.addQuestion(1,1);
        Question_db.insertQuestion(1,1,"",new ArrayList<>(),new ArrayList<>(),false,false);
        assertEquals(1,prof.getCompletedQuizes());
        assertEquals(1,prof.getCreatedQuizes());
        assertEquals(1,prof.getFriendCount());
        assertEquals(1,prof.getFriends().size());
        assertEquals(100,prof.getHighestScore());
        assertEquals("0s",prof.ShortestTime().toString());
        assertEquals(0,prof.AwardCount());
        assertEquals(0,prof.getAchievment().size());
        accManager.addAcc("vako","123");
        prof.setNotification("12",true);
        assertTrue(prof.getNotification("12"));
        assertEquals("vako",prof.getUserHistory("vako").get(0).getKey().getCreatorName());
        uq_db.clearTable(UserQuizDatabase.tablename);
        rank_db.clearTable(RankingsDatabase.tablename);
        fr_db.clearTable(FriendsDatabase.tablename);
        q_db.clearTable(QuizDatabase.tablename);
        qq_db.clearTable(QuizQuestionDatabase.tablename);
        Question_db.clearTable(QuestionsDatabase.tablename);
        uq_db.clearTable(UsersDatabase.tablename);
    }

}
