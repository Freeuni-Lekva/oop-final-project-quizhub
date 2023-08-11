package Questions_DAO;

import java.util.ArrayList;

import static Questions_DAO.QuestionMultiChoice.getStrings;


public class QuestionFillBlank implements Question {
    private final String question;
    private final ArrayList<String> answers;
    private final boolean ordered;
    private final boolean caseSensitive;
    public QuestionFillBlank(String question, String answer, boolean ordered, boolean caseSensitive) {
        this.question = question;
        this.ordered = ordered;
        this.caseSensitive = caseSensitive;
        answers = fillList(answer);
    }

    @Override
    public int getType() {
        return 2;
    }

    @Override
    public String getQuestion() {
        return question;
    }

    @Override
    public ArrayList<String> getTexts() {
        ArrayList<String> cuts = new ArrayList<>();
        int prev = 0;
        for(int i = 0; i < question.length(); i++) {
            if (i + 2 < question.length() && question.charAt(i) == '_'
                    && question.charAt(i + 1) == '_' && question.charAt(i + 2) == '_') {
                String tmp = question.substring(prev, i);
                cuts.add(tmp);
                i += 2;
                prev = i + 1;
            }
        }
        cuts.add(question.substring(prev));
        return cuts;
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

        int counter = 0;
        for (int i = 0; i < user_answers.size(); i++) {
            if (isCaseSensitive() && user_answers.get(i).equals(getAnswers().get(i))) { counter++; }
            if (!isCaseSensitive() && user_answers.get(i).toUpperCase().equals(getAnswers().get(i))) { counter++; }
        }

        return counter;
    }

    @Override
    public int getMaxScore() {
        return getAnswers().size();
    }

    private ArrayList<String> fillList(String answer) {
        if (!isCaseSensitive()) { answer = answer.toUpperCase(); }
        return getStrings(answer);
    }
}
