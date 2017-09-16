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
import com.example.yf.creatorshirt.mvp.presenter.PayOrderEntity;

import java.util.List;

import io.reactivex.Flowable;
import okhttp3.RequestBody;

/**
 * Created by yang on 17/05/2017.
 */

public interface HttpHelper {

    Flowable<HttpResponse<LoginBean>> login(RequestBody body);

    Flowable<HttpResponse<String>> getVerifyCode(RequestBody phone);

    Flowable<HttpResponse<LoginBean>> getUserInfo(String userToken);

    Flowable<HttpResponse<List<BombStyleBean>>> getBombData(RequestBody userToken);

    Flowable<HttpResponse<List<HotDesignsBean>>> getHotDesign(String userToken, RequestBody body);

    Flowable<HttpResponse<List<AddressBean>>> getAddressData();

    Flowable<HttpResponse<DesignBaseInfo>> getBaseDesign();

    Flowable<HttpResponse<DetailStyleBean>> getDetailDesign(RequestBody requestBody);

    Flowable<HttpResponse<String>> getQiToken(String userToken);

    Flowable<HttpResponse<OrderType>> saveOrderData(String userToken, RequestBody body);

    Flowable<HttpResponse<OrderStyleBean>> getOrdersFromOrderId(String userToken, RequestBody orderId);

    Flowable<HttpResponse<PayOrderEntity>> payMentOrders(String userToken, RequestBody requestBody);

    Flowable<HttpResponse> saveUserInfo(String userToken, RequestBody gson);

    Flowable<HttpResponse<List<BombStyleBean>>> getDesignOrders(RequestBody requestbody);

    Flowable<HttpResponse> saveAddress(String userToKen, RequestBody requestbody);

    Flowable<HttpResponse<Integer>> requestOrdersPraise(String userToKen, RequestBody requestbody);

    Flowable<HttpResponse<PraiseEntity>> OrderPraise(String token, RequestBody gson);
}
