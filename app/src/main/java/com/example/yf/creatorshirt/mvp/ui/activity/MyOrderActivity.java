package com.example.yf.creatorshirt.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.common.DefaultAddressEvent;
import com.example.yf.creatorshirt.mvp.model.AddressBean;
import com.example.yf.creatorshirt.mvp.model.orders.OrderStyleBean;
import com.example.yf.creatorshirt.mvp.presenter.MyOrderPresenter;
import com.example.yf.creatorshirt.mvp.presenter.PayOrderEntity;
import com.example.yf.creatorshirt.mvp.presenter.contract.MyOrderContract;
import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;
import com.example.yf.creatorshirt.mvp.ui.view.CircleView;
import com.example.yf.creatorshirt.utils.ToastUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 订单详情页面
 */

public class MyOrderActivity extends BaseActivity<MyOrderPresenter> implements MyOrderContract.MyOrderView {

    private static final String TAG = MyOrderActivity.class.getSimpleName();
    @BindView(R.id.order_receiver_address)
    TextView mOrderAddress;
    @BindView(R.id.order_receiver_mobile)
    TextView mOrderMobile;
    @BindView(R.id.order_receiver_name)
    TextView mOrderName;
    @BindView(R.id.pay_for_money)
    Button mPayfor;
    @BindView(R.id.pay_alipay)
    RadioButton mPayAlipay;
    @BindView(R.id.pay_weixin)
    RadioButton mPayWeixin;
    @BindView(R.id.pay_clothes_id)
    TextView mPayClothesId;
    @BindView(R.id.pay_clothes_prices)
    TextView mPayClothesPrices;
    @BindView(R.id.pay_clothes_size)
    TextView mPayClothesSize;
    @BindView(R.id.pay_order_clothes_numbers)
    TextView mPayClothesNumbers;
    @BindView(R.id.pay_clothes_color)
    CircleView mPayClothesColor;
    @BindView(R.id.pay_clothes_add)
    ImageView mPayClothesAdd;
    @BindView(R.id.pay_clothes_minus)
    ImageView mPayClothesMinus;
    @BindView(R.id.pay_clothes_sex_style)
    TextView mPayClothesSex;
    @BindView(R.id.pay_freight)
    TextView mPayClothesFreight;
    @BindView(R.id.pay_total)
    TextView mPayClothesTotal;
    @BindView(R.id.pay_clothes_picture)
    ImageView mPayClothesImage;


    private String orderId;
    private String payType;
    private OrderStyleBean orderData;
    private int number = 1;

    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }


    @Override
    protected void initView() {
        mAppBarTitle.setText(R.string.my_order);
        mAppBarBack.setVisibility(View.VISIBLE);

    }

    @OnClick({R.id.order_receiver_address, R.id.pay_for_money, R.id.pay_weixin, R.id.pay_alipay,
            R.id.pay_clothes_add, R.id.pay_clothes_minus})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.order_receiver_address:
                Intent intent = new Intent();
                intent.setClass(this, AddressShowActivity.class);
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
                if (mPayAlipay.isChecked()) {
                    payType = "aliPay";
                }
                break;
            case R.id.pay_weixin:
                mPayWeixin.setChecked(true);
                mPayAlipay.setChecked(false);
                if (mPayWeixin.isChecked()) {
                    payType = "wxPay";
                }
                break;
            case R.id.pay_clothes_add:
                ++number;
                mPayClothesNumbers.setText(String.valueOf(number));
                break;
            case R.id.pay_clothes_minus:
                if (number > 1) {
                    --number;
                } else {
                    number = 1;
                }
                mPayClothesNumbers.setText(String.valueOf(number));
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
        mPresenter.getAddressData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateDefaultAddress(DefaultAddressEvent event) {
        if (event.getFlag()) {
            //// TODO: 2017/9/20 有代检验
            mPresenter.getAddressData();
        }
    }

    @Override
    protected int getView() {
        return R.layout.activity_my_order;
    }

    //根据订单号查询到的订单详细信息
    @Override
    public void showSuccessOrderData(OrderStyleBean orderStyleBean) {
        if (isCheck())
            this.orderData = orderStyleBean;
        if (orderData.getGender() != null) {
            if (orderData.getGender().equals("A")) {
                mPayClothesSex.setText("女-" + orderData.getBaseName());
            }
        }
        mPayClothesColor.setOutColor(Color.parseColor(orderData.getColor()));
        mPayClothesPrices.setText("¥" + orderData.getFee());
        Glide.with(this).load(orderData.getFinishimage()).into(mPayClothesImage);
        mPayClothesNumbers.setText(String.valueOf(number));
    }

    private boolean isCheck() {
        if (TextUtils.isEmpty(orderData.getColor())) {
            Log.e(TAG, "没有颜色值");
            return true;
        } else if (TextUtils.isEmpty(orderData.getBaseName())) {
            Log.e(TAG, "没有衣服名字");
        } else if (TextUtils.isEmpty(orderData.getHeaderImage())) {
            Log.e(TAG, "头像缺少");
        } else if (TextUtils.isEmpty(orderData.getTitle())) {
            Log.e(TAG, "没有TITLE");
        }
        return false;
    }

    @Override
    public void showPayOrder(PayOrderEntity value) {
        if (value == null) {
            ToastUtil.showToast(this, "生成订单出错", 0);
        } else {
            mPresenter.payForWeiChat(value);
        }
    }

    @Override
    public void showAddressSuccess(List<AddressBean> addressBeen) {
        if (addressBeen != null) {
            for (AddressBean m : addressBeen) {
                if (m.getIsDefault() == 1) {//默认地址
                    mOrderName.setText(m.getUserName());
                    mOrderAddress.setText(m.getAddress());
                    mOrderMobile.setText(m.getMobile());
                }
            }
        }
    }

    @Override
    public void showErrorMsg(String msg) {
        ToastUtil.showToast(this, msg, 0);
    }
}
