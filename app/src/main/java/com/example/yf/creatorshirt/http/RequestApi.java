package com.example.yf.creatorshirt.http;

import com.example.yf.creatorshirt.mvp.model.AddressBean;
import com.example.yf.creatorshirt.mvp.model.BombStyleBean;
import com.example.yf.creatorshirt.mvp.model.HotDesignsBean;
import com.example.yf.creatorshirt.mvp.model.LoginBean;
import com.example.yf.creatorshirt.mvp.model.orders.OrderStyleBean;
import com.example.yf.creatorshirt.mvp.model.orders.OrderType;
import com.example.yf.creatorshirt.mvp.model.UserInfo;
import com.example.yf.creatorshirt.mvp.model.basechoice.DesignBaseInfo;
import com.example.yf.creatorshirt.mvp.model.detaildesign.DetailStyleBean;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by yang on 17/05/2017.
 */

public interface RequestApi {
    //主服务器的baseUrl
    String HOST = "http://style.man-kang.com/api/";

    //mobile登录
    @POST("f-Users/userLogin")
    Flowable<HttpResponse<LoginBean>> loginPhone(@Body RequestBody body);

    //获取mobile验证码
    @POST("f-Users/sendRegCode")
    Flowable<HttpResponse<String>> getCode(@Body RequestBody body);

    //获取用户信息
    @GET("user/getInfo")
    Observable<HttpResponse<UserInfo>> getUserInfo();

    //获取爆款数据
    @GET("clothers/getBombStyles")
    Flowable<HttpResponse<List<BombStyleBean>>> getBombData();

    //获取所有的设计师
    @GET("user/hotDesigns")
    Flowable<HttpResponse<List<HotDesignsBean>>> getHotDesign();

    //获取我的地址
    @GET("user/getAddress")
    Flowable<HttpResponse<List<AddressBean>>> getAddress();

    //设计的基本数据
    @POST("fDesigns/GetBaseInfo")
    Flowable<HttpResponse<DesignBaseInfo>> getBaseDesignData();

    //衣服具体设计的数据
    @POST("fDesigns/GetTypeversionResources")
    Flowable<HttpResponse<DetailStyleBean>> getDetailDesignStyle(@Body RequestBody requestBody);

    //上传图片衣服样式等数据
    @POST("f-Users/saveOrders")
    Flowable<HttpResponse<OrderType>> saveOrderData(@Header("Token") String token, @Body RequestBody body);


    //请求七牛token
    @POST("f-Users/GetQinniuToken")
    Flowable<HttpResponse<String>> getQiToken(@Header("Token") String token);

    //根据订单查找订单信息
    @POST("f-Users/requestOrdersFromOrderId")
    Flowable<HttpResponse<OrderStyleBean>> getOrdersFromOrderId(@Header("Token") String userToken, @Body RequestBody orderId);
}