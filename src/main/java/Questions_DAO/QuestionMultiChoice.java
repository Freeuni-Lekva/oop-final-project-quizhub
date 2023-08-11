package Questions_DAO;

import java.util.ArrayList;
import java.util.Collections;

public class QuestionMultiChoice implements Question {
    private final String question;
    private final ArrayList<String> possibleAnswers;
    private final String answer;
    private final boolean ordered;
    private final boolean caseSensitive;
    public QuestionMultiChoice(String question, String possibleAnswers, String answer, boolean ordered, boolean caseSensitive) {
        this.question = question;
        this.answer = answer;
        this.ordered = ordered;
        this.caseSensitive = caseSensitive;
        this.possibleAnswers = fillList(possibleAnswers);
    }

    @Override
    public int getType() {
        return 3;
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
        ArrayList<String> answers = new ArrayList<>();
        answers.add(answer);
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
        if (user_answers.size() == 0) { return 0; }
        if (user_answers.get(0).equals(getAnswers().get(0))) { return 1; }
        return 0;
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

    static ArrayList<String> getStrings(String possibleAnswers) {
        ArrayList<String> ls = new ArrayList<>();
        String curr = "";
        for (int i = 0; i < possibleAnswers.length(); i++) {
            if (possibleAnswers.charAt(i) == '/' && i < possibleAnswers.length() - 1 && possibleAnswers.charAt(i + 1) == '/') {
                ls.add(curr);
                curr = "";
                i++;
            }
            else { curr += possibleAnswers.charAt(i); }
        }
        ls.add(curr);
        return ls;
    }
}
