package com.example.yf.creatorshirt.mvp.presenter.contract;

import com.example.yf.creatorshirt.mvp.model.orders.OrderStyleBean;
import com.example.yf.creatorshirt.mvp.presenter.base.BasePresenter;
import com.example.yf.creatorshirt.mvp.view.BaseView;

import java.util.List;

/**
 * Created by yangfang on 2017/9/12.
 */

public interface DesignerOrdersContract {

    interface DesignerDesignView extends BaseView {


        void showSuccessData(List<OrderStyleBean> orderStyleBeen);
    }

    interface Presenter extends BasePresenter<DesignerDesignView> {
        void getTotalDesigner();
    }
}
