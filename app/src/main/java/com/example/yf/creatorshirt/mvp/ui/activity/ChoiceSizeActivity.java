package com.example.yf.creatorshirt.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.mvp.listener.CommonListener;
import com.example.yf.creatorshirt.mvp.model.orders.OrderBaseInfo;
import com.example.yf.creatorshirt.mvp.model.orders.OrderType;
import com.example.yf.creatorshirt.mvp.model.orders.TextureEntity;
import com.example.yf.creatorshirt.mvp.presenter.SizeOrSharePresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.SizeOrShareContract;
import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;
import com.example.yf.creatorshirt.mvp.ui.view.ChoiceSizePopupWindow;
import com.example.yf.creatorshirt.mvp.ui.view.SharePopupWindow;
import com.example.yf.creatorshirt.utils.Constants;
import com.example.yf.creatorshirt.utils.ToastUtil;
import com.umeng.socialize.UMShareAPI;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 选择设计尺寸大小页面
 */
public class ChoiceSizeActivity extends BaseActivity<SizeOrSharePresenter> implements SizeOrShareContract.SizeOrShareView {
    @BindView(R.id.clothes_image)
    ImageView mImageClothes;
    @BindView(R.id.rl_choice_size)
    RelativeLayout mRealChoiceSize;
    @BindView(R.id.btn_choice_order)
    Button mCreateOrder;
    @BindView(R.id.share_weixin)
    TextView mShareWeixin;
    @BindView(R.id.clothes_back)
    TextView mButtonBack;
    @BindView(R.id.clothes_front)
    TextView mButtonFront;
    private String mBackImageUrl;
    private String mFrontImageUrl;

    private ChoiceSizePopupWindow mPopupWindow;
    private SharePopupWindow mSharePopupWindow;
    private OrderBaseInfo mOrderBaseInfo;
    private String styleContext;//正面背面json数据
    private OrderType mOrderType;
    private List<TextureEntity> textureEntityList = new ArrayList<>();

    @Override
    public void initData() {
        super.initData();
        if (getIntent().getExtras() != null) {
            mOrderBaseInfo = getIntent().getExtras().getParcelable("allImage");
            styleContext = getIntent().getExtras().getString("styleContext");
            if (!TextUtils.isEmpty(mOrderBaseInfo.getBackUrl())) {
                mBackImageUrl = mOrderBaseInfo.getBackUrl();
            }
            if (!TextUtils.isEmpty(mOrderBaseInfo.getFrontUrl())) {
                mFrontImageUrl = mOrderBaseInfo.getFrontUrl();
            }
            mPresenter.getTexture(mOrderBaseInfo);

        }

    }


    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {
        RequestOptions options = new RequestOptions()
                .error(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(this).load(mFrontImageUrl).apply(options).into(mImageClothes);
        mAppBarTitle.setText(R.string.design);
        mAppBarBack.setVisibility(View.VISIBLE);
        mButtonFront.setSelected(true);
    }

    @OnClick({R.id.share_weixin, R.id.btn_choice_order, R.id.back, R.id.clothes_back, R.id.clothes_front})
    public void onClick(View view) {
        RequestOptions options = new RequestOptions()
                .error(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        switch (view.getId()) {
            case R.id.btn_choice_order:
                if (App.isLogin) {
                    if (mOrderType == null) {
                        mPresenter.getToken();
                        initPopupWindow().showAtLocation(mRealChoiceSize, Gravity.CENTER | Gravity.BOTTOM, 0, 0);
                        setWindowBgAlpha(Constants.CHANGE_ALPHA);
                    } else {
                        initPopupWindow().showAtLocation(mRealChoiceSize, Gravity.CENTER | Gravity.BOTTOM, 0, 0);
                        setWindowBgAlpha(Constants.CHANGE_ALPHA);
                    }
                } else {
                    startCommonActivity(this, null, LoginActivity.class);//跳转到登录界面

                }
                break;
            case R.id.back:
                finish();
                break;
            case R.id.share_weixin:
                //// TODO: 30/06/2017 微信分享
                if (App.isLogin) {
                    if (mOrderType == null) {
                        mPresenter.getShareToken();
                    } else {
                        //有orderId直接分享
                        mPresenter.setOrderClothes(mOrderBaseInfo);
                        mPresenter.getShare(this);
                    }
                } else {
                    startCommonActivity(this, null, LoginActivity.class);//跳转到登录界面
                }
                break;
            case R.id.clothes_front:
                mButtonBack.setSelected(false);
                mButtonFront.setSelected(true);
                Glide.with(this).load(mFrontImageUrl).apply(options).into(mImageClothes);
                break;
            case R.id.clothes_back:
                mButtonBack.setSelected(true);
                mButtonFront.setSelected(false);
                Glide.with(this).load(mBackImageUrl).apply(options).into(mImageClothes);
                break;
        }
    }

    //初始化PopupWindow
    private PopupWindow initPopupWindow() {
        mPopupWindow = new ChoiceSizePopupWindow(this, textureEntityList);
        mPopupWindow.showAtLocation(mRealChoiceSize, Gravity.CENTER | Gravity.BOTTOM, 0, 0);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (!mPopupWindow.isShowing()) {
                    setWindowBgAlpha(Constants.NORMAL_ALPHA);
                }
            }
        });
        mPopupWindow.setOnPopupClickListener(new CommonListener.CommonClickListener() {
            @Override
            public void onClickListener() {
                if (isCheck()) {
                    startNewActivity();
                }
            }

        });

