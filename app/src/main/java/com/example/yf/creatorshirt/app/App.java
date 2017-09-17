package com.example.yf.creatorshirt.app;

import android.app.Activity;
import android.support.multidex.MultiDexApplication;

import com.example.yf.creatorshirt.common.UserInfoManager;
import com.example.yf.creatorshirt.inject.component.AppComponent;
import com.example.yf.creatorshirt.inject.component.DaggerAppComponent;
import com.example.yf.creatorshirt.inject.module.AppModule;
import com.example.yf.creatorshirt.inject.module.HttpModule;
import com.example.yf.creatorshirt.utils.SharedPreferencesUtil;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by yf on 2017/5/11.
 */

public class App extends MultiDexApplication {

    private static App mInstance;
    private static AppComponent mAppComponent;
    private Set<Activity> allActivities;
    /*用户是否登录*/
    public static boolean isLogin;

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
        UMShareAPI.get(this);
        UserInfoManager.getInstance().init(this);
        initShareConfig();
        if (SharedPreferencesUtil.getIsLogin()) {
            setIsLogin(true);
        } else {
            setIsLogin(false);
        }
    }

    private void initShareConfig() {
//        59b105c7717c19193f00057f
        PlatformConfig.setWeixin("wx791dd5375a413291", "fa887ebfa17a272eb28d7b6c2fd9c16e");
    }

    public void addActivity(Activity activity) {
        if (allActivities == null) {
            allActivities = new HashSet<>();
        }
        allActivities.add(activity);
    }

    public static void setIsLogin(boolean b) {
        App.isLogin = b;
        SharedPreferencesUtil.setIsLogin(isLogin);
    }
}
