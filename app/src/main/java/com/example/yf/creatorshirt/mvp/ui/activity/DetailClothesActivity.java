package com.example.yf.creatorshirt.mvp.ui.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
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
    private List<View> urlList;

    @Override
    protected void inject() {

    }

    @Override
    protected void initView() {
        urlList = new ArrayList<>();
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

    private void initViewPager() {
        String[] imageUrl = mBombStyleBean.getAllImage().split(",");
        ImageViewAdapter adapter = new ImageViewAdapter(this);
        RequestOptions options = new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        final ImageView imageView = new ImageView(this);
        Glide.with(this).asBitmap().apply(options).load(imageUrl[0]).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                imageView.setImageBitmap(resource);
            }
        });
        urlList.add(imageView);
        final ImageView imageView2 = new ImageView(this);
        Glide.with(this).asBitmap().apply(options).load(imageUrl[1]).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                imageView2.setImageBitmap(resource);
            }
        });
        urlList.add(imageView2);
        if (urlList.size() == 2) {
            adapter.setData(urlList);
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
                        TextUtils.isEmpty((SharedPreferencesUtil.getUserPhone()))) {
                    startCommonActivity(this, null, LoginActivity.class);
                }else {
                    Bundle bundle = new Bundle();

                    startCommonActivity(this,bundle,ChoiceSizeActivity.class);
                }
                break;
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
        }
    }
}