        return mPopupWindow;
    }

    private boolean isCheck() {
        if (mPopupWindow.getBeforeView() == null) {
            ToastUtil.showToast(ChoiceSizeActivity.this, "请选择尺寸", 0);
            return false;
        } else if (TextUtils.isEmpty(mPopupWindow.getTextUre())) {
            ToastUtil.showToast(ChoiceSizeActivity.this, "请选择材质", 0);
            return false;
        }
        return true;
    }


    /**
     * 2中情况，分享后的生成订单
     * 保存直接生成订单
     */
    private void startNewActivity() {
        if (mOrderType == null) {
            saveOrderData(Constants.check);
        } else {
            mPresenter.saveOrdersFromShare(mOrderType.getOrderId(), mPopupWindow.getSize(),mPopupWindow.getTextUre());
        }
    }

    /**
     * 保存数据到服务器
     *
     * @param type
     */
    private void saveOrderData(String type) {
        if (type.equals(Constants.check)) {
            mPresenter.setClothesData(mOrderBaseInfo, mPopupWindow.getSize());

        } else if (type.equals(Constants.share)) {
            mPresenter.setClothesData(mOrderBaseInfo, null);
        }
        mPresenter.setStyleContext(styleContext);
        mPresenter.setIM(mBackImageUrl);
        mPresenter.setSaveType(type);
        mPresenter.setTextUre(mPopupWindow.getTextUre());
        mPresenter.request("A", mFrontImageUrl);
    }

    private void setWindowBgAlpha(float f) {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = f;
        params.dimAmount = f;
        getWindow().setAttributes(params);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }


    @Override
    protected int getView() {
        return R.layout.activity_choice;
    }

    //订单直接保存
    @Override
    public void showSuccessData(OrderType data) {
        mOrderType = data;
        Bundle bundle = new Bundle();
        bundle.putString("orderId", data.getOrderId());
        startCommonActivity(ChoiceSizeActivity.this, bundle, MyOrderActivity.class);
    }

    //分享直接保存
    @Override
    public void showShareSuccessData(OrderType s) {
        mOrderType = s;
        initSharePopupWindow().showAtLocation(mRealChoiceSize, Gravity.CENTER | Gravity.BOTTOM, 0, 0);
        setWindowBgAlpha(Constants.CHANGE_ALPHA);
    }

    @Override
    public void showTokenSuccess(String s) {
    }

    @Override
    public void showShareTokenSuccess(String s) {
        if (!TextUtils.isEmpty(s)) {
            saveOrderData(Constants.share);
        }
    }

    @Override
    public void hidePopupWindow() {
        mSharePopupWindow.dismiss();
    }

    /**
     * 材质
     *
     * @param textureEntity
     */
    @Override
    public void showSuccessTextUre(List<TextureEntity> textureEntity) {
        if (textureEntity != null)
            textureEntityList = textureEntity;
    }

    private SharePopupWindow initSharePopupWindow() {
        mSharePopupWindow = new SharePopupWindow(this);
        mSharePopupWindow.showAtLocation(mRealChoiceSize, Gravity.CENTER | Gravity.BOTTOM, 0, 0);
        mSharePopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (!mSharePopupWindow.isShowing()) {
                    setWindowBgAlpha(Constants.NORMAL_ALPHA);
                }
            }
        });
        mSharePopupWindow.setOnPopupClickListener(new CommonListener.CommonClickListener() {
            @Override
            public void onClickListener() {
                mPresenter.setOrderClothes(mOrderBaseInfo);
                mPresenter.getShare(ChoiceSizeActivity.this);
            }
        });
        return mSharePopupWindow;
    }

    @Override
    public void showErrorMsg(String msg) {
        ToastUtil.showToast(this, msg, 0);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
