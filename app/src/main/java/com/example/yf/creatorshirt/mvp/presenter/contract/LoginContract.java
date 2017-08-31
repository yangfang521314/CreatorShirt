package com.example.yf.creatorshirt.mvp.presenter.contract;

import com.example.yf.creatorshirt.mvp.model.LoginBean;
import com.example.yf.creatorshirt.mvp.presenter.base.BasePresenter;
import com.example.yf.creatorshirt.mvp.view.BaseView;

/**
 * Created by yang on 12/06/2017.
 * 微信登录和手机登陆的基类view和presenter
 */

public interface LoginContract{
    interface LoginView extends BaseView {
        //登录成功的返回
        void LoginSuccess(LoginBean t);
    }

    interface Presenter extends BasePresenter<LoginView> {
        //手机登录
        void phoneLogin(String textString, String string);

        void setPhoneNumber(String textString);

        void setPhoneCode(String textString);

        //获取验证码
        void getVerifyCode();

        //微信登录
        void wenxinLogin();
    }

}
