package com.example.yf.creatorshirt.common;

import android.content.Context;

import com.example.yf.creatorshirt.mvp.model.LoginBean;
import com.example.yf.creatorshirt.mvp.model.UserInfoCache;
import com.example.yf.creatorshirt.utils.SharedPreferencesUtil;

/**
 * 所有和用户登录相关的方法请在此封装
 * Created by liuyao on 2016/1/6 0006.
 */
public class UserInfoManager {
    private static UserInfoManager ourInstance = new UserInfoManager();
    private LoginBean mLoginResponse;
    private UserInfoCache userInfoCache;
    private Context mContext;

    public static UserInfoManager getInstance() {
        return ourInstance;
    }

    public void init(Context context) {
        mContext = context;
        userInfoCache = new UserInfoCache(context);
        mLoginResponse = userInfoCache.getUserInfo();
    }

    private UserInfoManager() {
    }


    public String getUserName() {
        return SharedPreferencesUtil.getUserName();
    }


    public LoginBean getLoginResponse() {
        return mLoginResponse;
    }

    /**
     * 登录后存储用户登录信息,需要同时更新视图
     *
     * @param loginResponse
     * @param name
     */
    public void setLoginSuccess(LoginBean loginResponse, String name, String token, String mobile) {
        SharedPreferencesUtil.setUserName(name);
        SharedPreferencesUtil.saveUserToken(token);
        SharedPreferencesUtil.saveUserPhone(mobile);
        mLoginResponse = loginResponse;
        userInfoCache.saveUserInfo(mLoginResponse);
    }

    /**
     * 登录下,更新本地存储的用户信息,需要同时更新视图
     */
    public void updateLocalLoginResponse() {
        userInfoCache.saveUserInfo(mLoginResponse);
    }

    /**
     * 登录下,注销登录,需要同时更新视图
     */
    public void logOut() {
        //注销时暂时不清楚用户名密码
        //SharedPreferencesUtil.setUserName("");
        //SharedPreferencesUtil.setUserPassword("");
        mLoginResponse = new LoginBean();
        userInfoCache.saveUserInfo(mLoginResponse);
    }

}