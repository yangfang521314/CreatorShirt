package com.example.yf.creatorshirt.mvp.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.app.GlideApp;
import com.example.yf.creatorshirt.common.UpdateStateEvent;
import com.example.yf.creatorshirt.common.manager.ClothesSizeManager;
import com.example.yf.creatorshirt.mvp.listener.ItemClickListener;
import com.example.yf.creatorshirt.mvp.model.MyOrderInfo;
import com.example.yf.creatorshirt.mvp.model.orders.ClothesSize;
import com.example.yf.creatorshirt.mvp.model.orders.OrderType;
import com.example.yf.creatorshirt.mvp.model.orders.SaveOrderInfo;
import com.example.yf.creatorshirt.mvp.presenter.CalculatePricesPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.CalculatePricesContract;
import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;
import com.example.yf.creatorshirt.mvp.ui.adapter.DetailOrderAdapter;
import com.example.yf.creatorshirt.mvp.ui.view.CircleView;
import com.example.yf.creatorshirt.mvp.ui.view.picker.LineConfig;
import com.example.yf.creatorshirt.mvp.ui.view.picker.NumberPicker;
import com.example.yf.creatorshirt.utils.DisplayUtil;
import com.example.yf.creatorshirt.utils.FileUtils;
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
    private static final String KID = "kid";
    private static final String MAN = "man";
    private static final String WOMAN = "woman";

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
    @BindView(R.id.detail_kid_recy)
    RecyclerView mDetailKidRecycler;
    @BindView(R.id.tv_order_kid)
    TextView mTvKidView;
    @BindView(R.id.tv_order_man)
    TextView mTvManView;
    @BindView(R.id.tv_order_woman)
    TextView mTvWomanView;

    private DetailOrderAdapter mManSizeAdapter;
    private DetailOrderAdapter mWomanSizeAdapter;
    private DetailOrderAdapter mKidSizeAdapter;
    private SaveOrderInfo mOrderClothesInfo;
    private int currentPosition;
    private ClothesSize mCurrentManClothesSize;
    private ClothesSize mCurrentWomanClothesSize;
    private ClothesSize mCurrentKidClothesSize;

    private NumberPicker picker;
    private MyOrderInfo myOrderInfo;

    private List<ClothesSize> clothesSizeList = new ArrayList<>();
    private ArrayMap<String, ClothesSize> mapManSize = new ArrayMap<>();//计算size的map集合
    private ArrayMap<String, ClothesSize> mapWomanSize = new ArrayMap<>();//计算size的map集合
    private ArrayMap<String, ClothesSize> mapKidSize = new ArrayMap<>();//计算size的map集合

    @Override
    public void initData() {
        super.initData();
        if (getIntent().hasExtra("clothesInfo") && getIntent().getExtras() != null) {
            mOrderClothesInfo = getIntent().getExtras().getParcelable("clothesInfo");
            assert mOrderClothesInfo != null;
            mOrderClothesInfo.setPicture1(mOrderClothesInfo.getPicture1() == null || mOrderClothesInfo.getPicture1().equals("") ? "0" : "1");
            mOrderClothesInfo.setPicture2(mOrderClothesInfo.getPicture2() == null || mOrderClothesInfo.getPicture2().equals("") ? "0" : "1");
            mOrderClothesInfo.setText(mOrderClothesInfo.getText() == null || mOrderClothesInfo.getText().equals("") ? "0" : "1");
            mOrderClothesInfo.setBackText(mOrderClothesInfo.getBackText() == null || mOrderClothesInfo.getBackText().equals("") ? "0" : "1");
        }
        if (getIntent().hasExtra("clothesInfoOrder") && getIntent().getExtras() != null) {
            myOrderInfo = getIntent().getExtras().getParcelable("clothesInfoOrder");
            mOrderClothesInfo = new SaveOrderInfo();
            mOrderClothesInfo.setDiscount("");
            mOrderClothesInfo.setColor(myOrderInfo.getColor());
            mOrderClothesInfo.setPartner(myOrderInfo.getPartner());
            mOrderClothesInfo.setMaskBName(myOrderInfo.getMaskBName());
            mOrderClothesInfo.setMaskAName(myOrderInfo.getMaskAName());
            mOrderClothesInfo.setBackText(myOrderInfo.getBackText() == null || myOrderInfo.getBackText().equals("") ? "0" : "1");
            mOrderClothesInfo.setText(myOrderInfo.getText() == null || myOrderInfo.getText().equals("") ? "0" : "1");
            mOrderClothesInfo.setPicture2(myOrderInfo.getPicture2() == null || myOrderInfo.getPicture2().equals("") ? "0" : "1");
            mOrderClothesInfo.setPicture1(myOrderInfo.getPicture1() == null || myOrderInfo.getPicture1().equals("") ? "0" : "1");
            mOrderClothesInfo.setBaseId(myOrderInfo.getBaseId());
            mOrderClothesInfo.setOrderId(String.valueOf(myOrderInfo.getOrderId()));
            mOrderClothesInfo.setPayorderid(myOrderInfo.getPayorderid());
            mOrderClothesInfo.setFinishAimage(myOrderInfo.getFinishAimage());
            mOrderClothesInfo.setFinishBimage(myOrderInfo.getFinishBimage());
        }
        if (ClothesSizeManager.getInstance().getClothesSizeList() == null) {
            mPresenter.getClothesSize();
        }

    }

    @Override
    protected int getView() {
        return R.layout.activity_new_order;
    }


    @Override
    protected void initView() {
        mAppBarTitle.setText(R.string.design);
        mDetailKidRecycler.setVisibility(View.GONE);
        mTvKidView.setVisibility(View.GONE);

        if (mOrderClothesInfo != null) {
            GlideApp.with(this).load(mOrderClothesInfo.getFinishAimage())
                    .skipMemoryCache(true)
                    .error(R.mipmap.mbaseball_white_a).into(mClothesImage);
            mCircleShape.setOutColor(Color.parseColor("#" + mOrderClothesInfo.getColor()));
        }
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        dividerItemDecoration.setDrawable(App.getInstance().getResources().getDrawable(R.drawable.mysetting_divider));

        mDetailWomanRecycler.setLayoutManager(new GridLinearLayoutManager(this, 3));
        mDetailManRecycler.setLayoutManager(new GridLinearLayoutManager(this, 3));
        mDetailKidRecycler.setLayoutManager(new GridLinearLayoutManager(this, 3));

        mDetailWomanRecycler.addItemDecoration(dividerItemDecoration);
        mDetailManRecycler.addItemDecoration(dividerItemDecoration);
        mDetailKidRecycler.addItemDecoration(dividerItemDecoration);

        if (ClothesSizeManager.getInstance().getClothesSizeList() != null && !ClothesSizeManager.getInstance().getClothesSizeList().isEmpty()) {
            initClothesSize(ClothesSizeManager.getInstance().getClothesSizeList());
        }

    }

    private void initChoiceNumber(final String flag) {
        int number = 0;
        if (flag.equals(MAN)) {
            if (mCurrentManClothesSize != null) {
                number = mCurrentManClothesSize.getCount();
            }
        } else if (flag.equals(WOMAN)) {
            if (mCurrentWomanClothesSize != null) {
                number = mCurrentWomanClothesSize.getCount();
            }
        } else if (flag.equals(KID)) {
            if (mCurrentKidClothesSize != null) {
                number = mCurrentKidClothesSize.getCount();
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
                if (flag.equals(MAN)) {
                    updateNumberClothes(String.valueOf(item.intValue()), mManSizeAdapter, mCurrentManClothesSize);
                    if (mapManSize.containsKey(mCurrentManClothesSize.getSize())) {
                        mapManSize.get(mCurrentManClothesSize.getSize()).setCount(item.intValue());
                    } else {
                        mCurrentManClothesSize.setCount(item.intValue());
                        mCurrentManClothesSize.setSex(0);
                        mapManSize.put(mCurrentManClothesSize.getSize(), mCurrentManClothesSize);
                    }
                } else if (flag.equals(WOMAN)) {
                    updateNumberClothes(String.valueOf(item.intValue()), mWomanSizeAdapter, mCurrentWomanClothesSize);
                    if (mapWomanSize.containsKey(mCurrentWomanClothesSize.getSize())) {
                        mapWomanSize.get(mCurrentWomanClothesSize.getSize()).setCount(item.intValue());
                    } else {
                        mCurrentWomanClothesSize.setCount(item.intValue());
                        mCurrentWomanClothesSize.setSex(1);
                        mapWomanSize.put(mCurrentWomanClothesSize.getSize(), mCurrentWomanClothesSize);
                    }

                } else if (flag.equals(KID)) {
                    updateNumberClothes(String.valueOf(item.intValue()), mKidSizeAdapter, mCurrentKidClothesSize);
                    if (mapKidSize.containsKey(mCurrentKidClothesSize.getSize())) {
                        mapKidSize.get(mCurrentKidClothesSize.getSize()).setCount(item.intValue());
                    } else {
                        mCurrentKidClothesSize.setCount(item.intValue());
                        mCurrentKidClothesSize.setSex(2);
                        mapKidSize.put(mCurrentKidClothesSize.getSize(), mCurrentKidClothesSize);
                    }
                }
                if (clothesSizeList != null && clothesSizeList.size() != 0) {
                    clothesSizeList.clear();
                    for (String key : mapManSize.keySet()) {
                        clothesSizeList.add(mapManSize.get(key));
                    }
                    for (String key : mapWomanSize.keySet()) {
                        clothesSizeList.add(mapWomanSize.get(key));
                    }
                    for (String key : mapKidSize.keySet()) {
                        clothesSizeList.add(mapKidSize.get(key));
                    }
                } else {
                    for (String key : mapManSize.keySet()) {
                        clothesSizeList.add(mapManSize.get(key));
                    }
                    for (String key : mapWomanSize.keySet()) {
                        clothesSizeList.add(mapWomanSize.get(key));
                    }
                    for (String key : mapKidSize.keySet()) {
                        clothesSizeList.add(mapKidSize.get(key));
                    }

                }
                if (clothesSizeList != null && clothesSizeList.size() != 0) {
                    calculate(clothesSizeList);
                }
            }
        });

        picker.show();
    }


    private void calculate(List<ClothesSize> clothesSizeList) {
        String dis;
        if (PhoneUtils.notEmptyText(mEditDiscount)) {
            dis = mEditDiscount.getText().toString();
        } else {
            dis = null;
        }
        mPresenter.setSaveEntity(mOrderClothesInfo, clothesSizeList, dis);
    }


    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }

    @OnClick({R.id.confirm_pay,R.id.back})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_pay:
                if (mOrderClothesInfo == null) {
                    return;
                }
                if (clothesSizeList != null && clothesSizeList.size() != 0) {
                    mPresenter.updateOrders();
                } else {
                    ToastUtil.showToast(this, "选择数量", 0);
                }
                break;
            case R.id.back:
                finish();
                break;
        }

    }

    @OnTextChanged(R.id.discount)
    void afterTextChanged(Editable s) {
        if (PhoneUtils.notEmptyText(mEditDiscount)) {
            if (PhoneUtils.getTextString(mEditDiscount).length() <= 10 && PhoneUtils.getTextString(mEditDiscount).length() > 3) {
                if (clothesSizeList != null && clothesSizeList.size() != 0) {
                    mPresenter.setSaveEntity(mOrderClothesInfo, clothesSizeList, PhoneUtils.getTextString(mEditDiscount));
                } else {
                    ToastUtil.showToast(OrderEditActivity.this, "选择件数", 0);
                }
            }
        }

    }

    private void updateNumberClothes(String number, DetailOrderAdapter detailOrderAdapter, ClothesSize mCurrentClothesSize) {
        ClothesSize clothesSize = new ClothesSize();
        clothesSize.setSize(mCurrentClothesSize.getSize());
        clothesSize.setCount(Integer.valueOf(number));
        clothesSize.setValue(mCurrentClothesSize.getValue());
        detailOrderAdapter.remove(currentPosition);
        detailOrderAdapter.add(currentPosition, clothesSize);
        detailOrderAdapter.notifyDataSetChanged();
    }

    @Override
    public void showPrices(double discountPrice, double orderPrice) {
        mPrices.setText("原价 ¥：" + orderPrice);
        mTotalPrice.setText("¥：" + discountPrice);
    }


    /**
     * 集合size的集合
     *
     * @param list
     */
    @Override
    public void showSizeList(Map<String, List<ClothesSize>> list) {
        initClothesSize(list);
    }

    @Override
    public void showPay(OrderType orderType) {
        if (orderType.getDispContext() != null) {
            ToastUtil.showToast(this, orderType.getDispContext(), 0);
        }
        if (mPresenter.getSaveOrderInfo() != null) {
            FileUtils.deleteDir(this);//删除cache图片资源
            Bundle bundle1 = new Bundle();
            SaveOrderInfo saveStyleEntity = mPresenter.getSaveOrderInfo();
            bundle1.putParcelable("orderInfo", saveStyleEntity);
            startCommonActivity(this, bundle1, ChoicePayActivity.class);
        }
    }

    private void initClothesSize(Map<String, List<ClothesSize>> listArrayMap) {
        mManSizeAdapter = new DetailOrderAdapter(this);
        mWomanSizeAdapter = new DetailOrderAdapter(this);
        if (mOrderClothesInfo.getBaseId().equals("hoodie") || mOrderClothesInfo.getBaseId().equals("sweater")
                || mOrderClothesInfo.getBaseId().equals("baseball")) {
            mManSizeAdapter.setData(listArrayMap.get("commonSizeMan"));
            mWomanSizeAdapter.setData(listArrayMap.get("commonSizeWomen"));

            mDetailManRecycler.setAdapter(mManSizeAdapter);
            mDetailWomanRecycler.setAdapter(mWomanSizeAdapter);
            commonChoiceKid(listArrayMap);
        } else if (mOrderClothesInfo.getBaseId().equals("kidl") || mOrderClothesInfo.getBaseId().equals("kids")) {
            commonChoiceKid(listArrayMap);
            mTvWomanView.setVisibility(View.GONE);
            mTvManView.setVisibility(View.GONE);
        } else {
            mManSizeAdapter.setData(listArrayMap.get("commonSizeMan"));
            mWomanSizeAdapter.setData(listArrayMap.get("commonSizeWomen"));
            mDetailManRecycler.setAdapter(mManSizeAdapter);
            mDetailWomanRecycler.setAdapter(mWomanSizeAdapter);
            mTvWomanView.setVisibility(View.VISIBLE);
            mTvManView.setVisibility(View.VISIBLE);
        }
        mManSizeAdapter.setOnItemClickListener(this);
        mWomanSizeAdapter.setOnItemClickListener(new ItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object object) {
                currentPosition = position;
                mCurrentWomanClothesSize = (ClothesSize) object;
                initChoiceNumber("woman");
            }
        });

    }

    private void commonChoiceKid(Map<String, List<ClothesSize>> listArrayMap) {
        mDetailKidRecycler.setVisibility(View.VISIBLE);
        mTvKidView.setVisibility(View.VISIBLE);
        mKidSizeAdapter = new DetailOrderAdapter(this);
        if (ClothesSizeManager.getInstance().getClothesSizeList() != null) {
            mKidSizeAdapter.setData(listArrayMap.get("commonKid"));
        }
        mDetailKidRecycler.setAdapter(mKidSizeAdapter);
        mKidSizeAdapter.setOnItemClickListener(new ItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object object) {
                currentPosition = position;
                mCurrentKidClothesSize = (ClothesSize) object;
                initChoiceNumber("kid");
            }
        });
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
        initChoiceNumber("man");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EventBus.getDefault().post(new UpdateStateEvent(true));
    }
}
