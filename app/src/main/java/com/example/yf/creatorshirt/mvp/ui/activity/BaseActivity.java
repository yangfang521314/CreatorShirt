package com.example.yf.creatorshirt.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.inject.component.ActivityComponent;
import com.example.yf.creatorshirt.inject.component.DaggerActivityComponent;
import com.example.yf.creatorshirt.inject.module.ActivityModule;
import com.example.yf.creatorshirt.utils.systembar.SystemUtilsBar;

import butterknife.ButterKnife;

/**
 * Created by yang on 2017/5/11.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected TextView mAppBarTitle;
    protected ImageView mAppBarBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemUtilsBar.with(this).init();
        setContentView(getView());
        SystemUtilsBar.with(this)
                .statusBarDarkFont(true, 0.2f)
                .init();
        ButterKnife.bind(this);
        inject();
        initData();
        initToolbar();
        initView();
    }

    private void initToolbar() {
        mAppBarTitle = (TextView) findViewById(R.id.app_bar_title);
        mAppBarBack = (ImageView) findViewById(R.id.back);
    }

    public void initData() {
    }

    /**
     * 依赖注入框架
     */
    protected abstract void inject();

    public ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder().appComponent(App.getAppComponent())
                .activityModule(getActivityModule()).build();
    }

    public ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    protected abstract void initView();

    protected abstract int getView();

    public void startCommonActivity(BaseActivity formActivity, Class toActivity) {
        if (formActivity != null && toActivity != null) {
            Intent intent = new Intent(formActivity, toActivity);
            formActivity.startActivity(intent);
        }
    }

    public void startCommonActivity(BaseActivity formActivity, Intent intent) {
        if (formActivity != null && intent != null) {
            formActivity.startActivity(intent);
        }
    }
}
