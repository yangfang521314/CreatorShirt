package com.example.yf.creatorshirt.mvp.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.GlideApp;
import com.example.yf.creatorshirt.mvp.listener.ItemClickListener;
import com.example.yf.creatorshirt.mvp.model.VersionStyle;
import com.example.yf.creatorshirt.mvp.presenter.ClothesPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.ClothesContract;
import com.example.yf.creatorshirt.mvp.ui.activity.NewDesignActivity;
import com.example.yf.creatorshirt.mvp.ui.adapter.NewClothesAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.NewDesignAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.NewMClothesAdapter;
import com.example.yf.creatorshirt.mvp.ui.fragment.base.BaseFragment;
import com.example.yf.creatorshirt.mvp.ui.view.scalelayoutmanager.ScaleLayoutManager;
import com.example.yf.creatorshirt.mvp.ui.view.scalelayoutmanager.ViewPagerLayoutManager;
import com.example.yf.creatorshirt.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by yangfang on 2017/11/2.
 */

public class NewDesignFragment extends BaseFragment<ClothesPresenter> implements ClothesContract.ClothesView {
    @BindView(R.id.base_recyclerview)
    RecyclerView mStyleRecyclerView;
    @BindView(R.id.rl_detail_style_m)
    RelativeLayout mManRelative;
    @BindView(R.id.rl_detail_style_w)
    RelativeLayout mWomanRelative;
    @BindView(R.id.detail_recyclerview)
    RecyclerView mDetailManRCY;
    @BindView(R.id.detail_recyclerview_w)
    RecyclerView mDetailWomanRCY;
    @BindView(R.id.delete_rcy)
    ImageView mDeleteCY;
    @BindView(R.id.tv_sex_m)
    TextView mShowM;
    @BindView(R.id.tv_sex_w)
    TextView mShowW;
    @Inject
    Activity mActivity;
    private ScaleLayoutManager scaleLayoutManager;
    private ScaleLayoutManager scaleMLayoutManager;
    private ScaleLayoutManager scaleWLayoutManager;
    private NewDesignAdapter adapter;
    private NewClothesAdapter designAdapter;
    private NewMClothesAdapter mMClothesAdapter;
    private List<String> mClothesName;
    private ArrayMap<String, List<VersionStyle>> mManData;
    private ArrayMap<String, List<VersionStyle>> mWomanData;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getClothesVersion();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_new_design;
    }

    @Override
    protected void initViews(View mView) {
        initClothesName();
        initClothesImage();
    }

    @OnClick({R.id.delete_rcy})
    void onClick(View view) {
        if (view.getId() == R.id.delete_rcy) {
            mManRelative.setVisibility(View.GONE);
            mStyleRecyclerView.setVisibility(View.VISIBLE);
            mWomanRelative.setVisibility(View.GONE);
            mDeleteCY.setVisibility(View.GONE);
            GlideApp.with(mActivity).pauseRequests();
        }
    }

    /**
     * 初始化衣服
     */
    private void initClothesImage() {
        scaleMLayoutManager = new ScaleLayoutManager(DisplayUtil.Dp2Px(mContext, 10));
        mDetailManRCY.setLayoutManager(scaleMLayoutManager);
        scaleMLayoutManager.setItemSpace(DisplayUtil.Dp2Px(mContext, 10));
        scaleMLayoutManager.setCenterScale(1.3f);
        scaleMLayoutManager.setOrientation(ViewPagerLayoutManager.HORIZONTAL);
        scaleWLayoutManager = new ScaleLayoutManager(DisplayUtil.Dp2Px(mContext, 10));
        mDetailWomanRCY.setLayoutManager(scaleWLayoutManager);
        scaleWLayoutManager.setItemSpace(DisplayUtil.Dp2Px(mContext, 10));
        scaleWLayoutManager.setCenterScale(1.3f);
        scaleWLayoutManager.setOrientation(ViewPagerLayoutManager.HORIZONTAL);

    }

    private void initClothesName() {
        scaleLayoutManager = new ScaleLayoutManager(DisplayUtil.Dp2Px(mContext, 10));
        mStyleRecyclerView.setLayoutManager(scaleLayoutManager);
        scaleLayoutManager.setItemSpace(DisplayUtil.Dp2Px(mContext, 15));
        scaleLayoutManager.setCenterScale(1.3f);
        scaleLayoutManager.setOrientation(ViewPagerLayoutManager.VERTICAL);
        adapter = new NewDesignAdapter(mContext);
        adapter.setData(mClothesName);
        mStyleRecyclerView.setAdapter(adapter);
        adapter.setOnComClickListener(new ItemClickListener.OnItemComClickListener() {
            @Override
            public void onItemClick(Object o, View view) {
                if (mManData.containsKey(o)) {
                    mManRelative.setVisibility(View.VISIBLE);
                    designAdapter = new NewClothesAdapter(mActivity);
                    designAdapter.setGender("m");
                    designAdapter.setData(mManData.get(o));
                    designAdapter.setOnItemClickListener(new OnObjectClickListener());
                    mDetailManRCY.setAdapter(designAdapter);
                    designAdapter.notifyDataSetChanged();
                    mShowM.setText(mManData.get(o).get(1).getGender());
                }
                if (mWomanData.containsKey(o)) {
                    mWomanRelative.setVisibility(View.VISIBLE);
                    mMClothesAdapter = new NewMClothesAdapter(mActivity);
                    mMClothesAdapter.setGender("w");
                    mMClothesAdapter.setData(mWomanData.get(o));
                    mMClothesAdapter.setOnItemClickListener(new OnObjectClickListener());
                    mDetailWomanRCY.setAdapter(mMClothesAdapter);
                    mMClothesAdapter.notifyDataSetChanged();
                    mShowW.setText(mWomanData.get(o).get(1).getGender());
                }
                mStyleRecyclerView.setVisibility(View.GONE);
                mDeleteCY.setVisibility(View.VISIBLE);
            }


        });
    }

    @Override
    public void showTotalClothes(ArrayMap<String, List<VersionStyle>> totalManMap, ArrayMap<String, List<VersionStyle>> totalWomanMap, List<String> mListVerName) {
        mClothesName = mListVerName;
        mManData = totalManMap;
        mWomanData = totalWomanMap;
    }


    public class OnObjectClickListener implements DesignOnClickListener {

        @Override
        public void onItemClickListener(Object o, Object o1) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("choice", (Parcelable) o);
            bundle.putParcelableArrayList("clothes", (ArrayList<? extends Parcelable>) o1);
            startCommonActivity(getActivity(), bundle, NewDesignActivity.class);
        }
    }

    interface DesignOnClickListener {
        void onItemClickListener(Object o, Object o1);
    }

}
