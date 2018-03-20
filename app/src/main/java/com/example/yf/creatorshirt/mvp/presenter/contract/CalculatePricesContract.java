package com.example.yf.creatorshirt.mvp.presenter.contract;

import com.example.yf.creatorshirt.mvp.model.orders.ClothesSize;
import com.example.yf.creatorshirt.mvp.model.orders.OrderType;
import com.example.yf.creatorshirt.mvp.presenter.base.BasePresenter;
import com.example.yf.creatorshirt.mvp.view.BaseView;

import java.util.List;
import java.util.Map;

/**
 * Created by yangfang on 2018/1/21.
 */

public interface CalculatePricesContract {

    interface CalculatePricesView extends BaseView{

        void showSizeList(Map<String, List<ClothesSize>> list);

        void showPay(OrderType orderType);

        void showPrices(double discountPrice, double orderPrice);
    }

    interface  Presenter extends BasePresenter<CalculatePricesView>{

    }
}
