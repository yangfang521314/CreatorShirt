package com.example.yf.creatorshirt.http;

import com.example.yf.creatorshirt.mvp.model.AddressBean;
import com.example.yf.creatorshirt.mvp.model.BombStyleBean;
import com.example.yf.creatorshirt.mvp.model.HotDesignsBean;
import com.example.yf.creatorshirt.mvp.model.LoginBean;
import com.example.yf.creatorshirt.mvp.model.UserInfo;
import com.example.yf.creatorshirt.mvp.model.basechoice.DesignBaseInfo;
import com.example.yf.creatorshirt.mvp.model.detaildesign.DetailStyleBean;
import com.example.yf.creatorshirt.mvp.model.orders.OrderStyleBean;
import com.example.yf.creatorshirt.mvp.model.orders.OrderType;
import com.example.yf.creatorshirt.mvp.presenter.PayOrderEntity;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * Created by yang on 27/05/2017.
 * 数据总的管理类
 */

public class DataManager implements HttpHelper {

    HttpHelper mHttpHelper;

    public DataManager(HttpHelper mHttpHelper) {
        this.mHttpHelper = mHttpHelper;
    }

    /**
     * phone 登录
     *
     * @return
     * @param body
     */
    @Override
    public Flowable<HttpResponse<LoginBean>> login(RequestBody body) {
        return mHttpHelper.login(body);
    }

    /**
     * send code
     *
     * @param phone
     * @return
     */
    public Flowable<HttpResponse<String>> getVerifyCode(RequestBody phone) {
        return mHttpHelper.getVerifyCode(phone);
    }

    public Observable<HttpResponse<UserInfo>> getUserInfo() {
        return mHttpHelper.getUserInfo();
    }

    public Flowable<HttpResponse<List<BombStyleBean>>> getBombData(RequestBody requestBody) {
        return mHttpHelper.getBombData(requestBody);
    }

    @Override
    public Flowable<HttpResponse<List<HotDesignsBean>>> getHotDesign(String userToken, RequestBody body) {
        return mHttpHelper.getHotDesign(userToken, body);
    }

    @Override
    public Flowable<HttpResponse<List<AddressBean>>> getAddressData() {
        return mHttpHelper.getAddressData();
    }

    /**
     * 首页选择衣服或者裤子的数据
     *
     * @return
     */
    @Override
    public Flowable<HttpResponse<DesignBaseInfo>> getBaseDesign() {
        return mHttpHelper.getBaseDesign();
    }

    /**
     * 具体设计样式的数据选择
     *
     * @param requestBody
     * @return
     */
    @Override
    public Flowable<HttpResponse<DetailStyleBean>> getDetailDesign(RequestBody requestBody) {
        return mHttpHelper.getDetailDesign(requestBody);
    }

    /**
     * 保存数据
     * @param userToken
     * @param body
     * @return
     */
    public Flowable<HttpResponse<OrderType>> saveOrderData(String userToken, RequestBody body) {
        return mHttpHelper.saveOrderData(userToken, body);
    }

    @Override
    public Flowable<HttpResponse<String>> getQiToken(String userToken) {
        return mHttpHelper.getQiToken(userToken);
    }

    public Flowable<HttpResponse<OrderStyleBean>> getOrdersFromOrderId(String userToken, RequestBody orderId) {
        return mHttpHelper.getOrdersFromOrderId(userToken,orderId);
    }


    public Flowable<HttpResponse<PayOrderEntity>> payMentOrders(String userToken, RequestBody requestBody) {
        return mHttpHelper.payMentOrders(userToken,requestBody);
    }

    @Override
    public Flowable<HttpResponse> saveUserInfo(String userToken, RequestBody requestBody) {
        return mHttpHelper.saveUserInfo(userToken,requestBody);
    }
}
