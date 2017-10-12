package com.example.yf.creatorshirt.mvp.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/30.
 */

public class SquarePagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragments;
    private ArrayList<String> tabNames;

    public SquarePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabNames.get(position);
    }

    public void setData(ArrayList<Fragment> mFragmentList, ArrayList<String> tabName) {
        this.fragments = mFragmentList;
        this.tabNames = tabName;
    }
}
