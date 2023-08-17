package Usernames_DAO.message;

import java.security.Timestamp;

public class FriendRequest {
    String from;
    String to;
    public Timestamp request_time;

    public FriendRequest(String from,String to,Timestamp time) {
        this.from = from;
        this.to = to;
        request_time = time;
    }
}
