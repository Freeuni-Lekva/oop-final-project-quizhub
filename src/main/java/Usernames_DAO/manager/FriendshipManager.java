package Usernames_DAO.manager;

import DATABASE_DAO.UsernameDatabases.FriendRequestsDatabase;
import DATABASE_DAO.UsernameDatabases.FriendsDatabase;
import Usernames_DAO.models.User;


import java.sql.SQLException;

public class FriendshipManager {
    private FriendsDatabase friend_db;
    private FriendRequestsDatabase friendRequest_db;

    private FriendRequestsDatabase friend_req_db;
    public FriendshipManager() throws SQLException {
        friend_db = new FriendsDatabase();
        friendRequest_db = new FriendRequestsDatabase();
        friend_req_db = new FriendRequestsDatabase();
    }

    public void confirmFriendshipRequest(String receiver, String sender) throws SQLException {
        if(User.getUser(receiver).getFriends().contains(sender))return;
        friendRequest_db.remove(sender,receiver);
        friend_db.add(sender, receiver);
    }
    public void addFriendshipRequest(String sender, String receiver) throws SQLException {
        if(User.getUser(receiver).getFriends().contains(sender)){
            confirmFriendshipRequest(sender,receiver);
        }else {
            friendRequest_db.add(sender,receiver);
        }
    }

    public boolean FriendRequestSent(String username_from,String username_to) throws SQLException {
        return friend_req_db.friendRequestSent(username_from,username_to);
    }

    public void rejectFriendshipRequest(String receiver,String sender) throws SQLException {
        friendRequest_db.remove(sender, receiver);
    }

    public boolean areFriend(String user1, String user2) throws SQLException {
        return friend_db.areFriends(user1, user2);
    }
    public void cancelFriendRequest(String username_from,String username_to) throws SQLException {
        friendRequest_db.remove(username_from,username_to);
    }

    public void removeFriend(String username_from,String username_to) throws SQLException {
        friend_db.removeOneFriend(username_from,username_to);
    }
}
