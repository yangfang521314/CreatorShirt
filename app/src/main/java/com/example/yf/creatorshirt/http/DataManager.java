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

/**
 * Created by yang on 27/05/2017.
 * 数据总的管理类
 */

public class DataManager implements HttpHelper {

    HttpHelper mHttpHelper;

    public DataManager(HttpHelper mHttpHelper) {
        this.mHttpHelper = mHttpHelper;
    }

    @Override
    public Flowable<NewsSummary> getDataNewsSummary() {
        return null;
    }

    @Override
    public Flowable<GirlData> getPhotoList(int size, int page) {
        return mHttpHelper.getPhotoList(size, page);
    }

    /**
     * phone 登录
     *
     * @param phone
     * @param password
     * @return
     */
    @Override
    public Observable<LoginBean> login(String phone, String password) {
        return mHttpHelper.login(phone, password);
    }

    /**
     * send code
     *
     * @param phone
     * @return
     */
    public Observable<LoginBean> getVerifyCode(String phone) {
        return mHttpHelper.getVerifyCode(phone);
    }

    public Observable<HttpResponse<UserInfo>> getUserInfo() {
        return mHttpHelper.getUserInfo();
    }

    public Flowable<HttpResponse<List<BombStyleBean>>> getBombData() {
        return mHttpHelper.getBombData();
    }

    @Override
    public Flowable<HttpResponse<List<HotDesignsBean>>> getHotDesign() {
        return mHttpHelper.getHotDesign();
    }

    @Override
    public Flowable<HttpResponse<List<AddressBean>>> getAddressData() {
        return mHttpHelper.getAddressData();
    }

    /**
     * 首页选择衣服或者裤子的数据
     * @return
     */
    @Override
    public Flowable<HttpResponse<DesignBaseInfo>> getBaseDesign() {
        return mHttpHelper.getBaseDesign();
    }

    /**
     * 具体设计样式的数据选择
     * @param gender
     * @param type
     * @return
     */
    @Override
    public Flowable<HttpResponse<DetailStyleBean>> getDetailDesign(String gender, String type) {
        return mHttpHelper.getDetailDesign(gender,type);
    }
}
