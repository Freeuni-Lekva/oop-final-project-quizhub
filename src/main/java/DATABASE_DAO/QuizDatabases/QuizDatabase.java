package DATABASE_DAO.QuizDatabases;

import DATABASE_DAO.Database;
import Questions_DAO.Quiz;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;

public class QuizDatabase extends Database {

    public static String tablename = "QuizTable";

    public QuizDatabase() throws SQLException {
        super();
    }

    public String getQuizName(int id) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("USE " + "my_database;");
        ResultSet resultSet = statement.executeQuery("SELECT* FROM " + tablename + " WHERE id =" + id + ";");
        if(resultSet.next()){
            return resultSet.getString(2);} return "";
    }

    public void addQuiz(int id, String name, String creator, String category, String description, boolean random, boolean onePage, boolean immediateCorrection, boolean practiceMode) throws SQLException {
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
        preparedStatement.execute("USE " + "my_database;");
        preparedStatement.executeUpdate();
    }

    public Quiz getQuiz(int id) throws SQLException {
        Quiz quiz = null;
        Statement statement = connection.createStatement();
        statement.execute("USE " + "my_database;");
        ResultSet resultSet = statement.executeQuery("SELECT* FROM " + tablename + " WHERE id =" + id + ";");
        if(resultSet.next()) {
            quiz = new Quiz(resultSet.getString(2), resultSet.getString(3),
                    resultSet.getString(4), resultSet.getString(5), null, null,
                    resultSet.getBoolean(7), resultSet.getBoolean(8),
                    resultSet.getBoolean(9), resultSet.getBoolean(10));
        }
        return quiz;
    }

}
