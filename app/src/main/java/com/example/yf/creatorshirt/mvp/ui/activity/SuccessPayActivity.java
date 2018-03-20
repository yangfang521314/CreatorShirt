package com.example.yf.creatorshirt.mvp.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class SuccessPayActivity extends BaseActivity {

    @BindView(R.id.btn_check_order)
    Button mCheckOrder;
    @BindView(R.id.share_weixin)
    TextView shareWeixin;

    @Override
    protected void inject() {

    }

    @Override
    protected void initView() {
        mAppBarBack.setVisibility(View.VISIBLE);
        mAppBarTitle.setText(R.string.design);
    }

    @OnClick({R.id.btn_check_order, R.id.back, R.id.share_weixin})
    public void onClick(View view) {
        if (view.getId() == R.id.btn_check_order) {
            Bundle bundle = new Bundle();
            bundle.putString("title", getString(R.string.my_order));
            startCommonActivity(this, bundle, AllOrdersActivity.class);
        }
        if (view.getId() == R.id.back) {
            startCommonActivity(this, null, MainActivity.class);
        }

    }

    @Override
    protected int getView() {
        return R.layout.activity_success_pay;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startCommonActivity(this, null, MainActivity.class);
    }
}
