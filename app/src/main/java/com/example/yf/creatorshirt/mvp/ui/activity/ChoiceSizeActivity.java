package com.example.yf.creatorshirt.mvp.ui.activity;

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
import com.example.yf.creatorshirt.mvp.ui.view.ChoiceSizePopupWindow;
import com.example.yf.creatorshirt.utils.Constants;

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

    private String imagePath;
    private ChoiceSizePopupWindow mPopupWindow;

    @Override
    public void initData() {
        super.initData();
//        imagePath = getIntent().getStringExtra("imageUrl");
//        if (imagePath == null) {
//            return;
//        }

    }

    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {
        mAppBarTitle.setText(R.string.design_title_bar);
        mAppBarBack.setVisibility(View.VISIBLE);
//        mImageClothes.setImageURI(Uri.parse(imagePath));

    }

    @OnClick({R.id.share_weixin, R.id.btn_choice_order, R.id.back})
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
                startCommonActivity(ChoiceSizeActivity.this,MyOrderActivity.class);
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
