package com.example.yf.creatorshirt.mvp.ui.activity;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.model.BombStyleBean;
import com.example.yf.creatorshirt.mvp.model.HotDesignsBean;
import com.example.yf.creatorshirt.mvp.presenter.DesignerOrdersPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.DesignerOrdersContract;
import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;
import com.example.yf.creatorshirt.mvp.ui.view.freshrecyler.FreshRecyclerView;
import com.example.yf.creatorshirt.utils.GridLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class DetailOrders22Activity extends BaseActivity<DesignerOrdersPresenter> implements
        DesignerOrdersContract.DesignerDesignView {
    @BindView(R.id.designer_avatar)
    ImageView mImageAvatar;
    @BindView(R.id.designer_name)
    TextView mDesignerName;
    @BindView(R.id.designer_design_number)
    TextView mDesignerNumber;
    @BindView(R.id.designer_recyclerview)
    FreshRecyclerView mDesignerRecycler;
    private HotDesignsBean mHotDesignsBean;
    private GridLayoutManager mGridLayoutManager;
    private List<BombStyleBean> mOrderStyleBeen;

    @Override
    protected void inject() {

    }


    @Override
    protected void initView() {
        mAppBarTitle.setText("");
        mAppBarBack.setVisibility(View.VISIBLE);
        mGridLayoutManager = new GridLinearLayoutManager(this, 3);
        mDesignerRecycler.setLayoutManager(mGridLayoutManager);
        mDesignerRecycler.setLoadingListener(new FreshRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                refreshOrdersData();
            }

            @Override
            public void onLoadMore() {
//                loadMoreNews(++mCurrentPage);
            }
        });
    }

    private void refreshOrdersData() {
        mPresenter.getTotalDesigner();
    }

    @Override
    protected int getView() {
        return R.layout.activity_detail_orders;
    }

    @Override
    public void initData() {
        super.initData();
        mOrderStyleBeen = new ArrayList<>();
        if (getIntent().getExtras() != null) {
            mHotDesignsBean = getIntent().getExtras().getParcelable("hotDesigner");
        }
        mPresenter.setUserId(mHotDesignsBean.getUserid());
        mPresenter.getTotalDesigner();
    }

    @Override
    public void showSuccessData(List<BombStyleBean> orderStyleBeen) {

    }

    @Override
    public void showMoreData(List<BombStyleBean> orderStyleBeen) {

    }
}
