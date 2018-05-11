package com.example.yf.creatorshirt.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.yf.creatorshirt.BuildConfig;
import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.presenter.DownloadImagePresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.CommonAvatarContract;
import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;
import com.example.yf.creatorshirt.mvp.ui.view.DialogAlert;
import com.example.yf.creatorshirt.mvp.view.BaseWebView;
import com.example.yf.creatorshirt.utils.LogUtil;

import java.io.File;

import butterknife.BindView;

public class ServerActivity extends BaseActivity<DownloadImagePresenter> implements CommonAvatarContract.CommonAvatarView {
    private static final String TAG = ServerActivity.class.getSimpleName();
    @BindView(R.id.webView)
    BaseWebView mWebView;

    private String url;
    private DialogAlert dialogAlert;

    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }


    @Override
    public void initData() {
        super.initData();
        if (getIntent().getExtras() != null) {
            url = getIntent().getExtras().getString("url");
            LogUtil.e("TAG", "initData: " + url);
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initView() {
        if (url.contains("https")) {
            mAppBarTitle.setText("更多图片");
        } else {
            mAppBarTitle.setText("用户协议说明");
        }
        mWebView.setOnLongClickListener(new MyOnLongClickListener());
        mAppBarBack.setOnClickListener(v -> {
                    if (dialogAlert != null && dialogAlert.isShowing()) {
                        dialogAlert.dismiss();
                        finish();
                    }
                }
        );
        mWebView.setWebViewClient(new ViewClient());
        mWebView.loadUrl(url);
    }

    @Override
    public void showErrorMsg(String msg) {
        showToast(msg);
    }

    @Override
    protected int getView() {
        return R.layout.activity_server;
    }

    @Override
    public void showSuccessAvatar(File cover) {
        showToast("已保存图片");
        scanFileAsync(this, cover.getAbsolutePath());
        scanDirAsync(this, cover.getParent());
    }

    private class ViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            mAppBar.setVisibility(View.GONE);
            return false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mWebView != null) {
            if (mWebView.canGoBack()) {
                mWebView.canGoForward();
            }
            if (dialogAlert != null) {
                dialogAlert.dismiss();
            }

        }
    }

    /**
     * 长按下载图片
     */
    public class MyOnLongClickListener implements View.OnLongClickListener {

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
        dialogAlert = new DialogAlert.Builder()
                .setContext(this)
                .setTitle("保存图片到手机")
                .setCancel("取消")
                .setConfirm("确定")
                .builder();
        dialogAlert.show();
        dialogAlert.setOnPositionClickListener(() -> mPresenter.downloadImage(extra));
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.destroy();
        }
    }
}
