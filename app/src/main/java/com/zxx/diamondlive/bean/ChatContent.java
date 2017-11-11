package com.zxx.diamondlive.bean;

/**
 * Created by Administrator on 2017/8/23 0023.
 */

public class ChatContent {
    private String userName;
    private String content;

    public ChatContent(String userName, String content) {
        this.userName = userName;
        this.content = content;
    }

    public ChatContent() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
