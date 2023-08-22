package DATABASE_DAO.UsernameDatabases;

import DATABASE_DAO.ConnectionPool;
import DATABASE_DAO.Database;
import javafx.util.Pair;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserQuizDatabase extends Database {

    public static String tablename = "UserQuiz";

    private String databaseName = "my_database;";

    public UserQuizDatabase() throws SQLException {
    }


    public void add(String username, int id, Timestamp create_time) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        String insertStatement = "INSERT INTO " + tablename + " (username, id, create_time) " +
                "VALUES (?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertStatement);
        preparedStatement.setString(1, username);
        preparedStatement.setInt(2, id);
        preparedStatement.setTimestamp(3, create_time);
        preparedStatement.execute("USE " + databaseName);
        preparedStatement.executeUpdate();
        ConnectionPool.closeConnection(connection);
    }

    public List<Integer> getQuizes(String username) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        List<Integer> result = new ArrayList<>();
        String statementToExecute = "SELECT id FROM " + tablename + " WHERE username = ? ORDER BY create_time DESC";
        PreparedStatement statement = connection.prepareStatement(statementToExecute);
            statement.setString(1, username);
            statement.execute("USE " + databaseName);
            statement.execute();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getInt("id"));
            }

        ConnectionPool.closeConnection(connection);
        return result;
    }

    public Integer numberOfCreatedQuizes(String username) throws SQLException {
        return getQuizes(username).size();
    }

    public void remove(String username) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        String statementToExecute = "DELETE FROM " + tablename + " WHERE username = ?";
        PreparedStatement statement = connection.prepareStatement(statementToExecute);
            statement.setString(1, username);
            statement.execute("USE " + databaseName);
            statement.execute();

        ConnectionPool.closeConnection(connection);
    }

    public List<Pair<Integer, Integer>> getRecentCreatedQuizes() throws SQLException{
        Connection connection = ConnectionPool.openConnection();
        List<Pair<Integer, Integer>> result = new ArrayList<>();
        RankingsDatabase rankingsDatabase = new RankingsDatabase();
        String query = "SELECT id FROM " + tablename + " ORDER BY create_time DESC";
        Statement statement = connection.createStatement();
        statement.execute("USE " + databaseName);
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            int quizId = resultSet.getInt("id");
            int num = rankingsDatabase.quizNumberOfPerformers(quizId);
            result.add(new Pair<>(quizId, num));
        }
        ConnectionPool.closeConnection(connection);
        return result;
    }

}
