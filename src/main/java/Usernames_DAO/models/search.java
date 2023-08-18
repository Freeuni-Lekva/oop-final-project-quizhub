package Usernames_DAO.models;

import DATABASE_DAO.QuizDatabases.QuizDatabase;
import DATABASE_DAO.QuizDatabases.QuizQuestionDatabase;
import DATABASE_DAO.QuizDatabases.TagsQuizDatabase;
import DATABASE_DAO.UsernameDatabases.UsersDatabase;
import Questions_DAO.Question;
import Questions_DAO.Quiz;
import javafx.util.Pair;


import java.sql.SQLException;
import java.util.ArrayList;

public class search {
    private QuizDatabase Q_db;
    private UsersDatabase U_db;
    private TagsQuizDatabase T_db;
    public search() throws SQLException {
        Q_db = new QuizDatabase();
        U_db = new UsersDatabase();
        T_db = new TagsQuizDatabase();
    }
    public ArrayList<Pair<Quiz,Integer>> getSearchedQuiz(String name) throws SQLException {
        ArrayList<Pair<Quiz,Integer>> ans = new ArrayList<>();
        if(name == null || name.equals(""))return ans;
        ArrayList<Integer> list = T_db.getFoundQuizByTag(name);
        ArrayList<Pair<Quiz,Integer>> getQuizesByName = Q_db.getFoundQuizByName(name);
        for(int i=0;i<getQuizesByName.size();i++){
            ans.add(getQuizesByName.get(i));
            if(list.contains(getQuizesByName.get(i).getValue())){
                list.remove(getQuizesByName.get(i).getValue());
            }
        }
        for(int i=0;i<list.size();i++) {
            int id = list.get(i);
            TagsQuizDatabase tagsQuizDatabase = new TagsQuizDatabase();
            QuizDatabase quizDatabase = new QuizDatabase();
            Quiz quiz1 = quizDatabase.getQuiz(id);
            ArrayList<String> tags = tagsQuizDatabase.getTags(id);
            ArrayList<Question> questions = new ArrayList<>();
            Quiz quiz = new Quiz(quiz1.getQuizName(), quiz1.getCreatorName(), quiz1.getCategory(), quiz1.getDescription(),
                    tags, questions, quiz1.isRandom(), quiz1.isOnePage(), quiz1.hasImmediateCorrection(), quiz1.isPracticeMode());
            Pair<Quiz, Integer> pair = new Pair<>(quiz, id);
            ans.add(pair);
        }
        return ans;
    }
    public ArrayList<String> getUsers(String username) throws SQLException {
        ArrayList<String> ans = new ArrayList<>();
        if(username == null || username.equals(""))return ans;
        return U_db.getUsers(username);
    }
}
