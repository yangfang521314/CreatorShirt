package com.example.yf.creatorshirt.mvp.ui.activity;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.listener.ItemClickListener;
import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;
import com.example.yf.creatorshirt.mvp.ui.adapter.AllOrderAdapter;

import butterknife.BindView;

public class AllOrdersActivity extends BaseActivity implements ItemClickListener.OnItemClickListener {
    @BindView(R.id.all_order_recyclerView)
    RecyclerView mAllOrderRY;

    private String title;

    @Override
    protected void inject() {

    }

    @Override
    public void initData() {
        super.initData();
        if (getIntent().getExtras() != null) {
            title = getIntent().getExtras().getString("title");
        } else {
            title = getString(R.string.my_order);
        }
    }

    @Override
    protected void initView() {
        mAppBarTitle.setText(title);
        AllOrderAdapter allOrderAdapter = new AllOrderAdapter(this);
        mAllOrderRY.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAllOrderRY.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        allOrderAdapter.setOnClickListener(this);
        mAllOrderRY.setAdapter(allOrderAdapter);
    }

    @Override
    protected int getView() {
        return R.layout.activity_all_order;
    }

    @Override
    public void onItemClick(View view, int position) {
        if(title.equals(getString(R.string.my_order))) {
            startCommonActivity(this, null, DetailOrderActivity.class);
        }else {
            startCommonActivity(this,null,MyDesignActivity.class);
        }
    }
}
