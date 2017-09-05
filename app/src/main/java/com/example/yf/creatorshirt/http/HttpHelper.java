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

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * Created by yang on 17/05/2017.
 */

public interface HttpHelper {

    Flowable<HttpResponse<LoginBean>> login(RequestBody body);

    Flowable<HttpResponse<String>> getVerifyCode(RequestBody phone);

    Observable<HttpResponse<UserInfo>> getUserInfo();

    Flowable<HttpResponse<List<BombStyleBean>>> getBombData();

    Flowable<HttpResponse<List<HotDesignsBean>>> getHotDesign();

    Flowable<HttpResponse<List<AddressBean>>> getAddressData();

    Flowable<HttpResponse<DesignBaseInfo>> getBaseDesign();

    Flowable<HttpResponse<DetailStyleBean>> getDetailDesign(RequestBody requestBody);

    Flowable<HttpResponse<String>> getQiToken(String userToken);

    Flowable<HttpResponse<OrderType>> saveOrderData(String userToken, RequestBody body);

    Flowable<HttpResponse<OrderStyleBean>> getOrdersFromOrderId(String userToken, RequestBody orderId);

    Flowable<HttpResponse> payMentOrders(String userToken, RequestBody requestBody);
}
