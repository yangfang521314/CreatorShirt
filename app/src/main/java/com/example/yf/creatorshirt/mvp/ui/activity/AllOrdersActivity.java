package com.example.yf.creatorshirt.mvp.ui.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.common.ChangeSelectEvent;
import com.example.yf.creatorshirt.mvp.listener.ItemClickListener;
import com.example.yf.creatorshirt.mvp.model.orders.SaveOrderInfo;
import com.example.yf.creatorshirt.mvp.presenter.AllOrderPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.AllOrderContract;
import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;
import com.example.yf.creatorshirt.mvp.ui.adapter.AllOrderAdapter;
import com.example.yf.creatorshirt.utils.systembar.SystemUtilsBar;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AllOrdersActivity extends BaseActivity<AllOrderPresenter> implements ItemClickListener.OnItemClickListener,
        AllOrderContract.AllOrderView {
    @BindView(R.id.all_order_recyclerView)
    RecyclerView mAllOrderRY;
    @BindView(R.id.no_data)
    TextView mTextNoData;

    private String title;

    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void initData() {
        super.initData();
        if (getIntent().getExtras() != null) {
            title = getIntent().getExtras().getString("title");
        }
        if (App.isLogin) {
            mPresenter.getMyAllOrder();
        }
    }


    @Override
    protected int getView() {
        return R.layout.activity_all_order;
    }

    @Override
    protected void initView() {
        SystemUtilsBar.with(this);
        mAppBarTitle.setText("我的订单");
        mAppBarBack.setVisibility(View.VISIBLE);

    }

    @OnClick({R.id.back})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                if (title != null) {
                    startCommonActivity(AllOrdersActivity.this, null, MainActivity.class);
                    EventBus.getDefault().post(new ChangeSelectEvent(true));
                }
                finish();
                break;
        }
    }


    @Override
    public void onItemClick(View view, int position, Object object) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("orderInfo", (Parcelable) object);
        startCommonActivity(this, bundle, DetailOrderActivity.class);
    }

    @Override
    public void showSuccess(List<SaveOrderInfo> myOrderInfo) {
        mAllOrderRY.setVisibility(View.VISIBLE);
        mTextNoData.setVisibility(View.GONE);
        AllOrderAdapter allOrderAdapter = new AllOrderAdapter(this);
        allOrderAdapter.setOnItemClickListener(this);
        allOrderAdapter.setData(myOrderInfo);
        mAllOrderRY.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAllOrderRY.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAllOrderRY.setAdapter(allOrderAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (title != null) {
            startCommonActivity(this, null, MainActivity.class);
            EventBus.getDefault().post(new ChangeSelectEvent(true));
        }
        finish();
    }

    @Override
    public void showErrorMsg(String msg) {
        super.showErrorMsg(msg);
        mTextNoData.setVisibility(View.VISIBLE);
        mAllOrderRY.setVisibility(View.GONE);
    }
}
