package UserTests;

import DATABASE_DAO.QuizDatabases.QuestionsDatabase;
import DATABASE_DAO.QuizDatabases.QuizDatabase;
import DATABASE_DAO.QuizDatabases.QuizQuestionDatabase;
import DATABASE_DAO.QuizDatabases.TagsQuizDatabase;
import DATABASE_DAO.UsernameDatabases.*;
import Usernames_DAO.models.record;
import junit.framework.TestCase;
import org.junit.Test;

import java.sql.SQLException;

public class recordTest extends TestCase {
    private record rec;
    public recordTest() throws SQLException {
        clearTables();
        rec = new record("vako",1);
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
    public void testGetters() throws SQLException {
        RankingsDatabase rank_db = new RankingsDatabase();
        QuizQuestionDatabase qq_db = new QuizQuestionDatabase();
        QuizDatabase q_db = new QuizDatabase();
        q_db.addQuiz(1,"quiz1","vako","math","1234",false,false,false,false);
        qq_db.addQuestion(1,1);
        rank_db.add(1,"vako",1,1,rank_db.getCurrentDate(),rank_db.getCurrentDate());
        clearTables();
    }
}
