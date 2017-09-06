package com.example.yf.creatorshirt.http;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by yangfang on 2017/8/31
 *
 *  //测试response ，为了查找数据格式
 *
 */

public interface TestRequestApi{

    //上传数据
    @POST("f-Users/saveOrders")
    Call<HttpResponse> saveOrderData(@Header("Token") String token, @Body RequestBody body);

    @POST("f-Users/requestOrdersFromOrderId")
    Call<HttpResponse> getOrderId(@Header("Token") String token, @Body RequestBody body);

    @POST("f-Users/paymentOrders")
    Call<HttpResponse> payMentOrders(@Header("Token") String userToken, @Body RequestBody requestBody);
    //保存用户信息
    @POST("f-Users/userModify")
    Call<HttpResponse> saveUserInfo(@Header("Token") String userToken, @Body RequestBody requestBody);
}
