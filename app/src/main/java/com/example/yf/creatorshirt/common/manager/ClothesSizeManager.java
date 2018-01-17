package com.example.yf.creatorshirt.common.manager;

import android.content.Context;

import com.example.yf.creatorshirt.common.cache.ClothesSizeCache;
import com.example.yf.creatorshirt.mvp.model.orders.ClothesSize;

import java.util.List;
import java.util.Map;


/**
 * Created by yangfang on 2018/1/14.
 * 衣服尺寸的缓存
 */

public class ClothesSizeManager {
    private static ClothesSizeManager clothesSizeManager = new ClothesSizeManager();
    private ClothesSizeCache clothesSizeCache;

    private ClothesSizeManager() {
    }

    public static ClothesSizeManager getInstance() {
        return clothesSizeManager;
    }

    public Map<String, List<ClothesSize>> getClothesSizeList() {
        return clothesSizeCache.getUserInfo();
    }

    public void saveCache(Map<String, List<ClothesSize>> list) {
        clothesSizeCache.saveUserInfo(list);
    }

    public void init(Context context) {
        clothesSizeCache = new ClothesSizeCache(context);
    }
}
