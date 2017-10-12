package com.example.yf.creatorshirt.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;


/**
 * Created by yang on 11/08/2017.
 */

public class NetBroadcastReceiver extends BroadcastReceiver {


    private static final String TAG = NetBroadcastReceiver.class.getSimpleName();
    private static final int MOBILE = 1;
    private static final int WIFI = 2;
    private static final int NONET = 0;

    private NetInterface netInterface = BaseActivity.netInterface;

    @Override
    public void onReceive(Context context, Intent intent) {
        // 监听网络连接，包括wifi和移动数据的打开和关闭,以及连接上可用的连接都会接到监听，这种广播可能有点慢
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (networkInfo != null) {
                if (networkInfo.isConnected()) {
                    if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                        netInterface.netChange(WIFI);
                        Log.e(TAG, "当前wifi可用");
                    } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                        netInterface.netChange(MOBILE);
                        Log.e(TAG, "当前移动网络连接可用");
                    }
                } else {
                    netInterface.netChange(NONET);
                    Log.e(TAG, "当前没有网络,请确保你已经打开网络");
                }
            } else {
                netInterface.netChange(NONET);
                Log.e(TAG, "当前没有网络连接，请确保你已经打开网络 ");
            }
        }
    }

    public interface NetInterface {
        void netChange(int type);
    }

}
