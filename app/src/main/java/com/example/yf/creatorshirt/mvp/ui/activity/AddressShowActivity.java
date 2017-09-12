package com.example.yf.creatorshirt.mvp.ui.activity;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.model.AddressBean;
import com.example.yf.creatorshirt.mvp.presenter.AddressPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.AddressContract;
import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AddressShowActivity extends BaseActivity<AddressPresenter> implements AddressContract.AddressView {
    @BindView(R.id.address_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.add_address)
    TextView mEditAddress;

    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter.getAddressData();
    }

    @OnClick({R.id.add_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_address:
                Intent intent = new Intent();
                intent.setClass(this, AddressEditActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void initView() {
        mAppBarTitle.setText(R.string.receiver_address);
        mAppBarBack.setVisibility(View.VISIBLE);

    }


    @Override
    protected int getView() {
        return R.layout.activity_address;
    }

    @Override
    public void showSuccess(List<AddressBean> addressBean) {
//        Log.e("TAG", "ADDRESS" + addressBean.get(1).getAddress());
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        AddressAdapter addressAdapter = new AddressAdapter(this);
//        addressAdapter.setData(addressBean);
//        mRecyclerView.setAdapter(addressAdapter);
    }
}
