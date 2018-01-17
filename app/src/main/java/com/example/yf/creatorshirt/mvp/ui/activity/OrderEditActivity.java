package com.example.yf.creatorshirt.mvp.ui.activity;

import android.os.Build;
import android.os.Bundle;
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
import com.example.yf.creatorshirt.common.manager.UserInfoManager;
import com.example.yf.creatorshirt.mvp.listener.ItemClickListener;
import com.example.yf.creatorshirt.mvp.model.ClothesPrice;
import com.example.yf.creatorshirt.mvp.model.VersionStyle;
import com.example.yf.creatorshirt.mvp.model.orders.ClothesSize;
import com.example.yf.creatorshirt.mvp.model.orders.OrderType;
import com.example.yf.creatorshirt.mvp.model.orders.SaveOrderInfo;
import com.example.yf.creatorshirt.mvp.model.orders.TextureEntity;
import com.example.yf.creatorshirt.mvp.presenter.OrderInfoPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.OrderInfoContract;
import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;
import com.example.yf.creatorshirt.mvp.ui.adapter.DetailOrderAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class OrderEditActivity extends BaseActivity<OrderInfoPresenter> implements ItemClickListener.OnItemObjectClickListener
        , OrderInfoContract.OrderInfoView {
    private ClothesSize mUpdateData;
    private ArrayList<ClothesSize> mOrderSizeInfo;//计算价格的尺寸list
    private ArrayList<ClothesSize> clothesSizeList;

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
    @BindView(R.id.clothes_order_color)
    TextView mTextClothesColor;
    @BindView(R.id.clothes_order_style)
    TextView mTextClothesType;
    @BindView(R.id.total_price)
    TextView mTotalPrice;
    private DetailOrderAdapter adapter;
    private VersionStyle mOrderClothesInfo;
    private double total;
    private double mDiscountTotal;

    @Override
    public void initData() {
        super.initData();
        mOrderSizeInfo = new ArrayList<>();
        if (getIntent().hasExtra("clothesInfo") && getIntent().getExtras() != null) {
            mOrderClothesInfo = getIntent().getExtras().getParcelable("clothesInfo");
        }
    }

    @Override
    protected int getView() {
        return R.layout.activity_new_order;
    }


    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }

    @OnClick({R.id.add_order, R.id.confirm_pay})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_order:
                Bundle bundle = new Bundle();
                bundle.putString("type", mOrderClothesInfo.getType());
                startCommonActivity(this, bundle, AddOrderActivity.class);
                break;
            case R.id.confirm_pay:
                if (mUpdateData != null) {
                    Bundle bundle1 = new Bundle();
                    SaveOrderInfo saveStyleEntity = new SaveOrderInfo();
                    saveStyleEntity.setBaseId(mOrderClothesInfo.getType());
                    saveStyleEntity.setDetailList(mOrderSizeInfo);
                    saveStyleEntity.setFinishAimage(mOrderClothesInfo.getFrontUrl());
                    saveStyleEntity.setFinishBimage(mOrderClothesInfo.getBackUrl());
                    saveStyleEntity.setColor(mOrderClothesInfo.getColorName());
                    saveStyleEntity.setOrderPrice((double) total);
                    saveStyleEntity.setPicture1(mOrderClothesInfo.getPicture1());
                    saveStyleEntity.setPicture2(mOrderClothesInfo.getPicture2());
                    saveStyleEntity.setMobile(UserInfoManager.getInstance().getLoginResponse().getUserInfo().getMobile());
                    saveStyleEntity.setPartner(UserInfoManager.getInstance().getLoginResponse().getUserInfo().getMobile());
                    bundle1.putParcelable("orderInfo", saveStyleEntity);
                    startCommonActivity(this, bundle1, ChoicePayActivity.class);
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
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        dividerItemDecoration.setDrawable(getDrawable(R.drawable.mysetting_divider));
        mDetailRecycler.addItemDecoration(dividerItemDecoration);
        GlideApp.with(this).load(mOrderClothesInfo.getFrontUrl()).error(R.mipmap.man_t_shirt).into(mClothesImage);
        mTextClothesColor.setText("颜色：" + mOrderClothesInfo.getColorName());
        mTextClothesType.setText("类型：" + mOrderClothesInfo.getClothesType());

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void upDateEvent(UpdateOrdersEvent event) {
        if (event.getFlag()) {
            mUpdateData = event.getClothesSize();
            if (mUpdateData == null) {
                return;
            }
            ClothesSize clothesSize = new ClothesSize();
            clothesSize.setSize(mUpdateData.getSize());
            clothesSize.setCount(mUpdateData.getCount());
            clothesSizeList = new ArrayList<>();
            clothesSizeList.add(clothesSize);
            mOrderSizeInfo.add(mUpdateData);
            mPresenter.setClothesData(mOrderClothesInfo, clothesSizeList);
            if (mUpdateData.getSex() != null) {//此时带表有折扣码
                mPresenter.setDiscount(mUpdateData.getSex());
                mPresenter.getDiscountPrices();
            }
            mPresenter.getComputePrices();

            updateView();

        }
    }

    private void updateView() {
        adapter.setData(mOrderSizeInfo);
        mDetailRecycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onItemClick(Object o) {
        if (mOrderSizeInfo.contains(o)) {
            mOrderSizeInfo.remove(o);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showSuccessData(OrderType s) {

    }

    @Override
    public void showShareSuccessData(OrderType s) {

    }

    @Override
    public void showTokenSuccess(String s) {

    }

    @Override
    public void showShareTokenSuccess(String s) {

    }

    @Override
    public void showSuccessTextUre(List<TextureEntity> textureEntity) {

    }

    @Override
    public void showPrices(ClothesPrice price) {
        total += price.getOrderPrice();
        mTotalPrice.setText(" ¥:" + total);
    }

    @Override
    public void showDiscountPrices(ClothesPrice price) {
        mDiscountTotal += price.getOrderPrice();

    }

}
