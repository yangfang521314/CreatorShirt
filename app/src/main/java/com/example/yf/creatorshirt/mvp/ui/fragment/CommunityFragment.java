package com.example.yf.creatorshirt.mvp.ui.fragment;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.model.bean.PhotoGirl;
import com.example.yf.creatorshirt.mvp.presenter.GirlContract;
import com.example.yf.creatorshirt.mvp.presenter.GirlPresenterImpl;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by yf on 2017/5/11.
 */

public class CommunityFragment extends BaseFragemnt implements GirlContract.GirlView {


    @Inject
    GirlPresenterImpl mPresenter;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.community_fragment;
    }

    @Override
    protected void initViews(View mView) {

    }

    @Override
    protected void initData() {
        mPresenter.attach(this);
        mPresenter.getGirlData();
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

    @Override
    public void showData(List<PhotoGirl> list) {
        Log.e("TAG", "LIST" + list.size());
    }
}