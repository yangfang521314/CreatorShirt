package com.example.yf.creatorshirt.mvp.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

//// TODO: 2017/7/4 maybe this page and ChoiceActivity use a common Activity;
public class SuccessPayActivity extends BaseActivity {

    @BindView(R.id.btn_check_order)
    Button mCheckOrder;

    @Override
    protected void inject() {

    }

    @Override
    protected void initView() {
        mAppBarBack.setVisibility(View.VISIBLE);
        mAppBarTitle.setText(R.string.design);
    }

    @OnClick({R.id.btn_check_order})
    public void onClick(View view) {
        if (view.getId() == R.id.btn_check_order) {
            Bundle bundle = new Bundle();
            bundle.putString("title",getString(R.string.my_order));
            startCommonActivity(this,bundle,AllOrderActivity.class);
        }
    }

    @Override
    protected int getView() {
        return R.layout.activity_success_pay;
    }
}
