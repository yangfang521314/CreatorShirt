package com.example.yf.creatorshirt.http;

import com.example.yf.creatorshirt.mvp.model.AddressBean;
import com.example.yf.creatorshirt.mvp.model.BombStyleBean;
import com.example.yf.creatorshirt.mvp.model.HotDesignsBean;
import com.example.yf.creatorshirt.mvp.model.LoginBean;
import com.example.yf.creatorshirt.mvp.model.PraiseEntity;
import com.example.yf.creatorshirt.mvp.model.basechoice.DesignBaseInfo;
import com.example.yf.creatorshirt.mvp.model.detaildesign.DetailStyleBean;
import com.example.yf.creatorshirt.mvp.model.orders.OrderStyleBean;
import com.example.yf.creatorshirt.mvp.model.orders.OrderType;
import com.example.yf.creatorshirt.mvp.model.PayOrderEntity;

import java.util.List;

import io.reactivex.Flowable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by yang on 17/05/2017.
 */

public interface RequestApi {


    //主服务器的baseUrl
    String HOST = "https://style.man-kang.com:3600/api/";

    //mobile登录
    @POST("f-Users/userLogin")
    Flowable<HttpResponse<LoginBean>> loginPhone(@Body RequestBody body);

    //获取mobile验证码
    @POST("f-Users/sendRegCode")
    Flowable<HttpResponse<String>> getCode(@Body RequestBody body);

    //获取用户信息
    @POST("f-Users/GetUserInfo")
    Flowable<HttpResponse<LoginBean>> getUserInfo(@Header("Token") String userToken);

    //获取爆款数据
    @POST("f-Users/requestOrdersFromsquare")
    Flowable<HttpResponse<List<BombStyleBean>>> getBombData(@Body RequestBody requestBody);

    //获取所有的设计师
    @POST("f-Users/requestSquareDesign")
    Flowable<HttpResponse<List<HotDesignsBean>>> getHotDesign(@Header("Token") String userToken, @Body RequestBody body);

    //获取我的地址
    @POST("f-Users/requestUserAddress")
    Flowable<HttpResponse<List<AddressBean>>> getAddress(@Header("Token") String userToken);

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

    //查找支付订单信息
    @POST("f-Users/paymentOrders")
    Flowable<HttpResponse<PayOrderEntity>> payMentOrders(@Header("Token") String userToken, @Body RequestBody requestBody);

    //保存用户信息
    @POST("f-Users/userModify")
    Flowable<HttpResponse> saveUserInfo(@Header("Token") String userToken, @Body RequestBody requestBody);

    //查询设计师的订单数
    @POST("f-Users/requestDesignOrders")
    Flowable<HttpResponse<List<BombStyleBean>>> getDesignOrders(@Body RequestBody requestbody);

    @POST("f-Users/addUseraddress")
    Flowable<HttpResponse> saveAddress(@Header("Token") String userToken, @Body RequestBody requestBody);

    //查询点赞
    @POST("f-Users/requestOrderPraise")
    Flowable<HttpResponse<Integer>> requestPraise(@Header("Token") String userToken, @Body RequestBody requestBody);

    //订单点赞
    @POST("f-Users/OrderPraise")
    Flowable<HttpResponse<PraiseEntity>> OrderPraise(@Header("Token") String userToken, @Body RequestBody requestBody);

    //下订单保存
    @POST("f-Users/saveOrdersFromShare")
    Flowable<HttpResponse<OrderType>> saveOrdersFromShare(@Header("Token") String token, @Body RequestBody requestBody);

    @POST("f-Users/setDefaultAddress")
    Flowable<HttpResponse<Integer>> setDefaultAddress(@Header("Token") String token, @Body RequestBody requestBody);
}