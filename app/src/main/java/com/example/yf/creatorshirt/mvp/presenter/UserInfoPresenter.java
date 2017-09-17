package com.example.yf.creatorshirt.mvp.presenter;

import com.example.yf.creatorshirt.common.UserInfoManager;
import com.example.yf.creatorshirt.http.DataManager;
import com.example.yf.creatorshirt.http.HttpResponse;
import com.example.yf.creatorshirt.mvp.model.LoginBean;
import com.example.yf.creatorshirt.mvp.presenter.base.RxPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.UserInfoContract;
import com.example.yf.creatorshirt.utils.RxUtils;
import com.example.yf.creatorshirt.utils.SharedPreferencesUtil;
import com.example.yf.creatorshirt.widget.CommonSubscriber;

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
     *
     * @param token
     */
    @Override
    public void getUserInfo(String token) {
        addSubscribe(manager.getUserInfo(token)
                .compose(RxUtils.<HttpResponse<LoginBean>>rxSchedulerHelper())
                .compose(RxUtils.<LoginBean>handleResult())
                .subscribeWith(new CommonSubscriber<LoginBean>(mView, "更新用户信息失败") {
                    @Override
                    public void onNext(@NonNull LoginBean loginBean) {
                        if (loginBean == null) {
                            return;
                        }
                        SharedPreferencesUtil.saveUserId(loginBean.getUserInfo().getUserid());
                        SharedPreferencesUtil.saveUserToken(loginBean.getToken());
                        SharedPreferencesUtil.saveUserPhone(loginBean.getUserInfo().getMobile());
                        UserInfoManager.getInstance().setLoginSuccess(loginBean, loginBean.getUserInfo().getName(),
                                loginBean.getToken(), loginBean.getUserInfo().getMobile());
                        UserInfoManager.getInstance().updateLocalLoginResponse();
                        mView.showUserInfo(loginBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        //请求失败,销毁用户信息和Token
                        UserInfoManager.getInstance().logOut();
                    }
                }));


    }
}
