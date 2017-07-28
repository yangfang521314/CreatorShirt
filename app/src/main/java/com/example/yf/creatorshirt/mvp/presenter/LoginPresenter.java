package com.example.yf.creatorshirt.mvp.presenter;

import com.example.yf.creatorshirt.mvp.model.bean.LoginBean;
import com.example.yf.creatorshirt.mvp.model.db.DataManager;
import com.example.yf.creatorshirt.mvp.presenter.base.RxPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.LoginContract;
import com.example.yf.creatorshirt.utils.RxUtils;
import com.example.yf.creatorshirt.widget.CommonObserver;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;

/**
 * Created by yang on 12/06/2017.
 * 微信登录和手机登录方法类
 */

public class LoginPresenter extends RxPresenter<LoginContract.LoginView> implements LoginContract.Presenter {
    private DataManager mDataManager;
    private String phone;
    private String password;

    @Inject
    public LoginPresenter(DataManager manager) {
        this.mDataManager = manager;
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        phone = phoneNumber;
    }

    @Override
    public void setPhoneCode(String code) {
        this.password = password;
    }

    /**
     * 手机号码登录
     */
    @Override
    public void phoneLogin() {
        addSubscribe(mDataManager.login(phone, password)
                .compose(RxUtils.<LoginBean>rxObScheduleHelper())
                .subscribeWith(new CommonObserver<LoginBean>(mView, "登录失败") {
                    @Override
                    public void onNext(@NonNull LoginBean loginBean) {
                        mView.LoginSuccess(loginBean);
                    }
                }));
    }

    /**
     * 获取验证码
     */
    @Override
    public void getVerifyCode() {
        mDataManager.getVerifyCode(phone);
    }

    /**
     * 微信登录
     */
    @Override
    public void wenxinLogin() {

    }
}
