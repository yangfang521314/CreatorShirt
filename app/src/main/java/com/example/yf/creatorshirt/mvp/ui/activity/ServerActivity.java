package com.example.yf.creatorshirt.mvp.ui.activity;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;
import com.example.yf.creatorshirt.mvp.view.BaseWebView;
import com.example.yf.creatorshirt.utils.LogUtil;

import butterknife.BindView;

public class ServerActivity extends BaseActivity {
    @BindView(R.id.webView)
    BaseWebView mWebView;

    private String url;

    @Override
    protected void inject() {

    }

    @Override
    public void initData() {
        super.initData();
        if(getIntent().getExtras() != null){
            url = getIntent().getExtras().getString("url");
            LogUtil.e("TAG", "initData: " +url);
        }
    }



    @Override
    protected void initView() {
        mAppBarTitle.setText("用户协议说明");
        mWebView.loadUrl(url);
        mAppBarBack.setOnClickListener(v -> finish());
    }

    @Override
    protected int getView() {
        return R.layout.activity_server;
    }
}
