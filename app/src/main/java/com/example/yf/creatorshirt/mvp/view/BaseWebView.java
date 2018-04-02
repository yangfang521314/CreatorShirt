package com.example.yf.creatorshirt.mvp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.yf.creatorshirt.utils.MyWebSetting;


public class BaseWebView extends WebView {
    private Context mContext;
    private WebView mWebView;

    {
        if (!isInEditMode()) {
            //延长webview的生命周期，防止内存泄漏
            mContext = this.getContext();
            mWebView = this;
            initSetting();
            secureSetting();

//            setDownloadListener(new MyWebViewDownLoadListener());
//            setOnLongClickListener(new MyOnLongClickListener());
        }
    }

    public BaseWebView(Context context) {
        super(context);
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

//
//
//    /**
//     * 长按下载图片
//     */
//    private class MyOnLongClickListener implements View.OnLongClickListener {
//        @Override
//        public boolean onLongClick(View v) {
//            if (v instanceof WebView) {
//                WebView.HitTestResult result = ((WebView) v).getHitTestResult();
//                if (result != null) {
//                    int type = result.getType();
//                    if (type == WebView.HitTestResult.IMAGE_TYPE
//                            || type == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
//                        Utils.downloadImage(mContext, result.getExtra());
//                    }
//                }
//            }
//            return false;
//        }
//    }

    /**
     * 解决滑动冲突
     * @param direction 滑动距离
     * @return
     */
     @Override
    public boolean canScrollHorizontally(int direction) {
        return false;
    }
}
