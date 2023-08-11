package Questions_DAO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import static Questions_DAO.QuestionMultiChoice.getStrings;

public class QuestionMultiChoiceMultiAnswer implements Question {
    private final String question;
    private final ArrayList<String> possibleAnswers;
    private final ArrayList<String> answers;
    private final boolean ordered;
    private final boolean caseSensitive;

    public QuestionMultiChoiceMultiAnswer(String question, String possibleAnswers, String answers, boolean ordered, boolean caseSensitive) {
        this.question = question;
        this.ordered = ordered;
        this.caseSensitive = caseSensitive;
        this.possibleAnswers = fillList(possibleAnswers);
        this.answers = getStrings(answers);
        Collections.sort(this.answers);
    }

    @Override
    public int getType() {
        return 6;
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
        return possibleAnswers;
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

        HashSet<String> s = new HashSet<>(user_answers);
        s.addAll(getAnswers());
        return user_answers.size() + getAnswers().size() - s.size();
    }

    @Override
    public int getMaxScore() {
        return getAnswers().size();
    }

    private ArrayList<String> fillList(String possibleAnswers) {
        ArrayList<String> ls = getStrings(possibleAnswers);
        if (!isOrdered()) {
            Collections.shuffle(ls);
        }
        return ls;
    }
}
