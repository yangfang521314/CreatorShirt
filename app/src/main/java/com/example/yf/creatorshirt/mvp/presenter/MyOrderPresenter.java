package com.example.yf.creatorshirt.mvp.presenter;

import android.util.Log;

import com.example.yf.creatorshirt.http.DataManager;
import com.example.yf.creatorshirt.http.HttpResponse;
import com.example.yf.creatorshirt.http.TestRequestServer;
import com.example.yf.creatorshirt.mvp.model.orders.OrderStyleBean;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                        mView.showSuccessOrderData(orderStyleBean);
                    }
                })
        );
    }


    /**
     * 选择支付
     * @param id
     * @param address
     * @param zipcode
     * @param payType
     * @param fee
     */
    public void payMomentOrders(int id, String address, String zipcode, String payType, double fee) {
        Map<String, String> map = new HashMap<>();
        map.put("orderId", orderId);
        map.put("address", address);
        map.put("zipCode", zipcode);
        map.put("payType",payType);
        map.put("fee",String.valueOf(fee));
        Gson gson = new Gson();
        String payEntity = gson.toJson(map);
        final RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), payEntity);
//        addSubscribe(manager.payMentOrders(SharedPreferencesUtil.getUserToken(),requestBody));
        Log.e("my fff","re"+payEntity.toString());
        TestRequestServer.getInstance().payMentOrders(SharedPreferencesUtil.getUserToken(),requestBody)
        .enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                Log.e("tga","fuck you"+response.body().getResult());
            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                Log.e("tag","on failure");
            }
        });
    }

}
