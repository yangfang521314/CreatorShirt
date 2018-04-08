package com.example.yf.creatorshirt.app;

import android.support.multidex.MultiDexApplication;

import com.example.yf.creatorshirt.BuildConfig;
import com.example.yf.creatorshirt.common.manager.ClothesSizeManager;
import com.example.yf.creatorshirt.common.manager.FontTypeManager;
import com.example.yf.creatorshirt.common.manager.UserInfoManager;
import com.example.yf.creatorshirt.inject.component.AppComponent;
import com.example.yf.creatorshirt.inject.component.DaggerAppComponent;
import com.example.yf.creatorshirt.inject.module.AppModule;
import com.example.yf.creatorshirt.inject.module.HttpModule;
import com.example.yf.creatorshirt.utils.SharedPreferencesUtil;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

/**
 * Created by yf on 2017/5/11.
 */

public class App extends MultiDexApplication {

    private static App mInstance;
    private static AppComponent mAppComponent;
    /*用户是否登录*/
    public static boolean isLogin;
    private RefWatcher refWatcher;

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
        FontTypeManager.getInstance().init(this);
        ClothesSizeManager.getInstance().init(App.getInstance());

        initShareConfig();
        setIsLogin(SharedPreferencesUtil.getIsLogin());
        if (BuildConfig.DEBUG) {
            refWatcher = LeakCanary.install(this);
        }
    }

    private void initShareConfig() {
        PlatformConfig.setWeixin("wx1b403c8f6e9b990b", "5167021796dfd6b73766af3a6b3a33ee");
        PlatformConfig.setSinaWeibo("4176293252", "a3dda28debdc8bc811013fcf928ad6a9", "http://sns.whalecloud.com");
    }

    public static void setIsLogin(boolean b) {
        App.isLogin = b;
        SharedPreferencesUtil.setIsLogin(isLogin);
    }

}
