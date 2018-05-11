package com.example.yf.creatorshirt.mvp.presenter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.http.DataManager;
import com.example.yf.creatorshirt.http.DownloadService;
import com.example.yf.creatorshirt.mvp.model.VersionUpdateResponse;
import com.example.yf.creatorshirt.mvp.presenter.base.RxPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.VersionUpdateContract;
import com.example.yf.creatorshirt.mvp.ui.view.CustomUpdateDialog;
import com.example.yf.creatorshirt.utils.LogUtil;
import com.example.yf.creatorshirt.utils.NetworkUtils;
import com.example.yf.creatorshirt.utils.RxUtils;
import com.example.yf.creatorshirt.utils.SharedPreferencesUtil;
import com.example.yf.creatorshirt.widget.CommonSubscriber;

import javax.inject.Inject;

/**
 * Created by yangfang on 2018/1/12.
 */

public class VersionUpdatePresenter extends RxPresenter<VersionUpdateContract.VersionUpdateView> {

    private DataManager manager;
    private Context mContext;

    @Inject
    public VersionUpdatePresenter(DataManager manager) {
        this.manager = manager;
    }

    public void checkVersion(Context context, int request) {
        update(request);
        mContext = context;

    }

    /**
     * 获取软件版本号
     *
     * @return
     */
    private int getVerCode() {
        int verCode = -1;
        try {
            verCode = App.getInstance().getPackageManager().getPackageInfo(
                    App.getInstance().getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verCode;
    }

    /**
     * 初始化检测更新
     * 如有更新弹窗更新
     *
     * @param request
     */
    private void update(final int request) {
//        isNeedToUpdate(request);
    }

    private void isNeedToUpdate(int request) {
        if (!NetworkUtils.isNetWorkConnected()) {
            mView.showErrorMsg(App.getInstance().getString(R.string.no_net));
        }

        addSubscribe(manager.getVersionCode(getVerCode())
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleResult())
                .subscribeWith(new CommonSubscriber<VersionUpdateResponse>(mView, "没有新版本") {
                    @Override
                    public void onNext(VersionUpdateResponse versionUpdateResponse) {
                        if (versionUpdateResponse == null) {
                            mView.showErrorMsg("连接超时");
                        } else {
                            if (versionUpdateResponse.getUpdate()) {
                                //提示更新
                                successUpdate(versionUpdateResponse);
                            } else {
                                mView.showErrorMsg("没有新版本");
                            }
                        }
                    }
                }));

    }

    private void successUpdate(VersionUpdateResponse versionResponse) {
        if (SharedPreferencesUtil.getLastVersionCodeFromServer().equals(versionResponse.getNew_version())) {
            //用户上次忽略更新，并且服务器无更新版本
            LogUtil.i("Update", "无版本更新");
        } else {
            suggestVerUpdate(versionResponse);
        }
    }

    /**
     * 更新提示界面
     *
     * @param updateResponse
     */
    private void suggestVerUpdate(final VersionUpdateResponse updateResponse) {
        final CustomUpdateDialog.Builder builder = new CustomUpdateDialog.Builder(mContext);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.dialog_version_update, null);
        builder.setContentView(v).setTitle(R.string.txt_update_title).setMessage(updateResponse.getUpdate_log()).setPositiveButton(R.string.txt_btn_update, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                downloadApk(updateResponse.getApk_url());
                dialog.dismiss();
            }
        }).setNegativeButton(R.string.txt_btn_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        final CustomUpdateDialog dialog = builder.create();
        v.findViewById(R.id.check_ignore).setVisibility(View.VISIBLE);
        ((CheckBox) v.findViewById(R.id.check_ignore)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //忽略更新
                    SharedPreferencesUtil.setLastVersionCodeFromServer(updateResponse.getNew_version());
                    dialog.dismiss();
                }
            }
        });

    }

    /**
     * 下载apk
     *
     * @param apk_url
     */
    private void downloadApk(String apk_url) {
        DownloadService.startService(apk_url, ".apk");
    }

}
