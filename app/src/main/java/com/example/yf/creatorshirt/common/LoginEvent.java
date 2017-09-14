package com.example.yf.creatorshirt.common;

/**
 * Created by yangfang on 2017/9/13.
 */

public class LoginEvent {
    private boolean isMinefragment;

    public LoginEvent(boolean isMinefragment) {
        this.isMinefragment = isMinefragment;
    }

    public boolean isMinefragment() {
        return isMinefragment;
    }

    public void setMinefragment(boolean minefragment) {
        isMinefragment = minefragment;
    }
}
