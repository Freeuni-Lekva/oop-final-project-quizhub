package DATABASE_DAO.UsernameDatabases;

import DATABASE_DAO.ConnectionPool;
import DATABASE_DAO.Database;
import Usernames_DAO.message.Message;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MessageDatabase extends Database {

    public static String tablename = "Messages";

    private String databaseName = "my_database;";
    public MessageDatabase() throws SQLException {
    }

    public MessageDatabase(String under_score) throws SQLException{
        super(under_score);
        databaseName = "test_database;";
    }

    public void delete(String username) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        String statementToExecute = "DELETE FROM " + tablename + " WHERE username_from = ? OR username_to = ?";
        PreparedStatement statement = connection.prepareStatement(statementToExecute);
        statement.setString(1, username);
        statement.setString(2,username);
        statement.execute("USE " + databaseName);
        statement.execute();
        ConnectionPool.closeConnection(connection);
    }

    public void add(Message message) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        String insertStatement = "INSERT INTO " + tablename + " (username_from, username_to, date, isChallenge, quiz_id, text) " +
                "VALUES (?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertStatement);
        preparedStatement.setString(1, message.getFrom());
        preparedStatement.setString(2, message.getTo());
        preparedStatement.setTimestamp(3, getCurrentDate());
        preparedStatement.setBoolean(4, message.isChallenge());
        preparedStatement.setInt(5, message.getQuiz_id());
        preparedStatement.setString(6, message.getText());
        preparedStatement.execute("USE " + databaseName);
        preparedStatement.executeUpdate();
        ConnectionPool.closeConnection(connection);
    }

    public ArrayList<String> getRecievedMessageSenders(String username) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        ArrayList<String> result = new ArrayList<>();
        Set<String> users = new HashSet<>();
        String query = "SELECT username_from, username_to FROM " + tablename + " WHERE username_to = ? OR username_from = ? ORDER BY date DESC";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, username);
        statement.setString(2, username);
        statement.execute("USE " + databaseName);
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()){
            String from = resultSet.getString("username_from");
            String to = resultSet.getString("username_to");
            if(!users.contains(from) && !from.equals(username)){
                result.add(from);
                users.add(from);
            } else if(!users.contains(to) && !to.equals(username)){
                result.add(to);
                users.add(to);
            }
        }
        ConnectionPool.closeConnection(connection);
        return result;
    }

    public ArrayList<Message> getMessagesBetween(String username1, String username2) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        ArrayList<Message> result = new ArrayList<>();
        String query = "SELECT* FROM " + tablename + " WHERE (username_from = ? AND username_to = ?) OR (username_from = ? AND username_to = ?) ORDER BY date DESC";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, username1);
        statement.setString(2, username2);
        statement.setString(3, username2);
        statement.setString(4, username1);
        statement.execute("USE " + databaseName);
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()){
            String username_from = resultSet.getString("username_from");
            String username_to = resultSet.getString("username_to");
            boolean isChallenge = resultSet.getBoolean("isChallenge");
            Integer quiz_id = resultSet.getInt("quiz_id");
            String text = resultSet.getString("text");
            Message message = new Message(username_from, username_to, text, isChallenge, quiz_id);
            result.add(message);
        }
        ConnectionPool.closeConnection(connection);
        return result;
    }

}
