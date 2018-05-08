package com.example.yf.creatorshirt.mvp.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.yf.creatorshirt.BuildConfig;
import com.example.yf.creatorshirt.http.TestRequestServer;
import com.example.yf.creatorshirt.mvp.ui.view.DialogAlert;
import com.example.yf.creatorshirt.utils.Constants;
import com.example.yf.creatorshirt.utils.MyWebSetting;
import com.example.yf.creatorshirt.utils.RxUtils;
import com.example.yf.creatorshirt.utils.ToastUtil;
import com.example.yf.creatorshirt.widget.CommonObserver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

import static org.greenrobot.eventbus.EventBus.TAG;


public class BaseWebView extends WebView {
    private Context mContext;
    private WebView mWebView;
    private File file;

    {
        if (!isInEditMode()) {
            //延长webview的生命周期，防止内存泄漏
            mContext = this.getContext();
            mWebView = this;
            initSetting();
            secureSetting();

//            setDownloadListener(new MyWebViewDownLoadListener());
            setOnLongClickListener(new MyOnLongClickListener());
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


    /**
     * 长按下载图片
     */
    private class MyOnLongClickListener implements View.OnLongClickListener {

        @Override
        public boolean onLongClick(View v) {
            if (v instanceof WebView) {
                WebView.HitTestResult result = ((WebView) v).getHitTestResult();
                if (result != null) {
                    int type = result.getType();
                    if (type == WebView.HitTestResult.IMAGE_TYPE
                            || type == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
                        getDialog(result.getExtra());
                    }
                }
            }
            return false;
        }
    }

    private void getDialog(String extra) {
        DialogAlert dialogAlert = new DialogAlert.Builder()
                .setContext(mContext)
                .setTitle("保存图片到手机")
                .setCancel("取消")
                .setConfirm("确定")
                .builder();
        dialogAlert.show();
        dialogAlert.setOnPositionClickListener(() -> downloadImage(extra));
    }

    /**
     * 下载图片
     *
     * @param
     * @param extra
     */
    @SuppressLint("CheckResult")
    public void downloadImage(String extra) {
        TestRequestServer.getDownInstance().downloadImage(extra)
                .map(this::saveFile)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribe(s -> {
                    if (s != null) {
                        ToastUtil.showToast(mContext, "已保存图片", 0);
                        scanFileAsync(mContext, s);
                        scanDirAsync(mContext, file.getAbsolutePath());
                    }else {
                        ToastUtil.showToast(mContext, "保存图片失败", 0);
                    }
                });
    }

    private String saveFile(ResponseBody responseBody) {
        String result = "";
        file = new File(Environment.getExternalStorageDirectory().toString() + Constants.DOWNIMAGES);
        if (!file.exists()) {
            file.mkdirs();
        }
        File imgfile = new File(file.getAbsolutePath(), new Date().getTime() + ".png");
        InputStream inputStream = responseBody.byteStream();
        byte[] buffer = new byte[4096];
        int len = 0;
        try {
            FileOutputStream outputStream = new FileOutputStream(imgfile);
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            inputStream.close();
            outputStream.close();
            result = imgfile.getAbsolutePath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    //扫描指定文件
    public void scanFileAsync(Context ctx, String filePath) {
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(Uri.fromFile(new File(filePath)));
        ctx.sendBroadcast(scanIntent);
    }

    //扫描指定目录
    public static final String ACTION_MEDIA_SCANNER_SCAN_DIR = "android.intent.action.MEDIA_SCANNER_SCAN_DIR";

    public void scanDirAsync(Context ctx, String dir) {
        Intent scanIntent = new Intent(ACTION_MEDIA_SCANNER_SCAN_DIR);
        Uri uri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//7.0通过FileProvider授权访问
            uri = FileProvider.getUriForFile(ctx, BuildConfig.PROVIDER_CONFIG, new File(dir));
        } else {
            uri = Uri.fromFile(new File(dir));
        }
        scanIntent.setData(uri);
        ctx.sendBroadcast(scanIntent);
    }

}
