package com.example.yf.creatorshirt.mvp.presenter;

import android.util.Log;

import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.http.DataManager;
import com.example.yf.creatorshirt.http.HttpResponse;
import com.example.yf.creatorshirt.mvp.model.orders.OrderStyleBean;
import com.example.yf.creatorshirt.mvp.presenter.base.RxPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.MyOrderContract;
import com.example.yf.creatorshirt.pay.weixin.WXPay;
import com.example.yf.creatorshirt.utils.RxUtils;
import com.example.yf.creatorshirt.utils.SharedPreferencesUtil;
import com.example.yf.creatorshirt.widget.CommonSubscriber;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

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
                        mView.showSuccessOrderData(orderStyleBean);
                    }
                })
        );
    }


    /**
     * 选择支付获取到支付订单的详细信息
     *
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
        map.put("payType", payType);
        map.put("fee", String.valueOf(fee));
        Gson gson = new Gson();
        String payEntity = gson.toJson(map);
        final RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), payEntity);
        addSubscribe(manager.payMentOrders(SharedPreferencesUtil.getUserToken(), requestBody)
                .compose(RxUtils.<HttpResponse<PayOrderEntity>>rxSchedulerHelper())
                .compose(RxUtils.<PayOrderEntity>handleResult())
                .subscribeWith(new CommonSubscriber<PayOrderEntity>(mView) {
                    @Override
                    public void onNext(PayOrderEntity payOrderEntity) {
                        Log.e("TAG", "GGGGGGGGGGGGGGGG" + payOrderEntity.toString());
                        mView.showPayOrder(payOrderEntity);
                    }
                })

        );
    }

    //微信支付
    public void payForWeiChat(PayOrderEntity value) {
        Log.e("TAG", "VALUE" + value);
        String wx_appid = value.getAppId();
        WXPay.init(App.getInstance(), wx_appid);
        //要在支付前调用
        WXPay.getInstance().doPay(value, new WXPay.WXPayResultCallBack() {
            @Override
            public void onSuccess() {
                mView.showErrorMsg("支付成功");
            }

            @Override
            public void onError(int error_code) {
                switch (error_code) {
                    case WXPay.NO_OR_LOW_WX:
                        mView.showErrorMsg("未安装微信或微信版本过低");
                        break;

                    case WXPay.ERROR_PAY_PARAM:
                        mView.showErrorMsg("参数错误");
                        break;

                    case WXPay.ERROR_PAY:
                        mView.showErrorMsg("支付失败");
                        break;
                }
            }

            @Override
            public void onCancel() {
                mView.showErrorMsg("支付取消");
            }
        });
    }


}
