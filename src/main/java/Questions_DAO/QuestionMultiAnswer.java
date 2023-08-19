package Questions_DAO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import static Questions_DAO.QuestionMultiChoice.getStrings;


public class QuestionMultiAnswer implements Question {
    private final String question;
    private final ArrayList<String> answers;
    private final boolean ordered;
    private final boolean caseSensitive;
    public QuestionMultiAnswer(String question, String answer, boolean ordered, boolean caseSensitive) {
        this.question = question;
        this.ordered = ordered;
        this.caseSensitive = caseSensitive;
        this.answers = fillList(answer);
    }

    @Override
    public int getType() {
        return 5;
    }

    @Override
    public String getQuestion() {
        return question;
    }

    @Override
    public ArrayList<String> getTexts() {
        return null;
    }

    @Override
    public String getImage() {
        return null;
    }

    @Override
    public ArrayList<String> getPossibleAnswers() {
        return null;
    }

    @Override
    public ArrayList<String> getAnswers() {
        return answers;
    }

    @Override
    public boolean isOrdered() {
        return ordered;
    }

    @Override
    public boolean isCaseSensitive() {
        return caseSensitive;
    }

    @Override
    public int checkAnswer(ArrayList<String> user_answers) {
        if (user_answers.size() > getAnswers().size()) { return 0; }

        if (isOrdered()) {
            int counter = 0;
            for (int i = 0; i < user_answers.size(); i++) {
                if ((isCaseSensitive() && user_answers.get(i).equals(getAnswers().get(i))) ||
                        (!isCaseSensitive() && user_answers.get(i).toUpperCase().equals(getAnswers().get(i)))) {
                    counter++;
                }
            }
            return counter;
        } else {
            HashSet<String> s = new HashSet<>();

            if (isCaseSensitive()) { s.addAll(user_answers); }
            else {
                for (String userAnswer : user_answers) {
                    s.add(userAnswer.toUpperCase());
                }
            }
            int sz = s.size();
            s.addAll(getAnswers());
            return sz + getAnswers().size() - s.size();
        }
    }

    @Override
    public int getMaxScore() {
        return getAnswers().size();
    }

    private ArrayList<String> fillList(String answer) {
        if (!isCaseSensitive()) { answer = answer.toUpperCase(); }

        ArrayList<String> ls = getStrings(answer);
        if (!isOrdered()) { Collections.sort(ls); }
        return ls;
    }
}
