//package com.example.yf.creatorshirt.mvp.ui.activity;
//
//import android.os.Bundle;
//import android.support.v4.widget.SwipeRefreshLayout;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.engine.DiskCacheStrategy;
//import com.bumptech.glide.request.RequestOptions;
//import com.example.yf.creatorshirt.R;
//import com.example.yf.creatorshirt.mvp.listener.ItemClickListener;
//import com.example.yf.creatorshirt.mvp.model.BombStyleBean;
//import com.example.yf.creatorshirt.mvp.model.HotDesignsBean;
//import com.example.yf.creatorshirt.mvp.presenter.DesignerOrdersPresenter;
//import com.example.yf.creatorshirt.mvp.presenter.contract.DesignerOrdersContract;
//import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;
//import com.example.yf.creatorshirt.mvp.ui.adapter.DesignerOrdersAdapter;
//import com.example.yf.creatorshirt.utils.GridLinearLayoutManager;
//import com.example.yf.creatorshirt.utils.ToastUtil;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.BindView;
//
///**
// * 设计师所有的设计页面
// */
//public class DesignerOrdersActivity extends BaseActivity<DesignerOrdersPresenter> implements
//        DesignerOrdersContract.DesignerDesignView, ItemClickListener.OnItemClickListener {
//    @BindView(R.id.designer_avatar)
//    ImageView mImageAvatar;
//    @BindView(R.id.designer_name)
//    TextView mDesignerName;
//    @BindView(R.id.designer_design_number)
//    TextView mDesignerNumber;
//    @BindView(R.id.designer_recyclerview)
//    RecyclerView mDesignerRecycler;
//    @BindView(R.id.designer_swipe)
//    SwipeRefreshLayout mSwipeRefresh;
//    private HotDesignsBean mHotDesignsBean;
//    private DesignerOrdersAdapter mAdapter;
//    private GridLayoutManager mGridLayoutManager;
//    private List<BombStyleBean> mOrderStyleBeen;
//    private boolean isLoadingMore = false;
//
//    @Override
//    protected void inject() {
//        getActivityComponent().inject(this);
//    }
//
//    @Override
//    protected void initView() {
//        mAppBarTitle.setText("");
//        mAppBarBack.setVisibility(View.VISIBLE);
//        mGridLayoutManager = new GridLinearLayoutManager(this, 3);
//        mDesignerRecycler.setLayoutManager(mGridLayoutManager);
//        mAdapter = new DesignerOrdersAdapter(this);
//        mAdapter.setOnClickListener(this);
//        if (!TextUtils.isEmpty(mHotDesignsBean.getHeadimage())) {
//            RequestOptions options = new RequestOptions();
//            options.circleCrop();
//            options.diskCacheStrategy(DiskCacheStrategy.ALL);
//            options.error(R.mipmap.mm);
//            Glide.with(this).load(mHotDesignsBean.getHeadimage()).apply(options).into(mImageAvatar);
//        }
//        if (!TextUtils.isEmpty(mHotDesignsBean.getName())) {
//            mDesignerName.setText(mHotDesignsBean.getName());
//        }
//        if (mHotDesignsBean.getCounts() != 0) {
//            mDesignerNumber.setText(mHotDesignsBean.getCounts() + "个原创设计");
//        }
//        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                mPresenter.getTotalDesigner();
//            }
//        });
//        mDesignerRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                int lastVisibleItem = mGridLayoutManager.findLastVisibleItemPosition();
//                int totalItemCount = mDesignerRecycler.getLayoutManager().getItemCount();//显示的total
//                if (lastVisibleItem >= totalItemCount - 2 && dy > 0) {  //还剩3个Item时加载更多
//                    if (!isLoadingMore) {
//                        isLoadingMore = true;
//                        mPresenter.getMoreDesignOrders();
//                    }
//                }
//            }
//        });
//    }
//
//    @Override
//    public void initData() {
//        super.initData();
//        mOrderStyleBeen = new ArrayList<>();
//        if (getIntent().getExtras() != null) {
//            mHotDesignsBean = getIntent().getExtras().getParcelable("hotDesigner");
//        }
//        mPresenter.setUserId(mHotDesignsBean.getUserid());
//        mPresenter.getTotalDesigner();
//    }
//
//    @Override
//    public void stateError() {
//        if (mSwipeRefresh.isRefreshing()) {
//            mSwipeRefresh.setRefreshing(false);
//        }
//    }
//
//    @Override
//    protected int getView() {
//        return R.layout.activity_user_design;
//    }
//
//
//    @Override
//    public void showSuccessData(List<BombStyleBean> orderStyleBeen) {
//        if (mSwipeRefresh.isRefreshing()) {
//            mSwipeRefresh.setRefreshing(false);
//        }
//        mOrderStyleBeen.clear();
//        mOrderStyleBeen.addAll(orderStyleBeen);
//        mAdapter.setData(mOrderStyleBeen);
//        mDesignerRecycler.setAdapter(mAdapter);
//        mAdapter.notifyDataSetChanged();
//    }
//
//    @Override
//    public void showMoreData(List<BombStyleBean> orderStyleBeen) {
//        if (orderStyleBeen.size() == 0) {
//            ToastUtil.showToast(this, "没有更多数据", 0);
//        } else {
//            isLoadingMore = false;
//            mOrderStyleBeen.addAll(orderStyleBeen);
//            for (int i = mOrderStyleBeen.size() - 10; i < mOrderStyleBeen.size(); i++) {    //使用notifyDataSetChanged已加载的图片会有闪烁，遂使用inserted逐个插入
//                mAdapter.notifyItemInserted(i);
//            }
//        }
//    }
//
//    @Override
//    public void showRefreshData(List<BombStyleBean> orderStyleBeen) {
//
//    }
//
//    @Override
//    public void onItemClick(View view, int position) {
//        Bundle bundle = new Bundle();
//        bundle.putParcelable("detail", mOrderStyleBeen.get(position));
//        startCommonActivity(this, bundle, DetailClothesActivity.class);
//    }
//}
