package com.example.yf.creatorshirt.mvp.ui.fragment;

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

public abstract class BaseFragemnt extends Fragment {

    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(getLayoutId(), null);
            ButterKnife.bind(this, mView);
        }
        initViews(mView);
        initInject();
        return mView;
    }

    protected FragmentComponent getFragmentComponent() {
        return DaggerFragmentComponent.builder().appComponent(App.getAppComponent())
                .fragmentModule(getFragmentModule())
                .build();
    }

    /**
     * 依赖注入
     */
    protected abstract void initInject();

    protected abstract int getLayoutId();

    protected abstract void initViews(View mView);

    public FragmentModule getFragmentModule() {
        return new FragmentModule(this);
    }
}
