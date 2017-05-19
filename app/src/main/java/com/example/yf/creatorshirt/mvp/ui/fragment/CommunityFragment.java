package com.example.yf.creatorshirt.mvp.ui.fragment;

import android.view.View;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.model.bean.GirlData;
import com.example.yf.creatorshirt.mvp.presenter.GirlContract;

import java.util.List;

/**
 * Created by yf on 2017/5/11.
 */

public class CommunityFragment extends BaseFragemnt implements GirlContract.GirlView{

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
    public void showErrorMsg(String msg) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showData(List<GirlData> list) {

    }
}