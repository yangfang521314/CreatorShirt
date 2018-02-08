package com.example.yf.creatorshirt.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.GlideApp;
import com.example.yf.creatorshirt.common.DefaultAddressEvent;
import com.example.yf.creatorshirt.mvp.model.AddressBean;
import com.example.yf.creatorshirt.mvp.model.PayInfoEntity;
import com.example.yf.creatorshirt.mvp.model.orders.SaveOrderInfo;
import com.example.yf.creatorshirt.mvp.presenter.MyOrderPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.MyOrderContract;
import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;
import com.example.yf.creatorshirt.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 付款详情页面
 */

public class ChoicePayActivity extends BaseActivity<MyOrderPresenter> implements MyOrderContract.MyOrderView {

    private static final String TAG = ChoicePayActivity.class.getSimpleName();
    @BindView(R.id.order_receiver_address)
    TextView mOrderAddress;
    @BindView(R.id.order_receiver_mobile)
    TextView mOrderMobile;
    @BindView(R.id.order_receiver_name)
    TextView mOrderName;
    @BindView(R.id.pay_for_money)
    Button mPayfor;
    @BindView(R.id.pay_alipay)
    CheckBox mPayAlipay;
    @BindView(R.id.pay_weixin)
    CheckBox mPayWeixin;
    @BindView(R.id.pay_clothes_picture)
    ImageView mPayClothesImage;
    @BindView(R.id.choice_iv)
    ImageView mChoiceImageView;
    @BindView(R.id.total)
    TextView mTextTotal;
    @BindView(R.id.freight)
    TextView mFreight;


    private String payType;
    private SaveOrderInfo mOrderClothesInfo;


    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }


    @Override
    public void initData() {
        super.initData();
        if (getIntent().getExtras() != null) {
            if (getIntent().hasExtra("orderInfo") && getIntent().getExtras() != null) {
                mPresenter.getAddressData();
                mOrderClothesInfo = getIntent().getExtras().getParcelable("orderInfo");
            }

        }

    }

    @Override
    protected void initView() {
        mAppBarTitle.setText(R.string.my_order);
        mAppBarBack.setVisibility(View.VISIBLE);
        if (mOrderClothesInfo.getFinishAimage() != null) {
            GlideApp.with(this).load(mOrderClothesInfo.getFinishAimage()).error(R.mipmap.mbaseball_white_a).into(mPayClothesImage);
        }
        if (mOrderClothesInfo.getOrderPrice() != 0) {
            mTextTotal.setText("价格：" + "¥" + mOrderClothesInfo.getOrderPrice());
        }
    }


    @Override
    protected int getView() {
        return R.layout.activity_my_order;
    }


    @OnClick({R.id.order_receiver_address, R.id.pay_for_money, R.id.pay_weixin, R.id.pay_alipay,
            R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.order_receiver_address:
            case R.id.choice_iv:
                Bundle bundle = new Bundle();
                bundle.putBoolean("choice", true);
                startCommonActivity(this, bundle, AddressShowActivity.class);
                break;
            case R.id.pay_for_money:
                if (payType == null) {
                    ToastUtil.showToast(mContext, "请选择支付方式", 0);
                    return;
                }
                if (payType.equals("aliPay")) {
                    mPresenter.aplipay(this);
                } else {
                    mPresenter.payForWeiChat(this);
                }
                break;
            case R.id.pay_alipay:
                mPayWeixin.setChecked(false);
                mPayAlipay.setChecked(true);
                if (mPayAlipay.isChecked()) {
                    payType = "aliPay";
                }
                if (isCheck()) {
                    paMomentOrders();
                }
                break;
            case R.id.pay_weixin:
                mPayWeixin.setChecked(true);
                mPayAlipay.setChecked(false);
                if (mPayWeixin.isChecked()) {
                    payType = "wxPay";
                }
                if (isCheck()) {
                    paMomentOrders();
                }
                break;
            case R.id.back:
                finish();
                break;
        }

    }

    /**
     * 支付上传
     */
    private void paMomentOrders() {
        PayInfoEntity payInfoEntity = new PayInfoEntity();
        payInfoEntity.setAddress(mOrderAddress.getText().toString());
        payInfoEntity.setMobile(mOrderMobile.getText().toString());
        payInfoEntity.setPaymode(payType);
        payInfoEntity.setOrderId(mOrderClothesInfo.getOrderId());
        payInfoEntity.setZipcode("");
        payInfoEntity.setUsername(mOrderName.getText().toString());
        mPresenter.setSaveEntity(payInfoEntity);
        if ("aliPay".equals(payType)) {
            mPresenter.payMomentOrders();//支付信息
        } else {
            mPresenter.payMomentWeChatOrders();
        }
    }

    private boolean isCheck() {
        if (TextUtils.isEmpty(mOrderAddress.getText().toString())) {
            ToastUtil.showToast(this, "请填写收件人的地址", 0);
            return false;
        }
        if (TextUtils.isEmpty(mOrderMobile.getText().toString())) {
            ToastUtil.showToast(this, "请填写收件人的电话", 0);
            return false;
        }
        if (TextUtils.isEmpty(mOrderName.getText().toString())) {
            ToastUtil.showToast(this, "请填写收件人姓名", 0);
            return false;
        }
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateDefaultAddress(DefaultAddressEvent event) {
        if (event.getFlag() != null) {
            upDateAddress(event.getFlag());
        }
    }

    private void upDateAddress(AddressBean data) {
        mOrderName.setText(data.getUserName());
        mOrderAddress.setText(data.getAddress());
        mOrderMobile.setText(data.getMobile());
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

    /**
     * 支付成功跳转
     *
     * @param msg
     */
    @Override
    public void showPaySuccess(String msg) {
        startCommonActivity(this, null, SuccessPayActivity.class);
    }

    @Override
    public void showErrorMsg(String msg) {
        ToastUtil.showToast(this, msg, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
