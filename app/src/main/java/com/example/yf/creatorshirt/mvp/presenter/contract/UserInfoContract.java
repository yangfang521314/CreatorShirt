package com.example.yf.creatorshirt.mvp.presenter.contract;

import com.example.yf.creatorshirt.mvp.model.bean.UserInfo;
import com.example.yf.creatorshirt.mvp.presenter.base.BasePresenter;
import com.example.yf.creatorshirt.mvp.view.BaseView;

/**
 * Created by yang on 28/07/2017.
 */

public interface UserInfoContract {

    interface UserView extends BaseView {

        void showUserInfo(UserInfo userInfo);
    }

    interface Presenter extends BasePresenter<UserView> {
        //获取用户信息
        void getUserInfo();
    }

}
