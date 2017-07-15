package com.example.yf.creatorshirt.mvp.ui.activity;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.listener.ItemClickListener;
import com.example.yf.creatorshirt.mvp.ui.adapter.AllOrderAdapter;

import butterknife.BindView;

public class AllOrderActivity extends BaseActivity implements ItemClickListener.OnItemClickListener {
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
        allOrderAdapter.setOnClickListener(this);
        mAllOrderRY.setAdapter(allOrderAdapter);
    }

    @Override
    protected int getView() {
        return R.layout.activity_all_order;
    }

    @Override
    public void onItemClick(View view, int position) {
        startCommonActivity(this,DetailOrderActivity.class);
    }
}
