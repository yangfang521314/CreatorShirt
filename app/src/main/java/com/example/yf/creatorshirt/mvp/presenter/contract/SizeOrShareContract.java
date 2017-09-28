package com.example.yf.creatorshirt.mvp.presenter.contract;

import com.example.yf.creatorshirt.mvp.model.orders.OrderType;
import com.example.yf.creatorshirt.mvp.model.orders.TextureEntity;
import com.example.yf.creatorshirt.mvp.presenter.base.BasePresenter;
import com.example.yf.creatorshirt.mvp.view.BaseView;

import java.util.List;

/**
 * Created by yangfang on 2017/8/28.
 */

public interface SizeOrShareContract {

    interface SizeOrShareView extends BaseView {

        void showSuccessData(OrderType s);

        void showShareSuccessData(OrderType s);

        void showTokenSuccess(String s);

        void showShareTokenSuccess(String s);

        void hidePopupWindow();

        void showSuccessTextUre(List<TextureEntity> textureEntity);
    }

    interface Presenter extends BasePresenter<SizeOrShareView> {

        //设置styleContex
        void setStyleContext(String s);
    }

}
