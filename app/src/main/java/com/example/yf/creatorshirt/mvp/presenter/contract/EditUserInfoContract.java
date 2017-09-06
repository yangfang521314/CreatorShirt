package com.example.yf.creatorshirt.mvp.presenter.contract;

import com.example.yf.creatorshirt.mvp.presenter.base.BasePresenter;
import com.example.yf.creatorshirt.mvp.view.BaseView;

import java.io.File;

/**
 * Created by yangfang on 2017/9/4.
 */

public interface EditUserInfoContract {

    interface EditUserInfoView extends BaseView {

        void showSuccessImage(String userAvatar);
    }

    interface Presenter extends BasePresenter<EditUserInfoView> {
        void saveUserInfo();

        void setImageFile(File file);

        void saveUserAvatar();

        void setUserName(String userName);
    }
}
