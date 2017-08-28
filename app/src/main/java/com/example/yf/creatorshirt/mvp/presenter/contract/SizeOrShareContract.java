package com.example.yf.creatorshirt.mvp.presenter.contract;

import com.example.yf.creatorshirt.mvp.presenter.base.BasePresenter;
import com.example.yf.creatorshirt.mvp.view.BaseView;

/**
 * Created by yangfang on 2017/8/28.
 */

public interface SizeOrShareContract {

    interface SizeOrShareView extends BaseView {

    }

    interface Presenter extends BasePresenter<SizeOrShareView> {
        void sendOrderData(String jsonObject);
    }

}
