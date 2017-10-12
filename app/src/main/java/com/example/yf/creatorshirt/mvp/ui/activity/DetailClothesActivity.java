package com.example.yf.creatorshirt.mvp.ui.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.common.UpdateOrdersEvent;
import com.example.yf.creatorshirt.common.UserInfoManager;
import com.example.yf.creatorshirt.mvp.listener.CommonListener;
import com.example.yf.creatorshirt.mvp.model.BombStyleBean;
import com.example.yf.creatorshirt.mvp.model.PraiseEntity;
import com.example.yf.creatorshirt.mvp.model.orders.OrderType;
import com.example.yf.creatorshirt.mvp.model.orders.TextureEntity;
import com.example.yf.creatorshirt.mvp.presenter.DetailClothesPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.DetailClothesContract;
import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;
import com.example.yf.creatorshirt.mvp.ui.adapter.ImageViewAdapter;
import com.example.yf.creatorshirt.mvp.ui.view.ChoiceSizePopupWindow;
import com.example.yf.creatorshirt.mvp.ui.view.CircleView;
import com.example.yf.creatorshirt.mvp.ui.view.ShapeView;
import com.example.yf.creatorshirt.utils.Constants;
import com.example.yf.creatorshirt.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 点击广场热款的详情页面
 */
public class DetailClothesActivity extends BaseActivity<DetailClothesPresenter> implements DetailClothesContract.DetailClothesView {
    private static final String TAG = DetailClothesActivity.class.getSimpleName();
    @BindView(R.id.user_avatar)
    ImageView mUserAavtar;
    @BindView(R.id.user_name)
    TextView mUserName;
    @BindView(R.id.clothes_design_name)
    TextView mClothesName;
    @BindView(R.id.praise)
    ImageView mPraise;
    @BindView(R.id.praise_num)
    TextView mClothesPraiseNum;
    @BindView(R.id.detail_clothes_time)
    TextView mDetailTime;
    @BindView(R.id.clothes_size)
    TextView mClothesSize;
    @BindView(R.id.clothes_id)
    TextView mClothesId;
    @BindView(R.id.clothes_iv_color)
    CircleView mClothesColor;
    @BindView(R.id.clothes_design_price)
    TextView mClothesPrice;
    @BindView(R.id.btn_start)
    Button mStartOrder;
    @BindView(R.id.view_pager_clothes)
    ViewPager mViewPager;
    @BindView(R.id.ll_indicator)
    LinearLayout mLinearLayout;
    @BindView(R.id.rl_detail_clothes)
    RelativeLayout mRelativeClothes;
    private ShapeView shapeView;


    private BombStyleBean mBombStyleBean;
    private List<View> mViewList;
    private String[] mAllImage;
    private boolean isFlag = false;
    private ChoiceSizePopupWindow mPopupWindow;
    private List<TextureEntity> textureEntityList;

    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {
        if (isCheck()) {
            return;
        }
        mViewList = new ArrayList<>();
        mAppBarTitle.setText("衣服详情");
        mAppBarBack.setVisibility(View.VISIBLE);
        mUserName.setText(mBombStyleBean.getUserName());
        Glide.with(this).load(mBombStyleBean.getHeaderImage()).into(mUserAavtar);
        mClothesName.setText(mBombStyleBean.getBaseName());
        mDetailTime.setText(mBombStyleBean.getTitle());
        mClothesPraiseNum.setText(mBombStyleBean.getPraise() + "人赞");
        mClothesId.setText("商品ID :  " + mBombStyleBean.getBaseId());
        int colorN = Color.parseColor("#" + mBombStyleBean.getColor());
        mClothesColor.setOutColor(colorN);
        mClothesPrice.setText("¥ " + mBombStyleBean.getFee());
        mClothesSize.setText("尺寸 :  " + mBombStyleBean.getSize());
        initViewPager();
    }

    private boolean isCheck() {
        if (TextUtils.isEmpty(mBombStyleBean.getColor())) {
            Log.e(TAG, "没有颜色值");
            return true;
        } else if (TextUtils.isEmpty(mBombStyleBean.getBaseName())) {
            Log.e(TAG, "没有衣服名字");
            return true;
        } else if (TextUtils.isEmpty(mBombStyleBean.getTitle())) {
            Log.e(TAG, "没有TITLE");
            return true;
        }
        return false;
    }

