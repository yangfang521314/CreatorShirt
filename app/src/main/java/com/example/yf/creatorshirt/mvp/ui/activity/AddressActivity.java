package com.example.yf.creatorshirt.mvp.ui.activity;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.utils.systembar.SystemUtilsBar;

public class AddressActivity extends BaseActivity {

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
