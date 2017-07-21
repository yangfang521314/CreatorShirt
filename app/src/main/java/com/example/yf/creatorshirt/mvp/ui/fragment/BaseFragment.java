package com.example.yf.creatorshirt.mvp.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.inject.component.DaggerFragmentComponent;
import com.example.yf.creatorshirt.inject.component.FragmentComponent;
import com.example.yf.creatorshirt.inject.module.FragmentModule;

import butterknife.ButterKnife;

/**
 * Created by yf on 2017/5/11.
 */

public abstract class BaseFragment extends Fragment {

    private View mView;
    protected Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = App.getInstance();
        if (mView == null) {
            mView = inflater.inflate(getLayoutId(), null);
            ButterKnife.bind(this, mView);
        }
        initInject();
        initData();
        initViews(mView);
        return mView;
    }

    protected FragmentComponent getFragmentComponent() {
        return DaggerFragmentComponent.builder()
                .appComponent(App.getAppComponent())
                .fragmentModule(getFragmentModule())
                .build();
    }

    /**
     * 依赖注入
     */
    protected abstract void initInject();

    protected abstract int getLayoutId();

    protected abstract void initViews(View mView);

    protected abstract void initData();

    protected void startCommonActivity(Activity formActivity, Class toActivity) {
        if (formActivity != null && toActivity != null) {
            Intent intent = new Intent(formActivity, toActivity);
            formActivity.startActivity(intent);
        }
    }

    public FragmentModule getFragmentModule() {
        return new FragmentModule(this);
    }
}
