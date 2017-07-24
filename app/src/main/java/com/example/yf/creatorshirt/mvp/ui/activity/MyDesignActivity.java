package com.example.yf.creatorshirt.mvp.ui.activity;

import com.example.yf.creatorshirt.R;

public class MyDesignActivity extends BaseActivity {


    @Override
    protected void inject() {

    }

    @Override
    protected void initView() {
        mAppBarTitle.setText("我的定制");
    }

    @Override
    protected int getView() {
        return R.layout.activity_my_design;
    }
}
