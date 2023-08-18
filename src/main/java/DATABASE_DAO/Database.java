package DATABASE_DAO;


import java.sql.*;
import java.time.LocalDateTime;

public class Database {

    private String databaseName = "my_database;";

    public Database() throws SQLException {

    }

    public Database(String under_score) throws SQLException{
        databaseName = "test_database;";
    }

    public void clearTable(String tablename) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        String clearStatement = "DELETE FROM " + tablename;
        PreparedStatement statement = connection.prepareStatement(clearStatement);
        statement.execute("USE " + databaseName);
        statement.executeUpdate();
        ConnectionPool.closeConnection(connection);
    }

    public int getMinId(String TableName) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        int maxId = 0;
        String statementToExecute = "SELECT MAX(id) FROM " + TableName;
        Statement statement = connection.createStatement();
        statement.execute("USE " + databaseName);
        ResultSet resultSet = statement.executeQuery(statementToExecute);
        if(resultSet.next()){
            maxId = resultSet.getInt(1);
        }
        maxId++;
        for(int i = 1; i <= maxId; i++){
            statementToExecute = "SELECT* FROM " + TableName + " WHERE id =" + i + ";";
            resultSet = statement.executeQuery(statementToExecute);
            if(!resultSet.next()){
                maxId = i;
                break;
            }
        }
        ConnectionPool.closeConnection(connection);
        return maxId;
    }



    public Timestamp getCurrentDate() throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        Timestamp result = null;
        Statement statement = connection.createStatement();
        statement.execute("USE " + databaseName);
        ResultSet resultSet = statement.executeQuery("SELECT NOW() AS current_datetime");
        if (resultSet.next()) {
            LocalDateTime localDateTime = resultSet.getObject("current_datetime", LocalDateTime.class);
            if (localDateTime != null) {
                result = java.sql.Timestamp.valueOf(localDateTime);
            }
        }
        ConnectionPool.closeConnection(connection);
        return result;
    }

    public void delete(int id, String tablename) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        String statementToExecute = "DELETE FROM " + tablename + " WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(statementToExecute);
        statement.setInt(1, id);
        statement.execute("USE " + databaseName);
        statement.executeUpdate();
        ConnectionPool.closeConnection(connection);
    }

    public void delete(String username, String tablename) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        String statementToExecute = "DELETE FROM " + tablename + " WHERE username = ?";
        PreparedStatement statement = connection.prepareStatement(statementToExecute);
        statement.setString(1, username);
        statement.execute("USE " + databaseName);
        statement.execute();
        ConnectionPool.closeConnection(connection);
    }
}
