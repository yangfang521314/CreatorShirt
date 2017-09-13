package com.example.yf.creatorshirt.common;

/**
 * Created by yangfang on 2017/9/13.
 */

public class LoginEvent {
    private boolean isMinefragment;
    private String isMine;

    public String getIsMine() {
        return isMine;
    }

    public void setIsMine(String isMine) {
        this.isMine = isMine;
    }

    public boolean isMinefragment() {
        return isMinefragment;
    }

    public void setMinefragment(boolean minefragment) {
        isMinefragment = minefragment;
    }
}
