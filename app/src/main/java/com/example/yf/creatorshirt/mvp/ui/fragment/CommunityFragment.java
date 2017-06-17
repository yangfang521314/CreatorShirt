package com.example.yf.creatorshirt.mvp.ui.fragment;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.model.bean.PhotoGirl;
import com.example.yf.creatorshirt.mvp.presenter.contract.GirlContract;
import com.example.yf.creatorshirt.mvp.presenter.GirlPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by yf on 2017/5/11.
 */

public class CommunityFragment extends BaseFragment implements GirlContract.GirlView {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Inject
    GirlPresenter mPresenter;

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

    }
}