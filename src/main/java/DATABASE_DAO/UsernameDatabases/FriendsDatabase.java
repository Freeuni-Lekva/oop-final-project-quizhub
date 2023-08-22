package DATABASE_DAO.UsernameDatabases;

import DATABASE_DAO.ConnectionPool;
import DATABASE_DAO.Database;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FriendsDatabase extends Database {

    public static String tablename = "Friends";

    private String databaseName = "my_database;";

    public FriendsDatabase() throws SQLException {
    }

    public void remove(String username) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        String query = "DELETE FROM " + tablename + " WHERE (username_from = ?) OR (username_to = ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, username);
        statement.setString(2, username);
        statement.execute("USE " + databaseName);
        statement.executeUpdate();
        ConnectionPool.closeConnection(connection);
    }

    public void add(String username_from, String username_to) throws SQLException {
        if(areFriends(username_from, username_to)) return;
        Connection connection = ConnectionPool.openConnection();
        String insertStatement = "INSERT INTO " + tablename + " (username_from, username_to) " +
                "VALUES (?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertStatement);
        preparedStatement.setString(1, username_from);
        preparedStatement.setString(2, username_to);
        preparedStatement.execute("USE " + databaseName);
        preparedStatement.executeUpdate();
        ConnectionPool.closeConnection(connection);
    }

    public boolean areFriends(String username_from, String username_to) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        boolean flag = false;
        String query = "SELECT * FROM " + tablename + " WHERE (username_from = ? AND username_to = ?) OR (username_from = ? AND username_to = ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, username_from);
        statement.setString(2, username_to);
        statement.setString(3, username_to);
        statement.setString(4, username_from);
        statement.execute("USE " + databaseName);
        ResultSet resultSet = statement.executeQuery();
        flag = resultSet.next();
        ConnectionPool.closeConnection(connection);
        return flag;
    }

    public void removeOneFriend(String username_from, String username_to) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        String query = "DELETE FROM " + tablename + " WHERE (username_from = ? AND username_to = ?) OR (username_from = ? AND username_to = ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, username_from);
        statement.setString(2, username_to);
        statement.setString(3, username_to);
        statement.setString(4, username_from);
        statement.execute("USE " + databaseName);
        statement.executeUpdate();
        ConnectionPool.closeConnection(connection);
    }




    public List<String> getFriends(String username) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        List<String> result = new ArrayList<>();
        String query = "SELECT * FROM " + tablename + " WHERE (username_from = ?) OR (username_to = ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, username);
        statement.setString(2, username);
        statement.execute("USE " + databaseName);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            String user1 = resultSet.getString("username_from");
            String user2 = resultSet.getString("username_to");
            if(user1.equals(username)){
                result.add(user2);
            } else result.add(user1);
        }
        ConnectionPool.closeConnection(connection);
        return result;
    }

    public ArrayList<String> getUsernamesLike(String usernameFlag, String username) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        String start = "%";
        usernameFlag += "%";
        start += usernameFlag;
        usernameFlag = start;
        ArrayList<String> result = new ArrayList<>();
        String query = "SELECT username_from, username_to FROM " + tablename + " WHERE (username_from LIKE ? AND username_to = ?) OR (username_from = ? AND username_to LIKE ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, usernameFlag);
        statement.setString(2, username);
        statement.setString(3, username);
        statement.setString(4, usernameFlag);
        statement.execute("USE " + databaseName);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            String from = resultSet.getString("username_from");
            String to = resultSet.getString("username_to");
            if(!from.equals(username)){
                result.add(from);
            } else if(!to.equals(username)){
                result.add(to);
            }
        }
        ConnectionPool.closeConnection(connection);
        return result;
    }
}
