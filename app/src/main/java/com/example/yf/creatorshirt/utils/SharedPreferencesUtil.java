package com.example.yf.creatorshirt.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 应用设置帮助类，SharedPreference存储
 * <p>
 * Created by yang on 17/05/2017.
 */
public final class SharedPreferencesUtil {
    public static Context mContext;
    private static Object history;

    public static void init(Context context) {
        mContext = context;
    }

    /**
     * 设置Int型键值
     *
     * @param key
     * @param value
     */
    public static void setInteger(String filename, String key, int value) {
        Editor editor = mContext.getSharedPreferences(filename, Context.MODE_PRIVATE).edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * 设置String型键值
     *
     * @param key
     * @param value
     */
    public static void setString(String filename, String key, String value) {
        Editor editor = mContext.getSharedPreferences(filename, Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 设置Boolean型键值
     *
     * @param key
     * @param value
     */
    public static void setBoolean(String filename, String key, boolean value) {
        Editor editor = mContext.getSharedPreferences(filename, Context.MODE_PRIVATE).edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 设置Long型键值
     *
     * @param key
     * @param value
     */
    public static void setLong(String filename, String key, long value) {
        Editor editor = mContext.getSharedPreferences(filename, Context.MODE_PRIVATE).edit();
        editor.putLong(key, value);
        editor.commit();
    }

    /**
     * 获取Int型值
     *
     * @param key
     * @return
     */
    public static int getInteger(String filename, String key, Integer defaultValue) {
        SharedPreferences preferences = mContext.getSharedPreferences(filename, Context.MODE_PRIVATE);
        return preferences.getInt(key, defaultValue);
    }

    /**
     * 获取String型值
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getString(String filename, String key, String defaultValue) {
        SharedPreferences preferences = mContext.getSharedPreferences(filename, Context.MODE_PRIVATE);
        return preferences.getString(key, defaultValue);
    }


    /**
     * 获取Boolean型值
     *
     * @param key
     * @return
     */
    public static boolean getBoolean(String filename, String key, boolean defaultValue) {
        SharedPreferences preferences = mContext.getSharedPreferences(filename, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, defaultValue);
    }

    /**
     * 获取Long型值
     *
     * @param key
     * @return
     */
    public static long getLong(String filename, String key, long defaultValue) {
        SharedPreferences preferences = mContext.getSharedPreferences(filename, Context.MODE_PRIVATE);
        return preferences.getLong(key, defaultValue);
    }


    /**
     * 设置app第一次启动
     *
     * @param flag
     */
    public static void setAppIsFirstLaunched(Boolean flag) {
        setBoolean(PrefKey.APP_LAUNCH, PrefKey.APP_FIRST_LAUNCH_KEY, flag);
    }

    /**
     * 读取app是否第一次启动
     *
     * @return
     */
    public static boolean getAppIsFirstLaunched() {
        return getBoolean(PrefKey.APP_LAUNCH, PrefKey.APP_FIRST_LAUNCH_KEY, true);
    }

    public static void saveUserId(int userid) {
        setInteger(Constants.USER_INFO, Constants.USER_ID, userid);
    }

    public static void saveUserToken(String token) {
        setString(Constants.USER_INFO, Constants.USER_TOKEN, token);
    }


    public static void saveUserPhone(String mobile) {
        setString(Constants.USER_INFO, Constants.USER_MOBILE, mobile);
    }

    public static String getUserToken() {
        return getString(Constants.USER_INFO, Constants.USER_TOKEN, "");
    }

    public static int getUserId() {
        return getInteger(Constants.USER_INFO, Constants.USER_ID, 0);
    }

    public static String getUserPhone() {
        return getString(Constants.USER_INFO, Constants.USER_MOBILE, "");
    }

    public static String getUserName() {
        return getString(Constants.USER_INFO, Constants.USER_NAME, "");
    }

    //保存用户名
    public static void setUserName(String userName) {
        setString(Constants.USER_INFO, Constants.USER_NAME, userName);
    }

    /**
     * 设置app是否已登录
     *
     * @param flag
     */
    public static void setIsLogin(boolean flag) {
        SharedPreferencesUtil.setBoolean(Constants.USER_INFO, PrefKey.APP_USER_IS_LOGIN_KEY, flag);
    }

    /**
     * 读取app是否登录
     *
     * @return
     */
    public static boolean getIsLogin() {
        return SharedPreferencesUtil.getBoolean(Constants.USER_INFO, PrefKey.APP_USER_IS_LOGIN_KEY, false);
    }

    /**
     * 存储从服务器获得的最新版本号
     */
    public static void setLastVersionCodeFromServer(String versionCode) {
        setString(PrefKey.APP_SETTING, PrefKey.VER_CODE_FROM_SERVER_KEY, versionCode);
    }

    public static String getLastVersionCodeFromServer() {
        return getString(PrefKey.APP_SETTING, PrefKey.VER_CODE_FROM_SERVER_KEY, "");
    }

    public static boolean getMoFlag(){
        return getBoolean(PrefKey.APP_SETTING,PrefKey.MOTION,true);
    }
    public static void setMoFlag(boolean flag){
         getBoolean(PrefKey.APP_SETTING,PrefKey.MOTION,flag);
    }
}
