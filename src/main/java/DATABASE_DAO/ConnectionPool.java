package DATABASE_DAO;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {

    private static final int CONNECTION_NUM = 10;

    private static List<Connection> availableConnections = new ArrayList<>();

    static {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306");
        dataSource.setUsername("root");
        dataSource.setPassword("Lmebo2021.");
        for(int i = 0; i < CONNECTION_NUM; i++){
            try {
                availableConnections.add(DriverManager.getConnection("jdbc:mysql://localhost:3306",
                        "root", "Lmebo2021."));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private ConnectionPool(){

    }

    public synchronized static Connection openConnection(){
        Connection connection = availableConnections.get(availableConnections.size() - 1);
        availableConnections.remove(availableConnections.size() - 1);
        return connection;
    }

    public synchronized static void closeConnection(Connection connection){
        availableConnections.add(connection);
    }


}
