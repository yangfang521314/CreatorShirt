package com.example.yf.creatorshirt.mvp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by yang on 12/06/2017.
 * 用户信息
 */

public class LoginBean implements Parcelable {
    private static final long serialVersionUID = 1L;
    /**
     * "result": {
     * "UserInfo": {
     * "userid": 1118,
     * "mobile": null,
     * "name": null,
     * "lastLogintime": "2017-08-21T14:42:52.000Z",
     * "password": null,
     * "headImage": null,
     * "isNew": false
     * },
     * "address": [],
     * "token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyaWQiOjExMTgsIm1vYmlsZSI6bnVsbCwibmFtZSI6bnVsbCwibGFzdExvZ2ludGltZSI6IjIwMTctMDgtMjFUMTQ6NDI6NTIuMDAwWiIsInBhc3N3b3JkIjpudWxsLCJoZWFkSW1hZ2UiOm51bGwsImlzTmV3IjpmYWxzZSwiaWF0IjoxNTA0MTQ4MTAxLCJleHAiOjE1MDU0NDQxMDF9.OGx8brVimXEuZTMtDB8RPayPlSraFxehps13VO3OPbsKrO2DUeidACrh1hrgSah1GlkOxPWaiFRY5GGN-J8aaYpZYmrWrIvwo3VBis5OEfC_M7rBEmL1QvHe-L5HkbG3yUkoK1-63L6v1rFeIhdO_cG_J7gIsiGY7zAGqLxgoKw"
     * }
     */

    private UserInfo UserInfo;
    private List<String> address;
    private String token;

    protected LoginBean(Parcel in) {
        address = in.createStringArrayList();
        token = in.readString();
    }

    public LoginBean() {
    }

    public static final Creator<LoginBean> CREATOR = new Creator<LoginBean>() {
        @Override
        public LoginBean createFromParcel(Parcel in) {
            return new LoginBean(in);
        }

        @Override
        public LoginBean[] newArray(int size) {
            return new LoginBean[size];
        }
    };

    public UserInfo getUserInfo() {
        return UserInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        UserInfo = userInfo;
    }

    public List<String> getAddress() {
        return address;
    }

    public void setAddress(List<String> address) {
        this.address = address;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "UserInfo=" + UserInfo +
                ", address=" + address +
                ", token='" + token + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(address);
        dest.writeString(token);
    }
}
