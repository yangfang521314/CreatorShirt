package com.example.yf.creatorshirt.ui.fragment;

import android.view.View;

import com.example.yf.creatorshirt.R;

/**
 * Created by yf on 2017/5/11.
 */

public class CommunityFragment extends BaseFragemnt {

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
}