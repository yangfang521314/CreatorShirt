package com.example.yf.creatorshirt.mvp.presenter;

import com.example.yf.creatorshirt.http.DataManager;
import com.example.yf.creatorshirt.http.HttpResponse;
import com.example.yf.creatorshirt.mvp.model.PayInfoEntity;
import com.example.yf.creatorshirt.mvp.model.AddressBean;
import com.example.yf.creatorshirt.mvp.model.PayTradeInfo;
import com.example.yf.creatorshirt.mvp.presenter.base.RxPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.MyOrderContract;
import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;
import com.example.yf.creatorshirt.pay.alipay.Alipay;
import com.example.yf.creatorshirt.utils.Constants;
import com.example.yf.creatorshirt.utils.GsonUtils;
import com.example.yf.creatorshirt.utils.RxUtils;
import com.example.yf.creatorshirt.utils.SharedPreferencesUtil;
import com.example.yf.creatorshirt.utils.demo.util.OrderInfoUtil2_0;
import com.example.yf.creatorshirt.widget.CommonSubscriber;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by yangfang on 2017/91.
 */

public class MyOrderPresenter extends RxPresenter<MyOrderContract.MyOrderView> implements MyOrderContract.Presenter {
    private DataManager manager;

    private PayInfoEntity payInfoEntity;

    public static final String APPID = "2017082308338778";
    private PayTradeInfo tradeInfo;

    @Inject
    public MyOrderPresenter(DataManager manager) {
        this.manager = manager;
    }

    //微信支付
//    public void payForWeiChat(PayTradeInfo value) {
//        Log.e("TAG", "VALUE" + value);
//        String wx_appid = value.getAppId();
//        WXPay.init(App.getInstance(), wx_appid);
//        //要在支付前调用
//        WXPay.getInstance().doPay(value, new WXPay.WXPayResultCallBack() {
//            @Override
//            public void onSuccess() {
//                mView.showErrorMsg("支付成功");
//            }
//
//            @Override
//            public void onError(int error_code) {
//                switch (error_code) {
//                    case WXPay.NO_OR_LOW_WX:
//                        mView.showErrorMsg("未安装微信或微信版本过低");
//                        break;
//
//                    case WXPay.ERROR_PAY_PARAM:
//                        mView.showErrorMsg("参数错误");
//                        break;
//
//                    case WXPay.ERROR_PAY:
//                        mView.showErrorMsg("支付失败");
//                        break;
//                }
//            }
//
//            @Override
//            public void onCancel() {
//                mView.showErrorMsg("支付取消");
//            }
//        });
//    }


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
     * @param activity
     */
    public void aplipay(BaseActivity activity) {
        if (tradeInfo == null) {
            return;
        }
        boolean rsa = false;
        //构造支付订单参数列表
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa, tradeInfo);
        //构造支付订单参数信息
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        //对支付参数信息进行签名
        String sign = OrderInfoUtil2_0.getSign(params, Constants.RSA_PRIVATE, rsa);
        //订单信息
        final String orderInfo = orderParam + "&" + sign;
        doAlipay(orderInfo, activity);
    }


    /**
     * 支付宝支付
     *
     * @param pay_param 支付服务生成的支付参数
     * @param activity
     */
    private void doAlipay(String pay_param, BaseActivity activity) {
        new Alipay(activity, pay_param, new Alipay.AlipayResultCallBack() {
            @Override
            public void onSuccess() {
                mView.showPaySuccess("支付成功");
            }

            @Override
            public void onDealing() {
                mView.showErrorMsg("支付处理中...");
            }

            @Override
            public void onError(int error_code) {
                switch (error_code) {
                    case Alipay.ERROR_RESULT:
                        mView.showErrorMsg("支付失败:支付结果解析错误...");
                        break;

                    case Alipay.ERROR_NETWORK:
                        mView.showErrorMsg("支付失败:网络连接错误");
                        break;

                    case Alipay.ERROR_PAY:
                        mView.showErrorMsg("支付错误:支付码支付失败");
                        break;

                    default:
                        mView.showErrorMsg("支付错误");
                        break;
                }

            }

            @Override
            public void onCancel() {
                mView.showErrorMsg("支付取消");
            }
        }).doPay();
    }


    /**
     * 选择支付获取到支付订单的详细信息
     */
    public void payMomentOrders() {
        addSubscribe(manager.payMentOrders(SharedPreferencesUtil.getUserToken(), GsonUtils.getGson(payInfoEntity))
                .compose(RxUtils.<HttpResponse<PayTradeInfo>>rxSchedulerHelper())
                .compose(RxUtils.<PayTradeInfo>handleResult())
                .subscribeWith(new CommonSubscriber<PayTradeInfo>(mView) {
                    @Override
                    public void onNext(PayTradeInfo payTradeInfo) {
                        if (payTradeInfo == null) {
                            mView.showErrorMsg("数据为空");
                        } else {
                            tradeInfo = payTradeInfo;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.showErrorMsg(e.getMessage());
                    }
                })

        );
    }


    public void setSaveEntity(PayInfoEntity payInfoEntity) {
        this.payInfoEntity = payInfoEntity;
    }
}
