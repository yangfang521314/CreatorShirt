package com.example.yf.creatorshirt.http;

import com.example.yf.creatorshirt.mvp.model.AddressBean;
import com.example.yf.creatorshirt.mvp.model.BombStyleBean;
import com.example.yf.creatorshirt.mvp.model.ClothesPrice;
import com.example.yf.creatorshirt.mvp.model.HotDesignsBean;
import com.example.yf.creatorshirt.mvp.model.LoginBean;
import com.example.yf.creatorshirt.mvp.model.MyOrderInfo;
import com.example.yf.creatorshirt.mvp.model.PayTradeInfo;
import com.example.yf.creatorshirt.mvp.model.PraiseEntity;
import com.example.yf.creatorshirt.mvp.model.VersionUpdateResponse;
import com.example.yf.creatorshirt.mvp.model.WechatInfo;
import com.example.yf.creatorshirt.mvp.model.basechoice.DesignBaseInfo;
import com.example.yf.creatorshirt.mvp.model.detaildesign.DetailStyleBean;
import com.example.yf.creatorshirt.mvp.model.orders.OrderStyleBean;
import com.example.yf.creatorshirt.mvp.model.orders.OrderType;
import com.example.yf.creatorshirt.mvp.model.orders.TextureEntity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import okhttp3.RequestBody;

/**
 * Created by yang on 17/05/2017.
 * Retrofit2获取数据辅助类
 */

public class RetrofitHelper implements HttpHelper {
    private RequestApi mRequestApi;

    /**
     * Dagger2直接注入对象
     *
     * @param requestApiService
     */
    @Inject
    public RetrofitHelper(RequestApi requestApiService) {
        mRequestApi = requestApiService;
    }

    /**
     * phone login
     *
     * @return
     * @param body
     */
    @Override
    public Flowable<HttpResponse<LoginBean>> login(RequestBody body) {
        return mRequestApi.loginPhone(body);
    }

    /**
     * 发送验证码
     *
     * @param phone
     * @return
     */
    @Override
    public Flowable<HttpResponse<String>> getVerifyCode(RequestBody phone) {
        return mRequestApi.getCode(phone);
    }

    @Override
    public Flowable<HttpResponse<LoginBean>> getUserInfo(String userToken) {
        return mRequestApi.getUserInfo(userToken);
    }

    @Override
    public Flowable<HttpResponse<List<BombStyleBean>>> getBombData(RequestBody requestBody) {
        return mRequestApi.getBombData(requestBody);
    }

    @Override
    public Flowable<HttpResponse<List<HotDesignsBean>>> getHotDesign(String userToken, RequestBody body) {
        return mRequestApi.getHotDesign(userToken,body);
    }

    @Override
    public Flowable<HttpResponse<List<AddressBean>>> getAddressData(String userToken) {
        return mRequestApi.getAddress(userToken);
    }

    @Override
    public Flowable<HttpResponse<DesignBaseInfo>> getBaseDesign() {
        return mRequestApi.getBaseDesignData();

    }

    @Override
    public Flowable<HttpResponse<DetailStyleBean>> getDetailDesign(RequestBody requestBody) {
        return mRequestApi.getDetailDesignStyle(requestBody);
    }

    @Override
    public Flowable<HttpResponse<String>> getQiToken(String userToken) {
        return mRequestApi.getQiToken(userToken);
    }

    @Override
    public Flowable<HttpResponse<OrderType>> saveOrderData(String userToken, RequestBody body) {
        return mRequestApi.saveOrderData(userToken,body);
    }

    @Override
    public Flowable<HttpResponse<OrderStyleBean>> getOrdersFromOrderId(String userToken, RequestBody orderId) {
        return mRequestApi.getOrdersFromOrderId(userToken,orderId);
    }

    @Override
    public Flowable<HttpResponse<PayTradeInfo>> payMentOrders(String userToken, RequestBody requestBody) {
        return mRequestApi.payMentOrders(userToken,requestBody);
    }

    @Override
    public Flowable<HttpResponse> saveUserInfo(String userToken, RequestBody requestBody) {
        return mRequestApi.saveUserInfo(userToken,requestBody);
    }

    @Override
    public Flowable<HttpResponse<List<BombStyleBean>>> getDesignOrders(RequestBody requestbody) {
        return mRequestApi.getDesignOrders(requestbody);
    }

    @Override
    public Flowable<HttpResponse> saveAddress(String userToKen, RequestBody requestbody) {
        return mRequestApi.saveAddress(userToKen,requestbody);
    }

    @Override
    public Flowable<HttpResponse<Integer>> requestOrdersPraise(String userToKen, RequestBody requestbody) {
        return mRequestApi.requestPraise(userToKen,requestbody);
    }

    @Override
    public Flowable<HttpResponse<PraiseEntity>> OrderPraise(String token, RequestBody gson) {
        return mRequestApi.OrderPraise(token,gson);
    }

    @Override
    public Flowable<HttpResponse<OrderType>> saveOrdersFromShare(String token, RequestBody requestBody) {
        return mRequestApi.saveOrdersFromShare(token,requestBody);
    }

    @Override
    public Flowable<HttpResponse<Integer>> setDefaultAddress(String token, RequestBody requestBody) {
        return mRequestApi.setDefaultAddress(token,requestBody);
    }

    @Override
    public Flowable<HttpResponse<List<TextureEntity>>> getTextUre(RequestBody gson) {
        return mRequestApi.getTexture(gson);
    }

    @Override
    public Flowable<HttpResponse<VersionUpdateResponse>> getVersionCode(int verCode) {
        return mRequestApi.getVersionCode(verCode);
    }

    @Override
    public Flowable<HttpResponse<ClothesPrice>> getCalculateOrderPrice(String token, RequestBody requestBody) {
        return mRequestApi.calculateOrderPrice(token,requestBody);
    }

    @Override
    public Flowable<HttpResponse<List<MyOrderInfo>>> requestMyOrder(String token, RequestBody gson) {
        return mRequestApi.requestOrderInfo(token,gson);
    }

    @Override
    public Flowable<HttpResponse<OrderType>> updateOrders(String token, RequestBody gson) {
        return mRequestApi.updateOrders(token,gson);
    }

    @Override
    public Flowable<HttpResponse<WechatInfo>> payMomentWeChatOrders(String userToken, RequestBody gson) {
        return mRequestApi.payMomentWeChatOrders(userToken,gson);
    }


}
