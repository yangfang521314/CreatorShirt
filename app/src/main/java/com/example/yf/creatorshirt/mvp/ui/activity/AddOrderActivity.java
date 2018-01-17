package com.example.yf.creatorshirt.mvp.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.common.UpdateOrdersEvent;
import com.example.yf.creatorshirt.common.manager.ClothesSizeManager;
import com.example.yf.creatorshirt.mvp.listener.ItemClickListener;
import com.example.yf.creatorshirt.mvp.model.orders.ClothesSize;
import com.example.yf.creatorshirt.mvp.presenter.ClothesSizePresenter;
import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;
import com.example.yf.creatorshirt.mvp.ui.adapter.ChoiceSizeAdapter;
import com.example.yf.creatorshirt.utils.PhoneUtils;
import com.example.yf.creatorshirt.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class AddOrderActivity extends BaseActivity implements ItemClickListener.OnItemComClickListener, ClothesSizePresenter.OrderSizeView {

    @BindView(R.id.recyclerView_size)
    RecyclerView mSizeRecyclerView;
    @BindView(R.id.add_sure)
    Button mConfirm;
    @BindView(R.id.man)
    TextView mTvMan;
    @BindView(R.id.woman)
    TextView mTvWoman;
    @BindView(R.id.numbers_clothes)
    TextView mClothesNumbers;
    @BindView(R.id.child)
    TextView mTvChild;
    @BindView(R.id.minus)
    ImageView mMinus;
    @BindView(R.id.add)
    ImageView mAdd;
    @BindView(R.id.discount)
    EditText mEditDiscount;

    private String gender;
    private String size;

    private View mCurrentView;
    private int number = 0;
    private ClothesSizePresenter sizePresenter;
    private Map<String, List<ClothesSize>> listArrayMap;
    private String type;
    private ChoiceSizeAdapter choiceSizeAdapter;
    private ClothesSize mUpdate;

    @Override
    protected void inject() {

    }

    @Override
    public void initData() {
        super.initData();
        if (getIntent() != null) {
            type = getIntent().getExtras().getString("type");
        }
    }

    @Override
    protected int getView() {
        return R.layout.activity_add_order;
    }

    @Override
    protected void initView() {
        sizePresenter = new ClothesSizePresenter();
        sizePresenter.attachView(this);
        mAppBarTitle.setText("新增订单");
        mClothesNumbers.setText(String.valueOf(0));
        mSizeRecyclerView.setLayoutManager(new LinearLayoutManager(App.getInstance(), LinearLayoutManager.HORIZONTAL, false));
        choiceSizeAdapter = new ChoiceSizeAdapter(App.getInstance());
        if (type.equals("kids") || type.equals("kidl")) {
            mTvMan.setVisibility(View.GONE);
            mTvWoman.setVisibility(View.GONE);
        } else {
            mTvWoman.setVisibility(View.VISIBLE);
            mTvMan.setVisibility(View.VISIBLE);
        }
        if (ClothesSizeManager.getInstance().getClothesSizeList() == null) {
            sizePresenter.getClothesSize();
        } else {
            listArrayMap = ClothesSizeManager.getInstance().getClothesSizeList();
            //默认尺寸数据
            if (listArrayMap != null) {
                choiceSizeAdapter.setData(listArrayMap.get("commonSizeMan"));
                mSizeRecyclerView.setAdapter(choiceSizeAdapter);
            }
        }
        //默认
        choiceSizeAdapter.setOnItemClickListener(this);
    }

    @OnClick({R.id.add_sure, R.id.child, R.id.woman, R.id.man, R.id.minus, R.id.add, R.id.numbers_clothes})
    void Onclick(View view) {
        switch (view.getId()) {
            case R.id.add_sure:
                if (isCheck()) {
                    mUpdate = new ClothesSize();
                    mUpdate.setLetter(gender);
                    mUpdate.setCount(number);
                    mUpdate.setSize(size);
                    if (PhoneUtils.notEmptyText(mEditDiscount)) {
                        mUpdate.setSex(mEditDiscount.getText().toString());
                    }
                    EventBus.getDefault().post(new UpdateOrdersEvent(true, mUpdate));
                    finish();
                }
                break;
            case R.id.man:
                mTvWoman.setSelected(false);
                mTvMan.setSelected(true);
                mTvChild.setSelected(false);
                gender = "男";
                choiceSizeAdapter.setData(listArrayMap.get("commonSizeMan"));
                choiceSizeAdapter.notifyDataSetChanged();
                break;
            case R.id.woman:
                mTvWoman.setSelected(true);
                mTvMan.setSelected(false);
                mTvChild.setSelected(false);
                gender = "女";
                choiceSizeAdapter.setData(listArrayMap.get("commonSizeWomen"));
                choiceSizeAdapter.notifyDataSetChanged();
                break;
            case R.id.child:
                mTvWoman.setSelected(false);
                mTvMan.setSelected(false);
                mTvChild.setSelected(true);
                gender = "儿童";
                choiceSizeAdapter.setData(listArrayMap.get("commonKid"));
                choiceSizeAdapter.notifyDataSetChanged();
                break;
            case R.id.add:
                number++;
                mClothesNumbers.setText(String.valueOf(number));
                break;
            case R.id.minus:
                if (number == 0) {
                    mClothesNumbers.setText(String.valueOf(number));
                    return;
                }
                number--;
                mClothesNumbers.setText(String.valueOf(number));
                break;
        }

    }

    private boolean isCheck() {
        if (number <= 0) {
            ToastUtil.showToast(this, "衣服数量不少于1", 0);
            return false;
        }
        if (gender == null) {
            ToastUtil.showToast(this, "没有选择性别", 0);
            return false;
        }
        if (size == null) {
            ToastUtil.showToast(this, "没有选择大小", 0);
            return false;
        }
        return true;
    }

    @Override
    public void onItemClick(Object o, View view) {
        if (mCurrentView != null) {
            mCurrentView.setSelected(false);
        }
        size = (String) o;
        mCurrentView = view;
        view.setSelected(true);
    }

    @Override
    public void showSizeList(Map<String, List<ClothesSize>> list) {
        ClothesSizeManager.getInstance().saveCache(list);
        listArrayMap = list;
        choiceSizeAdapter.setData(listArrayMap.get("commonSizeMan"));
        mSizeRecyclerView.setAdapter(choiceSizeAdapter);
    }


}
