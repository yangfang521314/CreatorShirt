package com.example.yf.creatorshirt.http;

import com.example.yf.creatorshirt.mvp.model.BombStyleBean;
import com.example.yf.creatorshirt.mvp.model.GirlData;
import com.example.yf.creatorshirt.mvp.model.HotDesignsBean;
import com.example.yf.creatorshirt.mvp.model.LoginBean;
import com.example.yf.creatorshirt.mvp.model.NewsSummary;
import com.example.yf.creatorshirt.mvp.model.UserInfo;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by yang on 17/05/2017.
 */

public interface RequestApi {
    //主服务器的baseUrl
    String HOST = "http://rapapi.org/mockjsdata/23280/";

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
}
