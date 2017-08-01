package com.example.yf.creatorshirt.mvp.ui.fragment;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.model.BombStyleBean;
import com.example.yf.creatorshirt.mvp.presenter.BombStylePresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.BombStylesContract;
import com.example.yf.creatorshirt.mvp.ui.adapter.BombStyleAdapter;
import com.example.yf.creatorshirt.mvp.ui.fragment.base.BaseFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/7/30.
 */

public class BombStylesFragment extends BaseFragment<BombStylePresenter> implements BombStylesContract.BombView {
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @Inject
    Activity mActivity;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bomb_styles;
    }

    @Override
    protected void initViews(View mView) {

    }

    @Override
    protected void initData() {
        mPresenter.getBombData(1);
    }

    @Override
    public void showSuccess(List<BombStyleBean> bombStyles) {
        BombStyleAdapter adapter = new BombStyleAdapter(mActivity);
        adapter.setData(bombStyles);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity,2));
        mRecyclerView.setAdapter(adapter);
    }
}
