package com.example.yf.creatorshirt.mvp.model.db;

import com.example.yf.creatorshirt.mvp.model.bean.GirlData;
import com.example.yf.creatorshirt.mvp.model.bean.LoginBean;
import com.example.yf.creatorshirt.mvp.model.bean.NewsSummary;
import com.example.yf.creatorshirt.mvp.model.bean.UserInfo;

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

    @GET("user/getInfo")
    Observable<UserInfo> getUserInfo();
}
