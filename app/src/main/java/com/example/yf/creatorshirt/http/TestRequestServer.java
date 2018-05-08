package com.example.yf.creatorshirt.http;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
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
    static String host2 = "https://eyun.baidu.com/";

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
    static public TestRequestApi getDownInstance() {
        if (instance != null)
            return instance;
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                //打印retrofit日志
                try {
                    String newMessage = message.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
                    Log.i("RetrofitLog", "retrofitBack = " + URLDecoder.decode(message, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(loggingInterceptor);
        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(host2)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        instance = restAdapter.create(TestRequestApi.class);

        return instance;
    }
}
