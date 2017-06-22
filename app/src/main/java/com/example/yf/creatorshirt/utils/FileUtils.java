package com.example.yf.creatorshirt.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.yf.creatorshirt.app.App;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by yang on 17/05/2017.
 */

public class FileUtils {
    public static String getCacheFile() {
        return App.getInstance().getCacheDir().getAbsolutePath() + File
                .separator + "data";
    }

    /**
     * save image
     *
     * @param bitmap
     * @param context
     * @return
     */
    public static String saveBitmap(@NonNull Bitmap bitmap, @NonNull Context context) {
        String path = null;
        File file = getFile("Sticker");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            path = file.getAbsolutePath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
        LogUtil.e("TAG", "SaveImageToGallery: the path of Bitmap is " + file.getAbsolutePath());
        return path;
    }

    public static File getFile(String sticker) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
        String timeStamp = simpleDateFormat.format(new Date());
        String path;
        if (isSDAvailable()) {
            path = getFolderName(sticker) + File.separator + timeStamp + ".jpg";//卡路径
        } else {
            path = App.getInstance().getFilesDir().getPath() + File.separator + timeStamp + ".jpg";//app包路径下面
        }
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        return new File(path);
    }

    private static String getFolderName(String name) {
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), name);
        if (!storageDir.exists()) {
            if (!storageDir.mkdirs()) {
                return "";
            }
        }
        return storageDir.getAbsolutePath();
    }

    /**
     * 判断SD卡是否可以用
     *
     * @return
     */
    private static boolean isSDAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
}
