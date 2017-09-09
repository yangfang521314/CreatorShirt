package com.example.yf.creatorshirt.mvp.ui.activity;

import android.net.Uri;
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

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.listener.CommonListener;
import com.example.yf.creatorshirt.mvp.model.detaildesign.CommonStyleData;
import com.example.yf.creatorshirt.mvp.model.orders.OrderData;
import com.example.yf.creatorshirt.mvp.model.orders.OrderType;
import com.example.yf.creatorshirt.mvp.presenter.SizeOrSharePresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.SizeOrShareContract;
import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;
import com.example.yf.creatorshirt.mvp.ui.view.ChoiceSizePopupWindow;
import com.example.yf.creatorshirt.utils.Constants;
import com.example.yf.creatorshirt.utils.ToastUtil;

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
    private CommonStyleData mFrontData;
    private CommonStyleData mBackData;
    private ChoiceSizePopupWindow mPopupWindow;

    @Override
    public void initData() {
        super.initData();
        mPresenter.getToken();
        if (getIntent().getExtras() != null) {
            mFrontData = getIntent().getExtras().getParcelable("front");
            mBackData = getIntent().getExtras().getParcelable("back");
            if (!TextUtils.isEmpty(mBackData.getBackUrl())) {
                mBackImageUrl = mBackData.getBackUrl();
            }
            if (!TextUtils.isEmpty(mFrontData.getFrontUrl())) {
                mFrontImageUrl = mFrontData.getFrontUrl();
            }
        }

    }

    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {
        mAppBarTitle.setText(R.string.design);
        mAppBarBack.setVisibility(View.VISIBLE);
        mImageClothes.setImageURI(Uri.parse(mFrontImageUrl));
        mButtonFront.setSelected(true);
    }

    @OnClick({R.id.share_weixin, R.id.btn_choice_order, R.id.back, R.id.clothes_back, R.id.clothes_front})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_choice_order:
                initPopupWindow().showAtLocation(mRealChoiceSize, Gravity.CENTER | Gravity.BOTTOM, 0, 0);
                setWindowBgAlpha(Constants.CHANGE_ALPHA);
                break;
            case R.id.back:
                finish();
                break;
            case R.id.share_weixin:
                //// TODO: 30/06/2017 微信分享
                break;
            case R.id.clothes_front:
                mButtonBack.setSelected(false);
                mButtonFront.setSelected(true);
                mImageClothes.setImageURI(Uri.parse(mFrontImageUrl));
                break;
            case R.id.clothes_back:
                mButtonBack.setSelected(true);
                mButtonFront.setSelected(false);
                mImageClothes.setImageURI(Uri.parse(mBackImageUrl));
                break;
        }
    }

    //初始化PopupWindow
    private PopupWindow initPopupWindow() {
        mPopupWindow = new ChoiceSizePopupWindow(this);
        mPopupWindow.showAtLocation(mRealChoiceSize, Gravity.CENTER | Gravity.BOTTOM, 0, 0);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (!mPopupWindow.isShowing()) {
                    setWindowBgAlpha(Constants.NORMAL_ALPHA);
                }
            }
        });
        mPopupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mPopupWindow.setOnPopupClickListener(new CommonListener.CommonClickListener() {
            @Override
            public void onClickListener() {
                if (mPopupWindow.getBeforeView() != null) {
                    startNewActivity();
                } else {
                    ToastUtil.showToast(ChoiceSizeActivity.this, "请选择尺寸", 0);
                }
            }
        });
        return mPopupWindow;
    }

    private void startNewActivity() {
        saveOrderData();
    }

    /**
     * 保存数据到服务器
     */
    private void saveOrderData() {
        OrderData orderData = new OrderData();
        orderData.setBackData(mBackData);
        orderData.setFrontData(mFrontData);
        String styleContext = orderData.getJsonObject();
        mPresenter.setClothesData(mFrontData, mBackData, mPopupWindow.getSize());
        mPresenter.setStyleContext(styleContext);
        mPresenter.setIM(mBackImageUrl);
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

    @Override
    public void showSuccessData(OrderType data) {
        Bundle bundle = new Bundle();
        bundle.putString("orderId", data.getOrderId());
        startCommonActivity(ChoiceSizeActivity.this, bundle, MyOrderActivity.class);
    }

    @Override
    public void showErrorMsg(String msg) {
        ToastUtil.showToast(this, msg, 0);
    }

    @Override
    public void stateError() {

    }
}
