package com.example.yf.creatorshirt.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.yf.creatorshirt.app.App;

/**
 * Created by yang on 17/05/2017.
 */

public class NetworkUtils {

    /**
     * 检查是否有可用网络
     *
     * @return
     */
    public static boolean isNetWorkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) App.getInstance().getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        return connectivityManager.getActiveNetworkInfo() != null;
    }

    /**
     * 检查是否是wifi
     *
     * @return
     */
    public static boolean isWifiConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) App.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo wifiInfo = connectivityManager.getActiveNetworkInfo();
        return (wifiInfo != null
                && wifiInfo.getType() == ConnectivityManager.TYPE_WIFI);
    }

    /**
     * 检查是否是2g/3g/4g
     *
     * @return
     */
    public static boolean isMobileConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) App.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo mobileNetWorkInfo = connectivityManager.getActiveNetworkInfo();
        return (mobileNetWorkInfo != null
                && mobileNetWorkInfo.getType() == ConnectivityManager.TYPE_MOBILE);
    }
}
