package com.mec.pants;

public class Message {

    private String content;
    private String username, date;

    public Message() {
    }

    public Message(String content) {
        this.content = content;
    }

   /* public Message(String content, String username) {
        this.content = content;
        this.username = username;
    }

    public Message(String content, String username, String date) {
        this.content = content;
        this.username = username;
        this.date = date;

    }*/

    /*public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }*/

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
