package UserTests;

import DATABASE_DAO.QuizDatabases.QuestionsDatabase;
import DATABASE_DAO.QuizDatabases.QuizDatabase;
import DATABASE_DAO.QuizDatabases.QuizQuestionDatabase;
import DATABASE_DAO.QuizDatabases.TagsQuizDatabase;
import DATABASE_DAO.UsernameDatabases.*;
import Usernames_DAO.manager.accountManager;
import Usernames_DAO.models.search;
import junit.framework.TestCase;

import java.sql.SQLException;

public class searchTest extends TestCase {
    private search srch;
    public searchTest() throws SQLException {
        srch = new search();
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

    public void testGetUser() throws Exception {
        accountManager accmanager = new accountManager();
        accmanager.addAcc("vako","123");
        srch.getUsers("vako");
        assertEquals(1,srch.getUsers("vako").size());
        accmanager.addAcc("vako1","123");
        srch.getUsers("vako");
        assertEquals(2,srch.getUsers("vako").size());
    }

    public void testGetQuiz() throws SQLException {
        QuizDatabase q_db = new QuizDatabase();
        q_db.addQuiz(1,"quiz1","vako","math","123",false,false,false,false);
        assertEquals(1,srch.getSearchedQuiz("quiz").size());
        q_db.addQuiz(2,"q","vako","math","123",false,false,false,false);
        TagsQuizDatabase tag_db = new TagsQuizDatabase();
        tag_db.addQuiz("quiz1",2);
        assertEquals(2,srch.getSearchedQuiz("qu").size());
    }
}
