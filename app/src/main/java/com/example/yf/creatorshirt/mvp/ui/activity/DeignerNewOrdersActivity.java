package com.example.yf.creatorshirt.mvp.ui.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.common.UpdateOrdersEvent;
import com.example.yf.creatorshirt.mvp.listener.ItemClickListener;
import com.example.yf.creatorshirt.mvp.model.BombStyleBean;
import com.example.yf.creatorshirt.mvp.model.HotDesignsBean;
import com.example.yf.creatorshirt.mvp.presenter.DesignerOrdersPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.DesignerOrdersContract;
import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;
import com.example.yf.creatorshirt.mvp.ui.adapter.DesignerOrdersAdapter;
import com.example.yf.creatorshirt.mvp.ui.view.freshrecyler.FreshRecyclerView;
import com.example.yf.creatorshirt.utils.GridLinearLayoutManager;
import com.example.yf.creatorshirt.utils.ToastUtil;
import com.example.yf.creatorshirt.widget.CommonSubscriber;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class DeignerNewOrdersActivity extends BaseActivity<DesignerOrdersPresenter> implements
        DesignerOrdersContract.DesignerDesignView, ItemClickListener.OnItemObjectClickListener {
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
    private DesignerOrdersAdapter mAdapter;
    private TextView heardTextView;
    private List<BombStyleBean> mFirstStyleEntity = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {
        mAppBarTitle.setText("定制详情");
        mAppBarBack.setVisibility(View.VISIBLE);
        mGridLayoutManager = new GridLinearLayoutManager(this, 3);
        mDesignerRecycler.setLayoutManager(mGridLayoutManager);
        mAdapter = new DesignerOrdersAdapter(this);
        mAdapter.setOnClickListener(this);
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
            mDesignerNumber.setText(mHotDesignsBean.getCounts() + "个原创设计");
        }
        mDesignerRecycler.setLoadingListener(new FreshRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                refreshOrdersData();
            }

            @Override
            public void onLoadMore() {
                mPresenter.getMoreDesignOrders();
            }
        });
        View newsListNum = LayoutInflater.from(this).inflate(R.layout.heard_item_num, null);
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        newsListNum.setLayoutParams(layoutParams1);
        newsListNum.setPadding(20, 0, 20, 0);
        heardTextView = (TextView) newsListNum.findViewById(R.id.updateNewsNum);
        heardTextView.setVisibility(View.GONE);
        mDesignerRecycler.addHeaderView(newsListNum);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void UpDateOrders(UpdateOrdersEvent event) {
        if (event.getFlag()) {
            refreshOrdersData();
        }
    }

    @OnClick({R.id.back})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    private void refreshOrdersData() {
        mPresenter.getRefreshDesigner();
    }

    @Override
    protected int getView() {
        return R.layout.activity_detail_orders;
    }

    @Override
    public void initData() {
        super.initData();
        if (getIntent().getExtras() != null) {
            mHotDesignsBean = getIntent().getExtras().getParcelable("hotDesigner");
        }
        if (mHotDesignsBean != null) {
            mPresenter.setUserId(mHotDesignsBean.getUserid());
        }
        mPresenter.getTotalDesigner();
    }

    @Override
    public void showSuccessData(List<BombStyleBean> orderStyleBeen) {
        mFirstStyleEntity.addAll(orderStyleBeen);
        mAdapter.setData(mFirstStyleEntity);
        mDesignerRecycler.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMoreData(List<BombStyleBean> orderStyleBeen) {
        if (orderStyleBeen == null) {
            mDesignerRecycler.noMoreLoading();
        } else {
            if (orderStyleBeen.size() == 0) {
                Toast.makeText(mContext, "到底了，已经没有更多数据", Toast.LENGTH_SHORT).show();
                mDesignerRecycler.noMoreLoading();
            } else {
                mFirstStyleEntity.addAll(orderStyleBeen);
                mAdapter.notifyDataSetChanged();
                mDesignerRecycler.refreshComplete();
            }

        }
    }

    @Override
    public void showRefreshData(List<BombStyleBean> orderStyle) {
        if (mFirstStyleEntity != null) {
            mFirstStyleEntity.clear();
        }
        if(orderStyle != null) {
            mFirstStyleEntity.addAll(orderStyle);
        }
        mAdapter.setData(mFirstStyleEntity);
        mAdapter.notifyDataSetChanged();
        Flowable.interval(0, 1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Long, Integer>() {
                    @Override
                    public Integer apply(@NonNull Long aLong) throws Exception {
                        return 4 - aLong.intValue();
                    }
                })
                .take(5).doOnSubscribe(new Consumer<Subscription>() {
            @Override
            public void accept(@NonNull Subscription subscription) throws Exception {

            }
        })
                .subscribeWith(new CommonSubscriber<Integer>(this) {
                    @Override
                    public void onNext(Integer integer) {
                        if (integer < 3) {
                            mDesignerRecycler.refreshComplete();
                            heardTextView.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onComplete() {
                        heardTextView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mDesignerRecycler.refreshComplete();
                        heardTextView.setVisibility(View.GONE);
                    }

                });

    }

    @Override
    public void showUpdateZero(int i) {
        updateNewsToast(i);
    }


    private void updateNewsToast(final int newsCount) {
        String toastStr;
        if (newsCount > 0) {
            toastStr = "为您推荐" + newsCount + "条更新";
        } else {
            toastStr = "暂时无更多数据";
        }
        heardTextView.setText(toastStr);
        Flowable.interval(0, 1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Long, Integer>() {
                    @Override
                    public Integer apply(@NonNull Long aLong) throws Exception {
                        return 4 - aLong.intValue();
                    }
                })
                .take(5).doOnSubscribe(new Consumer<Subscription>() {
            @Override
            public void accept(@NonNull Subscription subscription) throws Exception {

            }
        })
                .subscribeWith(new CommonSubscriber<Integer>(this) {
                    @Override
                    public void onNext(Integer integer) {
                        if (integer < 3) {
                            mDesignerRecycler.refreshComplete();
                            heardTextView.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onComplete() {
                        heardTextView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mDesignerRecycler.refreshComplete();
                        heardTextView.setVisibility(View.GONE);
                    }

                });
    }

    @Override
    public void onItemClick(Object o) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("detail", (Parcelable) o);
        startCommonActivity(this, bundle, DetailClothesActivity.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void showErrorMsg(String msg) {
        super.showErrorMsg(msg);
        ToastUtil.showToast(mContext, msg, 0);
        mDesignerRecycler.refreshComplete();

    }
}
