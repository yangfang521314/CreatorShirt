package com.example.yf.creatorshirt.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.listener.ItemClickListener;
import com.example.yf.creatorshirt.mvp.model.AddressBean;
import com.example.yf.creatorshirt.mvp.presenter.AddressPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.AddressContract;
import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;
import com.example.yf.creatorshirt.mvp.ui.adapter.AddressAdapter;
import com.example.yf.creatorshirt.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 地址显示页面
 */
public class AddressShowActivity extends BaseActivity<AddressPresenter> implements AddressContract.AddressView
        , ItemClickListener.OnItemComClickListener {
    @BindView(R.id.address_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.add_address)
    TextView mEditAddress;
    private AddressAdapter addressAdapter;
    private ImageView mCurrentView;
    private ImageView mBeforeView;

    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void initData() {
        super.initData();

    }

    @OnClick({R.id.add_address,R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_address:
                Intent intent = new Intent();
                intent.setClass(this, AddressEditActivity.class);
                startActivity(intent);
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    protected void initView() {
        mAppBarTitle.setText(R.string.receiver_address);
        mAppBarBack.setVisibility(View.VISIBLE);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        addressAdapter = new AddressAdapter(this);
        addressAdapter.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getAddressData();
    }

    @Override
    protected int getView() {
        return R.layout.activity_address;
    }

    @Override
    public void showSuccess(List<AddressBean> list) {
        AddressBean mAddressBean = null;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getIsDefault() == 1) {
                mAddressBean = list.get(i);
                list.remove(i);
            }
        }
        list.add(0, mAddressBean);
        addressAdapter.setData(list);
        mRecyclerView.setAdapter(addressAdapter);
    }

    @Override
    public void SuccessSaveAddress(String message) {
        ToastUtil.showToast(this, message, 0);
    }

    @Override
    public void successDefaultAddress(String flag) {
        ToastUtil.showToast(this, flag, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onItemClick(Object o, View view) {

        switch (view.getId()) {
            case R.id.edit_address_btn:
                Bundle bundle = new Bundle();
                bundle.putParcelable("address", (Parcelable) o);
                startCommonActivity(this, bundle, AddressEditActivity.class);
                break;
            case R.id.choice_default_address:
                if (mBeforeView != null) {
                    mBeforeView.setImageResource(R.mipmap.choice_not_address);
                }
                AddressBean addressBean = (AddressBean) o;
                mPresenter.setDefaultAddress(addressBean.getId());
                mCurrentView = (ImageView) view;
                mCurrentView.setImageResource(R.mipmap.choice_address);
                mBeforeView = mCurrentView;
                break;
        }
    }
}
