package com.example.yf.creatorshirt.utils;


import android.util.Log;

import com.example.yf.creatorshirt.BuildConfig;

/**
 * Created by yang on 2016/8/3.
 */
public class LogUtil {

    public static boolean isDebug = BuildConfig.DEBUG;

    /**
     * 设置开启debug模式
     * @param flag true debug; false no debug
     */
    public static void setDebug(boolean flag) {
        isDebug = flag;
    }
    public static void i(String tag,String msg){
        if(isDebug){
            Log.i(tag, msg);
        }
    }
    public static void d(String tag,String msg){
        if(isDebug){
            Log.d(tag, msg);
        }
    }
    public static void e(String tag,String msg){
        if(isDebug){
            Log.e(tag, msg);
        }
    }
}
