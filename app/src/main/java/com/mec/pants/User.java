package com.mec.pants;

/**
 * Created by User on 11/11/2017.
 */

public class User {
    String userId;
    String username;
    String location;

    public User() {
    }

    public User(String username){
        this.username = username;
    }

    public User(String userId, String username, String location) {
        this.userId = userId;
        this.username = username;
        this.location = location;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getLocation() {
        return location;
    }
}
