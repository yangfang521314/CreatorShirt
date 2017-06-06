package com.example.yf.creatorshirt.mvp.model.db;

import com.example.yf.creatorshirt.mvp.model.bean.GirlData;
import com.example.yf.creatorshirt.mvp.model.bean.NewsSummary;

import io.reactivex.Flowable;

/**
 * Created by yang on 27/05/2017.
 * 数据总的管理类
 */

public class DataManager implements HttpHelper {

    HttpHelper mHttpHelper;

    public DataManager(HttpHelper mHttpHelper) {
        this.mHttpHelper = mHttpHelper;
    }

    @Override
    public Flowable<NewsSummary> getDataNewsSummary() {
        return null;
    }

    @Override
    public Flowable<GirlData> getPhotoList(int size, int page) {
        return mHttpHelper.getPhotoList(size, page);
    }
}
