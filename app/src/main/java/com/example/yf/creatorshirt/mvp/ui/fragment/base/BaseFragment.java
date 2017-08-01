package com.example.yf.creatorshirt.mvp.ui.fragment.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.inject.component.DaggerFragmentComponent;
import com.example.yf.creatorshirt.inject.component.FragmentComponent;
import com.example.yf.creatorshirt.inject.module.FragmentModule;
import com.example.yf.creatorshirt.mvp.presenter.base.BasePresenter;
import com.example.yf.creatorshirt.mvp.view.BaseView;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by yf on 2017/5/11.
 */

public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements BaseView{

    private View mView;
    protected Context mContext;

    @Inject
    public T mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = App.getInstance();
        if (mView == null) {
            mView = inflater.inflate(getLayoutId(), null);
            ButterKnife.bind(this, mView);
        }
        initInject();
        if(mPresenter != null){
            mPresenter.attachView(this);
        }
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

    protected void initData(){

    }


    public void startCommonActivity(Activity formActivity, Bundle bundle, Class toActivity) {
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

    public FragmentModule getFragmentModule() {
        return new FragmentModule(this);
    }

    @Override
    public void onDestroy() {
        if(mPresenter != null){
            mPresenter.detachView(this);
        }
        super.onDestroy();
    }

    @Override
    public void showErrorMsg(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
