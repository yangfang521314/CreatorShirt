package com.example.yf.creatorshirt.app;

import android.app.Application;

import com.example.yf.creatorshirt.inject.component.AppComponent;
import com.example.yf.creatorshirt.inject.component.DaggerAppComponent;
import com.example.yf.creatorshirt.inject.module.AppModule;
import com.example.yf.creatorshirt.inject.module.HttpModule;
import com.example.yf.creatorshirt.utils.SharedPreferencesUtil;

/**
 * Created by yf on 2017/5/11.
 */

public class App extends Application {

    private static App mInstance;
    private static AppComponent mAppComponent;

    public static AppComponent getAppComponent() {
        if (mAppComponent == null) {
            mAppComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(mInstance))
                    .httpModule(new HttpModule())
                    .build();
        }
        return mAppComponent;
    }

    public static synchronized App getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        SharedPreferencesUtil.init(this);
    }
}
