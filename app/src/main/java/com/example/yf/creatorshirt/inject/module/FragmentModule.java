package com.example.yf.creatorshirt.inject.module;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.example.yf.creatorshirt.inject.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yang on 16/05/2017.
 */
@Module
public class FragmentModule {
    private Fragment mFragment;

    public FragmentModule(Fragment fragment) {
        this.mFragment = fragment;
    }

    /**
     * 提供fragment的Context引用
     *
     * @return
     */
    @Provides
    @FragmentScope
    public Activity providerActivity() {
        return mFragment.getActivity();
    }
}
