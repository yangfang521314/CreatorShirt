package com.example.yf.creatorshirt.mvp.presenter.contract;

import com.example.yf.creatorshirt.mvp.presenter.base.BasePresenter;
import com.example.yf.creatorshirt.mvp.view.BaseView;

/**
 * Created by yangfang on 2017/9/4.
 */

public interface EditUserInfoContract {

    interface EditUserInfoView extends BaseView {

    }

    interface Presenter extends BasePresenter<EditUserInfoView> {
        void saveUserInfo();
    }
}
