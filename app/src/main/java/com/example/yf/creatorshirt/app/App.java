package com.example.yf.creatorshirt.app;

import android.app.Activity;
import android.support.multidex.MultiDexApplication;

import com.example.yf.creatorshirt.inject.component.AppComponent;
import com.example.yf.creatorshirt.inject.component.DaggerAppComponent;
import com.example.yf.creatorshirt.inject.module.AppModule;
import com.example.yf.creatorshirt.inject.module.HttpModule;
import com.example.yf.creatorshirt.utils.SharedPreferencesUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by yf on 2017/5/11.
 */

public class App extends MultiDexApplication {

    private static App mInstance;
    private static AppComponent mAppComponent;
    private Set<Activity> allActivities;
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

    public void addActivity(Activity activity) {
        if (allActivities == null) {
            allActivities = new HashSet<>();
        }
        allActivities.add(activity);
    }

    public void exitApp() {
        if (allActivities != null) {
            synchronized (allActivities) {
                for (Activity act : allActivities) {
                    act.finish();
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
