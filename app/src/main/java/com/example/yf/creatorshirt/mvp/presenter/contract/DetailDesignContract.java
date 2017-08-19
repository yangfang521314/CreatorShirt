package com.example.yf.creatorshirt.mvp.presenter.contract;

import com.example.yf.creatorshirt.mvp.presenter.base.BasePresenter;
import com.example.yf.creatorshirt.mvp.view.BaseView;

/**
 * Created by yangfang on 2017/8/19.
 */

public interface DetailDesignContract {

    interface DetailDesignView extends BaseView {
    }

    interface Presenter extends BasePresenter<DetailDesignView> {
        void getDetailDesign(String gender, String type);
    }

}
