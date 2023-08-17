package Usernames_DAO.models;

import java.sql.Timestamp;

public class UserHistory {
    private final Timestamp finishDate;

    private final String score;

    private final Integer actionTime;

    public UserHistory(Timestamp finishDate, String score, Integer actionTime) {
        this.finishDate = finishDate;
        this.score = score;
        this.actionTime = actionTime;
    }

    public Integer getAtionTime() {
        return this.actionTime;
    }

    public String getScore() {
        return this.score;
    }

    public Timestamp getFinishDate() {
        return this.finishDate;
    }
}