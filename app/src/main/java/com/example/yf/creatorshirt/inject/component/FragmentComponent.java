package com.example.yf.creatorshirt.inject.component;

import android.app.Activity;

import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.inject.module.FragmentModule;
import com.example.yf.creatorshirt.inject.scope.FragmentScope;

import dagger.Component;

/**
 * Created by yang on 16/05/2017.
 */
@FragmentScope
@Component(dependencies = {AppComponent.class}, modules = {FragmentModule.class})
public interface FragmentComponent {
    Activity getActivity();
}
