package UserTests;

import DATABASE_DAO.QuizDatabases.QuestionsDatabase;
import DATABASE_DAO.QuizDatabases.QuizDatabase;
import DATABASE_DAO.QuizDatabases.QuizQuestionDatabase;
import DATABASE_DAO.QuizDatabases.TagsQuizDatabase;
import DATABASE_DAO.UsernameDatabases.*;
import Questions_DAO.QuestionResponse;
import Usernames_DAO.UserQuiz.UserCreatesQuiz;
import Usernames_DAO.manager.AdminManager;
import Usernames_DAO.manager.HomepageManager;
import Usernames_DAO.manager.accountManager;
import Usernames_DAO.message.Message;
import Usernames_DAO.models.User;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;

public class AdminManagerTest extends TestCase {
    private UsersDatabase db;
    private AdminManager manager;
    private accountManager accManager;
    private AchievementDatabase ach_db;
    private FriendsDatabase fr_db;
    private FriendRequestsDatabase frReq_db;
    private UserQuizDatabase userQuiz_db;
    private QuestionsDatabase Q_db;
    private QuizDatabase quiz_db;
    private QuizQuestionDatabase quizQ_db;
    private TagsQuizDatabase tag_db;
    private RankingsDatabase rank_db;
    private MessageDatabase msg_db;
    private AnnouncementDatabase ann_db;

    @BeforeEach
    protected void setUp() throws Exception {
        clearTables();
        db = new UsersDatabase();
        db.clearTable(UsersDatabase.tablename);
        accManager = new accountManager();
        manager = new AdminManager("admin");
        this.ach_db = new AchievementDatabase();
        this.fr_db = new FriendsDatabase();
        this.frReq_db = new FriendRequestsDatabase();
        this.userQuiz_db = new UserQuizDatabase();
        this.Q_db = new QuestionsDatabase();
        this.quiz_db = new QuizDatabase();
        this.quizQ_db = new QuizQuestionDatabase();
        this.tag_db = new TagsQuizDatabase();
        this.rank_db = new RankingsDatabase();
        this.msg_db = new MessageDatabase();
        this.ann_db = new AnnouncementDatabase();
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
    public void testNumOfUser() throws Exception {
        accManager.addAcc("vako","123");
        assertEquals(1,manager.numOfUsers());
        accManager.addAcc("luka","123");
        assertEquals(2,manager.numOfUsers());
        db.clearTable(UsersDatabase.tablename);
    }

    @Test
    public void testNumOfQuiz() throws SQLException {
        rank_db.add(1,"vako",1,1,null,null);
        assertEquals(1,manager.numOfQuizes());
        rank_db.add(1,"vako",1,1,null,null);
        assertEquals(2,manager.numOfQuizes());
        rank_db.clearTable(RankingsDatabase.tablename);
    }


    @Test
    public void testAddAnouncement() throws SQLException {
        manager.addAnnouncement("vako","1","2");
        HomepageManager hmanager = new HomepageManager();
        assertEquals(hmanager.getAnnouncements().get(0).getName(),"vako");
        assertEquals(hmanager.getAnnouncements().get(0).getText(),"2");
        assertEquals(hmanager.getAnnouncements().get(0).getSubject(),"1");
        ann_db.clearTable(AnnouncementDatabase.tablename);
    }

    @Test
    public void testClearHistory() throws SQLException {
        rank_db.add(1,"vako",1,1,null,null);
        manager.clearHistory(1);
        assertEquals(0,rank_db.getRecords("vako").size());
        rank_db.clearTable(RankingsDatabase.tablename);
    }
    @Test
    public void testRemoveUser() throws Exception {
        accManager.addAcc("vako","123");
        User u = new User("vako",false);
        UserCreatesQuiz quiz = new UserCreatesQuiz(u);
        quiz.addQuestion(new QuestionResponse("1+1?", "2", false,false));
        quiz.FinishAndPublish();
        manager.removeUser("vako");
        assertFalse(accManager.ContainsKey("vako"));
    }
    @Test
    public void testRemoveQuiz() throws SQLException {
        quiz_db.addQuiz(1,"12","vako","","",false,false,false,false);
        manager.removeQuiz(1);
        assertEquals("",quiz_db.getQuizName(1));
    }
}
