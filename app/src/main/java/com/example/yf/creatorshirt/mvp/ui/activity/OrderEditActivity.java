package com.example.yf.creatorshirt.mvp.ui.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.GlideApp;
import com.example.yf.creatorshirt.common.manager.ClothesSizeManager;
import com.example.yf.creatorshirt.common.manager.UserInfoManager;
import com.example.yf.creatorshirt.mvp.listener.ItemClickListener;
import com.example.yf.creatorshirt.mvp.model.ClothesPrice;
import com.example.yf.creatorshirt.mvp.model.VersionStyle;
import com.example.yf.creatorshirt.mvp.model.orders.ClothesSize;
import com.example.yf.creatorshirt.mvp.model.orders.SaveOrderInfo;
import com.example.yf.creatorshirt.mvp.presenter.CalculatePricesPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.CalculatePricesContract;
import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;
import com.example.yf.creatorshirt.mvp.ui.adapter.DetailOrderAdapter;
import com.example.yf.creatorshirt.mvp.ui.view.CircleView;
import com.example.yf.creatorshirt.mvp.ui.view.picker.LineConfig;
import com.example.yf.creatorshirt.mvp.ui.view.picker.NumberPicker;
import com.example.yf.creatorshirt.utils.DisplayUtil;
import com.example.yf.creatorshirt.utils.GridLinearLayoutManager;
import com.example.yf.creatorshirt.utils.PhoneUtils;
import com.example.yf.creatorshirt.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class OrderEditActivity extends BaseActivity<CalculatePricesPresenter> implements ItemClickListener.OnItemClickListener
        , CalculatePricesContract.CalculatePricesView {
    private ArrayList<ClothesSize> mOrderSizeInfo;//计算价格的尺寸list
    private ArrayList<ClothesSize> clothesSizeList;

    @BindView(R.id.clothes_picture)
    ImageView mClothesImage;
    @BindView(R.id.detail_man_recy)
    RecyclerView mDetailManRecycler;
    @BindView(R.id.detail_woman_recy)
    RecyclerView mDetailWomanRecycler;
    @BindView(R.id.order_clothes_prices)
    TextView mOnePrices;
    @BindView(R.id.confirm_pay)
    TextView mConfirmPay;
    @BindView(R.id.clothes_order_color)
    TextView mTextClothesColor;
    @BindView(R.id.clothes_detail_color)
    CircleView mCircleShape;
    @BindView(R.id.clothes_order_style)
    TextView mTextClothesType;
    @BindView(R.id.total_price)
    TextView mTotalPrice;
    @BindView(R.id.discount)
    EditText mEditDiscount;
    @BindView(R.id.prices)
    TextView mPrices;
    @BindView(R.id.rl_order_edit)
    RelativeLayout mRelativeClothes;

    private DetailOrderAdapter mManSizeAdapter;
    private DetailOrderAdapter mWomanSizeAdapter;
    private VersionStyle mOrderClothesInfo;
    private double total;
    private Map<String, List<ClothesSize>> listArrayMap;
    private int currentPosition;
    private ClothesSize mCurrentManClothesSize;
    private ClothesSize mCurrentWomanClothesSize;
    private NumberPicker picker;

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


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initView() {
        mAppBarTitle.setText(R.string.design);
        if (mOrderClothesInfo != null) {
            GlideApp.with(this).load(mOrderClothesInfo.getFrontUrl()).error(R.mipmap.mbaseball_white_a).into(mClothesImage);
            mCircleShape.setOutColor(Color.parseColor("#" + mOrderClothesInfo.getColor()));
        }
        mDetailManRecycler.setLayoutManager(new GridLinearLayoutManager(this, 3));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        dividerItemDecoration.setDrawable(mContext.getDrawable(R.drawable.mysetting_divider));
        mDetailWomanRecycler.setLayoutManager(new GridLinearLayoutManager(this, 3));

        mDetailWomanRecycler.addItemDecoration(dividerItemDecoration);
        mDetailManRecycler.addItemDecoration(dividerItemDecoration);
        mManSizeAdapter = new DetailOrderAdapter(this);
        mWomanSizeAdapter = new DetailOrderAdapter(this);
        mManSizeAdapter.setOnItemClickListener(this);
        if (ClothesSizeManager.getInstance().getClothesSizeList() == null) {
            mPresenter.getClothesSize();
        } else {
            listArrayMap = ClothesSizeManager.getInstance().getClothesSizeList();
            mManSizeAdapter.setData(listArrayMap.get("commonSizeMan"));
            mWomanSizeAdapter.setData(listArrayMap.get("commonSizeWomen"));
        }
        mDetailManRecycler.setAdapter(mManSizeAdapter);
        mDetailWomanRecycler.setAdapter(mWomanSizeAdapter);
        mWomanSizeAdapter.setOnItemClickListener(new ItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position, Object object) {
                currentPosition = position;
                mCurrentWomanClothesSize = (ClothesSize) object;
                initChoiceNumber(false);
            }
        });

    }

    private void initChoiceNumber(final boolean flag) {
        int number = 0;
        if (flag) {
            if (mCurrentManClothesSize != null) {
                number = mCurrentManClothesSize.getCount();
            }
        } else {
            if (mCurrentWomanClothesSize != null) {
                number = mCurrentWomanClothesSize.getCount();
            }
        }
        picker = new NumberPicker(OrderEditActivity.this);
        picker.setWidth(picker.getScreenWidthPixels());
        picker.setHeight(picker.getScreenHeightPixels() * 2 / 5);
        picker.setItemWidth(DisplayUtil.getScreenW(this));
        picker.setCanLoop(false);
        picker.setLineVisible(false);
        picker.setWheelModeEnable(true);
        picker.setCancelTextColor(mContext.getResources().getColor(R.color.unitednationsblue));
        picker.setCancelTextSize(18);
        picker.setSubmitTextColor(mContext.getResources().getColor(R.color.unitednationsblue));
        picker.setSubmitTextSize(18);
        picker.setOffset(2);//偏移量
        picker.setTitleText("选择数量");
        picker.setTitleTextSize(18);
        picker.setSelectedTextColor(0xFFEE0000);
        picker.setUnSelectedTextColor(0xFF999999);
        picker.setTitleTextColor(mContext.getResources().getColor(R.color.black_1));
        picker.setRange(0, 200, 1);//数字范围
        picker.setSelectedItem(number);
        LineConfig config = new LineConfig();
        config.setColor(mContext.getResources().getColor(R.color.gainsboro));//线颜色
        config.setWidth(DisplayUtil.getScreenW(mContext));
        config.setThick(DisplayUtil.Dp2Px(mContext, 1));//线粗
        picker.setLineConfig(config);
        picker.setOnNumberPickListener(new NumberPicker.OnNumberPickListener() {
            @Override
            public void onNumberPicked(int index, Number item) {
                if (flag) {
                    updateNumberClothes(String.valueOf(item.intValue()));
                } else {
                    updateNumberWomanClothes(String.valueOf(item.intValue()));
                }
            }
        });
        picker.show();
    }


    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }

    @OnClick({R.id.confirm_pay})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_pay:
                if (mOrderClothesInfo != null) {
                    Bundle bundle1 = new Bundle();
                    SaveOrderInfo saveStyleEntity = new SaveOrderInfo();
                    saveStyleEntity.setBaseId(mOrderClothesInfo.getType());
                    saveStyleEntity.setDetailList(mOrderSizeInfo);
                    saveStyleEntity.setFinishAimage(mOrderClothesInfo.getFrontUrl());
                    saveStyleEntity.setFinishBimage(mOrderClothesInfo.getBackUrl());
                    saveStyleEntity.setColor(mOrderClothesInfo.getColorName());
//                    saveStyleEntity.setOrderPrice((double) total);
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

    @OnTextChanged(R.id.discount)
    void onTextChanged(CharSequence s, int start, int before, int count) {
        if (check(PhoneUtils.getTextString(mEditDiscount))) {
            mPresenter.setDiscount(PhoneUtils.getTextString(mEditDiscount));
            mPresenter.computerOrderPrice();
        }
    }


    private boolean check(String discount) {
        if (discount.length() != 6) {
            ToastUtil.showToast(this, "输入的折扣码出错", 0);
            return false;
        }
        if (total == 0) {
            ToastUtil.showToast(this, "订单数量出错", 0);
            return false;
        }
        return true;
    }


    /**
     * woman
     *
     * @param object
     */
    private void updateNumberWomanClothes(String object) {
        ClothesSize clothesSize = new ClothesSize();
        clothesSize.setLetter(mCurrentWomanClothesSize.getLetter());
        clothesSize.setCount(Integer.valueOf(object));
        clothesSize.setSize(mCurrentWomanClothesSize.getSize());
        mWomanSizeAdapter.remove(currentPosition);
        mWomanSizeAdapter.add(currentPosition, clothesSize);

    }

    /**
     * man
     *
     * @param number
     */
    private void updateNumberClothes(String number) {
        ClothesSize clothesSize = new ClothesSize();
        clothesSize.setLetter(mCurrentManClothesSize.getLetter());
        clothesSize.setCount(Integer.valueOf(number));
        clothesSize.setSize(mCurrentManClothesSize.getSize());
        mManSizeAdapter.remove(currentPosition);
        mManSizeAdapter.add(currentPosition, clothesSize);

    }


    @Override
    public void showPrices(ClothesPrice price) {
        total += price.getOrderPrice();
        if (PhoneUtils.notEmptyText(mEditDiscount)) {
            mTotalPrice.setText("合计 ¥：" + price.getDiscountPrice());
            mPrices.setText("原价 ¥：" + price.getOrderPrice());
        } else {
            mTotalPrice.setText("合计 ¥：" + price.getOrderPrice());
        }
    }

    /**
     * 集合size的集合
     *
     * @param list
     */
    @Override
    public void showSizeList(Map<String, List<ClothesSize>> list) {
        ClothesSizeManager.getInstance().saveCache(list);
        listArrayMap = list;
        mManSizeAdapter.setData(listArrayMap.get("commonSizeMan"));
        mWomanSizeAdapter.setData(listArrayMap.get("commonSizeWomen"));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onItemClick(View view, int position, Object object) {
        currentPosition = position;
        mCurrentManClothesSize = (ClothesSize) object;
        initChoiceNumber(true);
    }
}
