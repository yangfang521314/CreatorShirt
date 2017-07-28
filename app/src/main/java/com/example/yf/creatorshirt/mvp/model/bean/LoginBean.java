package com.example.yf.creatorshirt.mvp.model.bean;

/**
 * Created by yang on 12/06/2017.
 */

public class LoginBean {

    private LoginToken result;
    private int status;

    public class LoginToken {
        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

    public LoginToken getResult() {
        return result;
    }

    public void setResult(LoginToken result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
