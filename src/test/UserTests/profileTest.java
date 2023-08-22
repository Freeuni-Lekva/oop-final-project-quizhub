package UserTests;

import DATABASE_DAO.QuizDatabases.QuestionsDatabase;
import DATABASE_DAO.QuizDatabases.QuizDatabase;
import DATABASE_DAO.QuizDatabases.QuizQuestionDatabase;
import DATABASE_DAO.QuizDatabases.TagsQuizDatabase;
import DATABASE_DAO.UsernameDatabases.*;
import Questions_DAO.QuestionResponse;
import Usernames_DAO.UserQuiz.UserCreatesQuiz;
import Usernames_DAO.UserQuiz.UserTakesQuiz;
import Usernames_DAO.manager.FriendshipManager;
import Usernames_DAO.manager.accountManager;
import Usernames_DAO.models.User;
import Usernames_DAO.models.profile;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class profileTest extends TestCase {
    private UserQuizDatabase uq_db;
    private RankingsDatabase rank_db;
    private FriendsDatabase fr_db;
    private QuizDatabase q_db;
    private QuizQuestionDatabase qq_db;
    private QuestionsDatabase Question_db;

    @BeforeEach
    protected void setUp() throws SQLException {
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
    @Test
    public void testProfile1() throws Exception {
        User u = new User("vako",false);
        profile prof = new profile("vako");
        accountManager accManager = new accountManager();
        accManager.addAcc("vako", "123");
        UserCreatesQuiz quiz = new UserCreatesQuiz(u);
        quiz.addQuestion(new QuestionResponse("1+1?", "2", false,false));
        quiz.FinishAndPublish();
        Timestamp t = new Timestamp(System.currentTimeMillis());
        UserTakesQuiz take = new UserTakesQuiz(u, 1, t, false);
        ArrayList<String> ans = new ArrayList<>();
        ans.add("2");
        take.submitQuestion(ans);
        take.finish();
        User luka = new User("luka",false);
        accManager.addAcc("luka", "123");
        FriendshipManager fr = new FriendshipManager();
        fr.addFriendshipRequest("luka", "vako");
        fr.confirmFriendshipRequest("vako", "luka");
        fr.addFriendshipRequest("vako", "luka");
        prof.ShortestTime().toString();
        assertEquals(1,prof.getCompletedQuizes());
        assertEquals(1,prof.getCreatedQuizes());
        assertEquals(1,prof.getFriendCount());
        assertEquals(1,prof.getFriends().size());
        assertEquals(100,prof.getHighestScore());
        assertEquals(2,prof.AwardCount());
        assertEquals(prof.AwardCount(),prof.getAchievment().size());
        accManager.addAcc("vako","123");
        prof.setNotification("12",true);
        assertTrue(prof.getNotification("12"));
        assertEquals("vako",prof.getUserHistory("vako").get(0).getKey().getCreatorName());
    }

    @Test
    public void TestTime() {
        String time = profile.timeToString(2*86400 + 2*3600 + 548);
        assertTrue(time.contains("d"));
        time = profile.timeToString(1008957);
        assertTrue(time.contains("h"));
        time = profile.timeToString(2756);
        assertTrue(time.contains("m"));
    }

}
