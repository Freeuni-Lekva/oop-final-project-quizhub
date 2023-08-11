package Usernames_DAO.UserQuiz;

import DATABASE_DAO.QuizDatabases.QuestionsDatabase;
import DATABASE_DAO.QuizDatabases.QuizDatabase;
import DATABASE_DAO.QuizDatabases.QuizQuestionDatabase;
import DATABASE_DAO.QuizDatabases.TagsQuizDatabase;
import DATABASE_DAO.UsernameDatabases.RankingsDatabase;
import Questions_DAO.Question;
import Questions_DAO.Quiz;
import Usernames_DAO.models.Achievement;
import Usernames_DAO.models.User;

import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class UserTakesQuiz {
    private final User user;
    private final int quiz_id;
    private final Timestamp start_time;
    private Timestamp end_time;
    private final Quiz quiz;
    private QuizDatabase quizDatabase;
    private QuestionsDatabase questionsDatabase;
    private QuizQuestionDatabase quizQuestionDatabase;
    private TagsQuizDatabase tagsQuizDatabase;
    private RankingsDatabase rankingsDatabase;
    public UserTakesQuiz(User user, int quiz_id, Timestamp start_time, boolean practiceMode) throws SQLException {
        quizDatabase = new QuizDatabase();
        questionsDatabase = new QuestionsDatabase();
        quizQuestionDatabase = new QuizQuestionDatabase();
        tagsQuizDatabase = new TagsQuizDatabase();
        rankingsDatabase = new RankingsDatabase();

        this.user = user;
        this.quiz_id = quiz_id;
        this.start_time = start_time;

        Quiz quiz1 = quizDatabase.getQuiz(quiz_id);
        ArrayList<String> tags = tagsQuizDatabase.getTags(quiz_id);
        ArrayList<Question> questions = quizQuestionDatabase.getQuestions(quiz_id);
        quiz = new Quiz(quiz1.getQuizName(), quiz1.getCreatorName(), quiz1.getCategory(), quiz1.getDescription(),
                tags, questions, quiz1.isRandom(), quiz1.isOnePage(), quiz1.hasImmediateCorrection(), practiceMode);
    }

    public User getUser() {
        return user;
    }
    public Quiz getQuiz() {
        return quiz;
    }
    public Integer getQuizId() { return quiz_id; }

    public ArrayList<String> submitQuestion(ArrayList<String> user_answers) {
        quiz.processAnswer(user_answers);
        if (quiz.hasImmediateCorrection()){
            return quiz.getCurrentQuestion().getAnswers();
        } else {
            return null;
        }
    }

    public void finish() throws SQLException {
        end_time = new Timestamp(System.currentTimeMillis());

        Achievement achievement = new Achievement(user.getUsername(), -1);
        if (quiz.isPracticeMode()) {
            achievement.alertPractice(user.getUsername());
        } else {
            rankingsDatabase.add(rankingsDatabase.getMinId(RankingsDatabase.tablename), user.getUsername(), quiz_id,
                    quiz.getUserScore(), start_time, end_time);
            achievement.alertQuizTaken(user.getUsername());
            if (quiz.getUserScore() == quiz.getMaxScore()) {
                achievement.alertHighestscore(user.getUsername());
            }
        }
    }

    public String getTime() {
        LocalDateTime startDateTime = start_time.toLocalDateTime();
        LocalDateTime endDateTime = end_time.toLocalDateTime();
        Duration duration = Duration.between(startDateTime, endDateTime);
        return SummaryQuiz.timeToString((int)(duration.toMillis() / 1000));
    }
}
