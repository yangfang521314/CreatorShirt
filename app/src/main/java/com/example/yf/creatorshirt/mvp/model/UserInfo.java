package com.example.yf.creatorshirt.mvp.model;

/**
 * Created by yang on 28/07/2017.
 * 用户信息
 */

public class UserInfo {

    private int userid;
    private String mobile;
    private String name;
    private String lastLogintime;
    private String password;
    private String headImage;
    private Boolean isNew;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastLogintime() {
        return lastLogintime;
    }

    public void setLastLogintime(String lastLogintime) {
        this.lastLogintime = lastLogintime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public Boolean getNew() {
        return isNew;
    }

    public void setNew(Boolean aNew) {
        isNew = aNew;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userid=" + userid +
                ", mobile=" + mobile +
                ", name='" + name + '\'' +
                ", lastLogintime='" + lastLogintime + '\'' +
                ", password='" + password + '\'' +
                ", headImage='" + headImage + '\'' +
                ", isNew=" + isNew +
                '}';
    }
}