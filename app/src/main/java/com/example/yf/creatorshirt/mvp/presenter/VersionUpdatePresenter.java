package com.example.yf.creatorshirt.mvp.presenter;

import android.content.Context;
import android.content.pm.PackageManager;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.http.DataManager;
import com.example.yf.creatorshirt.http.HttpResponse;
import com.example.yf.creatorshirt.mvp.model.VersionUpdateResponse;
import com.example.yf.creatorshirt.mvp.presenter.base.RxPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.VersionUpdateContract;
import com.example.yf.creatorshirt.utils.NetworkUtils;
import com.example.yf.creatorshirt.utils.RxUtils;
import com.example.yf.creatorshirt.widget.CommonSubscriber;

import javax.inject.Inject;

/**
 * Created by yangfang on 2018/1/12.
 */

public class VersionUpdatePresenter extends RxPresenter<VersionUpdateContract.VersionUpdateView> {

    private DataManager manager;

    @Inject
    public VersionUpdatePresenter(DataManager manager) {
        this.manager = manager;
    }

    public void checkVersion(Context context, int request) {
        update(request);

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
        isNeedToUpdate(request);
    }

    private void isNeedToUpdate(int request) {
        if (!NetworkUtils.isNetWorkConnected()) {
            mView.showErrorMsg(App.getInstance().getString(R.string.no_net));
        }

        addSubscribe(manager.getVersionCode(getVerCode())
                .compose(RxUtils.<HttpResponse<VersionUpdateResponse>>rxSchedulerHelper())
                .compose(RxUtils.<VersionUpdateResponse>handleResult())
                .subscribeWith(new CommonSubscriber<VersionUpdateResponse>(mView) {
                    @Override
                    public void onNext(VersionUpdateResponse versionUpdateResponse) {
                        if (versionUpdateResponse == null) {
                            mView.showErrorMsg("连接超时");
                        } else {
                            if (versionUpdateResponse.getUpdate()) {
                                //提示更新
                                mView.showSuccessUpdate(versionUpdateResponse);
                            } else {
                                mView.showErrorMsg("无版本更新");
                            }
                        }
                    }
                }));

    }

}
