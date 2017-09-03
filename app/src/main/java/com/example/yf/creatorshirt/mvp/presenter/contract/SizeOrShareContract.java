package com.example.yf.creatorshirt.mvp.presenter.contract;

import com.example.yf.creatorshirt.mvp.model.orders.OrderType;
import com.example.yf.creatorshirt.mvp.presenter.base.BasePresenter;
import com.example.yf.creatorshirt.mvp.view.BaseView;

/**
 * Created by yangfang on 2017/8/28.
 */

public interface SizeOrShareContract {

    interface SizeOrShareView extends BaseView {

        void showSuccessData(OrderType s);

    }

    interface Presenter extends BasePresenter<SizeOrShareView> {

        //设置styleContex
        void setStyleContext(String s);
    }

}
