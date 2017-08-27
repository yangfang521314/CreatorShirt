package com.example.yf.creatorshirt.mvp.ui.activity;

import android.net.Uri;
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
import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;
import com.example.yf.creatorshirt.mvp.ui.view.ChoiceSizePopupWindow;
import com.example.yf.creatorshirt.utils.Constants;
import com.example.yf.creatorshirt.utils.LogUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 选择设计尺寸大小页面
 */
public class ChoiceSizeActivity extends BaseActivity {
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

    private String mBackImage;
    private String mFrontImage;
    private ChoiceSizePopupWindow mPopupWindow;

    @Override
    public void initData() {
        super.initData();
        if (getIntent().getExtras() != null) {
            mBackImage = getIntent().getExtras().getString("backUrl");
            mFrontImage = getIntent().getExtras().getString("frontUrl");
            LogUtil.e("choiceSizeActivity","mB:"+mBackImage+"mF："+mFrontImage);
        } else {
//            imagePath = getString(R.string.my_order);
        }

    }

    @Override
    protected void inject() {
//        getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {
        mAppBarTitle.setText(R.string.design);
        mAppBarBack.setVisibility(View.VISIBLE);
        mImageClothes.setImageURI(Uri.parse(mFrontImage));
    }

    @OnClick({R.id.share_weixin, R.id.btn_choice_order, R.id.back,R.id.clothes_back,R.id.clothes_front})
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
                mImageClothes.setImageURI(Uri.parse(mFrontImage));
                break;
            case R.id.clothes_back:
                mButtonBack.setSelected(true);
                mButtonFront.setSelected(false);
                mImageClothes.setImageURI(Uri.parse(mBackImage));
                break;
        }
    }

    //初始化PopupWindow
    private PopupWindow initPopupWindow() {
        mPopupWindow = new ChoiceSizePopupWindow();
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
//                Intent intent = new Intent();
//                intent.setClass(ChoiceSizeActivity.this, MyOrderActivity.class);
//                startActivity(intent);
                startCommonActivity(ChoiceSizeActivity.this,null,MyOrderActivity.class);
            }
        });
        return mPopupWindow;
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

}
