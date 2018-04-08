package com.example.yf.creatorshirt.utils.systembar;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.FloatRange;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.ColorUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.example.yf.creatorshirt.mvp.view.BaseView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yang on 07/06/2017.
 */

public class SystemUtilsBar {
    private Map<String, BarParams> mMap = new HashMap<>();
    private String mFragmentName;
    private static List<String> mFragmentList = new ArrayList<>();
    private String mActivityName;
    private Activity mActivity;
    private Window mWindow;
    private ViewGroup mViewGroup;
    private ViewGroup mContentView;
    private BarConfig mConfig;
    private BarParams mBarParams;

    private SystemUtilsBar(Activity activity) {
        mActivityName = activity.getClass().getName();
        initParams(activity, mActivityName);
    }

    /**
     * Instantiates a new Immersion bar.
     *
     * @param fragment the fragment
     */
    private SystemUtilsBar(Fragment fragment) {
        mActivityName = fragment.getActivity().getClass().getName();
        mFragmentName = mActivityName + "_and_" + fragment.getClass().getName();
        if (!mFragmentList.contains(mFragmentName))
            mFragmentList.add(mFragmentName);
        initParams(fragment.getActivity(), mFragmentName);
    }

    /**
     * With immersion bar.
     *
     * @param fragment the fragment
     * @return the immersion bar
     */
    public static SystemUtilsBar with(Fragment fragment) {
        return new SystemUtilsBar(fragment);
    }

    /**
     * 初始化沉浸式默认的参数
     *
     * @param activity
     * @param mActivityName
     */
    private void initParams(Activity activity, String mActivityName) {
        mActivity = activity;
        mWindow = mActivity.getWindow();
        mViewGroup = (ViewGroup) mWindow.getDecorView();
        mContentView = mActivity.findViewById(android.R.id.content);
        mConfig = new BarConfig(activity);
        if (!mMap.isEmpty() && !mActivityName.isEmpty()) {
            if (mMap.get(mActivityName) == null) {
                mBarParams = new BarParams();
                if (mFragmentName != null) { //保证一个activity页面有同一个状态栏view和导航栏view
                    mBarParams.statusBarView = mMap.get(mActivityName).statusBarView;
                    mBarParams.navigationBarView = mMap.get(mActivityName).navigationBarView;
                }
                mMap.put(mActivityName, mBarParams);
            } else {
                mBarParams = mMap.get(mActivityName);
            }
        } else {
            mBarParams = new BarParams();
            if (mFragmentName != null) {  //保证一个activity页面有同一个状态栏view和导航栏view
                mBarParams.statusBarView = mMap.get(mActivityName).statusBarView;
                mBarParams.navigationBarView = mMap.get(mActivityName).navigationBarView;
            }
            mMap.put(mActivityName, mBarParams);
        }
    }

    /**
     * With immersion bar.
     *
     * @param activity the activity
     * @return the immersion bar
     */
    public static SystemUtilsBar with(Activity activity) {
        return new SystemUtilsBar(activity);
    }


    /**
     * 通过状态栏高度动态设置状态栏布局
     *
     * @param view the view
     * @return the immersion bar
     */
    public SystemUtilsBar statusBarView(View view) {
        mBarParams.statusBarViewByHeight = view;
        return this;
    }

