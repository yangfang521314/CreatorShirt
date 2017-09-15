package com.example.yf.creatorshirt.mvp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by yang on 28/07/2017.
 * 用户信息
 */

public class UserInfo implements Parcelable,Serializable{

    private int userid;
    private String mobile;
    private String name;
    private String lastLogintime;
    private String password;
    private String headImage;
    private Boolean isNew;

    protected UserInfo(Parcel in) {
        userid = in.readInt();
        mobile = in.readString();
        name = in.readString();
        lastLogintime = in.readString();
        password = in.readString();
        headImage = in.readString();
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(userid);
        parcel.writeString(mobile);
        parcel.writeString(name);
        parcel.writeString(lastLogintime);
        parcel.writeString(password);
        parcel.writeString(headImage);
    }
}