package com.example.yf.creatorshirt.http;

import io.reactivex.Flowable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by yangfang on 2017/8/31
 * <p>
 * //测试response ，为了查找数据格式
 */

public interface TestRequestApi {

    //上传数据
    @POST("f-orders/saveOrders")
    Call<HttpResponse> saveOrderData(@Header("Token") String token, @Body RequestBody body);

    @POST("f-Users/requestOrdersFromOrderId")
    Call<HttpResponse> getOrderId(@Header("Token") String token, @Body RequestBody body);

    @POST("f-orders/paymentOrders")
    Call<HttpResponse> payMentOrders(@Header("Token") String userToken, @Body RequestBody requestBody);

    //保存用户信息
    @POST("f-Users/userModify")
    Call<HttpResponse> saveUserInfo(@Header("Token") String userToken, @Body RequestBody requestBody);

    //地址
    @POST("f-Users/requestUserAddress")
    Call<HttpResponse> getAddressData(@Header("Token") String userToken);

    @POST("f-Users/requestSquareDesign")
    Call<HttpResponse> getHotDesign(@Header("Token") String userToken, @Body RequestBody body);

    @POST("f-Users/requestOrdersFromsquare")
    Call<HttpResponse> getBomData(@Body RequestBody body);

    @POST("f-Users/requestDesignOrders")
    Call<HttpResultResponse> getDesignOrders(@Body RequestBody body);

    @POST("f-Users/GetUserInfo")
    Call<HttpResponse> getUserInfo(@Header("Token") String userToken);

    @POST("f-Users/addUseraddress")
    Call<HttpResponse> saveAddress(@Header("Token") String userToken, @Body RequestBody body);

    @POST("f-Users/saveOrdersFromShare")
    Call<HttpResponse> saveOrdersFromShare(@Header("Token") String userToken, @Body RequestBody body);

    @POST("f-Users/setDefaultAddress")
    Call<HttpResponse> setDefaultAddress(@Header("Token") String userToken, @Body RequestBody body);

    @POST("fDesigns/GetTypeversionResources")
    Call<HttpResponse> getDetailDesign(@Body RequestBody requestBody);

    @POST("fDesigns/GetTexture")
    Call<HttpResponse> getTexture(RequestBody gson);

    @POST("f-Users/userLogin")
    Call<HttpResponse> loginPhone(@Body RequestBody gson);

    @POST("f-orders/computerOrderPrice")
    Call<HttpResponse> getCalculateOrderPrice(@Header("Token") String token, @Body RequestBody gson);

    @POST("f-orders/requestOrders")
    Call<HttpResponse> getMyOrder(@Header("Token") String token, @Body RequestBody gson);

    @POST("f-orders/updateOrders")
    Call<HttpResponse> updateOrders(@Header("Token") String token, @Body RequestBody gson);

    @POST("f-orders/requestOrders")
    Call<HttpResponse> requestMyorder(@Header("Token") String token, @Body RequestBody gson);

    @Streaming
    @GET
    Flowable<ResponseBody> downloadImage(@Url String url);
}
