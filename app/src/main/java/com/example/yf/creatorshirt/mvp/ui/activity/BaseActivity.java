package com.example.yf.creatorshirt.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemUtilsBar.with(this).init();
        setContentView(getView());
        ButterKnife.bind(this);
        inject();
        initData();
        initView();
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
}
