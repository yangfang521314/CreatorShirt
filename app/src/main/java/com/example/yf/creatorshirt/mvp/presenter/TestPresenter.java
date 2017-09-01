package com.example.yf.creatorshirt.mvp.presenter;

import android.util.Log;

import com.example.yf.creatorshirt.http.HttpResponse;
import com.example.yf.creatorshirt.http.TestRequestServer;
import com.example.yf.creatorshirt.utils.SharedPreferencesUtil;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yangfang on 2017/9/1.
 * 测试json数据类
 */

public class TestPresenter {

    public static void test() {
        Map<String, String> map = new HashMap<>();
        map.put("id", "95");
        Gson gson = new Gson();
        String orderEntity = gson.toJson(map);
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), orderEntity);
        TestRequestServer.getInstance().getOrderId(SharedPreferencesUtil.getUserToken(), requestBody).enqueue(new Callback<HttpResponse>() {


            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                Log.e("TestPresenter", "Test......." + response.body().getResult());
            }


            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {

            }
        });
    }

}
