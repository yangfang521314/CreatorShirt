package com.example.yf.creatorshirt.utils;

import com.google.gson.Gson;

import java.util.Map;

import okhttp3.RequestBody;


/**
 * Created by yangfang on 2017/9/6.
 */

public class GsonUtils {
    private volatile static GsonUtils instance;

    public RequestBody getGson(Map<String, String> data) {
        Gson gson = new Gson();
        String entity = gson.toJson(data);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), entity);
        return body;
    }

    public GsonUtils() {

    }

    public static GsonUtils getInstance() {
        if (instance == null) {
            synchronized (GsonUtils.class) {
                if (instance == null) {
                    instance = new GsonUtils();
                }
            }
        }
        return instance;
    }

}
