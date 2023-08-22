package UserTests;

import DATABASE_DAO.QuizDatabases.QuestionsDatabase;
import DATABASE_DAO.QuizDatabases.QuizDatabase;
import DATABASE_DAO.QuizDatabases.QuizQuestionDatabase;
import DATABASE_DAO.QuizDatabases.TagsQuizDatabase;
import DATABASE_DAO.UsernameDatabases.*;
import Usernames_DAO.UserQuiz.UserCreatesQuiz;
import Usernames_DAO.manager.accountManager;
import Usernames_DAO.models.User;
import Usernames_DAO.models.search;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class searchTest extends TestCase {
    private search srch;

    @BeforeEach
    protected void setUp() throws SQLException {
        srch = new search();
        QuizDatabase database = new QuizDatabase();
        database.clearAllTables();
    }

    @Test
    public void testGetUser() throws Exception {
        accountManager accmanager = new accountManager();
        accmanager.addAcc("vako","123");
        srch.getUsers("vako");
        assertEquals(1,srch.getUsers("vako").size());
        accmanager.addAcc("vako1","123");
        srch.getUsers("vako");
        assertEquals(2,srch.getUsers("vako").size());
    }

    @Test
    public void testGetQuiz() throws SQLException {
        User user = new User("vako", false);
        UserCreatesQuiz quiz = new UserCreatesQuiz(user);
        quiz.setQuizName("quiz1");
        quiz.FinishAndPublish();
        assertEquals(1,srch.getSearchedQuiz("quiz").size());
        quiz = new UserCreatesQuiz(user);
        quiz.setQuizName("q");
        quiz.setTags("quiz", "", "");
        quiz.FinishAndPublish();
        assertEquals(2,srch.getSearchedQuiz("qu").size());
        quiz = new UserCreatesQuiz(user);
        quiz.setQuizName("name");
        quiz.setTags("name", "", "");
        quiz.FinishAndPublish();
        assertEquals(1, srch.getSearchedQuiz("name").size());
    }
}
