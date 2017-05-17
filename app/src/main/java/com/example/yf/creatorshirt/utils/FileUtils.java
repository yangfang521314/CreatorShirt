package com.example.yf.creatorshirt.utils;

import com.example.yf.creatorshirt.app.App;

import java.io.File;

/**
 * Created by yang on 17/05/2017.
 */

public class FileUtils {
    public static String getCacheFile() {
        return App.getInstance().getCacheDir().getAbsolutePath()+ File
                .separator+"data";
    }
}
