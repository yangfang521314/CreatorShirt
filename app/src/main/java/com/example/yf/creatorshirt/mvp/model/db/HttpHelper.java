package com.example.yf.creatorshirt.mvp.model.db;

import com.example.yf.creatorshirt.mvp.model.bean.GirlData;
import com.example.yf.creatorshirt.mvp.model.bean.LoginBean;
import com.example.yf.creatorshirt.mvp.model.bean.NewsSummary;

import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * Created by yang on 17/05/2017.
 */

public interface HttpHelper {
    Flowable<NewsSummary> getDataNewsSummary();

    Flowable<GirlData> getPhotoList(int size, int page);

    Observable<LoginBean> login(String phone, String code);

    Observable<LoginBean> getVerifyCode(String phone);

}
