package com.example.yf.creatorshirt.mvp.presenter.contract;

import com.example.yf.creatorshirt.mvp.view.BaseView;

import java.io.File;

/**
 * Created by yangfang on 2017/10/12.
 */

public interface CommonAvatarContract {
    interface CommonAvatarView extends BaseView {
        void showSuccessAvatar(File cover);

    }
}
