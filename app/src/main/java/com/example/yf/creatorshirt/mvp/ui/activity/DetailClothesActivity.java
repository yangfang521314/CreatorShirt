package com.example.yf.creatorshirt.mvp.ui.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.model.BombStyleBean;
import com.example.yf.creatorshirt.mvp.model.orders.OrderBaseInfo;
import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;
import com.example.yf.creatorshirt.mvp.ui.adapter.ImageViewAdapter;
import com.example.yf.creatorshirt.mvp.ui.view.CircleView;
import com.example.yf.creatorshirt.mvp.ui.view.ShapeView;
import com.example.yf.creatorshirt.utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DetailClothesActivity extends BaseActivity {
    private static final String TAG = DetailClothesActivity.class.getSimpleName();
    @BindView(R.id.user_avatar)
    ImageView mUserAavtar;
    @BindView(R.id.user_name)
    TextView mUserName;
    @BindView(R.id.clothes_design_name)
    TextView mClothesName;
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
    private ShapeView shapeView;


    private BombStyleBean mBombStyleBean;
    private List<View> mViewList;
    private String[] mAllImage;

    @Override
    protected void inject() {

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
        } else if (TextUtils.isEmpty(mBombStyleBean.getHeaderImage())) {
            Log.e(TAG, "头像缺少");
        } else if (TextUtils.isEmpty(mBombStyleBean.getTitle())) {
            Log.e(TAG, "没有TITLE");
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

    @OnClick({R.id.btn_start, R.id.user_avatar})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                if (TextUtils.isEmpty(SharedPreferencesUtil.getUserToken()) ||
                        TextUtils.isEmpty((SharedPreferencesUtil.getUserPhone()))
                        || SharedPreferencesUtil.getUserId() == 0) {
                    startCommonActivity(this, null, LoginActivity.class);
                } else {
                    startChoiceActivity();
                }
            case R.id.user_avatar:
                break;
        }

    }

    private void startChoiceActivity() {
        Bundle bundle = new Bundle();
        OrderBaseInfo orderBaseInfo = new OrderBaseInfo();
        orderBaseInfo.setType(mBombStyleBean.getBaseId());
        orderBaseInfo.setColor(mBombStyleBean.getColor());
        orderBaseInfo.setGender(mBombStyleBean.getGender());
        orderBaseInfo.setFrontUrl(mAllImage[0]);
        orderBaseInfo.setBackUrl(mAllImage[1]);
        bundle.putParcelable("allImage", orderBaseInfo);
        bundle.putString("styleContext", mBombStyleBean.getStyleContext());
        startCommonActivity(this, bundle, ChoiceSizeActivity.class);
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
        }
    }
}
