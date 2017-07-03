package com.example.yf.creatorshirt.mvp.ui.activity;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.ui.adapter.AllOrderAdapter;

import butterknife.BindView;

public class AllOrderActivity extends BaseActivity {
    @BindView(R.id.all_order_recyclerView)
    RecyclerView mAllOrderRY;

    @Override
    protected void inject() {

    }

    @Override
    protected void initView() {
        mAppBarTitle.setText("我的订单");
        AllOrderAdapter allOrderAdapter = new AllOrderAdapter(this);
        mAllOrderRY.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAllOrderRY.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mAllOrderRY.setAdapter(allOrderAdapter);
    }

    @Override
    protected int getView() {
        return R.layout.activity_all_order;
    }
}
