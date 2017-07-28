package com.example.yf.creatorshirt.mvp.ui.fragment;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.model.bean.PhotoGirl;
import com.example.yf.creatorshirt.mvp.presenter.GirlPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.GirlContract;

import java.util.List;

import butterknife.BindView;

/**
 * Created by yf on 2017/5/11.
 */

public class SquareFragment extends BaseFragment<GirlPresenter> implements GirlContract.GirlView {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.square_fragment;
    }

    @Override
    protected void initViews(View mView) {
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//        mRecyclerView.setAdapter();
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    protected void initData() {
//        mPresenter.getGirlData();
    }

    @Override
    public void showData(List<PhotoGirl> list) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}