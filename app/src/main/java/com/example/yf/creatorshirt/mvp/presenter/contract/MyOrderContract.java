package com.example.yf.creatorshirt.mvp.presenter.contract;

import com.example.yf.creatorshirt.mvp.model.orders.OrderStyleBean;
import com.example.yf.creatorshirt.mvp.presenter.base.BasePresenter;
import com.example.yf.creatorshirt.mvp.view.BaseView;

/**
 * Created by yangfang on 2017/9/1.
 */

public interface MyOrderContract {
    interface MyOrderView extends BaseView{


        void showSuccessOrderData(OrderStyleBean orderStyleBean);

        void showPayOrder(String value);
    }

    interface Presenter extends BasePresenter<MyOrderView>{
        void setOrderId(String orderId);

        void getOrdersData();
    }
}
