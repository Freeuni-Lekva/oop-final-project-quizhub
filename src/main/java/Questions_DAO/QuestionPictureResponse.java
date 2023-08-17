package Questions_DAO;

import java.util.ArrayList;

public class QuestionPictureResponse implements Question {
    private final String question;
    private final String image;
    private final String answer;
    private final boolean ordered;
    private final boolean caseSensitive;
    public QuestionPictureResponse(String question, String image, String answer, boolean ordered, boolean caseSensitive) {
        this.question = question;
        this.image = image;
        this.answer = answer;
        this.ordered = ordered;
        this.caseSensitive = caseSensitive;
    }
    @Override
    public int getType() {
        return 4;
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
        return image;
    }

    @Override
    public ArrayList<String> getPossibleAnswers() {
        return null;
    }

    @Override
    public ArrayList<String> getAnswers() {
        ArrayList<String> answers = new ArrayList<>();

        if (isCaseSensitive()) {
            answers.add(answer);
        } else {
            answers.add(answer.toUpperCase());
        }

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
        if (user_answers.size() > getAnswers().size() || user_answers.size() == 0) { return 0; }
        if ((isCaseSensitive() && user_answers.get(0).equals(getAnswers().get(0))) ||
                (!isCaseSensitive() && user_answers.get(0).toUpperCase().equals(getAnswers().get(0)))) { return 1; }
        return 0;
    }

    @Override
    public int getMaxScore() {
        return getAnswers().size();
    }
}
