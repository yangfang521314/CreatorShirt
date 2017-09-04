package com.example.yf.creatorshirt.mvp.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.model.orders.OrderStyleBean;
import com.example.yf.creatorshirt.mvp.presenter.MyOrderPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.MyOrderContract;
import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;
import com.example.yf.creatorshirt.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 订单详情页面
 */

public class MyOrderActivity extends BaseActivity<MyOrderPresenter> implements MyOrderContract.MyOrderView {

    private static final String TAG = MyOrderActivity.class.getSimpleName();
    @BindView(R.id.order_receiver_address)
    TextView mOrderAddress;
    @BindView(R.id.order_receiver_email)
    TextView mOrderEmail;
    @BindView(R.id.order_receiver_name)
    TextView mOrderName;
    @BindView(R.id.pay_for_money)
    Button mPayfor;
    @BindView(R.id.pay_alipay)
    RadioButton mPayAlipay;
    @BindView(R.id.pay_weixin)
    RadioButton mPayWeixin;
    private String orderId;
    private String payType;
    private OrderStyleBean orderData;

    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }


    @Override
    protected void initView() {
        mAppBarTitle.setText(R.string.my_order);
        mAppBarBack.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.order_receiver_address, R.id.pay_for_money, R.id.pay_weixin, R.id.pay_alipay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.order_receiver_address:
                Intent intent = new Intent();
                intent.setClass(this, AddressActivity.class);
                startActivity(intent);
                break;
            case R.id.pay_for_money:
                //// TODO: 30/06/2017 跳转到支付宝或者微信去支付
//                startCommonActivity(this, null, SuccessPayActivity.class)
                if (payType != null) {
                    if (orderData != null) {
                        mPresenter.payMomentOrders(orderData.getId(), orderData.getAddress(),
                                orderData.getZipcode(), payType, orderData.getFee());
                    } else {
                        ToastUtil.showToast(this, "订单出错", 0);
                    }
                } else {
                    ToastUtil.showToast(this, "请选择支付方式", 0);
                }
                break;
            case R.id.pay_alipay:
                mPayWeixin.setChecked(false);
                mPayAlipay.setChecked(true);
                if(mPayAlipay.isChecked()) {
                    payType = "aliPay";
                }
                break;
            case R.id.pay_weixin:
                mPayWeixin.setChecked(true);
                mPayAlipay.setChecked(false);
                if(mPayWeixin.isChecked()) {
                    payType = "wxPay";
                }
                break;
        }
    }


    @Override
    public void initData() {
        super.initData();
        if (getIntent().getExtras() != null) {
            orderId = getIntent().getExtras().getString("orderId");
        }
        mPresenter.setOrderId(orderId);
        mPresenter.getOrdersData();
    }

    @Override
    protected int getView() {
        return R.layout.activity_my_order;
    }

    //根据订单号查询到的订单详细信息
    @Override
    public void showSuccessOrderData(OrderStyleBean orderStyleBean) {
        this.orderData = orderStyleBean;
    }
}
