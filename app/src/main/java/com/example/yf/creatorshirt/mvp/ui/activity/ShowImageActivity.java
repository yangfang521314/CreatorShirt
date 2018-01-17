package com.example.yf.creatorshirt.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.app.GlideApp;
import com.example.yf.creatorshirt.mvp.model.VersionStyle;
import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;
import com.example.yf.creatorshirt.utils.ToastUtil;
import com.umeng.socialize.UMShareAPI;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 选择设计尺寸大小页面
 */
public class ShowImageActivity extends BaseActivity {
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

    @Override
    public void initData() {
        super.initData();
        if (getIntent().getExtras() != null) {
            mOrderBaseInfo = getIntent().getExtras().getParcelable("clothesInfo");
            if (mOrderBaseInfo != null) {
                mBackImageUrl = mOrderBaseInfo.getBackUrl();
                mFrontImageUrl = mOrderBaseInfo.getFrontUrl();
            }
        }
    }


    @Override
    protected int getView() {
        return R.layout.activity_choice;
    }

    @Override
    protected void inject() {
    }

    @Override
    protected void initView() {
        mAppBarTitle.setText(R.string.design);
        mAppBarBack.setVisibility(View.VISIBLE);
        mButtonFront.setSelected(true);
        if (mFrontImageUrl != null) {
            GlideApp.with(this).load(mFrontImageUrl).into(mFrontImage);
        }
    }

    @OnClick({R.id.btn_choice_order, R.id.back, R.id.clothes_back, R.id.clothes_front})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_choice_order:
                if (App.isLogin) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("clothesInfo", mOrderBaseInfo);
                    startCommonActivity(ShowImageActivity.this, bundle, OrderEditActivity.class);
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
                        .into(mFrontImage);
                break;
            case R.id.clothes_back:
                mRlFront.setVisibility(View.GONE);
                mRlBack.setVisibility(View.VISIBLE);
                mButtonBack.setSelected(true);
                mButtonFront.setSelected(false);
                if (mBackImageUrl != null) {
                    GlideApp.with(this).load(mBackImageUrl)
                            .into(mBackImage);
                }
                break;
        }
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
}
