package com.example.yf.creatorshirt.mvp.presenter.contract;

import com.example.yf.creatorshirt.mvp.model.PraiseEntity;
import com.example.yf.creatorshirt.mvp.model.orders.OrderType;
import com.example.yf.creatorshirt.mvp.model.orders.TextureEntity;
import com.example.yf.creatorshirt.mvp.presenter.base.BasePresenter;
import com.example.yf.creatorshirt.mvp.view.BaseView;

import java.util.List;

/**
 * Created by yang on 07/08/2017.
 * address的control
 */

public interface DetailClothesContract {

    interface DetailClothesView extends BaseView {

        void showPraise(Integer integer);

        void addPraiseNum(PraiseEntity integer);

        void showSuccessOrder(OrderType orderType);

        void showSuccessTextUre(List<TextureEntity> textureEntity);
    }

    interface Presenter extends BasePresenter<DetailClothesView> {
        void requestOrdersPraise(int id);
    }
}
