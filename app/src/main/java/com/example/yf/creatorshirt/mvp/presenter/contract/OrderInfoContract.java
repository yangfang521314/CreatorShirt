package com.example.yf.creatorshirt.mvp.presenter.contract;

import com.example.yf.creatorshirt.mvp.model.orders.OrderType;
import com.example.yf.creatorshirt.mvp.presenter.base.BasePresenter;
import com.example.yf.creatorshirt.mvp.view.BaseView;

/**
 * Created by yangfang on 2017/8/28.
 */

public interface OrderInfoContract {

    interface OrderInfoView extends BaseView {

        void showOrderId(OrderType orderType);

        void showPreExecute(String info);
    }

    interface Presenter extends BasePresenter<OrderInfoView> {

    }

}
