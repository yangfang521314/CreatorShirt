package com.example.yf.creatorshirt.http;

import com.example.yf.creatorshirt.mvp.model.AddressBean;
import com.example.yf.creatorshirt.mvp.model.BombStyleBean;
import com.example.yf.creatorshirt.mvp.model.basechoice.DesignBaseInfo;
import com.example.yf.creatorshirt.mvp.model.GirlData;
import com.example.yf.creatorshirt.mvp.model.HotDesignsBean;
import com.example.yf.creatorshirt.mvp.model.LoginBean;
import com.example.yf.creatorshirt.mvp.model.NewsSummary;
import com.example.yf.creatorshirt.mvp.model.UserInfo;
import com.example.yf.creatorshirt.mvp.model.detaildesign.DetailStyleBean;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by yang on 17/05/2017.
 */

public interface RequestApi {
    //主服务器的baseUrl
    String HOST = "http://style.man-kang.com/api/";

    //抓取数据
    Flowable<NewsSummary> getNewsSummary();

    //妹纸
    @GET("data/福利/{size}/{page}")
    Flowable<GirlData> getPhotoList(
            @Path("size") int size,
            @Path("page") int page);

    //登录
    @POST("user/login")
    Observable<LoginBean> loginPhone(
            @Path("mobile") String mobile,
            @Path("password") String password);

    //获取验证码
    Observable<LoginBean> getCode(String phone);

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

    //具体设计的数据
    @POST("fDesigns/GetTypeversionResources")
    Flowable<HttpResponse<DetailStyleBean>> getDetailDesignStyle(@Body RequestBody requestBody);
}