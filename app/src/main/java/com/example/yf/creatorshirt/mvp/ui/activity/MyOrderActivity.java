package com.example.yf.creatorshirt.mvp.ui.activity;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.utils.systembar.SystemUtilsBar;

/**
 * 订单详情页面
 */

public class MyOrderActivity extends BaseActivity {


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
        return R.layout.activity_my_order;
    }
}
