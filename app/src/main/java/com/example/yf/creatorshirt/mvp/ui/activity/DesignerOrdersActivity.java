package com.example.yf.creatorshirt.mvp.ui.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.model.HotDesignsBean;
import com.example.yf.creatorshirt.mvp.model.orders.OrderStyleBean;
import com.example.yf.creatorshirt.mvp.presenter.DesignerOrdersPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.DesignerOrdersContract;
import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;
import com.example.yf.creatorshirt.mvp.ui.adapter.DesignerOrdersAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * 设计师所有的设计页面
 */
public class DesignerOrdersActivity extends BaseActivity<DesignerOrdersPresenter> implements
        DesignerOrdersContract.DesignerDesignView {
    @BindView(R.id.designer_avatar)
    ImageView mImageAvatar;
    @BindView(R.id.designer_name)
    TextView mDesignerName;
    @BindView(R.id.designer_design_number)
    TextView mDesignerNumber;
    @BindView(R.id.designer_recyclerview)
    RecyclerView mDesignerRecycler;
    @BindView(R.id.designer_swipe)
    SwipeRefreshLayout mSwipeRefresh;
    private HotDesignsBean mHotDesignsBean;
    private DesignerOrdersAdapter mAdapter;

    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {
        mDesignerRecycler.setLayoutManager(new GridLayoutManager(this, 3));
        mAdapter = new DesignerOrdersAdapter(this);
        if (!TextUtils.isEmpty(mHotDesignsBean.getHeadimage())) {
            RequestOptions options = new RequestOptions();
            options.circleCrop();
            options.diskCacheStrategy(DiskCacheStrategy.ALL);
            options.error(R.mipmap.mm);
            Glide.with(this).load(mHotDesignsBean.getHeadimage()).apply(options).into(mImageAvatar);
        }
        if (!TextUtils.isEmpty(mHotDesignsBean.getName())) {
            mDesignerName.setText(mHotDesignsBean.getName());
        }
        if (mHotDesignsBean.getCounts() != 0) {
            mDesignerNumber.setText(mHotDesignsBean.getCounts() + "原创设计");
        }
    }

    @Override
    public void initData() {
        super.initData();
        if (getIntent().getExtras() != null) {
            mHotDesignsBean = getIntent().getExtras().getParcelable("hotDesigner");
        }
        mPresenter.setUserId(mHotDesignsBean.getUserid());
        mPresenter.getTotalDesigner();

    }

    @Override
    protected int getView() {
        return R.layout.activity_user_design;
    }


    @Override
    public void showSuccessData(List<OrderStyleBean> orderStyleBeen) {
        mDesignerRecycler.setAdapter(mAdapter);
        mAdapter.setData(orderStyleBeen);
    }
}
