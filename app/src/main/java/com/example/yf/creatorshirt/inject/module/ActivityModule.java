package com.example.yf.creatorshirt.inject.module;

import android.app.Activity;

import com.example.yf.creatorshirt.inject.scope.AcitivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yang on 16/05/2017.
 * 依赖注入Activity
 */
@Module
public class ActivityModule {

    Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    @AcitivityScope
    Activity providerActivity() {
        return mActivity;
    }
}
