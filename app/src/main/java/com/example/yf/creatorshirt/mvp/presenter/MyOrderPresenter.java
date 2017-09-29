package com.example.yf.creatorshirt.mvp.presenter;

import android.util.Log;
import android.widget.Toast;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.common.UserInfoManager;
import com.example.yf.creatorshirt.http.DataManager;
import com.example.yf.creatorshirt.http.HttpResponse;
import com.example.yf.creatorshirt.http.TestRequestServer;
import com.example.yf.creatorshirt.mvp.model.AddressBean;
import com.example.yf.creatorshirt.mvp.model.PayOrderEntity;
import com.example.yf.creatorshirt.mvp.model.orders.OrderStyleBean;
import com.example.yf.creatorshirt.mvp.presenter.base.RxPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.MyOrderContract;
import com.example.yf.creatorshirt.pay.alipay.Alipay;
import com.example.yf.creatorshirt.pay.weixin.WXPay;
import com.example.yf.creatorshirt.utils.RxUtils;
import com.example.yf.creatorshirt.utils.SharedPreferencesUtil;
import com.example.yf.creatorshirt.utils.ToastUtil;
import com.example.yf.creatorshirt.widget.CommonSubscriber;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
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
                        if (orderStyleBean == null) {
                            mView.showErrorMsg("没有订单数据");
                            return;
                        }
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
    public void payMomentOrders(String id, String address, String zipcode, String payType, double fee) {
        Map<String, String> map = new HashMap<>();
        map.put("orderId", orderId);
        map.put("address", address);
        map.put("zipCode", zipcode);
        map.put("payType", payType);
        map.put("fee", String.valueOf(fee));
        Gson gson = new Gson();
        String payEntity = gson.toJson(map);
        final RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), payEntity);
//        addSubscribe(manager.payMentOrders(SharedPreferencesUtil.getUserToken(), requestBody)
//                .compose(RxUtils.<HttpResponse<PayOrderEntity>>rxSchedulerHelper())
//                .compose(RxUtils.<PayOrderEntity>handleResult())
//                .subscribeWith(new CommonSubscriber<PayOrderEntity>(mView) {
//                    @Override
//                    public void onNext(PayOrderEntity payOrderEntity) {
//                        if (payOrderEntity == null) {
//                            mView.showErrorMsg("数据为空");
//                            return;
//                        }
//                        mView.showPayOrder(payOrderEntity);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        super.onError(e);
//                        mView.showErrorMsg(e.getMessage());
//                    }
//                })
//
//        );
        TestRequestServer.getInstance().payMentOrders(UserInfoManager.getInstance().getToken(), requestBody)
                .enqueue(new Callback<HttpResponse>() {
                    @Override
                    public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                        if (response.isSuccessful()) {
                            mView.showErrorMsg(response.body().getResult().toString());
                            mView.showPayOrder(null);
                        }
                        //// TODO: 2017/9/29 支付暂无
                    }

                    @Override
                    public void onFailure(Call<HttpResponse> call, Throwable t) {
                        mView.showErrorMsg(App.getInstance().getString(R.string.no_net));
                    }
                });
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


    public void getAddressData() {
        addSubscribe(manager.getAddressData(SharedPreferencesUtil.getUserToken())
                .compose(RxUtils.<HttpResponse<List<AddressBean>>>rxSchedulerHelper())
                .compose(RxUtils.<List<AddressBean>>handleResult())
                .subscribeWith(new CommonSubscriber<List<AddressBean>>(mView, "请求地址错误") {
                    @Override
                    public void onNext(List<AddressBean> addressBeen) {
                        if (addressBeen == null) {
                            mView.showErrorMsg("地址为空");
                            return;
                        }
                        if (addressBeen.size() == 0)
                            return;

                        mView.showAddressSuccess(addressBeen);
                    }
                })
        );
    }

    /**
     * 支付宝支付
     *
     * @param value
     */
    public void aplipay(PayOrderEntity value) {
        String alipay_appid = value.toString();
        doAlipay(alipay_appid);
    }

    /**
     * 支付宝支付
     *
     * @param pay_param 支付服务生成的支付参数
     */
    private void doAlipay(String pay_param) {
        new Alipay(App.getInstance(), pay_param, new Alipay.AlipayResultCallBack() {
            @Override
            public void onSuccess() {
                ToastUtil.showToast(App.getInstance(), "支付成功", Toast.LENGTH_SHORT);
            }

            @Override
            public void onDealing() {
                ToastUtil.showToast(App.getInstance(), "支付处理中...", Toast.LENGTH_SHORT);
            }

            @Override
            public void onError(int error_code) {
                switch (error_code) {
                    case Alipay.ERROR_RESULT:
                        ToastUtil.showToast(App.getInstance(), "支付失败:支付结果解析错误", Toast.LENGTH_SHORT);
                        break;

                    case Alipay.ERROR_NETWORK:
                        ToastUtil.showToast(App.getInstance(), "支付失败:网络连接错误", Toast.LENGTH_SHORT);
                        break;

                    case Alipay.ERROR_PAY:
                        ToastUtil.showToast(App.getInstance(), "支付错误:支付码支付失败", Toast.LENGTH_SHORT);
                        break;

                    default:
                        ToastUtil.showToast(App.getInstance(), "支付错误", Toast.LENGTH_SHORT);
                        break;
                }

            }

            @Override
            public void onCancel() {
                ToastUtil.showToast(App.getInstance(), "支付取消", Toast.LENGTH_SHORT);
            }
        }).doPay();
    }
}
