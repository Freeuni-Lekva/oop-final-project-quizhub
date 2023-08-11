package Questions_DAO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Quiz {
    private final String quizName;
    private final String creator_name;
    private final String category;
    private final String description;
    private final ArrayList<String> tags;
    private final ArrayList<Question> questions;
    private final boolean random;
    private final boolean onePage;
    private final boolean immediateCorrection;
    private final int max_score;
    private int currNumber;
    private final ArrayList<Integer> userScores;
    private int userScore;
    private final ArrayList<ArrayList<String>> userAnswers;
    private final boolean practiceMode;
    private ArrayList<Integer> question;
    private HashMap<Integer, Integer> frequencies;
    public Quiz(String quizName, String creator_name, String category, String description, ArrayList<String> tags,
                ArrayList<Question> questions, boolean random, boolean onePage, boolean immediateCorrection, boolean practiceMode) {
        this.quizName = quizName;
        this.creator_name = creator_name;
        this.category = category;
        this.description = description;
        this.tags = tags;
        this.random = random;
        this.questions = questions;
        if (isRandom() && questions != null) {
            Collections.shuffle(questions);
        }
        this.onePage = onePage;
        this.immediateCorrection = immediateCorrection;
        this.practiceMode = practiceMode;

        if (questions != null) {
            max_score = calculateMaxScore();
        } else {
            max_score = 0;
        }
        currNumber = 0;
        userScores = new ArrayList<>();
        userAnswers = new ArrayList<>();
        userScore = 0;

        if (questions != null) {
            question = new ArrayList<>();
            for (int i = 0; i < questions.size(); i++){
                question.add(i);
            }
            frequencies = new HashMap<>();
        }
    }

    public String getQuizName() {
        return quizName;
    }

    public String getCategory() {
        return category;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public String getCreatorName() {
        return creator_name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isRandom() {
        return random;
    }

    public boolean isOnePage() {
        return onePage;
    }

    public boolean hasImmediateCorrection() {
        return immediateCorrection;
    }

    public boolean isPracticeMode() { return practiceMode; }

    public ArrayList<Question> getQuestionList() {
        return questions;
    }

    public int getMaxScore() {
        return max_score;
    }

    private int calculateMaxScore() {
        int score = 0;
        for (int i = 0; i < questions.size(); i++) { score += questions.get(i).getMaxScore(); }
        return score;
    }

    public int getCurrentQuestionNumber() {
        return question.get(currNumber) + 1;
    }

    public Question getCurrentQuestion() {
        return questions.get(getCurrentQuestionNumber() - 1);
    }

    public boolean hasNextQuestion() {
        if (practiceMode && (!frequencies.containsKey(question.get(currNumber)) || frequencies.get(question.get(currNumber)) < 3)){
            return true;
        }
        return question.size() > 1;
    }

    public void goToNextQuestion() {
        if (!practiceMode) {
            question.remove(currNumber);
        } else if (frequencies.containsKey(question.get(currNumber)) && frequencies.get(question.get(currNumber)) == 3) {
            question.remove(currNumber);
        } else {
            currNumber++;
        }
        if (question.size() != 0) {
            currNumber %= question.size();
        }
    }

    public int getTotalNumberOfQuestions() {
        return questions.size();
    }

    public void processAnswer(ArrayList<String> user_answers) {
        userAnswers.add(user_answers);
        int n = getCurrentQuestion().checkAnswer(user_answers);
        userScore += n;
        userScores.add(n);

        if (practiceMode) {
            if (n == getCurrentQuestion().getMaxScore()) {
                if (frequencies.containsKey(question.get(currNumber))){
                    int k = frequencies.get(question.get(currNumber));
                    k++;
                    frequencies.put(question.get(currNumber), k);
                } else {
                    frequencies.put(question.get(currNumber), 1);
                }
            }
        }
    }

    public int getUserScore() {
        return userScore;
    }

    public ArrayList<Integer> getUserScores() {
        return userScores;
    }

    public ArrayList<ArrayList<String>> getUserAnswers() {
        return userAnswers;
    }

    public ArrayList<Integer> getQuestionScores() {
        ArrayList<Integer> ls = new ArrayList<>();
        for (Question question : questions) {
            ls.add(question.getMaxScore());
        }
        return ls;
    }

    public ArrayList<ArrayList<String>> getCorrectAnswers() {
        ArrayList<ArrayList<String>> ls = new ArrayList<>();
        for (Question question : questions) {
            ls.add(question.getAnswers());
        }
        return ls;
    }

    public String getProcessedAnswer(ArrayList<String> answer, int type, boolean caseS){
        String ans = "";
        if (answer == null) {
            return "empty";
        }
        ArrayList<String> answers = new ArrayList<>();
        for(int i = 0; i < answer.size(); i++){
            if(answer.get(i).equals(""))
                continue;
            if(!caseS)
                answers.add(answer.get(i).toLowerCase());
            else
                answers.add(answer.get(i));
        }
        if (answers.size() != 0) {
            if(type == 1 || type == 3 || type == 4){
                if(!caseS)
                    ans = answers.get(0).toLowerCase();
                else
                    ans = answers.get(0);
            }else if(type == 2 || type == 5 || type == 6){
                for(int i = 0; i < answers.size() - 1; i++){
                    if(answers.get(i).equals(""))
                        continue;
                    if(!caseS)
                        ans += answers.get(i).toLowerCase();
                    else
                        ans += answers.get(i);
                    ans += ", ";
                }
                if(!caseS)
                    ans += answers.get(answers.size()-1).toLowerCase();
                else
                    ans += answers.get(answers.size()-1);
            }else{
                for(int i = 0; i < answers.size()-1; i++){
                    String ln;
                    if(!caseS)
                        ln = matching(answers.get(i)).toLowerCase();
                    else
                        ln = matching(answers.get(i));
                    if(ln.equals(""))
                        continue;
                    ans += ln;
                    ans += "@#";
                }
                ans += matching(answer.get(answer.size()-1));
            }
        }
        if (ans.equals("")){
            return "empty";
        }
        return ans;
    }

    private String matching(String line){
        if(line.equals("$//$"))
            return "";
        String ans = "";
        for(int i = 0; i < line.length(); i++){
            if(line.charAt(i) == '/' && line.charAt(i+1) == '/'){
                ans += "   <-->   ";
                ans += line.substring(i+2);
                break;
            }
            ans += line.charAt(i);
        }
        return ans;
    }
}
