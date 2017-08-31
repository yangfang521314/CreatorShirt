package com.example.yf.creatorshirt.http;

import com.example.yf.creatorshirt.mvp.model.AddressBean;
import com.example.yf.creatorshirt.mvp.model.BombStyleBean;
import com.example.yf.creatorshirt.mvp.model.GirlData;
import com.example.yf.creatorshirt.mvp.model.HotDesignsBean;
import com.example.yf.creatorshirt.mvp.model.LoginBean;
import com.example.yf.creatorshirt.mvp.model.NewsSummary;
import com.example.yf.creatorshirt.mvp.model.OrderStyleBean;
import com.example.yf.creatorshirt.mvp.model.UserInfo;
import com.example.yf.creatorshirt.mvp.model.basechoice.DesignBaseInfo;
import com.example.yf.creatorshirt.mvp.model.detaildesign.DetailStyleBean;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * Created by yang on 17/05/2017.
 */

public interface HttpHelper {
    Flowable<NewsSummary> getDataNewsSummary();

    Flowable<GirlData> getPhotoList(int size, int page);

    Flowable<HttpResponse<LoginBean>> login(RequestBody body);

    Flowable<HttpResponse<String>> getVerifyCode(RequestBody phone);

    Observable<HttpResponse<UserInfo>> getUserInfo();

    Flowable<HttpResponse<List<BombStyleBean>>> getBombData();

    Flowable<HttpResponse<List<HotDesignsBean>>> getHotDesign();

    Flowable<HttpResponse<List<AddressBean>>> getAddressData();

    Flowable<HttpResponse<DesignBaseInfo>> getBaseDesign();

    Flowable<HttpResponse<DetailStyleBean>> getDetailDesign(RequestBody requestBody);

    Flowable<HttpResponse<String>> getQiToken(String userToken);

    Flowable<HttpResponse<OrderStyleBean>> saveOrderData(String userToken, RequestBody body);
}
