package com.example.yf.creatorshirt.mvp.ui.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.model.bean.AddressBean;
import com.example.yf.creatorshirt.mvp.ui.adapter.AddressAdapter;
import com.example.yf.creatorshirt.utils.systembar.SystemUtilsBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AddressActivity extends BaseActivity {
    @BindView(R.id.address_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.app_bar_title)
    TextView mAppBarTitle;
    @BindView(R.id.back)
    ImageView mAppBarBack;
    @BindView(R.id.add_address)
    TextView mEditAddress;

    private List<AddressBean> addressBeanList = new ArrayList<>();

    @Override
    protected void inject() {

    }

    @Override
    public void initData() {
        super.initData();
        for (int i = 0; i < 10; i++) {
            AddressBean bean = new AddressBean();
            bean.setAddress("中国重庆中国重庆中国重庆中国重庆中国重庆中国重庆");
            bean.setName("张芷溪");
            bean.setPhone("15868178345");
            addressBeanList.add(bean);
        }

    }

    @OnClick({R.id.add_address})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.add_address:
                Intent intent = new Intent();
                intent.setClass(this,EditAddressActivity.class);
                startActivity(intent);
                break;
        }
    }


    @Override
    protected void initView() {
        SystemUtilsBar.with(this)
                .statusBarDarkFont(true, 0.2f)
                .init();
        mAppBarTitle.setText(R.string.receiver_address);
        mAppBarBack.setVisibility(View.VISIBLE);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        AddressAdapter addressAdapter = new AddressAdapter(this);
        addressAdapter.setData(addressBeanList);
        mRecyclerView.setAdapter(addressAdapter);
    }


    @Override
    protected int getView() {
        return R.layout.activity_address;
    }
}
