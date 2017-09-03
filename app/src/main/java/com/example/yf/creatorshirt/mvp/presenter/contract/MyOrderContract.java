package com.example.yf.creatorshirt.mvp.presenter.contract;

import com.example.yf.creatorshirt.mvp.presenter.base.BasePresenter;
import com.example.yf.creatorshirt.mvp.view.BaseView;

/**
 * Created by yangfang on 2017/9/1.
 */

public interface MyOrderContract {
    interface MyOrderView extends BaseView{


    }

    interface Presenter extends BasePresenter<MyOrderView>{
        void setOrderId(String orderId);

        void getOrdersData();
    }
}