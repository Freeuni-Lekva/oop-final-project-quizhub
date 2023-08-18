package DATABASE_DAO.UsernameDatabases;

import DATABASE_DAO.ConnectionPool;
import DATABASE_DAO.Database;
import Usernames_DAO.models.Achievement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AchievementDatabase extends Database {

    public static String tablename = "Achievements";

    private String databaseName = "my_database;";

    public AchievementDatabase() throws SQLException {
    }

    public AchievementDatabase(String under_score) throws SQLException{
        super(under_score);
        databaseName = "test_database;";
    }

    public void add(String username, int id) throws SQLException {
        if(hasAchievement(username,id)) return;
        Connection connection = ConnectionPool.openConnection();
        String insertStatement = "INSERT INTO " + tablename + " (username, id) " +
                "VALUES (?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertStatement);
        preparedStatement.setString(1, username);
        preparedStatement.setInt(2, id);
        preparedStatement.execute("USE " + databaseName);
        preparedStatement.executeUpdate();
        ConnectionPool.closeConnection(connection);
    }

    public List<Integer> getAchievements(String username) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        List<Integer> result = new ArrayList<>();
        String query = "SELECT id FROM " + tablename + " WHERE username = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, username);
        preparedStatement.execute("USE " + databaseName);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            result.add(resultSet.getInt("id"));
        }
        ConnectionPool.closeConnection(connection);
        return result;
    }

    public Boolean hasAchievement(String username, int id) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        List<Integer> achievements = getAchievements(username);
        ConnectionPool.closeConnection(connection);
        return achievements.contains(id);
    }
}