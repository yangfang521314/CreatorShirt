package com.example.yf.creatorshirt.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by yangfang on 2017/8/29.
 */

public class Utils {
    //获取时间
    public static String getTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
        String timeStamp = simpleDateFormat.format(new Date());
        return timeStamp;
    }
}
