package DATABASE_DAO.UsernameDatabases;

import DATABASE_DAO.ConnectionPool;
import DATABASE_DAO.Database;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;

public class UsersDatabase extends Database {

    public static String tablename = "Users";

    private String databaseName = "my_database;";

    public UsersDatabase() throws SQLException {
    }


    public void add(String username, String password, Boolean isAdministrator) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        String insertStatement = "INSERT INTO " + tablename + " (username, password, administrator, challengeNotification, messageNotification, requestNotification) " +
                "VALUES (?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertStatement);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        preparedStatement.setBoolean(3, isAdministrator);
        preparedStatement.setBoolean(4, false);
        preparedStatement.setBoolean(5, false);
        preparedStatement.setBoolean(6, false);
        preparedStatement.execute("USE " + databaseName);
        preparedStatement.executeUpdate();
        ConnectionPool.closeConnection(connection);
    }

    public boolean getNotification(String username, String notification) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        boolean result = false;
        String query = "";
        if(notification.equals("request")){
            notification = "requestNotification";
        } else if(notification.equals("challenge")){
            notification = "challengeNotification";
        } else {
            notification = "messageNotification";
        }
        query = "SELECT " + notification + " FROM " + tablename + " WHERE username = ?";
        PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.execute("USE " + databaseName);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                result = resultSet.getBoolean(notification);
            }

        ConnectionPool.closeConnection(connection);
        return result;
    }

    public void setNotification(String username, String notification, Boolean flag) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        String query = "";
        if(notification.equals("request")){
            query = "UPDATE " + tablename +  " SET requestNotification = ? WHERE username = ?";
        } else if(notification.equals("challenge")){
            query = "UPDATE " + tablename +  " SET challengeNotification = ? WHERE username = ?";
        } else {
            query = "UPDATE " + tablename +  " SET messageNotification = ? WHERE username = ?";
        }
        PreparedStatement statement = connection.prepareStatement(query);
            statement.setBoolean(1, flag);
            statement.setString(2, username);
            statement.execute("USE " + databaseName);
            statement.executeUpdate();

        ConnectionPool.closeConnection(connection);
    }

    public void removeAdministrator(String username) throws SQLException{
        Connection connection = ConnectionPool.openConnection();
        String query = "UPDATE " + tablename +  " SET administrator = ? WHERE username = ?";
        PreparedStatement statement = connection.prepareStatement(query);
            statement.setBoolean(1, false);
            statement.setString(2, username);
            statement.execute("USE " + databaseName);
            statement.executeUpdate();


        ConnectionPool.closeConnection(connection);
    }

    public Boolean isAdministrator(String name) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        String query = "SELECT administrator FROM " + tablename + " WHERE username = ?";
        boolean result = false;
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, name);
        preparedStatement.execute("USE " + databaseName);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            result = resultSet.getBoolean("administrator");
        }
        ConnectionPool.closeConnection(connection);
        return result;
    }

    public void promoteAdministrator(String username) throws SQLException{
        Connection connection = ConnectionPool.openConnection();
        String query = "UPDATE " + tablename +  " SET administrator = ? WHERE username = ?";
        PreparedStatement statement = connection.prepareStatement(query);
            statement.setBoolean(1, true);
            statement.setString(2, username);
            statement.execute("USE " + databaseName);
            statement.executeUpdate();

        ConnectionPool.closeConnection(connection);
    }

    public String getPassword(String username) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        String query = "SELECT password FROM " + tablename + " WHERE username = ?";
        String result = "";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.execute("USE " + databaseName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getString("password");
            }

        ConnectionPool.closeConnection(connection);
        return result;
    }
    public Boolean contains(String username) throws SQLException {
        return !getPassword(username).equals("");
    }

    public Integer numberOfUsers() throws SQLException{
        Connection connection = ConnectionPool.openConnection();
        String query = "SELECT COUNT(DISTINCT username) AS distinct_users FROM " + tablename + ";";
        int result = 0;
        PreparedStatement statement = connection.prepareStatement(query);
            statement.execute("USE " + databaseName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt("distinct_users");
            }

        ConnectionPool.closeConnection(connection);
        return result;
    }

    public ArrayList<String> getUsers(String username) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        ArrayList<String> list = new ArrayList<>();
        Statement statement = connection.createStatement();
        statement.execute("USE " + databaseName);
        ResultSet resultSet = statement.executeQuery("SELECT* FROM " + tablename + " WHERE username LIKE '%" + username + "%';");
        while(resultSet.next()){
            list.add(resultSet.getString("username"));
        }
        ConnectionPool.closeConnection(connection);
        return list;
    }
}
