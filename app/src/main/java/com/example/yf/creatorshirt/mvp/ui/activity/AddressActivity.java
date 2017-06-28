package com.example.yf.creatorshirt.mvp.ui.activity;

import android.support.v7.widget.RecyclerView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.utils.systembar.SystemUtilsBar;

import butterknife.BindView;

public class AddressActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;


    @Override
    protected void inject() {

    }

    @Override
    protected void initView() {
        SystemUtilsBar.with(this)
                .statusBarDarkFont(true, 0.2f)
                .init();
    }

    @Override
    protected int getView() {
        return R.layout.activity_address;
    }
}
