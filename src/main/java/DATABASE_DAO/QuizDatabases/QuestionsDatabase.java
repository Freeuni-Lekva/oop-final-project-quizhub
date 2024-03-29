package DATABASE_DAO.QuizDatabases;

import DATABASE_DAO.ConnectionPool;
import DATABASE_DAO.Database;
import Questions_DAO.*;

import java.sql.*;
import java.util.ArrayList;

public class QuestionsDatabase extends Database {

    public static String tablename = "QuestionsTable";
    private String databaseName = "my_database;";
    public QuestionsDatabase() throws SQLException {

    }

    private String ListToString(ArrayList<String> list){
        String str = "";
        if(list == null) return str;
        for(int i = 0; i < list.size(); i++){
            str += list.get(i);
            if(i < list.size() - 1) str += "//";
        }
        return str;
    }

    public void insertQuestion(int id, int type, String question, ArrayList<String> possibleAnswers, ArrayList<String> answers, boolean ordered, boolean caseSensitive) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        String possibleAnswersStr = ListToString(possibleAnswers);
        String answersStr = ListToString(answers);
        String insertStatement = "INSERT INTO " + tablename + " (id, type, question, possibleAnswers, answer, ordered, caseSensitive) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertStatement);
        preparedStatement.setInt(1, id);
        preparedStatement.setInt(2, type);
        preparedStatement.setString(3, question);
        preparedStatement.setString(4, possibleAnswersStr);
        preparedStatement.setString(5, answersStr);
        preparedStatement.setBoolean(6, ordered);
        preparedStatement.setBoolean(7, caseSensitive);
        preparedStatement.execute("USE " + databaseName);
        preparedStatement.executeUpdate();
        ConnectionPool.closeConnection(connection);
    }

    public Question getQuestion(int id) throws SQLException {
        Connection connection = ConnectionPool.openConnection();
        Question question = null;
        Statement statement = connection.createStatement();
        statement.execute("USE " + databaseName);
        ResultSet resultSet = statement.executeQuery("SELECT* FROM " + tablename + " WHERE id =" + id + ";");
        int type = -1;
        if(resultSet.next()){
            type = resultSet.getInt(2);
        }
        if(type == 1){
            question = new QuestionResponse(resultSet.getString(3), resultSet.getString(5), resultSet.getBoolean(6), resultSet.getBoolean(7));
        } else if(type == 2){
            question = new QuestionFillBlank(resultSet.getString(3), resultSet.getString(5), resultSet.getBoolean(6), resultSet.getBoolean(7));
        } else if(type == 3){
            question = new QuestionMultiChoice(resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getBoolean(6), resultSet.getBoolean(7));
        } else if(type == 4){
            question = new QuestionPictureResponse(resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getBoolean(6), resultSet.getBoolean(7));
        } else if(type == 5){
            question = new QuestionMultiAnswer(resultSet.getString(3), resultSet.getString(5), resultSet.getBoolean(6), resultSet.getBoolean(7));
        } else if(type == 6){
            question = new QuestionMultiChoiceMultiAnswer(resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getBoolean(6), resultSet.getBoolean(7));
        } else {
            question = new QuestionMatching(resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getBoolean(6), resultSet.getBoolean(7));}
        ConnectionPool.closeConnection(connection);
        return question;
    }
}