    private void initViewPager() {
        mAllImage = mBombStyleBean.getAllImage().split(",");
        ImageViewAdapter adapter = new ImageViewAdapter(this);
        RequestOptions options = new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        final ImageView imageView = new ImageView(this);
        Glide.with(this).asBitmap().apply(options).load(mAllImage[0]).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                imageView.setImageBitmap(resource);
            }
        });
        mViewList.add(imageView);
        final ImageView imageView2 = new ImageView(this);
        Glide.with(this).asBitmap().apply(options).load(mAllImage[1]).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                imageView2.setImageBitmap(resource);
            }
        });
        mViewList.add(imageView2);
        if (mViewList.size() == 2 && mViewList != null) {
            adapter.setData(mViewList);
            mViewPager.setAdapter(adapter);
        }
        shapeView = new ShapeView(this);
        shapeView.setColor(Color.parseColor("#4aa2ce"), Color.parseColor("#dedede"));
        shapeView.initView(2);
        mLinearLayout.addView(shapeView);
        shapeView.setCurrent(0);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                shapeView.setCurrent(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @OnClick({R.id.btn_start, R.id.user_avatar, R.id.praise, R.id.back})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                if (App.isLogin) {
                    initPopupWindow().showAtLocation(mRelativeClothes, Gravity.CENTER | Gravity.BOTTOM, 0, 0);
                    setWindowBgAlpha(Constants.CHANGE_ALPHA);
                } else {
                    startCommonActivity(this, null, LoginActivity.class);
                }
            case R.id.user_avatar:
                break;
            case R.id.praise:
                if (App.isLogin) {
                    if (!TextUtils.isEmpty(UserInfoManager.getInstance().getLoginResponse().getToken())) {
                        mPresenter.OrderPraise(mBombStyleBean.getId());
                    }
                } else {
                    isFlag = true;
                    startCommonActivity(this, null, LoginActivity.class);
                }
                break;
            case R.id.back:
                finish();
                break;
        }

    }

    private void setWindowBgAlpha(float f) {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = f;
        params.dimAmount = f;
        getWindow().setAttributes(params);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    //初始化PopupWindow
    private PopupWindow initPopupWindow() {
        mPopupWindow = new ChoiceSizePopupWindow(this, textureEntityList);
        mPopupWindow.showAtLocation(mRelativeClothes, Gravity.CENTER | Gravity.BOTTOM, 0, 0);
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
                if (isCheckSzieOrTexture()) {
                    saveOrderAndStart();
                }
            }
        });

        return mPopupWindow;
    }

    private boolean isCheckSzieOrTexture() {
        if (mPopupWindow.getBeforeView() == null) {
            ToastUtil.showToast(mContext, "请选择尺寸", 0);
            return false;
        } else if (TextUtils.isEmpty(mPopupWindow.getTextUre())) {
            ToastUtil.showToast(mContext, "请选择材质", 0);
            return false;
        }
        return true;
    }

    private void saveOrderAndStart() {
        mPresenter.saveOrdersFromShare(mBombStyleBean.getId(), mPopupWindow.getSize(), mPopupWindow.getTextUre());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (App.isLogin) {
            if (isFlag) {
                if (UserInfoManager.getInstance().getLoginResponse() != null) {
                    mPresenter.requestOrdersPraise(mBombStyleBean.getId());
                }
            }
        }
        isFlag = false;
    }

    private void startChoiceActivity(OrderType orderType) {
        Bundle bundle = new Bundle();
        bundle.putString("orderId", orderType.getOrderId());
        startCommonActivity(this, bundle, MyOrderActivity.class);
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
        }
    }


    @Override
    protected int getView() {
        return R.layout.activity_detail_clothes;
    }

    @Override
    public void initData() {
        super.initData();
        if (getIntent().getExtras() != null) {
            mBombStyleBean = getIntent().getExtras().getParcelable("detail");
            mPresenter.getTexture(mBombStyleBean);

            if (App.isLogin) {
                if (UserInfoManager.getInstance().getLoginResponse() != null) {

                    mPresenter.requestOrdersPraise(mBombStyleBean.getId());
                }
            }
        }
    }

    @Override
    public void showPraise(Integer integer) {
        if (integer == 1) {
            mPraise.setBackgroundResource(R.drawable.designer_parise_press_bg);
            mClothesPraiseNum.setBackgroundResource(R.drawable.designer_parise_press_bg);
            mPraise.setEnabled(false);
//            mClothesPraiseNum.setEnabled(false);
        } else {
            mPraise.setEnabled(true);
//            mClothesPraiseNum.setEnabled(true);
        }
    }

    @Override
    public void addPraiseNum(PraiseEntity integer) {
        mPraise.setEnabled(false);
        mPraise.setBackgroundResource(R.drawable.designer_parise_press_bg);
        mClothesPraiseNum.setBackgroundResource(R.drawable.designer_parise_press_bg);
        mClothesPraiseNum.setText(integer.getPraise() + "人赞");
    }

    @Override
    public void showSuccessOrder(OrderType orderType) {
        if (orderType != null) {
            startChoiceActivity(orderType);
        }
    }

    @Override
    public void showSuccessTextUre(List<TextureEntity> textureEntity) {
        textureEntityList = textureEntity;
    }

    @Override
    public void showErrorMsg(String msg) {
        super.showErrorMsg(msg);
        ToastUtil.showToast(mContext, msg, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
        }
        EventBus.getDefault().post(new UpdateOrdersEvent(true));
    }
}
