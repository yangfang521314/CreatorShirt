package com.example.yf.creatorshirt.mvp.ui.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.model.HotDesignsBean;
import com.example.yf.creatorshirt.mvp.presenter.DesignerOrdersPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.DesignerOrdersContract;
import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;

import butterknife.BindView;

/**
 * 设计师所有的设计页面
 */
public class DesignerOrdersActivity extends BaseActivity<DesignerOrdersPresenter> implements
        DesignerOrdersContract.DesignerDesignView{
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

    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {
    }

    @Override
    public void initData() {
        super.initData();
        if(getIntent().getExtras() != null){
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
    public void showSuccessData() {

    }
}
