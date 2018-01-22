package com.example.yf.creatorshirt.inject.component;

import android.app.Activity;

import com.example.yf.creatorshirt.inject.module.FragmentModule;
import com.example.yf.creatorshirt.inject.scope.FragmentScope;
import com.example.yf.creatorshirt.mvp.ui.fragment.BombStylesFragment;
import com.example.yf.creatorshirt.mvp.ui.fragment.HotDesignsFragment;
import com.example.yf.creatorshirt.mvp.ui.fragment.MineFragment;
import com.example.yf.creatorshirt.mvp.ui.fragment.NewDesignFragment;

import dagger.Component;

/**
 * Created by yang on 16/05/2017.
 */
@FragmentScope
@Component(dependencies = {AppComponent.class}, modules = {FragmentModule.class})
public interface FragmentComponent {
    Activity getActivity();

    void inject(MineFragment mineFragment);

    void inject(BombStylesFragment bombStylesFragment);

    void inject(HotDesignsFragment hotDesignsFragment);


    void inject(NewDesignFragment newDesignFragment);
}
