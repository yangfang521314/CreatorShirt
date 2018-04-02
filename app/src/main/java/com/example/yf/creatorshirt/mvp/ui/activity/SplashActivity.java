package com.example.yf.creatorshirt.mvp.ui.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends BaseActivity {

    private List<Boolean> mPermissionList = new ArrayList<>();
    private static final String SCHEME = "package";
    private  Disposable disposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCheckPermission();
    }

    private void onCheckPermission() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.requestEach(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(permission -> mPermissionList.add(permission.granted), new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable t) {
                            }
                        },
                        () -> {
                            //默认所有的权限已经申请成功
                            boolean flag = false;
                            for (Boolean b : mPermissionList) {
                                if (!b) {//当前有禁止的权限
                                    flag = true;
                                    break;
                                }
                            }
                            if (flag) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
                                builder.setMessage(getResources().getString(R.string.permission_tip));
                                builder.setNegativeButton("暂不", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                });
                                builder.setPositiveButton("去设置", (dialog, which) -> {
                                    Intent intent = new Intent();
                                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts(SCHEME, getPackageName(), null);
                                    intent.setData(uri);
                                    startActivity(intent);
                                    finish();
                                });
                                builder.show();
                            } else {
                                startOtherActivity();
                            }
                        });
    }

    private void startOtherActivity() {
       disposable = Observable.interval(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    if (aLong.intValue() == 0) {
                        startCommonActivity(SplashActivity.this, null, MainActivity.class);
                        SplashActivity.this.finish();
                    }
                });

    }

    @Override
    protected void inject() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(disposable != null){
            disposable.dispose();
        }
    }
}
