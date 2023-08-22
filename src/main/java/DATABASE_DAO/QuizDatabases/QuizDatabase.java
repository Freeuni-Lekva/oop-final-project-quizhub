package DATABASE_DAO.QuizDatabases;

import DATABASE_DAO.ConnectionPool;
import DATABASE_DAO.Database;
import Questions_DAO.Question;
import Questions_DAO.Quiz;
import javafx.util.Pair;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;

public class QuizDatabase extends Database {

    public static String tablename = "QuizTable";

    private String databaseNmae = "my_database;";

    public QuizDatabase() throws SQLException {
    }


    public String getQuizName(int id) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        String result = "";
        Statement statement = connection.createStatement();
        statement.execute("USE " + databaseNmae);
        ResultSet resultSet = statement.executeQuery("SELECT* FROM " + tablename + " WHERE id =" + id + ";");
        if(resultSet.next()){
            result = resultSet.getString(2);
        }
        ConnectionPool.closeConnection(connection);
        return result;
    }

    public void addQuiz(int id, String name, String creator, String category, String description, boolean random, boolean onePage, boolean immediateCorrection, boolean practiceMode) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        String insertStatement = "INSERT INTO " + tablename + " (id, name, creator, category, description,create_date, random, onePage, immediateCorrection, practiceMode) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertStatement);
        preparedStatement.setInt(1, id);
        preparedStatement.setString(2, name);
        preparedStatement.setString(3, creator);
        preparedStatement.setString(4, category);
        preparedStatement.setString(5, description);
        preparedStatement.setTimestamp(6, getCurrentDate());
        preparedStatement.setBoolean(7, random);
        preparedStatement.setBoolean(8, onePage);
        preparedStatement.setBoolean(9, immediateCorrection);
        preparedStatement.setBoolean(10, practiceMode);
        preparedStatement.execute("USE " + databaseNmae);
        preparedStatement.executeUpdate();
        ConnectionPool.closeConnection(connection);
    }

    public Quiz getQuiz(int id) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        Quiz quiz = null;
        Statement statement = connection.createStatement();
        statement.execute("USE " + databaseNmae);
        ResultSet resultSet = statement.executeQuery("SELECT* FROM " + tablename + " WHERE id =" + id + ";");
        if(resultSet.next()) {
            quiz = new Quiz(resultSet.getString(2), resultSet.getString(3),
                    resultSet.getString(4), resultSet.getString(5), null, null,
                    resultSet.getBoolean(7), resultSet.getBoolean(8),
                    resultSet.getBoolean(9), resultSet.getBoolean(10));
        }
        ConnectionPool.closeConnection(connection);
        return quiz;
    }

    public ArrayList<Integer> getAllQuizes() throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        ArrayList<Integer> ls = new ArrayList<>();
        Statement statement = connection.createStatement();
        statement.execute("USE " + databaseNmae);
        ResultSet resultSet = statement.executeQuery("SELECT id FROM " + tablename + ";");
        while(resultSet.next()) {
            ls.add(resultSet.getInt("id"));
        }
        ConnectionPool.closeConnection(connection);
        return ls;
    }

    public ArrayList<Pair<Quiz,Integer>> getFoundQuizByName(String name) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        TagsQuizDatabase tagsQuizDatabase = new TagsQuizDatabase();
        QuizQuestionDatabase quizQuestionDatabase = new QuizQuestionDatabase();
        ArrayList<Pair<Quiz,Integer>> list = new ArrayList<>();
        Statement statement = connection.createStatement();
        statement.execute("USE " + databaseNmae);
        ResultSet resultSet = statement.executeQuery("SELECT* FROM " + tablename + " WHERE name LIKE '%" + name + "%';");

        while(resultSet.next()){
            int id = resultSet.getInt("id");
            Quiz quiz1 = getQuiz(id);
            ArrayList<String> tags = tagsQuizDatabase.getTags(id);
            ArrayList<Question> questions = quizQuestionDatabase.getQuestions(id);
            Quiz quiz = new Quiz(quiz1.getQuizName(), quiz1.getCreatorName(), quiz1.getCategory(), quiz1.getDescription(),
                    tags, questions, quiz1.isRandom(), quiz1.isOnePage(), quiz1.hasImmediateCorrection(), quiz1.isPracticeMode());
            Pair<Quiz, Integer> pair = new Pair<>(quiz, id);
            list.add(pair);
        }

        ConnectionPool.closeConnection(connection);
        return list;
    }
}
