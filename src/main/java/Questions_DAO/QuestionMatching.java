package Questions_DAO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import static Questions_DAO.QuestionMultiChoice.getStrings;


public class QuestionMatching implements Question {
    private final String question;
    private final ArrayList<String> possibleAnswers;
    private final ArrayList<String> answers;
    private final boolean ordered;
    private final boolean caseSensitive;
    public QuestionMatching(String question, String possibleAnswers, String answers, boolean ordered, boolean caseSensitive) {
        this.question = question;
        this.ordered = ordered;
        this.caseSensitive = caseSensitive;
        this.possibleAnswers = fillPossibleAnswers(possibleAnswers);
        this.answers = fillAnswers(answers);
    }
    @Override
    public int getType() {
        return 7;
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
        HashSet<String> s = new HashSet<>(answers);
        s.addAll(user_answers);
        HashSet<String> s1 = new HashSet<>(user_answers);
        return s1.size() + answers.size() - s.size();
    }

    @Override
    public int getMaxScore() {
        return getAnswers().size();
    }

    private ArrayList<String> fillPossibleAnswers(String possibleAnswers) {
        ArrayList<String> ls = getStrings(possibleAnswers);

        ArrayList<String> ls1 = new ArrayList<>();
        ArrayList<String> ls2 = new ArrayList<>();
        for (int i = 0; i < ls.size() / 2; i++) { ls1.add(ls.get(i)); }
        for (int i = ls.size() / 2; i < ls.size(); i++) { ls2.add(ls.get(i)); }

        if (!isOrdered()) {
            Collections.shuffle(ls1);
            Collections.shuffle(ls2);
        }

        ls.clear();
        ls.addAll(ls1);
        ls.addAll(ls2);

        return ls;
    }

    private ArrayList<String> fillAnswers(String answers) {
        ArrayList<String> ls1 = getStrings(answers);
        ArrayList<String> ls = new ArrayList<>();
        for (int i = 0; i < ls1.size(); i += 2) { ls.add(ls1.get(i) + "//" + ls1.get(i + 1)); }
        Collections.sort(ls);
        return ls;
    }
}
