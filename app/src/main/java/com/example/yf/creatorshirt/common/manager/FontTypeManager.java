package com.example.yf.creatorshirt.common.manager;

import android.content.Context;
import android.graphics.Typeface;

import com.example.yf.creatorshirt.common.cache.FontTypeCache;

import java.util.List;

/**
 * Created by yangfang on 2018/1/16.
 * 字体大小在这里存储
 */

public class FontTypeManager {
    public static FontTypeManager mInstance;
    private FontTypeCache fontTypeCache;

    public FontTypeManager() {
    }

    public static FontTypeManager getInstance() {
        if (mInstance == null) {
            mInstance = new FontTypeManager();
        }
        return mInstance;
    }

    public List<Typeface> getFontTypeList() {
        return fontTypeCache.getFontType();
    }

    public void setFontTypeCache(List<Typeface> fontTypeList) {
        if (fontTypeList == null) {
            return;
        }
        fontTypeCache.saveFontCache(fontTypeList);
    }

    public void init(Context context) {
        fontTypeCache = new FontTypeCache(context);
    }
}
