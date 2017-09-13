package com.example.yf.creatorshirt.mvp.presenter;

import com.example.yf.creatorshirt.http.DataManager;
import com.example.yf.creatorshirt.http.HttpResponse;
import com.example.yf.creatorshirt.mvp.model.UserInfo;
import com.example.yf.creatorshirt.mvp.presenter.base.RxPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.UserInfoContract;
import com.example.yf.creatorshirt.utils.RxUtils;
import com.example.yf.creatorshirt.utils.SharedPreferencesUtil;
import com.example.yf.creatorshirt.widget.CommonObserver;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;

/**
 * Created by yang on 28/07/2017.
 */

public class UserInfoPresenter extends RxPresenter<UserInfoContract.UserView> implements UserInfoContract.Presenter {
    private DataManager manager;

    @Inject
    public UserInfoPresenter(DataManager manager) {
        this.manager = manager;
    }

    /**
     * 获取用户信息
     */
    @Override
    public void getUserInfo() {
            addSubscribe(manager.getUserInfo(SharedPreferencesUtil.getUserToken())
                    .compose(RxUtils.<HttpResponse<UserInfo>>rxObScheduleHelper())
                    .compose(RxUtils.<UserInfo>handleObservableResult())
                    .subscribeWith(new CommonObserver<UserInfo>(mView, "请求失败") {
                        @Override
                        public void onNext(@NonNull UserInfo userInfo) {
                            if (userInfo == null) {
                                return;
                            }
                            mView.showUserInfo(userInfo);
                        }
                    }));
    }
}
