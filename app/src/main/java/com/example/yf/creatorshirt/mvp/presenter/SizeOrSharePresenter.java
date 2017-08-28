package com.example.yf.creatorshirt.mvp.presenter;

import com.example.yf.creatorshirt.http.DataManager;
import com.example.yf.creatorshirt.mvp.presenter.base.RxPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.SizeOrShareContract;

import javax.inject.Inject;

import okhttp3.RequestBody;

/**
 * Created by yangfang on 2017/8/28.
 */

public class SizeOrSharePresenter extends RxPresenter<SizeOrShareContract.SizeOrShareView> implements SizeOrShareContract.Presenter {

    private DataManager manager;

    @Inject
    public SizeOrSharePresenter(DataManager manager) {
        this.manager = manager;
    }

    @Override
    public void sendOrderData(String jsonString) {
        RequestBody body =
                RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonString);
        manager.saveOrderData(body);
    }
}
