package Usernames_DAO.message;
import DATABASE_DAO.QuizDatabases.QuizDatabase;
import DATABASE_DAO.UsernameDatabases.MessageDatabase;
import Questions_DAO.Quiz;

import java.security.Timestamp;
import java.sql.SQLException;

public class Message {
    private String from;
    private String to;
    private String text;
    private boolean isChallenge;
    private int quiz_id;
    public Message(String from,String to,String text,boolean isChallenge,int quiz_id) throws SQLException {
        this.from = from;
        this.to = to;
        this.text = text;
        this.isChallenge = isChallenge;
        this.quiz_id = quiz_id;
    }

    public String getQuizName() throws SQLException {
        String name = "";
        if(isChallenge){
            String text = getText();
            for(int i = 0; i < text.length(); i++){
                if(text.charAt(i) == '/' && text.charAt(i+1) == '/')
                    break;
                name += text.charAt(i);
            }
        }
        return name;
    }

    public String getFrom() {
        return from;
    }
    public String getTo() {
        return to;
    }

    public String getText() {
        return text;
    }

    public int getQuiz_id() {
        return quiz_id;
    }

    public boolean isChallenge() {
        return isChallenge;
    }
}
