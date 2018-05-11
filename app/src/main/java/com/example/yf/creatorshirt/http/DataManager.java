package com.example.yf.creatorshirt.http;

import com.example.yf.creatorshirt.mvp.model.AddressBean;
import com.example.yf.creatorshirt.mvp.model.BombStyleBean;
import com.example.yf.creatorshirt.mvp.model.ClothesPrice;
import com.example.yf.creatorshirt.mvp.model.HotDesignsBean;
import com.example.yf.creatorshirt.mvp.model.LoginBean;
import com.example.yf.creatorshirt.mvp.model.PayTradeInfo;
import com.example.yf.creatorshirt.mvp.model.PraiseEntity;
import com.example.yf.creatorshirt.mvp.model.VersionUpdateResponse;
import com.example.yf.creatorshirt.mvp.model.WechatInfo;
import com.example.yf.creatorshirt.mvp.model.basechoice.DesignBaseInfo;
import com.example.yf.creatorshirt.mvp.model.detaildesign.DetailStyleBean;
import com.example.yf.creatorshirt.mvp.model.orders.OrderStyleBean;
import com.example.yf.creatorshirt.mvp.model.orders.OrderType;
import com.example.yf.creatorshirt.mvp.model.orders.SaveOrderInfo;
import com.example.yf.creatorshirt.mvp.model.orders.TextureEntity;

import java.util.List;

import io.reactivex.Flowable;
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
     * @param body
     * @return
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

    public Flowable<HttpResponse<LoginBean>> getUserInfo(String userToken) {
        return mHttpHelper.getUserInfo(userToken);
    }

    public Flowable<HttpResponse<List<BombStyleBean>>> getBombData(RequestBody requestBody) {
        return mHttpHelper.getBombData(requestBody);
    }

    @Override
    public Flowable<HttpResponse<List<HotDesignsBean>>> getHotDesign(String userToken, RequestBody body) {
        return mHttpHelper.getHotDesign(userToken, body);
    }

    @Override
    public Flowable<HttpResponse<List<AddressBean>>> getAddressData(String userToken) {
        return mHttpHelper.getAddressData(userToken);
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
     *
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
        return mHttpHelper.getOrdersFromOrderId(userToken, orderId);
    }


    public Flowable<HttpResponse<PayTradeInfo>> payMentOrders(String userToken, RequestBody requestBody) {
        return mHttpHelper.payMentOrders(userToken, requestBody);
    }

    @Override
    public Flowable<HttpResponse> saveUserInfo(String userToken, RequestBody requestBody) {
        return mHttpHelper.saveUserInfo(userToken, requestBody);
    }

    @Override
    public Flowable<HttpResponse<List<BombStyleBean>>> getDesignOrders(RequestBody requestbody) {
        return mHttpHelper.getDesignOrders(requestbody);
    }

    @Override
    public Flowable<HttpResponse> saveAddress(String userToKen, RequestBody requestbody) {
        return mHttpHelper.saveAddress(userToKen, requestbody);
    }

    @Override
    public Flowable<HttpResponse<Integer>> requestOrdersPraise(String userToKen, RequestBody requestbody) {
        return mHttpHelper.requestOrdersPraise(userToKen, requestbody);
    }

    @Override
    public Flowable<HttpResponse<PraiseEntity>> OrderPraise(String token, RequestBody gson) {
        return mHttpHelper.OrderPraise(token, gson);
    }

    @Override
    public Flowable<HttpResponse<OrderType>> saveOrdersFromShare(String token, RequestBody gson) {
        return mHttpHelper.saveOrdersFromShare(token, gson);
    }

    @Override
    public Flowable<HttpResponse<Integer>> setDefaultAddress(String token, RequestBody gson) {
        return mHttpHelper.setDefaultAddress(token, gson);
    }

    @Override
    public Flowable<HttpResponse<List<TextureEntity>>> getTextUre(RequestBody gson) {
        return mHttpHelper.getTextUre(gson);
    }


    public Flowable<HttpResponse<VersionUpdateResponse>> getVersionCode(int verCode) {
        return mHttpHelper.getVersionCode(verCode);
    }

    public Flowable<HttpResponse<ClothesPrice>> getCalculateOrderPrice(String token, RequestBody requestBody) {
        return mHttpHelper.getCalculateOrderPrice(token, requestBody);
    }

    @Override
    public Flowable<HttpResponse<List<SaveOrderInfo>>> requestMyOrder(String token, RequestBody gson) {
        return mHttpHelper.requestMyOrder(token, gson);
    }

    @Override
    public Flowable<HttpResponse<OrderType>> updateOrders(String token, RequestBody gson) {
        return mHttpHelper.updateOrders(token, gson);
    }

    @Override
    public Flowable<HttpResponse<WechatInfo>> payMomentWeChatOrders(String userToken, RequestBody gson) {
        return mHttpHelper.payMomentWeChatOrders(userToken, gson);
    }


}
