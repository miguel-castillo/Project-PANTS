package com.mec.pants;

/**
 * Created by User on 11/11/2017.
 */

public class User {
    String userId;
    String username;

    public User() {
    }

    public User(String username){
        this.username = username;
    }

    public User(String userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

}
