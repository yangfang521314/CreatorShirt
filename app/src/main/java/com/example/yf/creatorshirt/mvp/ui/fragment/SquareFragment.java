package com.example.yf.creatorshirt.mvp.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.ui.adapter.SquarePagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by yf on 2017/5/11.
 */

public class SquareFragment extends BaseFragment {
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.tab)
    TabLayout mTab;
    private ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private HotDesignsFragment mHotDesignsFragment;
    private BombStylesFragment mBombStylesFragment;
    @Override
    protected void initInject() {
//        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_square;
    }

    @Override
    protected void initViews(View mView) {
        mFragmentList.clear();
        mHotDesignsFragment = new HotDesignsFragment();
        mBombStylesFragment = new BombStylesFragment();
        mFragmentList.add(mHotDesignsFragment);
        mFragmentList.add(mBombStylesFragment);
        ArrayList<String> tabName = new ArrayList<>();
        tabName.add("爆款推荐");
        tabName.add("设计师");
        SquarePagerAdapter adapter = new SquarePagerAdapter(getChildFragmentManager());
        adapter.setData(mFragmentList,tabName);
        mViewPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        mTab.setupWithViewPager(mViewPager);
        mTab.setTabTextColors(R.color.black,R.color.sand);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}