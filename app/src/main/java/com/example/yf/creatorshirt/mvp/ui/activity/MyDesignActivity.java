package com.example.yf.creatorshirt.mvp.ui.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;

import butterknife.BindView;

public class MyDesignActivity extends BaseActivity {

    @BindView(R.id.order_clothes_number)
    RecyclerView mRecyclerNumber;
    @BindView(R.id.order_receiver_address)
    TextView mTextAddress;
    @BindView(R.id.order_mobile)
    TextView mOrderMobile;
    @BindView(R.id.order_receiver_name)
    TextView mOrderName;
    @BindView(R.id.view_pager_clothes)
    ViewPager mViewPager;
    @BindView(R.id.ll_indicator)
    LinearLayout mLinearIndicator;//指示器

    @Override
    protected void inject() {

    }

    @Override
    protected void initView() {
        mAppBarTitle.setText("我的定制");
    }

    @Override
    protected int getView() {
        return R.layout.activity_my_design;
    }
}
