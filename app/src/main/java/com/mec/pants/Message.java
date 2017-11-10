package com.mec.pants;

import java.util.Date;

public class Message {

    private String content;
    private String username;
    private long time;

    public Message() {
    }

    public Message(String content) {
        this.content = content;
    }

    public Message(String content, String username) {
        this.content = content;
        this.username = username;

        time = new Date().getTime();
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

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
