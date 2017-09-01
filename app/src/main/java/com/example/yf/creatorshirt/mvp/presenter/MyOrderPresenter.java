package com.example.yf.creatorshirt.mvp.presenter;

import com.example.yf.creatorshirt.http.DataManager;
import com.example.yf.creatorshirt.http.HttpResponse;
import com.example.yf.creatorshirt.mvp.model.OrderStyleBean;
import com.example.yf.creatorshirt.mvp.presenter.base.RxPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.MyOrderContract;
import com.example.yf.creatorshirt.utils.RxUtils;
import com.example.yf.creatorshirt.utils.SharedPreferencesUtil;
import com.example.yf.creatorshirt.widget.CommonSubscriber;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.RequestBody;

/**
 * Created by yangfang on 2017/91.
 */

public class MyOrderPresenter extends RxPresenter<MyOrderContract.MyOrderView> implements MyOrderContract.Presenter {

    private DataManager manager;
    private String orderId;

    @Inject
    public MyOrderPresenter(DataManager manager) {
        this.manager = manager;
    }

    @Override
    public void setOrderId(String orderId) {
        this.orderId = orderId;

    }

    @Override
    public void getOrdersData() {
        Map<String, String> map = new HashMap<>();
        map.put("id", orderId);
        Gson gson = new Gson();
        String orderEntity = gson.toJson(map);
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), orderEntity);
        addSubscribe(manager.getOrdersFromOrderId(SharedPreferencesUtil.getUserToken(), requestBody)
                .compose(RxUtils.<HttpResponse<OrderStyleBean>>rxSchedulerHelper())
                .compose(RxUtils.<OrderStyleBean>handleResult())
                .subscribeWith(new CommonSubscriber<OrderStyleBean>(mView) {
                    @Override
                    public void onNext(OrderStyleBean orderStyleBean) {

                    }
                })
        );
    }


}
