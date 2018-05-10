package com.example.yf.creatorshirt.mvp.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import com.example.yf.creatorshirt.BuildConfig;
import com.example.yf.creatorshirt.http.DataManager;
import com.example.yf.creatorshirt.mvp.presenter.base.RxPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.CommonAvatarContract;
import com.example.yf.creatorshirt.utils.Constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.inject.Inject;

import okhttp3.ResponseBody;

public class DownloadImagePresenter extends RxPresenter<CommonAvatarContract.CommonAvatarView> {
    private DataManager manager;
    private File file;

    @Inject
    DownloadImagePresenter(DataManager manager) {
        this.manager = manager;
    }


    public void downloadImage(String extra) {
//        TestRequestServer.getDownInstance().downloadImage(extra)
//                .map(this::saveFile)
//                .compose(RxUtils.rxSchedulerHelper())
//                .subscribe(s -> {
//                    if (s != null) {
//                        ToastUtil.showToast(this, "已保存图片", 0);
//                        scanFileAsync(this, s);
//                        scanDirAsync(this, file.getAbsolutePath());
//                    } else {
//                        ToastUtil.showToast(this, "保存图片失败", 0);
//                    }
//                });


    }


    private String saveFile(ResponseBody responseBody) {
        String result = "";
        file = new File(Environment.getExternalStorageDirectory().toString() + Constants.DOWNIMAGES);
        if (!file.exists()) {
            file.mkdirs();
        }
        File imgfile = new File(file.getAbsolutePath(), new Date().getTime() + ".png");
        InputStream inputStream = responseBody.byteStream();
        byte[] buffer = new byte[4096];
        int len = 0;
        try {
            FileOutputStream outputStream = new FileOutputStream(imgfile);
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            inputStream.close();
            outputStream.close();
            result = imgfile.getAbsolutePath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    //扫描指定文件
    public void scanFileAsync(Context ctx, String filePath) {
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(Uri.fromFile(new File(filePath)));
        ctx.sendBroadcast(scanIntent);
    }

    //扫描指定目录
    public static final String ACTION_MEDIA_SCANNER_SCAN_DIR = "android.intent.action.MEDIA_SCANNER_SCAN_DIR";

    public void scanDirAsync(Context ctx, String dir) {
        Intent scanIntent = new Intent(ACTION_MEDIA_SCANNER_SCAN_DIR);
        Uri uri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//7.0通过FileProvider授权访问
            uri = FileProvider.getUriForFile(ctx, BuildConfig.PROVIDER_CONFIG, new File(dir));
        } else {
            uri = Uri.fromFile(new File(dir));
        }
        scanIntent.setData(uri);
        ctx.sendBroadcast(scanIntent);
    }

}
