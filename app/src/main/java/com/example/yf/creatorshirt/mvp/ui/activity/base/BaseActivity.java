package com.example.yf.creatorshirt.mvp.ui.activity.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.inject.component.ActivityComponent;
import com.example.yf.creatorshirt.inject.component.DaggerActivityComponent;
import com.example.yf.creatorshirt.inject.module.ActivityModule;
import com.example.yf.creatorshirt.mvp.presenter.base.BasePresenter;
import com.example.yf.creatorshirt.mvp.view.BaseView;
import com.example.yf.creatorshirt.utils.systembar.SystemUtilsBar;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by yang on 2017/5/11.
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView {

    protected TextView mAppBarTitle;
    protected ImageView mAppBarBack;
    protected LinearLayout mAppBar;
    protected TextView mSaveAddress;

    @Inject
    public T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemUtilsBar.with(this).init();
        setContentView(getView());
        SystemUtilsBar.with(this)
                .statusBarDarkFont(true, 0.2f)
                .init();
        ButterKnife.bind(this);
        App.getInstance().addActivity(this);
        inject();
        if (mPresenter != null)
            mPresenter.attachView(this);
        initData();
        initToolbar();
        initView();
    }


    private void initToolbar() {
        mAppBarTitle = (TextView) findViewById(R.id.app_bar_title);
        mAppBarBack = (ImageView) findViewById(R.id.back);
        mAppBar = (LinearLayout) findViewById(R.id.app_bar);
        mSaveAddress = (TextView) findViewById(R.id.save);
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

    public void startCommonActivity(BaseActivity formActivity, Bundle bundle, Class toActivity) {
        if (formActivity != null && toActivity != null) {
            if (bundle == null) {
                Intent intent = new Intent(formActivity, toActivity);
                formActivity.startActivity(intent);
            } else {
                Intent intent = new Intent(formActivity, toActivity);
                intent.putExtras(bundle);
                formActivity.startActivity(intent);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView(this);
        }
    }

    @Override
    public void showErrorMsg(String msg) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void stateError() {

    }
}
