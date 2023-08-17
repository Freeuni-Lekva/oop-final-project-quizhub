package Usernames_DAO.message;

import DATABASE_DAO.UsernameDatabases.FriendRequestsDatabase;
import DATABASE_DAO.UsernameDatabases.FriendsDatabase;
import DATABASE_DAO.UsernameDatabases.MessageDatabase;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class MessageManager {
    private MessageDatabase db;
    private FriendRequestsDatabase fr_db;
    private FriendsDatabase friendsDatabase;

    public MessageManager() throws SQLException {
        db = new MessageDatabase();
        fr_db = new FriendRequestsDatabase();
        friendsDatabase = new FriendsDatabase();
    }

    public  void sendMessage(Message msg) throws SQLException {
        db.add(msg);
    }

    public  ArrayList<String> getFriendRequests(String username) {
        ArrayList<String> requests;
        requests = fr_db.friendRequestsRecieved(username);
        return requests;
    }

    public  ArrayList<String> getRecievedUsers(String username) {
        return db.getRecievedMessageSenders(username);
    }


    public ArrayList<Message> getMessages(String username_from,String username_to) throws SQLException {
        return db.getMessagesBetween(username_from,username_to);
    }

    public ArrayList<String> getMessagesSearch(String username, String usernameFlag) throws SQLException {
        return this.friendsDatabase.getUsernamesLike(usernameFlag, username);
    }
}