package com.example.yf.creatorshirt.mvp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.yf.creatorshirt.utils.MyWebSetting;

import java.io.File;

import static org.greenrobot.eventbus.EventBus.TAG;


public class BaseWebView extends WebView {
    private Context mContext;
    private WebView mWebView;
    private File file;

    {
        if (!isInEditMode()) {
            Log.e(TAG, "instance initializer: ");
            //延长webview的生命周期，防止内存泄漏
            mContext = this.getContext();
            mWebView = this;
            initSetting();
            secureSetting();

//            setDownloadListener(new MyWebViewDownLoadListener());
        }
    }

    public BaseWebView(Context context) {
        super(context);
        mContext = this.getContext();
        mWebView = this;
        initSetting();
        secureSetting();
        Log.e(TAG, "BaseWebView: ");
    }

    public BaseWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void initSetting() {
        WebSettings settings = getSettings();
        MyWebSetting unifySetting = new MyWebSetting(mContext, settings);
        unifySetting.setSaveMode();
        unifySetting.setZoomMode();
        unifySetting.setUseWideViewPort(true);
        unifySetting.setDatabasePath();//开启Application H5 Caches 功能
        settings.setDefaultTextEncodingName("GBK");// 设置字符编码
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setSaveFormData(true);//使用缓存

        mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);//该句是因为出现了dequeueBuffer failed 崩溃的情况
    }

    private void secureSetting() {
        mWebView.removeJavascriptInterface("searchBoxJavaBridge_");
        mWebView.removeJavascriptInterface("accessibility");
        mWebView.removeJavascriptInterface("accessibilityTraversal");
    }


    /**
     * 解决滑动冲突
     *
     * @param direction 滑动距离
     * @return
     */
    @Override
    public boolean canScrollHorizontally(int direction) {
        return false;
    }


}
