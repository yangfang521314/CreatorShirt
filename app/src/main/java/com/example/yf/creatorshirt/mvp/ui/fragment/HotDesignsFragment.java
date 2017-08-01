package com.example.yf.creatorshirt.mvp.ui.fragment;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.model.HotDesignsBean;
import com.example.yf.creatorshirt.mvp.presenter.HotDesignPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.HotDesignContract;
import com.example.yf.creatorshirt.mvp.ui.adapter.HotDesignStyleAdapter;
import com.example.yf.creatorshirt.mvp.ui.fragment.base.BaseFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/7/30.
 */

public class HotDesignsFragment extends BaseFragment<HotDesignPresenter> implements HotDesignContract.HotDesignView {

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
        super.initData();
        mPresenter.getHotDesign();
    }

    @Override
    public void showSuccess(List<HotDesignsBean> hotDesigns) {
        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity,3));
        HotDesignStyleAdapter adapter = new HotDesignStyleAdapter(mActivity);
        adapter.setData(hotDesigns);
        mRecyclerView.setAdapter(adapter);
    }
}
