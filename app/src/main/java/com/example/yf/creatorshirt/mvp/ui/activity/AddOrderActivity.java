package com.example.yf.creatorshirt.mvp.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.common.UpdateOrdersEvent;
import com.example.yf.creatorshirt.mvp.listener.ItemClickListener;
import com.example.yf.creatorshirt.mvp.model.orders.ClothesSize;
import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;
import com.example.yf.creatorshirt.mvp.ui.adapter.ChoiceSizeAdapter;
import com.example.yf.creatorshirt.utils.Constants;
import com.example.yf.creatorshirt.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class AddOrderActivity extends BaseActivity implements ItemClickListener.OnItemComClickListener {

    @BindView(R.id.recyclerView_size)
    RecyclerView mSizeRecyclerView;
    @BindView(R.id.add_sure)
    Button mConfirm;
    @BindView(R.id.man)
    TextView mTvMan;
    @BindView(R.id.woman)
    TextView mTvWoman;
    @BindView(R.id.numbers_clothes)
    EditText mEditN;
    @BindView(R.id.child)
    TextView mTvChild;
    @BindView(R.id.minus)
    TextView mMinus;
    @BindView(R.id.add)
    TextView mAdd;
    private String gender;
    private String size;


    private ArrayList<ClothesSize> clothesList;
    private ClothesSize clothesSize;
    private ClothesSize mUpdate;
    private View mCurrentView;
    private int number = 0;

    @Override
    protected void inject() {

    }

    @Override
    public void initData() {
        super.initData();
        clothesList = new ArrayList<>();
        for (int i = 0; i < Constants.size.length; i++) {
            clothesSize = new ClothesSize();
            clothesSize.setSize(Constants.size[i]);
            clothesSize.setLetter(Constants.letter[i]);
            clothesList.add(clothesSize);
        }
        mUpdate = new ClothesSize();
    }

    @OnClick({R.id.add_sure, R.id.child, R.id.woman, R.id.man, R.id.minus, R.id.add, R.id.numbers_clothes})
    void Onclick(View view) {
        switch (view.getId()) {
            case R.id.add_sure:
                if (isCheck()) {
                    mUpdate.setLetter(gender);
                    mUpdate.setNumbers(number);
                    mUpdate.setSize(size);
                    EventBus.getDefault().post(new UpdateOrdersEvent(true, mUpdate));
                    finish();
                }
                break;
            case R.id.man:
                mTvWoman.setSelected(false);
                mTvMan.setSelected(true);
                mTvChild.setSelected(false);
                gender = "M";
                break;
            case R.id.woman:
                mTvWoman.setSelected(true);
                mTvMan.setSelected(false);
                mTvChild.setSelected(false);
                gender = "W";
                break;
            case R.id.child:
                mTvWoman.setSelected(false);
                mTvMan.setSelected(false);
                mTvChild.setSelected(true);
                gender = "C";
                break;
            case R.id.add:
                number++;
                mEditN.setText(String.valueOf(number));
                break;
            case R.id.minus:
                if (number == 0) {
                    mEditN.setText(String.valueOf(number));
                    return;
                }
                number--;
                mEditN.setText(String.valueOf(number));
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
    protected void initView() {
        mAppBarTitle.setText("新增订单");
        mSizeRecyclerView.setLayoutManager(new LinearLayoutManager(App.getInstance(), LinearLayoutManager.HORIZONTAL, false));
        ChoiceSizeAdapter choiceSizeAdapter = new ChoiceSizeAdapter(App.getInstance());
        choiceSizeAdapter.setData(clothesList);
        choiceSizeAdapter.setOnItemClickListener(this);
        mSizeRecyclerView.setAdapter(choiceSizeAdapter);
        mEditN.setText(String.valueOf(0));
    }

    @Override
    protected int getView() {
        return R.layout.activity_add_order;
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
}
