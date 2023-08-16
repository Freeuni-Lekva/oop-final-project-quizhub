package DATABASE_DAO.QuizDatabases;

import DATABASE_DAO.Database;
import DATABASE_DAO.QuizDatabases.QuestionsDatabase;
import Questions_DAO.Question;

import java.sql.*;
import java.util.ArrayList;

public class QuizQuestionDatabase extends Database {

    public static String tablename = "Quiz_QuestionTable";

    public QuizQuestionDatabase() throws SQLException {
    }

    public void addQuestion(int quiz_id, int question_id) throws SQLException {
        String insertStatement = "INSERT INTO " + tablename + " (id, question_id) " +
                "VALUES (?, ?)";
        PreparedStatement preparedStatement = getConnection().prepareStatement(insertStatement);
        preparedStatement.setInt(1, quiz_id);
        preparedStatement.setInt(2, question_id);
        preparedStatement.execute("USE " + "my_database;");
        preparedStatement.executeUpdate();
    }
    public ArrayList<Integer> getQuestionIds(int quiz_id) throws SQLException {
        ArrayList<Integer> list = new ArrayList<>();
        Statement statement = getConnection().createStatement();
        statement.execute("USE " + "my_database");
        ResultSet resultSet = statement.executeQuery("SELECT question_id FROM " + tablename + " WHERE id =" + quiz_id + ";");
        while(resultSet.next()){
            list.add(resultSet.getInt("question_id"));
        }
        return list;
    }

    public ArrayList<Question> getQuestions(int quiz_id) throws SQLException {
        // gathering all the question id-s occurring in the given quiz.
        ArrayList<Integer> list = getQuestionIds(quiz_id);
        // gathering questions having the selected id-s
        QuestionsDatabase questionsDatabase = new QuestionsDatabase();
        ArrayList<Question> answer = new ArrayList<>();
        for(int id : list){
            answer.add(questionsDatabase.getQuestion(id));
        }
        return answer;
    }

}
