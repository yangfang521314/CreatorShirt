package com.example.yf.creatorshirt.mvp.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.listener.ItemClickListener;
import com.example.yf.creatorshirt.mvp.model.HotDesignsBean;
import com.example.yf.creatorshirt.mvp.presenter.HotDesignPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.HotDesignContract;
import com.example.yf.creatorshirt.mvp.ui.activity.DeignerNewOrdersActivity;
import com.example.yf.creatorshirt.mvp.ui.adapter.HotDesignStyleAdapter;
import com.example.yf.creatorshirt.mvp.ui.fragment.base.BaseFragment;
import com.example.yf.creatorshirt.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/7/30.
 */

public class HotDesignsFragment extends BaseFragment<HotDesignPresenter> implements HotDesignContract.HotDesignView
        , ItemClickListener.OnItemClickListener {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    @Inject
    Activity mActivity;
    private List<HotDesignsBean> hotDesignsBeen;
    private GridLayoutManager mGridManager;
    private HotDesignStyleAdapter adapter;
    private boolean isLoadMore = false;

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
        hotDesignsBeen = new ArrayList<>();
        mGridManager = new GridLayoutManager(mActivity, 3);
        mRecyclerView.setLayoutManager(mGridManager);
        adapter = new HotDesignStyleAdapter(mActivity);
        adapter.setOnclicklistener(this);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getHotDesign();
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = mGridManager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition >= mRecyclerView.getLayoutManager().getItemCount() - 2 && dy > 0) {
                    if (!isLoadMore) {
                        mPresenter.loadMore();
                        isLoadMore = true;
                    }
                }
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getHotDesign();
    }

    @Override
    public void showSuccess(List<HotDesignsBean> hotDesigns) {
        if (mSwipeRefresh.isRefreshing()) {
            mSwipeRefresh.setRefreshing(false);
        }
        hotDesignsBeen.clear();
        hotDesignsBeen.addAll(hotDesigns);
        adapter.setData(hotDesignsBeen);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void showMoreSuccess(List<HotDesignsBean> hotDesigns) {
        if (hotDesigns == null) {
            ToastUtil.showToast(getActivity(), "没有更多数据", 0);
        } else {
            isLoadMore = false;
            hotDesignsBeen.addAll(hotDesigns);
            for (int i = hotDesignsBeen.size() - 10; i < hotDesignsBeen.size(); i++) {
                adapter.notifyItemInserted(i);
            }
        }
    }

//    private class DividerItemDecoration extends Y_DividerItemDecoration {
//
//        private DividerItemDecoration(Context context) {
//            super(context);
//        }
//
//        @Override
//        public Y_Divider getDivider(int itemPosition) {
//            Y_Divider divider = null;
//            switch (itemPosition % 2) {
//                case 0:
//                    //每一行第一个显示rignt和bottom
//                    divider = new Y_DividerBuilder()
//                            .setRightSideLine(true, Color.parseColor("#dddddd"), 1, 0, 0)
//                            .setBottomSideLine(true, Color.parseColor("#dddddd"), 1, 0, 0)
//                            .create();
//                    break;
//                case 1:
//                    //每一行第一个显示rignt和bottom
//                    divider = new Y_DividerBuilder()
//                            .setRightSideLine(true, Color.parseColor("#dddddd"), 1, 0, 0)
//                            .setBottomSideLine(true, Color.parseColor("#dddddd"), 1, 0, 0)
//                            .create();
//                    break;
//                case 2:
//                    //第二个显示Left和bottom
//                    divider = new Y_DividerBuilder()
//                            .setBottomSideLine(true, Color.parseColor("#dddddd"), 1, 0, 0)
//                            .create();
//                    break;
//                default:
//                    break;
//            }
//            return divider;
//        }
//    }

    @Override
    public void showErrorMsg(String msg) {
        ToastUtil.showToast(getActivity(), msg, 0);
    }

    @Override
    public void onItemClick(View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("hotDesigner", hotDesignsBeen.get(position));
        startCommonActivity(getActivity(), bundle, DeignerNewOrdersActivity.class);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
