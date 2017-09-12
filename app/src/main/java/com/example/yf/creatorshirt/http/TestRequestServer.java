package com.example.yf.creatorshirt.http;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yangfang on 2017/8/31.
 * 测试api
 */

public class TestRequestServer {
    static TestRequestApi instance;
    //主服务器的baseUrl
    static String host = "https://style.man-kang.com:3600/api/";

    static public TestRequestApi getInstance() {
        if (instance != null)
            return instance;

        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(host)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        instance = restAdapter.create(TestRequestApi.class);

        return instance;
    }
}
