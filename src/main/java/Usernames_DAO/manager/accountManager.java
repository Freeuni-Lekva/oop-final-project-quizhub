package Usernames_DAO.manager;

import DATABASE_DAO.UsernameDatabases.UsersDatabase;

import java.security.MessageDigest;
import java.sql.SQLException;
import java.util.HashMap;

public class accountManager {
    private UsersDatabase database;
    public accountManager() throws SQLException {
        database = new UsersDatabase();
    }

    public boolean addAcc(String userName,String password) throws Exception {
        if(database.contains(userName) || userName == null
                || password == null || userName.length() == 0
                || password.length() == 0)return false;

        database.add(userName,encodeString(password), false);
        return true;
    }

    public boolean matchesPassword(String username,String password) throws Exception {
        if(username == null || password == null || !ContainsKey(username))return false;
        return database.getPassword(username).equals(encodeString(password));
    }

    public boolean ContainsKey(String username) throws SQLException, SQLException {
        return database.contains(username);
    }

    private static String hexToString(byte[] bytes) {
        StringBuffer buff = new StringBuffer();
        for (int i=0; i<bytes.length; i++) {
            int val = bytes[i];
            val = val & 0xff;
            if (val<16) buff.append('0');
            buff.append(Integer.toString(val, 16));
        }
        return buff.toString();
    }

    private static String encodeString(String arg) throws Exception{
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(arg.getBytes());
        byte[] bytes = md.digest();
        return hexToString(bytes);
    }
}
