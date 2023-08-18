package DATABASE_DAO.UsernameDatabases;

import DATABASE_DAO.ConnectionPool;
import DATABASE_DAO.Database;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FriendRequestsDatabase extends Database {

    public static String tablename = "FriendRequests";

    private String databaseName = "my_database;";
    public FriendRequestsDatabase() throws SQLException {

    }

    public FriendRequestsDatabase(String under_score) throws SQLException{
        super(under_score);
        databaseName = "test_database;";
    }


    public void add(String username_from, String username_to) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        String insertStatement = "INSERT INTO " + tablename + " (username_from, username_to, date) " +
                "VALUES (?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertStatement);
        preparedStatement.setString(1, username_from);
        preparedStatement.setString(2, username_to);
        preparedStatement.setTimestamp(3, getCurrentDate());
        preparedStatement.execute("USE " + databaseName);
        preparedStatement.executeUpdate();
        ConnectionPool.closeConnection(connection);
    }

    public void remove(String username_from, String username_to) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        String statementToExecute = "DELETE FROM " + tablename + " WHERE username_from = ? AND username_to = ?";
        try (PreparedStatement statement = connection.prepareStatement(statementToExecute)) {
            statement.setString(1, username_from);
            statement.setString(2, username_to);
            statement.execute("USE " + databaseName);
            statement.executeUpdate();
        }
        ConnectionPool.closeConnection(connection);
    }

    public void delete(String username) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        String statementToExecute = "DELETE FROM " + tablename + " WHERE username_from = ? OR username_to = ?";
        try (PreparedStatement statement = connection.prepareStatement(statementToExecute)) {
            statement.setString(1, username);
            statement.setString(2,username);
            statement.execute("USE " + databaseName);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ConnectionPool.closeConnection(connection);
    }

    public Boolean friendRequestSent(String username_from, String username_to) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        boolean flag = false;
        String query = "SELECT * FROM " + tablename + " WHERE username_from = ? AND username_to = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, username_from);
        statement.setString(2, username_to);
        statement.execute("USE " + databaseName);
        ResultSet resultSet = statement.executeQuery();
        flag = resultSet.next();
        ConnectionPool.closeConnection(connection);
        return flag;
    }

    public ArrayList<String> friendRequestsRecieved(String username) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        ArrayList<String> result = new ArrayList<>();
        String query = "SELECT username_from FROM " + tablename + " WHERE username_to = ? ORDER BY date DESC";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, username);
        preparedStatement.execute("USE " + databaseName);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            result.add(resultSet.getString("username_from"));
        }
        ConnectionPool.closeConnection(connection);
        return result;
    }

}