    /**
     * 通过上面配置后初始化后方可成功调用
     */
    public void init() {
        mMap.put(mActivityName, mBarParams);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            initBar();   //初始化沉浸式
            setStatusBarView();  //通过状态栏高度动态设置状态栏布局
        }
//        transformView();  //变色view
    }

    private void initBar() {
        int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;  //防止系统栏隐藏时内容区域大小发生变化
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && !OSUtils.isEMUI3_1()) {
                uiFlags = initBarAboveLOLLIPOP(uiFlags); //初始化5.0以上，包含5.0
            } else {
                initBarBelowLOLLIPOP(); //初始化5.0以下，4.4以上沉浸式
//                solveNavigation();  //解决android4.4有导航栏的情况下，activity底部被导航栏遮挡的问题和android 5.0以下解决状态栏和布局重叠问题
            }
            uiFlags = setStatusBarDarkFont(uiFlags); //android 6.0以上设置状态栏字体为暗色
            uiFlags = hideBar(uiFlags);  //隐藏状态栏或者导航栏
        }
        mWindow.getDecorView().setSystemUiVisibility(uiFlags);
        if (OSUtils.isMIUI6More()) {
            MIUISetStatusBarLightMode();         //修改miui状态栏字体颜色
        }
        if (OSUtils.isFlymeOS4More()) {          // 修改Flyme OS状态栏字体颜色
            if (mBarParams.flymeOSStatusBarFontColor != 0) {
                FlymeOSStatusBarFontUtils.setStatusBarDarkIcon(mWindow, mBarParams.flymeOSStatusBarFontColor);
            } else {
                FlymeOSStatusBarFontUtils.setStatusBarDarkIcon(mWindow, mBarParams.darkFont);
            }
        }

    }


    /**
     * 通过状态栏高度动态设置状态栏布局
     */
    private void setStatusBarView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && mBarParams.statusBarViewByHeight != null) {
            ViewGroup.LayoutParams params = mBarParams.statusBarViewByHeight.getLayoutParams();
            params.height = mConfig.getStatusBarHeight();
            mBarParams.statusBarViewByHeight.setLayoutParams(params);
        }
    }

    /**
     * 设置状态栏字体图标为深色，需要MIUIv6
     */
    private void MIUISetStatusBarLightMode() {
        if (mWindow != null) {
            Class clazz = mWindow.getClass();
            int darkModeFlag = 0;
            try {
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (mBarParams.darkFont) {
                    extraFlagField.invoke(mWindow, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(mWindow, 0, darkModeFlag);//清除黑色字体
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Hide bar.
     * 隐藏或显示状态栏和导航栏。 状态栏和导航栏的颜色不起作用，都是透明色，以最后一次调用为准
     *
     * @param uiFlags the ui flags
     * @return the int
     */
    private int hideBar(int uiFlags) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            switch (mBarParams.barHide) {
                case FLAG_HIDE_BAR:
                    uiFlags |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.INVISIBLE;
                    break;
                case FLAG_HIDE_STATUS_BAR:
                    uiFlags |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.INVISIBLE;
                    break;
                case FLAG_HIDE_NAVIGATION_BAR:
                    uiFlags |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
                    break;
                case FLAG_SHOW_BAR:
                    uiFlags |= View.SYSTEM_UI_FLAG_VISIBLE;
                    break;
            }
        }
        return uiFlags | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
    }

    /**
     * 初始化android 5.0以上状态栏
     *
     * @param uiFlags the ui flags
     * @return the int
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private int initBarAboveLOLLIPOP(int uiFlags) {
        uiFlags |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;  //Activity全屏显示，但状态栏不会被隐藏覆盖，状态栏依然可见，Activity顶端布局部分会被状态栏遮住。
        mWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);  //需要设置这个才能设置状态栏颜色
        if (mBarParams.titleBarView == null)
            mWindow.setStatusBarColor(ColorUtils.blendARGB(mBarParams.statusBarColor,
                    mBarParams.statusBarColorTransform, mBarParams.statusBarAlpha));  //设置状态栏颜色
        else
            mWindow.setStatusBarColor(ColorUtils.blendARGB(mBarParams.statusBarColor,
                    Color.TRANSPARENT, mBarParams.statusBarAlpha));  //设置状态栏颜色
        return uiFlags;
    }

    /**
     * 初始化android 4.4和emui3.1状态栏和导航栏
     */
    private void initBarBelowLOLLIPOP() {
        mWindow.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//透明状态栏
//        mWindow.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//透明导航栏，设置这个，如果有导航栏，底部布局会被导航栏遮住
        setupStatusBarView(); //创建一个假的状态栏
//        if (mConfig.hasNavigtionBar())  //判断是否存在导航栏
//            setupNavBarView();   //创建一个假的导航栏
    }

    /**
     * 设置一个自定义的颜色的状态栏
     */
    private void setupStatusBarView() {
        if (mBarParams.statusBarView == null) {
            mBarParams.statusBarView = new View(mActivity);
        }
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, mConfig.getStatusBarHeight());
        params.gravity = Gravity.TOP;
//        if (!mConfig.isNavigationAtBottom()) {
//            params.rightMargin = mConfig.getNavigationBarWidth();
//        }
        mBarParams.statusBarView.setLayoutParams(params);
        if (mBarParams.titleBarView == null || mBarParams.statusBarFlag)
            mBarParams.statusBarView.setBackgroundColor(ColorUtils.blendARGB(mBarParams.statusBarColor,
                    mBarParams.statusBarColorTransform, mBarParams.statusBarAlpha));
        else
            mBarParams.statusBarView.setBackgroundColor(ColorUtils.blendARGB(mBarParams.statusBarColor,
                    Color.TRANSPARENT, mBarParams.statusBarAlpha));
        mBarParams.statusBarView.setVisibility(View.VISIBLE);
        ViewGroup viewGroup = (ViewGroup) mBarParams.statusBarView.getParent();
        if (viewGroup != null)
            viewGroup.removeView(mBarParams.statusBarView);
        mViewGroup.addView(mBarParams.statusBarView);
    }

    /**
     * Sets status bar dark font.
     * 设置状态栏字体颜色，android6.0以上
     */
    private int setStatusBarDarkFont(int uiFlags) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && mBarParams.darkFont) {
            return uiFlags | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        } else {
            return uiFlags;
        }
    }


    /**
     * 状态栏字体深色或亮色，判断设备支不支持状态栏变色来设置状态栏透明度
     * Status bar dark font immersion bar.
     *
     * @param isDarkFont  the is dark font
     * @param statusAlpha the status alpha 如果不支持状态栏字体变色可以使用statusAlpha来指定状态栏透明度，比如白色状态栏的时候可以用到
     * @return the immersion bar
     */
    public SystemUtilsBar statusBarDarkFont(boolean isDarkFont, @FloatRange(from = 0f, to = 1f) float statusAlpha) {
        mBarParams.darkFont = isDarkFont;
        if (!isDarkFont)
            mBarParams.flymeOSStatusBarFontColor = 0;
        if (!isSupportStatusBarDarkFont()) {
            mBarParams.statusBarAlpha = statusAlpha;
        } else {
            mBarParams.statusBarAlpha = 0;
        }
        return this;
    }

    /**
     * 判断手机支不支持状态栏字体变色
     * Is support status bar dark font boolean.
     *
     * @return the boolean
     */
    public static boolean isSupportStatusBarDarkFont() {
        return OSUtils.isMIUI6More() || OSUtils.isFlymeOS4More()
                || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M);
    }

    public void onDestroy() {
        if (mMap != null) {
            mMap.clear();
            mMap = null;
        }
    }
}