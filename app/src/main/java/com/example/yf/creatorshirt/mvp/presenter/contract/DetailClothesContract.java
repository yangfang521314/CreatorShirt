package com.example.yf.creatorshirt.mvp.presenter.contract;

import com.example.yf.creatorshirt.mvp.model.PraiseEntity;
import com.example.yf.creatorshirt.mvp.model.orders.OrderType;
import com.example.yf.creatorshirt.mvp.presenter.base.BasePresenter;
import com.example.yf.creatorshirt.mvp.view.BaseView;

/**
 * Created by yang on 07/08/2017.
 * address的control
 */

public interface DetailClothesContract {

    interface DetailClothesView extends BaseView {

        void showPraise(Integer integer);

        void addPraiseNum(PraiseEntity integer);

        void showSuccessOrder(OrderType orderType);
    }

    interface Presenter extends BasePresenter<DetailClothesView> {
        void requestOrdersPraise(int id);
    }
}
