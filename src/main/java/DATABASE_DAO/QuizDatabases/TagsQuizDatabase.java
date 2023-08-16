package DATABASE_DAO.QuizDatabases;

import DATABASE_DAO.Database;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;

public class TagsQuizDatabase extends Database {

    public static String tablename = "Tags_QuizTable";

    public TagsQuizDatabase() throws SQLException {
    }

    public void addQuiz(String tags, int quiz_id) throws SQLException {
        String insertStatement = "INSERT INTO " + tablename + " (tags, id) " +
                "VALUES (?, ?)";
        PreparedStatement preparedStatement = getConnection().prepareStatement(insertStatement);
        preparedStatement.setString(1, tags);
        preparedStatement.setInt(2, quiz_id);
        preparedStatement.execute("USE " + "my_database;");
        preparedStatement.executeUpdate();
    }

    public ArrayList<String> getTags(int quiz_id) throws SQLException {
        ArrayList<String> list = new ArrayList<>();
        Statement statement = getConnection().createStatement();
        statement.execute("USE " + "my_database");
        ResultSet resultSet = statement.executeQuery("SELECT tags FROM " + tablename + " WHERE id =" + quiz_id + ";");
        while(resultSet.next()){
            list.add(resultSet.getString("tags"));
        }
        return list;
    }


    public ArrayList<Integer> getFoundQuizByTag(String tag) throws SQLException {
        ArrayList<Integer> list = new ArrayList<>();
        Statement statement = getConnection().createStatement();
        statement.execute("USE " + "my_database;");
        ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tablename + " WHERE tags LIKE '%" + tag + "%';");
        while(resultSet.next()){
            int id = resultSet.getInt("id");
            list.add(id);
        }
        return list;
    }
}
