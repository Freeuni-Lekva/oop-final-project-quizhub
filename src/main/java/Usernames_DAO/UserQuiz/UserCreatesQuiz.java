package Usernames_DAO.UserQuiz;

import DATABASE_DAO.QuizDatabases.QuestionsDatabase;
import DATABASE_DAO.QuizDatabases.QuizDatabase;
import DATABASE_DAO.QuizDatabases.QuizQuestionDatabase;
import DATABASE_DAO.QuizDatabases.TagsQuizDatabase;
import DATABASE_DAO.UsernameDatabases.UserQuizDatabase;
import Questions_DAO.Question;
import Usernames_DAO.models.Achievement;
import Usernames_DAO.models.User;

import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;

public class UserCreatesQuiz {
    private String quizName;
    private String creator_name;
    private String category;
    private String description;
    private ArrayList<String> tags;
    private ArrayList<Question> questions;
    private boolean random;
    private boolean onePage;
    private boolean immediateCorrection;
    private boolean practiceMode;
    private int numberOfQuestions;
    private int maxScore;
    private final QuizDatabase quizDatabase;
    private final QuestionsDatabase questionsDatabase;
    private final QuizQuestionDatabase quizQuestionDatabase;
    private final TagsQuizDatabase tagsQuizDatabase;
    public UserCreatesQuiz (User user) throws SQLException {
        creator_name = user.getUsername();
        tags = new ArrayList<>();
        questions = new ArrayList<>();
        numberOfQuestions = 0;
        maxScore = 0;

        quizDatabase = new QuizDatabase();
        questionsDatabase = new QuestionsDatabase();
        quizQuestionDatabase = new QuizQuestionDatabase();
        tagsQuizDatabase = new TagsQuizDatabase();
    }

    public void setQuizName (String quizName) {
        this.quizName = quizName;
    }
    public String getQuizName () {
        return quizName;
    }
    public void setCategory (String category) {
        this.category = category;
    }
    public String getCategory () {
        return category;
    }
    public String getCreator_name() {
        return creator_name;
    }
    public ArrayList<String> getTags() {
        return tags;
    }
    public void setDescription (String description) {
        this.description = description;
    }
    public String getDescription () {
        return description;
    }
    public void setRandom (boolean random) {
        this.random = random;
    }
    public boolean isRandom () {
        return random;
    }
    public void setOnePage (boolean onePage) {
        this.onePage = onePage;
    }
    public boolean isOnePage () {
        return onePage;
    }
    public void setTags (String tags1, String tags2, String tags3) {
        if (!tags1.equals(""))
            tags.add(tags1);
        if (!tags2.equals(""))
            tags.add(tags2);
        if (!tags3.equals(""))
            tags.add(tags3);
    }
    public void setImmediateCorrection (boolean immediateCorrection) {
        this.immediateCorrection = immediateCorrection;
    }
    public boolean hasImmediateCorrection () {
        return immediateCorrection;
    }
    public void setPracticeMode (boolean practiceMode) {
        this.practiceMode = practiceMode;
    }
    public boolean isPracticeMode () {
        return  practiceMode;
    }
    public int getNumberOfQuestions () {
        return numberOfQuestions;
    }
    public int getMaxScore() {
        return maxScore;
    }
    public void addQuestion(Question question) {
        questions.add(question);
        numberOfQuestions++;
        maxScore += question.getMaxScore();
    }
    public int FinishAndPublish() throws SQLException {
        int quiz_id = quizDatabase.getMinId(QuizDatabase.tablename);
        quizDatabase.addQuiz(quiz_id, quizName, creator_name, category, description, random, onePage, immediateCorrection, practiceMode);

        for (Question question : questions) {
            int question_id = questionsDatabase.getMinId(QuestionsDatabase.tablename);
            questionsDatabase.insertQuestion(question_id, question.getType(), question.getQuestion(),
                    question.getPossibleAnswers(), question.getAnswers(), question.isOrdered(), question.isCaseSensitive());
            quizQuestionDatabase.addQuestion(quiz_id, question_id);
        }

        for (String tag : tags) {
            tagsQuizDatabase.addQuiz(tag, quiz_id);
        }

        UserQuizDatabase userQuizDatabase = new UserQuizDatabase();
        userQuizDatabase.add(creator_name, quiz_id, new Timestamp(System.currentTimeMillis()));

        Achievement achievement = new Achievement(creator_name, -1);
        achievement.alertCreateQuiz(creator_name);
        return quiz_id;
    }
}
