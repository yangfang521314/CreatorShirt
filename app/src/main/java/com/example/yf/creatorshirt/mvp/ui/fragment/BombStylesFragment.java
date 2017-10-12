package com.example.yf.creatorshirt.mvp.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.listener.ItemClickListener;
import com.example.yf.creatorshirt.mvp.model.BombStyleBean;
import com.example.yf.creatorshirt.mvp.presenter.BombStylePresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.BombStylesContract;
import com.example.yf.creatorshirt.mvp.ui.activity.DetailClothesActivity;
import com.example.yf.creatorshirt.mvp.ui.adapter.BombStyleAdapter;
import com.example.yf.creatorshirt.mvp.ui.fragment.base.BaseFragment;
import com.example.yf.creatorshirt.utils.GridLinearLayoutManager;
import com.example.yf.creatorshirt.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/7/30.
 */

public class BombStylesFragment extends BaseFragment<BombStylePresenter> implements BombStylesContract.BombView
        , ItemClickListener.OnItemClickListener {
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @Inject
    Activity mActivity;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    private GridLayoutManager gridLayoutManager;
    private boolean isLoadingMore = false;
    private BombStyleAdapter adapter;
    private List<BombStyleBean> bombStyles;

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
        bombStyles = new ArrayList<>();
        mPresenter.getBombData();
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getBombData();
            }
        });
        gridLayoutManager = new GridLinearLayoutManager(mActivity, 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        adapter = new BombStyleAdapter(mActivity);
        adapter.setOnClickListener(this);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition();
                int totalItemCount = mRecyclerView.getLayoutManager().getItemCount();//显示的total
                if (lastVisibleItem >= totalItemCount - 3 && dy > 0) {  //还剩2个Item时加载更多
                    if (!isLoadingMore) {
                        isLoadingMore = true;
                        mPresenter.getMoreBombData();
                    }
                }
            }
        });
    }


    @Override
    public void showSuccess(List<BombStyleBean> list) {
        if (mSwipeRefresh.isRefreshing()) {
            mSwipeRefresh.setRefreshing(false);
        }
        bombStyles.clear();
        bombStyles.addAll(list);
        adapter.setData(bombStyles);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showMoreSuccessData(List<BombStyleBean> bombStyleBeen) {
        if (bombStyleBeen.size() == 0) {
            ToastUtil.showToast(getActivity(), "没有更多数据", 0);
        } else {
            isLoadingMore = false;
            bombStyles.addAll(bombStyleBeen);
            for (int i = bombStyles.size() - 10; i < bombStyles.size(); i++) {    //使用notifyDataSetChanged已加载的图片会有闪烁，遂使用inserted逐个插入
                adapter.notifyItemInserted(i);
            }
        }
    }

    @Override
    public void stateError() {
        if (mSwipeRefresh.isRefreshing()) {
            mSwipeRefresh.setRefreshing(false);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("detail", bombStyles.get(position));
        startCommonActivity(getActivity(), bundle, DetailClothesActivity.class);
    }

    @Override
    public void showErrorMsg(String msg) {
        super.showErrorMsg(msg);
        ToastUtil.showToast(getActivity(),msg,0);
        if(mSwipeRefresh.isRefreshing()){
            mSwipeRefresh.setRefreshing(false);
        }
    }
}
