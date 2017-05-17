package com.example.yf.creatorshirt.model.db;

import com.example.yf.creatorshirt.model.bean.NewsSummary;

import io.reactivex.Flowable;

/**
 * Created by yang on 17/05/2017.
 */

public interface RequestApi {
    //主服务器的baseUrl
    String HOST = "";

    //抓取数据
    Flowable<NewsSummary> getNewsSummary();
}
