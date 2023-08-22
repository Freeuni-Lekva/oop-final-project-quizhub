package DATABASE_DAO.QuizDatabases;

import DATABASE_DAO.ConnectionPool;
import DATABASE_DAO.Database;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;

public class TagsQuizDatabase extends Database {

    public static String tablename = "Tags_QuizTable";

    private String databaseName = "my_database;";

    public TagsQuizDatabase() throws SQLException {
    }


    public void addQuiz(String tags, int quiz_id) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        String insertStatement = "INSERT INTO " + tablename + " (tags, id) " +
                "VALUES (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertStatement);
        preparedStatement.setString(1, tags);
        preparedStatement.setInt(2, quiz_id);
        preparedStatement.execute("USE " + databaseName);
        preparedStatement.executeUpdate();
        ConnectionPool.closeConnection(connection);
    }

    public ArrayList<String> getTags(int quiz_id) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        ArrayList<String> list = new ArrayList<>();
        Statement statement = connection.createStatement();
        statement.execute("USE " + databaseName);
        ResultSet resultSet = statement.executeQuery("SELECT tags FROM " + tablename + " WHERE id =" + quiz_id + ";");
        while(resultSet.next()){
            list.add(resultSet.getString("tags"));
        }
        ConnectionPool.closeConnection(connection);
        return list;
    }


    public ArrayList<Integer> getFoundQuizByTag(String tag) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        ArrayList<Integer> list = new ArrayList<>();
        Statement statement = connection.createStatement();
        statement.execute("USE " + databaseName);
        ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tablename + " WHERE tags LIKE '%" + tag + "%';");
        while(resultSet.next()){
            int id = resultSet.getInt("id");
            list.add(id);
        }
        ConnectionPool.closeConnection(connection);
        return list;
    }
}
