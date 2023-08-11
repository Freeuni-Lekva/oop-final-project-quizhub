package Questions_DAO;

import java.util.ArrayList;

public interface Question {
    int getType();
    String getQuestion();
    ArrayList<String> getTexts();
    String getImage();
    ArrayList<String> getPossibleAnswers();
    ArrayList<String> getAnswers();
    boolean isOrdered();
    boolean isCaseSensitive();
    int checkAnswer(ArrayList<String> user_answers);
    int getMaxScore();
}