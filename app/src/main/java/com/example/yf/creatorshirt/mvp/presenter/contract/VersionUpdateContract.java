package com.example.yf.creatorshirt.mvp.presenter.contract;

import com.example.yf.creatorshirt.mvp.model.VersionUpdateResponse;
import com.example.yf.creatorshirt.mvp.presenter.base.BasePresenter;
import com.example.yf.creatorshirt.mvp.view.BaseView;

/**
 * Created by yangfang on 2018/1/12.
 */

public interface VersionUpdateContract {
    interface VersionUpdateView extends BaseView {

        void showSuccessUpdate(VersionUpdateResponse versionUpdateResponse);
    }

    interface Presenter extends BasePresenter {

    }

}
