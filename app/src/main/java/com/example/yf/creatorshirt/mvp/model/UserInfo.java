package com.example.yf.creatorshirt.mvp.model;

/**
 * Created by yang on 28/07/2017.
 * 用户信息
 */

public class UserInfo {
    private String headImage;
    private String mobile;
    private String nickname;

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "User{" +
                "headImage='" + headImage + '\'' +
                ", mobile='" + mobile + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}