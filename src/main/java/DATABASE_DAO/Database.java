package DATABASE_DAO;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.time.LocalDateTime;

public class Database {

    public Connection connection;

    public Database() throws SQLException {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306");
        dataSource.setUsername("root");
        dataSource.setPassword("1234");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306",
                "root", "1234");
    }

    public void clearTable(String tablename) throws SQLException {
        String clearStatement = "DELETE FROM " + tablename;
        PreparedStatement statement = connection.prepareStatement(clearStatement);
        statement.execute("USE " + "my_database;");
        statement.executeUpdate();
    }

    public int getMinId(String TableName) throws SQLException {
        int maxId = 0;
        String statementToExecute = "SELECT MAX(id) FROM " + TableName;
        Statement statement = connection.createStatement();
        statement.execute("USE " + "my_database;");
        ResultSet resultSet = statement.executeQuery(statementToExecute);
        if(resultSet.next()){
            maxId = resultSet.getInt(1);
        }

        maxId++;

        for(int i = 1; i <= maxId; i++){
            statementToExecute = "SELECT* FROM " + TableName + " WHERE id =" + i + ";";
            resultSet = statement.executeQuery(statementToExecute);
            if(!resultSet.next()){
                return i;}} return maxId;}



    public Timestamp getCurrentDate() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT NOW() AS current_datetime");

        if (resultSet.next()) {
            LocalDateTime localDateTime = resultSet.getObject("current_datetime", LocalDateTime.class);
            if (localDateTime != null) {
                return java.sql.Timestamp.valueOf(localDateTime);
            }
        }

        return null;
    }

    public void delete(int id, String tablename){
        String statementToExecute = "DELETE FROM " + tablename + " WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(statementToExecute)) {
            statement.setInt(1, id);
            statement.execute("USE " + "my_database;");
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(String username, String tablename) throws SQLException {
        String statementToExecute = "DELETE FROM " + tablename + " WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(statementToExecute)) {
            statement.setString(1, username);
            statement.execute("USE " + "my_database;");
            statement.execute();
        }
    }
}
