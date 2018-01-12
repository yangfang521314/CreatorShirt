package com.example.yf.creatorshirt.mvp.ui.activity;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.GlideApp;
import com.example.yf.creatorshirt.common.UpdateOrdersEvent;
import com.example.yf.creatorshirt.mvp.listener.ItemClickListener;
import com.example.yf.creatorshirt.mvp.model.orders.ClothesSize;
import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;
import com.example.yf.creatorshirt.mvp.ui.adapter.DetailOrderAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class NewOrderActivity extends BaseActivity implements ItemClickListener.OnItemObjectClickListener {
    private ClothesSize mUpdateData;
    private List<ClothesSize> mOrderInfo;

    @BindView(R.id.clothes_picture)
    ImageView mClothesImage;
    @BindView(R.id.add_order)
    TextView mOrderAdd;
    @BindView(R.id.detail_order_recy)
    RecyclerView mDetailRecycler;
    @BindView(R.id.order_clothes_prices)
    TextView mPrices;
    @BindView(R.id.confirm_pay)
    TextView mConfirmPay;
    private DetailOrderAdapter adapter;
    private double prices = 49.99;//价格
    private String imageUrl;

    @Override
    public void initData() {
        super.initData();
        mOrderInfo = new ArrayList<>();
        if (getIntent().hasExtra("clothes")) {
            imageUrl = getIntent().getStringExtra("clothes");
        }
    }

    @Override
    protected void inject() {

    }

    @OnClick({R.id.add_order,R.id.confirm_pay})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_order:
                startCommonActivity(this,null, AddOrderActivity.class);
                break;
            case R.id.confirm_pay:
                if(mUpdateData != null) {
                    startCommonActivity(this, null, ChoicePayActivity.class);
                }
                break;
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        mAppBarTitle.setText(R.string.design);
        mDetailRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new DetailOrderAdapter(this);
        adapter.setOnItemClickListener(this);
        mDetailRecycler.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        dividerItemDecoration.setDrawable(getDrawable(R.drawable.mysetting_divider));
        mDetailRecycler.addItemDecoration(dividerItemDecoration);
        GlideApp.with(this).load(imageUrl).error(R.mipmap.man_t_shirt).into(mClothesImage);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void upDateEvent(UpdateOrdersEvent event) {
        if (event.getFlag()) {
            mUpdateData = event.getClothesSize();
            mUpdateData.setPrices(prices);
            mOrderInfo.add(mUpdateData);
            updateView();
        }
    }

    private void updateView() {
        adapter.setData(mOrderInfo);
        adapter.notifyDataSetChanged();

    }


    @Override
    protected int getView() {
        return R.layout.activity_new_order;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onItemClick(Object o) {
        if (mOrderInfo.contains(o)) {
            mOrderInfo.remove(o);
            adapter.notifyDataSetChanged();
        }
    }
}
