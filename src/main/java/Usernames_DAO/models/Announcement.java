package Usernames_DAO.models;

import DATABASE_DAO.UsernameDatabases.AnnouncementDatabase;

import java.sql.SQLException;
import java.util.List;

public class Announcement {
    private String username;
    private String subject;
    private String text;

    public Announcement(String username,String subject,String text) throws SQLException {
        this.username = username;
        this.subject = subject;
        this.text = text;
    }

    public String getName() {
        return username;
    }

    public String getSubject() {
        return subject;
    }

    public String getText() {
        return text;
    }
}