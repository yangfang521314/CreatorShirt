package com.example.yf.creatorshirt.http;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.utils.LogUtil;

/**
 * Created by yangfang on 2018/1/12.
 */

public class DownloadService extends Service {
    private Context mContext;
    private boolean flag = true;
    private String downloadPath;
    private String type;
    private DownloadManager dManager = null;
    private long ext = 0l;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = App.getInstance();
        LogUtil.e("DOWLOADService", "Start");
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(downloadCompleteReceiver, filter);
        dManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            return super.onStartCommand(intent, flags, startId);
        } else if (TextUtils.isEmpty(intent.getStringExtra("downloadPath"))) {
            return super.onStartCommand(intent, flags, startId);
        }
        return 0;
    }

    private BroadcastReceiver downloadCompleteReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long downloadCompletedId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
            if(ext != downloadCompletedId){//检查是否是自己的下载队列
                return;
            }
        }
    };


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static void startService(String content, String type) {
        LogUtil.i("DownloadService", "startService");
        Intent intent = new Intent();
        intent.setClassName(App.getInstance().getPackageName(),DownloadService.class.getName());
        intent.putExtra("downloadPath",content);
        intent.putExtra("type",type);

    }
}
