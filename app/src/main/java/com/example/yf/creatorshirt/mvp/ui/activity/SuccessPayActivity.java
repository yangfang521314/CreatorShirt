package com.example.yf.creatorshirt.mvp.ui.activity;

import android.view.View;
import android.widget.Button;

import com.example.yf.creatorshirt.R;

import butterknife.BindView;
import butterknife.OnClick;

public class SuccessPayActivity extends BaseActivity {

    @BindView(R.id.btn_check_order)
    Button mCheckOrder;



    @Override
    protected void inject() {

    }

    @Override
    protected void initView() {
        mAppBarBack.setVisibility(View.VISIBLE);
        mAppBarTitle.setText(R.string.design_title_bar);
    }

    @OnClick({R.id.btn_check_order})
    public void onClick(View view) {
        if (view.getId() == R.id.btn_check_order) {
            startCommonActivity(this, AllOrderActivity.class);
        }
    }

    @Override
    protected int getView() {
        return R.layout.activity_success_pay;
    }
}
