package com.example.yf.creatorshirt.utils;


import com.google.gson.Gson;

import okhttp3.RequestBody;


/**
 * Created by yangfang on 2017/9/6.
 */

public class GsonUtils {
    public static RequestBody getGson(Object data) {
        Gson gson = new Gson();
        String entity = gson.toJson(data);
        LogUtil.e("GsonUtils", "ENTITY" + entity);
        return  RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), entity);
    }

}
