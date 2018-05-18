package com.example.yf.creatorshirt.http;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.yf.creatorshirt.BuildConfig;
import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.utils.LogUtil;
import com.example.yf.creatorshirt.utils.ToastUtil;

import java.io.File;
import java.util.Objects;

/**
 * Created by yangfang on 2018/1/12.
 */

public class DownloadService extends Service {
    public static final String id = "channel_1";
    public static final int notifyId = 1;
    public static final String name = "应用更新";
    private boolean flag = true;
    private String downloadPath;
    private String type;
    private DownloadManager dManager = null;
    private long ext = 0L;
    private NotificationManager notificationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.e("DOWLOADService", "Start");
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(downloadCompleteReceiver, filter);
        dManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
    }

    @SuppressLint("WrongConstant")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            return super.onStartCommand(intent, flags, startId);
        } else if (TextUtils.isEmpty(intent.getStringExtra("downloadPath"))) {
            return super.onStartCommand(intent, flags, startId);
        } else {
            downloadPath = intent.getStringExtra("downloadPath");
            if (intent.hasExtra("type")) {
                type = intent.getStringExtra("type");
                DownloadManager.Query query = new DownloadManager.Query();
                Cursor cursor = dManager.query(query);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        do {
                            int id = cursor.getColumnIndex(DownloadManager.COLUMN_ID);
                            int status = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                            int uriId = cursor.getColumnIndex(DownloadManager.COLUMN_URI);
                            String uirPath = cursor.getString(uriId);
                            int statusCode = cursor.getInt(status);
                            if (uirPath == null || uirPath.equals(downloadPath)) {
                                if (statusCode == DownloadManager.STATUS_FAILED) {
                                    dManager.remove(ext);
                                } else if (statusCode == DownloadManager.STATUS_SUCCESSFUL) {
                                    dManager.remove(ext);
                                } else {
                                    flag = false;
                                    Toast.makeText(getApplicationContext(), R.string.downloading_info, Toast.LENGTH_LONG).show();
                                }
                            }
                        } while (cursor.moveToNext());
                        cursor.close();
                    }
                    if (flag) {
                        ToastUtil.showToast(getApplicationContext(), getString(R.string.begin_download), Toast.LENGTH_LONG);
                        startDownload();
                    }
                }
            }
        }
        return START_STICKY;
    }

    private void startDownload() {
        int state = getApplicationContext().getPackageManager().getApplicationEnabledSetting("com.android.providers.downloads");
        if (state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED
                || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER
                || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:com.android.providers.downloads"));
            getApplicationContext().startActivity(intent);
        } else {
            requestDownload();
        }
    }

    private void requestDownload() {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadPath));
        //
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        //设置保存路径
        // request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "fk.apk");
        //sdcard的目录下的download文件夹
        if (TextUtils.isEmpty(type)) {
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, getFileName(downloadPath));
        } else {
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, getFileName(downloadPath) + type);
        }
        //设置在下载中和下载完成的时候，通知栏都显示下载进度
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            sendNotificationO();
        } else {
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
            request.setTitle("衣加一版本更新下载");
            request.setDescription(getString(R.string.update_notify_content));
        }
        ext = dManager.enqueue(request);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void sendNotificationO() {
        notificationManager = (NotificationManager) DownloadService.this.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(DownloadService.this)
                .setTicker("开始下载")
                .setSmallIcon(R.mipmap.ic_icon)
                .setLargeIcon(BitmapFactory.decodeResource(DownloadService.this.getResources(), R.mipmap.ic_icon))
                .setAutoCancel(true)
                .setContentTitle("衣加一版本更新下载")
                .setContentIntent(PendingIntent.getActivity(DownloadService.this, 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT))
                .setContentText("最新版应用继续下载，请稍等");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(id,
                    name, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(mChannel);
            builder.setChannelId(id);
        }
        notificationManager.notify(notifyId, builder.build());
    }

    private String getFileName(String downloadPath) {
            return downloadPath.substring(downloadPath.lastIndexOf("/"),downloadPath.length());
    }

    /**
     * 接受广播
     */
    private BroadcastReceiver downloadCompleteReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long downloadCompletedId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
            if (ext != downloadCompletedId) {//检查是否是自己的下载队列
                return;
            }
            DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            if (Objects.requireNonNull(intent.getAction()).equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)){
                DownloadManager.Query query = new DownloadManager.Query();
                //在广播中取出下载任务的id
                long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,0);
                query.setFilterById(id);
                assert manager != null;
                Cursor c = manager.query(query);
                if(c.moveToFirst()){
                    int columnIndex = c.getColumnIndex(DownloadManager.COLUMN_STATUS);
                    int state = c.getInt(columnIndex);
                    switch (state){
                        case DownloadManager.STATUS_SUCCESSFUL:
                            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                                notificationManager.cancel(notifyId);
                            }
                            String filepath = null;
                            String downUri = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                            if(downUri != null){
                                filepath = new File( Uri.parse(downUri).getPath()).getAbsolutePath();
                            }
                            Intent intentFile =getFileIntent(filepath);
                            startActivity(intentFile);
                            break;
                    }
                }
            }

        }


    };

    private Intent getFileIntent(String filepath) {
        File apkFile = new File(filepath);
        Intent intent = new Intent("android.intent.action.VIEW");
        Uri uri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(App.getInstance(), BuildConfig.PROVIDER_CONFIG, apkFile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } else {
            uri = Uri.fromFile(apkFile);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        String type = getMIMEType(apkFile);
        intent.setDataAndType(uri,type);
        return intent;
    }

    public  String getMIMEType(File file){
        String type = "";
        String fName = file.getName();
        /* 取得扩展名 */
        String end = fName
                .substring(fName.lastIndexOf(".") + 1, fName.length())
                .toLowerCase();

        /* 依扩展名的类型决定MimeType */
        if (end.equals("pdf")) {
            type = "application/pdf";//
        } else if (end.equals("m4a") || end.equals("mp3") || end.equals("mid")
                || end.equals("xmf") || end.equals("ogg") || end.equals("wav")) {
            type = "audio/*";
        } else if (end.equals("3gp") || end.equals("mp4")) {
            type = "video/*";
        } else if (end.equals("jpg") || end.equals("gif") || end.equals("png")
                || end.equals("jpeg") || end.equals("bmp")) {
            type = "image/*";
        } else if (end.equals("apk")) {
            /* android.permission.INSTALL_PACKAGES */
            type = "application/vnd.android.package-archive";
        }
        else {
            // /*如果无法直接打开，就跳出软件列表给用户选择 */
            type = "*/*";
        }
        return type;
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(downloadCompleteReceiver);
    }
}
