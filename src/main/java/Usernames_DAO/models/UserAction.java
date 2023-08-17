package Usernames_DAO.models;

import java.sql.Timestamp;

public class UserAction {
    private final String source;
    private final long id;
    private final String username;
    private final Timestamp actionTime;
    private final String score;

    public UserAction(String source, long id, String username, Timestamp actionTime, String score) {
        this.source = source;
        this.id = id;
        this.username = username;
        this.actionTime = actionTime;
        this.score = score;
    }



    @Override
    public String toString() {
        return "Source: " + source + ", ID: " + id + ", Username: " + username + ", Action Time: " + actionTime + ", Score: " + score;
    }

    public String getSource(){
        return this.source;
    }

    public String getUsername(){ return this.username; }

    public long quizId(){
        return this.id;
    }

    public String getScore(){
        return this.score;
    }

    public Timestamp getActionTime(){ return this.actionTime; }

}