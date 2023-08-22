package DATABASE_DAO.UsernameDatabases;

import DATABASE_DAO.ConnectionPool;
import DATABASE_DAO.Database;
import Usernames_DAO.models.UserAction;
import Usernames_DAO.models.UserHistory;
import javafx.util.Pair;

import java.sql.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class RankingsDatabase extends Database {

    public static String tablename = "Rankings";

    private String databaseName = "my_database;";

    public RankingsDatabase() throws SQLException {
    }

    public void add(int id, String username, int quiz_id, int score, Timestamp start_time, Timestamp end_time) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        String insertStatement = "INSERT INTO " + tablename + " (id, username, quiz_id, score, start_time, end_time) " +
                "VALUES (?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertStatement);
        preparedStatement.setInt(1, id);
        preparedStatement.setString(2, username);
        preparedStatement.setInt(3, quiz_id);
        preparedStatement.setInt(4, score);
        preparedStatement.setTimestamp(5, start_time);
        preparedStatement.setTimestamp(6, end_time);
        preparedStatement.execute("USE " + databaseName);
        preparedStatement.executeUpdate();
        ConnectionPool.closeConnection(connection);

    }

    public List<Integer> getRecords(String username) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        List<Integer> recordIds = new ArrayList<>();
        String query = "SELECT quiz_id FROM " + tablename + " WHERE username = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, username);
        statement.execute("USE " + databaseName);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            int recordId = resultSet.getInt("quiz_id");
            recordIds.add(recordId);
        }
        ConnectionPool.closeConnection(connection);
        return recordIds;
    }

    public List<Integer> getRecentTakenQuizes(String username) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        List<Integer> recordIds = new ArrayList<>();
        String query = "SELECT quiz_id FROM " + tablename + " WHERE username = ? ORDER BY end_time DESC";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, username);
        statement.execute("USE " + databaseName);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            int recordId = resultSet.getInt("quiz_id");
            if (!recordIds.contains(recordId)){
                recordIds.add(recordId);
            }
        }
        ConnectionPool.closeConnection(connection);
        return recordIds;
    }

    public Integer distinctQuizes(String username) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        int answer = 0;
        String query = "SELECT COUNT(DISTINCT quiz_id) AS distinct_quizes FROM " + tablename + " WHERE username = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, username);
        statement.execute("USE " + databaseName);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            answer = resultSet.getInt("distinct_quizes");
        }
        ConnectionPool.closeConnection(connection);
        return answer;
    }


    public Integer UserMaxScore(String username, int quiz_id) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        int answer = 0;
        String query = "SELECT MAX(score) AS max_score FROM " + tablename + " WHERE username = ? AND quiz_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, username);
        statement.setInt(2, quiz_id);
        statement.execute("USE " + databaseName);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            answer = resultSet.getInt("max_score");
        }
        ConnectionPool.closeConnection(connection);
        return answer;
    }

    public Integer quizMaxScore(int quiz_id) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        int answer = 0;
        String query = "SELECT MAX(score) AS max_score FROM " + tablename + " WHERE quiz_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, quiz_id);
        statement.execute("USE " + databaseName);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            answer = resultSet.getInt("max_score");
        }
        ConnectionPool.closeConnection(connection);
        return answer;
    }

    public Double quizAverageScore(int quiz_id) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        double answer = (double) 0;
        String query = "SELECT AVG(score) AS avg_score FROM " + tablename + " WHERE quiz_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, quiz_id);
        statement.execute("USE " + databaseName);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            answer = resultSet.getDouble("avg_score");
        }
        ConnectionPool.closeConnection(connection);
        return answer;
    }

    public Integer quizMinTime(int quiz_id) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        int answer = 0;
        String query = "SELECT MIN(TIMESTAMPDIFF(SECOND, start_time, end_time)) AS quizMinTime FROM " + tablename + " WHERE quiz_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, quiz_id);
        preparedStatement.execute("USE " + databaseName);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            answer = resultSet.getInt("quizMinTime");
        }
        ConnectionPool.closeConnection(connection);
        return answer;
    }

    public Integer quizAverageTime(int quiz_id) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        int answer = 0;
        String query = "SELECT AVG(TIMESTAMPDIFF(SECOND, start_time, end_time)) AS quizAvgTime FROM " + tablename + " WHERE quiz_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.execute("USE " + databaseName);
        preparedStatement.setInt(1, quiz_id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            answer = resultSet.getInt("quizAvgTime");
        }
        ConnectionPool.closeConnection(connection);
        return answer;
    }

    public Integer quizNumberOfPerformers(int quiz_id) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        int answer = 0;
        String query = "SELECT COUNT(DISTINCT username) AS number FROM " + tablename + " WHERE quiz_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, quiz_id);
            statement.execute("USE " + databaseName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                answer = resultSet.getInt("number");
            }

        ConnectionPool.closeConnection(connection);
        return answer;
    }

    public UserHistory FriendMaxScore(String username, int quiz_id) throws SQLException {
        UserHistory userHistory = null;
        String query = "SELECT end_time AS finishDate , score, TIMESTAMPDIFF(SECOND, start_time, end_time) AS actionTime " +
                "FROM " + tablename +
                " WHERE username = ? AND quiz_id = ? AND score = " + UserMaxScore(username, quiz_id) + ";";
        Connection connection = ConnectionPool.openConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, quiz_id);
            preparedStatement.execute("USE " + databaseName);
            ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    Timestamp finishDate = resultSet.getTimestamp("finishDate");
                    String score = resultSet.getString("score");
                    long actiontime = resultSet.getLong("actionTime");
                    Integer actionTime = (int) actiontime;
                    userHistory = new UserHistory(finishDate, score, actionTime);
                }


        ConnectionPool.closeConnection(connection);
        return userHistory;
    }

    public List<UserAction> retrieveUserActions(String username) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        List<UserAction> userActions = new ArrayList<>();
        String userQuizQuery = "SELECT DISTINCT id, username, create_time AS action_time FROM " + UserQuizDatabase.tablename + " WHERE username = ?";
        String rankingsQuery = "SELECT DISTINCT quiz_id, username, end_time AS action_time, score FROM " + tablename + " WHERE username = ?";
        PreparedStatement userQuizStatement = connection.prepareStatement(userQuizQuery);
        PreparedStatement rankingsStatement = connection.prepareStatement(rankingsQuery);
        userQuizStatement.setString(1, username);
        rankingsStatement.setString(1, username);
        userQuizStatement.execute("USE " + databaseName);
        rankingsStatement.execute("USE " + databaseName);
        ResultSet userQuizResultSet = userQuizStatement.executeQuery();
        ResultSet rankingsResultSet = rankingsStatement.executeQuery();
        while (userQuizResultSet.next()) {
            long id = userQuizResultSet.getLong("id");
            Timestamp actionTime = userQuizResultSet.getTimestamp("action_time");
            UserAction userAction = new UserAction("UserQuiz", id, username, actionTime, "x");
            userActions.add(userAction);
        }
        while (rankingsResultSet.next()) {
            long id = rankingsResultSet.getLong("quiz_id");
            Timestamp actionTime = rankingsResultSet.getTimestamp("action_time");
            String score = rankingsResultSet.getString("score");
            UserAction userAction = new UserAction("Rankings", id, username, actionTime, score);
            userActions.add(userAction);
        }
        userActions.sort(Comparator.comparing(UserAction::getActionTime));
        ConnectionPool.closeConnection(connection);
        return userActions;
    }


    public List<UserAction> retrieveFriendsActions(String username) throws SQLException{
        List<UserAction> result = new ArrayList<>();
        FriendsDatabase friendsDatabase = new FriendsDatabase();
        List<String> friends = friendsDatabase.getFriends(username);
        for(String friend : friends){
            List<UserAction> friendActions = retrieveUserActions(friend);
            result.addAll(friendActions);
        }
        result.sort(Comparator.comparing(UserAction::getActionTime));
        return result;
    }

    public List<Pair<Integer, Integer>> getPopularQuizes() throws SQLException{
        Connection connection = ConnectionPool.openConnection();
        List<Pair<Integer, Integer>> result = new ArrayList<>();
        String query = "SELECT quiz_id, COUNT(DISTINCT username) AS quiz_count FROM " + tablename + " GROUP BY quiz_id ORDER BY quiz_count DESC";
        Statement statement = connection.createStatement();
        statement.execute("USE " + databaseName);
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            int quizId = resultSet.getInt("quiz_id");
            int quizCount = resultSet.getInt("quiz_count");
            result.add(new Pair<>(quizId, quizCount));
        }
        ConnectionPool.closeConnection(connection);
        return result;
    }
    public ArrayList<UserAction> getRecentPerformers(int quiz_id) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        ArrayList<UserAction> result = new ArrayList<>();
        HashMap<String, UserAction> userActionMap = new HashMap<>();
        String query = "SELECT username, score, " +
                "TIMESTAMPDIFF(SECOND, start_time, end_time) as elapsed_time FROM " + tablename + " WHERE quiz_id = ? ORDER BY end_time DESC";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, quiz_id);
        preparedStatement.execute("USE " + "my_database;");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String username = resultSet.getString("username");
            int score = resultSet.getInt("score");
            int actionTime = resultSet.getInt("elapsed_time");
            if (!userActionMap.containsKey(username)) {
                UserAction userAction = new UserAction("_", quiz_id, username, new Timestamp(actionTime), Integer.toString(score));
                userActionMap.put(username, userAction);
                result.add (userAction);
            }
        }
        ConnectionPool.closeConnection(connection);
        return result;
    }

    public ArrayList<UserAction> getTopPerformers(int quiz_id, String filter) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        String query = "";
        if(filter.equals("All Time")){
            query = "SELECT username, start_time, end_time, score, " +
                    "TIMESTAMPDIFF(SECOND, start_time, end_time) as elapsed_time FROM " + tablename + " WHERE quiz_id = ? " +
                    "ORDER BY score DESC, elapsed_time";
        } else {
            query =  "SELECT username, start_time, end_time, score, " +
                    "TIMESTAMPDIFF(SECOND, start_time, end_time) as elapsed_time FROM " + tablename +
                    " WHERE quiz_id = ? AND TIMESTAMPDIFF(HOUR, end_time, NOW()) <= 24 " +
                    "ORDER BY score DESC, elapsed_time";
        }
        ArrayList<UserAction> result = new ArrayList<>();
        Set<String> set = new HashSet<>();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, quiz_id);
        preparedStatement.execute("USE " + databaseName);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String username = resultSet.getString("username");
            String score = resultSet.getString("score");
            int elapsedSeconds = resultSet.getInt("elapsed_time");
            UserAction userAction = new UserAction("_", quiz_id, username, new Timestamp(elapsedSeconds), score);
            if(!set.contains(username)){
                result.add(userAction);
                set.add(username);
            }
        }
        ConnectionPool.closeConnection(connection);
        return result;

    }

    public ArrayList<UserAction> quizGetPerformers(int quiz_id, String filter) throws SQLException{
        if (filter.equals("Recent")) {
            return getRecentPerformers(quiz_id);
        } else {
            return getTopPerformers(quiz_id, filter);
        }

    }
    public List<UserHistory> getUserQuizHistory(String username, int quiz_id, String order) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        List<UserHistory> result = new ArrayList<>();
        String orderBy = "";
        if(order.equals("score")){
            orderBy = "ORDER BY score DESC, actionTime";
        } else if(order.equals("date")){
            orderBy = "ORDER BY end_time DESC, score DESC, actionTime";
        } else {
            orderBy = "ORDER BY actionTime, score DESC";
        }
        String query = "SELECT end_time AS finishDate, score, TIMESTAMPDIFF(SECOND, start_time, end_time) AS actionTime " +
                "FROM " + tablename +
                " WHERE username = ? AND quiz_id = ? " +
                orderBy;
        PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, quiz_id);
            preparedStatement.execute("USE " + databaseName);
            ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Timestamp finishDate = resultSet.getTimestamp("finishDate");
                    String score = resultSet.getString("score");
                    long actiontime = resultSet.getLong("actionTime");
                    Integer actionTime = (int) actiontime;
                    result.add(new UserHistory(finishDate, score, actionTime));
                }


        ConnectionPool.closeConnection(connection);
        return result;
    }

    public Pair<Integer, Integer> getHighestScore(String username) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        Pair<Integer,Integer> pair = new Pair<>(0,0);
        String query = "SELECT MAX(score) AS max_score,quiz_id FROM " + tablename + " WHERE username = ? GROUP BY quiz_id";
        PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.execute("USE " + databaseName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                pair = new Pair<>(resultSet.getInt("max_score"),resultSet.getInt("quiz_id"));
            }

        ConnectionPool.closeConnection(connection);
        return pair;
    }

    public Integer UserMinTime(String username) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        int answ = 0;
        String query = "SELECT MIN(TIMESTAMPDIFF(SECOND, start_time, end_time)) AS quizMinTime FROM " + tablename + " WHERE username = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.execute("USE " + databaseName);
            ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    answ = resultSet.getInt("quizMinTime");
                }


        ConnectionPool.closeConnection(connection);
        return answ;
    }

    public Integer numberOfTakenQuizes() throws SQLException{
        Connection connection = ConnectionPool.openConnection();
        int answ = 0;
        String query = "SELECT COUNT(*) AS numberOfRows FROM " + tablename + ";";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.execute("USE " + databaseName);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            answ = resultSet.getInt("numberOfRows");
        }
        ConnectionPool.closeConnection(connection);
        return answ;
    }

    public void delete(int id) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        String statementToExecute = "DELETE FROM " + tablename + " WHERE quiz_id = ?";
        PreparedStatement statement = connection.prepareStatement(statementToExecute);
        statement.setInt(1, id);
        statement.execute("USE " + databaseName);
        statement.executeUpdate();
        ConnectionPool.closeConnection(connection);
    }
}