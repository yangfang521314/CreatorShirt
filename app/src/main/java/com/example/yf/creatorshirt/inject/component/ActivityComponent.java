package com.example.yf.creatorshirt.inject.component;

import android.app.Activity;

import com.example.yf.creatorshirt.inject.module.ActivityModule;
import com.example.yf.creatorshirt.inject.scope.AcitivityScope;
import com.example.yf.creatorshirt.mvp.ui.activity.MainActivity;

import dagger.Component;

/**
 * Created by yang on 16/05/2017.
 */

@AcitivityScope
@Component(dependencies = {AppComponent.class}, modules = {ActivityModule.class})
public interface ActivityComponent {
    Activity getActivity();

    void inject(MainActivity mainActivity);
}
