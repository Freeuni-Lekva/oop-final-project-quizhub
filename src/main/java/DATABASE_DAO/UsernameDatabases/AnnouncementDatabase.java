package DATABASE_DAO.UsernameDatabases;

import DATABASE_DAO.ConnectionPool;
import DATABASE_DAO.Database;
import Usernames_DAO.models.Announcement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class AnnouncementDatabase extends Database {

    public static String tablename = "Announcements";

    private String databaseName = "my_database;";
    public AnnouncementDatabase() throws SQLException {
    }

    public void add(int id, String username, String subject, String text) throws SQLException{
        Connection connection = ConnectionPool.openConnection();
        String insertStatement = "INSERT INTO " + tablename + " (id, username, subject, text) " +
                "VALUES (?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertStatement);
        preparedStatement.setInt(1, id);
        preparedStatement.setString(2, username);
        preparedStatement.setString(3, subject);
        preparedStatement.setString(4, text);
        preparedStatement.execute("USE " + databaseName);
        preparedStatement.executeUpdate();
        ConnectionPool.closeConnection(connection);
    }

    public ArrayList<Announcement> getAnnouncements() throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        ArrayList<Announcement> list = new ArrayList<>();
        String query = "SELECT id, username, subject, text FROM " + tablename + ";";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.execute("USE " + databaseName);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String username = resultSet.getString("username");
            String subject = resultSet.getString("subject");
            String text = resultSet.getString("text");
            Announcement announcement = new Announcement(username,subject,text);
            list.add(announcement);
        }
        ConnectionPool.closeConnection(connection);
        return list;
    }


}
