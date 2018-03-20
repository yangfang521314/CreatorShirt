package com.example.yf.creatorshirt.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.SimpleArrayMap;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.app.GlideApp;
import com.example.yf.creatorshirt.common.UpdateStateEvent;
import com.example.yf.creatorshirt.common.manager.UserInfoManager;
import com.example.yf.creatorshirt.mvp.model.VersionStyle;
import com.example.yf.creatorshirt.mvp.model.orders.ClothesSize;
import com.example.yf.creatorshirt.mvp.model.orders.OrderType;
import com.example.yf.creatorshirt.mvp.model.orders.SaveOrderInfo;
import com.example.yf.creatorshirt.mvp.presenter.OrderInfoPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.OrderInfoContract;
import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;
import com.example.yf.creatorshirt.utils.ToastUtil;
import com.umeng.socialize.UMShareAPI;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 选择设计尺寸大小页面
 */
public class ShowImageActivity extends BaseActivity<OrderInfoPresenter> implements OrderInfoContract.OrderInfoView {
    @BindView(R.id.clothes_front_image)
    ImageView mFrontImage;
    @BindView(R.id.clothes_back_image)
    ImageView mBackImage;
    @BindView(R.id.rl_choice_size)
    LinearLayout mRealChoiceSize;
    @BindView(R.id.btn_choice_order)
    Button mCreateOrder;
    @BindView(R.id.clothes_back)
    TextView mButtonBack;
    @BindView(R.id.clothes_front)
    TextView mButtonFront;
    @BindView(R.id.rl_front)
    RelativeLayout mRlFront;
    @BindView(R.id.rl_back)
    RelativeLayout mRlBack;

    private String mBackImageUrl;
    private String mFrontImageUrl;
    private VersionStyle mOrderBaseInfo;
    private SimpleArrayMap<String, String> arrayList = new SimpleArrayMap<>();
    private SaveOrderInfo saveStyleEntity;

    @Override
    public void initData() {
        super.initData();
        EventBus.getDefault().register(this);
        if (getIntent().getExtras() != null) {
            mOrderBaseInfo = getIntent().getExtras().getParcelable("clothesInfo");
            if (mOrderBaseInfo != null) {
                mFrontImageUrl = mOrderBaseInfo.getFrontUrl();
                if (mOrderBaseInfo.getBackUrl() != null) {
                    mBackImageUrl = mOrderBaseInfo.getBackUrl();
                }
            }
            assert mOrderBaseInfo != null;
            if (mOrderBaseInfo.getPicture1() != null) {
                arrayList.put("0", mOrderBaseInfo.getPicture1());
            }
            if (mOrderBaseInfo.getPicture2() != null) {
                arrayList.put("1", mOrderBaseInfo.getPicture2());
            }
        }
    }

    @Override
    protected int getView() {
        return R.layout.activity_choice;
    }

    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {
        mAppBarTitle.setText(R.string.design);
        mAppBarBack.setVisibility(View.VISIBLE);
        mButtonFront.setSelected(true);
        if (mFrontImageUrl != null) {
            GlideApp.with(this).load(mFrontImageUrl)
                    .placeholder(R.mipmap.mcshort_orange_a)
                    .error(R.mipmap.mcshort_orange_a)
                    .into(mFrontImage);
        }
    }

    @OnClick({R.id.btn_choice_order, R.id.back, R.id.clothes_back, R.id.clothes_front})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_choice_order:
                if (App.isLogin) {
                    mPresenter.setSaveEntity(setBaseInfo());
                    mPresenter.requestSave(mOrderBaseInfo.getFrontUrl(), mOrderBaseInfo.getBackUrl(), arrayList);
                    mCreateOrder.setEnabled(false);
                } else {
                    startCommonActivity(this, null, LoginActivity.class);//跳转到登录界面
                }
                break;
            case R.id.back:
                finish();
                break;
            case R.id.clothes_front:
                mRlFront.setVisibility(View.VISIBLE);
                mRlBack.setVisibility(View.GONE);
                mButtonBack.setSelected(false);
                mButtonFront.setSelected(true);
                GlideApp.with(this).load(mFrontImageUrl)
                        .skipMemoryCache(true)
                        .placeholder(R.mipmap.mcshort_orange_a)
                        .error(R.mipmap.mcshort_orange_a)
                        .into(mFrontImage);
                break;
            case R.id.clothes_back:
                mRlFront.setVisibility(View.GONE);
                mRlBack.setVisibility(View.VISIBLE);
                mButtonBack.setSelected(true);
                mButtonFront.setSelected(false);
                if (mBackImageUrl != null) {
                    GlideApp.with(this).load(mBackImageUrl)
                            .skipMemoryCache(true)
                            .placeholder(R.mipmap.mcshort_orange_a)
                            .error(R.mipmap.mcshort_orange_a)
                            .into(mBackImage);

                }
                break;
        }
    }

    private SaveOrderInfo setBaseInfo() {
        saveStyleEntity = new SaveOrderInfo();
        saveStyleEntity.setBaseId(mOrderBaseInfo.getType());
        saveStyleEntity.setFinishAimage(mOrderBaseInfo.getFrontUrl());
        saveStyleEntity.setFinishBimage(mOrderBaseInfo.getBackUrl());
        saveStyleEntity.setColor(mOrderBaseInfo.getColor());
        saveStyleEntity.setPicture1(mOrderBaseInfo.getPicture1());
        saveStyleEntity.setPicture2(mOrderBaseInfo.getPicture2());
        saveStyleEntity.setMaskAName(mOrderBaseInfo.getMaskA());
        saveStyleEntity.setMaskBName(mOrderBaseInfo.getMaskB());
        if (mOrderBaseInfo.getText() != null && mOrderBaseInfo.getText().size() != 0) {
            saveStyleEntity.setText(mOrderBaseInfo.getText().toString());
        } else {
            saveStyleEntity.setText("");
        }
        if (mOrderBaseInfo.getBackText() != null && mOrderBaseInfo.getBackText().size() != 0) {
            saveStyleEntity.setBackText(mOrderBaseInfo.getBackText().toString());
        } else {
            saveStyleEntity.setBackText("");
        }
        saveStyleEntity.setPartner(UserInfoManager.getInstance().getLoginResponse().getUserInfo().getMobile());
        saveStyleEntity.setDiscount("");
        List<ClothesSize> list = new ArrayList<>();//这里传""
        ClothesSize versionStyle = new ClothesSize();
        versionStyle.setSize("");
        saveStyleEntity.setDetailList(list);
        return saveStyleEntity;
    }

    @Override
    public void showErrorMsg(String msg) {
        ToastUtil.showToast(this, msg, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (App.isLogin) {
            mPresenter.getToken();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUserViewByLogin(UpdateStateEvent event) {
        if (event.getFlag()) {
            mCreateOrder.setEnabled(true);
        }
    }

    @Override
    public void showOrderId(OrderType orderType, SaveOrderInfo saveOrderInfo) {//orderId
        if (orderType.getDispContext() != null) {
            ToastUtil.showToast(this, orderType.getDispContext(), 0);
        }
        Bundle bundle = new Bundle();
        saveOrderInfo.setOrderId(orderType.getOrderId());
        bundle.putParcelable("clothesInfo", saveOrderInfo);
        startCommonActivity(ShowImageActivity.this, bundle, OrderEditActivity.class);
    }

    @Override
    public void showPreExecute(String info) {
        ToastUtil.showProgressToast(this, info, R.drawable.progress_icon);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
