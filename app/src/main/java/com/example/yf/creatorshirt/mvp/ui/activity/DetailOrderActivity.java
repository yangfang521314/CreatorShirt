package com.example.yf.creatorshirt.mvp.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.GlideApp;
import com.example.yf.creatorshirt.mvp.model.MyOrderInfo;
import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;
import com.example.yf.creatorshirt.mvp.ui.adapter.ImageViewAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.MyOrderSizeAdapter;
import com.example.yf.creatorshirt.mvp.ui.view.ShapeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DetailOrderActivity extends BaseActivity {
    @BindView(R.id.btn_start)
    Button mStartOrder;
    @BindView(R.id.view_pager_clothes)
    ViewPager mViewPager;
    @BindView(R.id.ll_indicator)
    LinearLayout mLinearLayout;
    @BindView(R.id.order_receiver_name)
    TextView mReceiverName;
    @BindView(R.id.order_mobile)
    TextView mReceiverMobile;
    @BindView(R.id.order_receiver_address)
    TextView mReceiverAddress;
    @BindView(R.id.recyclerView_size)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_price)
    TextView mTvPrices;
    private ShapeView shapeView;
    private MyOrderInfo myOrderInfo;
    private String[] mAllImage = new String[2];
    private List<View> mViewList = new ArrayList<>();

    @Override
    protected void inject() {
    }

    @Override
    public void initData() {
        super.initData();
        if (getIntent().hasExtra("orderInfo") && getIntent().getExtras() != null) {
            myOrderInfo = getIntent().getExtras().getParcelable("orderInfo");
            if (myOrderInfo != null) {
                mAllImage[0] = myOrderInfo.getAllimage1();
                mAllImage[1] = myOrderInfo.getAllimage2();
            }
        }
    }

    @Override
    protected void initView() {
        mAppBarTitle.setText("订单详情");
        mAppBarBack.setVisibility(View.VISIBLE);
        initViewPager();
        initViewSize();
        mReceiverName.setText(myOrderInfo.getUsername());
        mReceiverMobile.setText(myOrderInfo.getMobile());
        mReceiverAddress.setText(myOrderInfo.getAddress());
        mTvPrices.setText("¥：" + myOrderInfo.getOrderPrice());

    }

    @OnClick({R.id.btn_start})
    void onClick(View view) {
        if (view.getId() == R.id.btn_start) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("clothesInfoOrder", myOrderInfo);
            startCommonActivity(this, bundle, OrderEditActivity.class);
        }
    }

    private void initViewSize() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyOrderSizeAdapter myOrderSizeAdapter = new MyOrderSizeAdapter(this);
        myOrderSizeAdapter.setData(myOrderInfo.getDetailList());
        mRecyclerView.setAdapter(myOrderSizeAdapter);
    }

    @Override
    protected int getView() {
        return R.layout.activity_detail_order;
    }

    private void initViewPager() {

        ImageViewAdapter adapter = new ImageViewAdapter(this);
        final ImageView imageView = new ImageView(this);
        if (mAllImage != null) {
            GlideApp.with(this).load(mAllImage[0]).
                    skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.RESOURCE).
                    into(imageView);
            mViewList.add(imageView);
            final ImageView imageView2 = new ImageView(this);
            GlideApp.with(this).load(mAllImage[1]).
                    skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.RESOURCE).
                    into(imageView2);
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
    }
}
