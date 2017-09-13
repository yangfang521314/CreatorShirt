package com.example.yf.creatorshirt.mvp.presenter.contract;

import com.example.yf.creatorshirt.mvp.presenter.base.BasePresenter;
import com.example.yf.creatorshirt.mvp.view.BaseView;

/**
 * Created by yangfang on 2017/9/12.
 */

public interface DesignerOrdersContract {

    interface DesignerDesignView extends BaseView {

        void showSuccessData();

    }

    interface Presenter extends BasePresenter<DesignerDesignView> {
        void getTotalDesigner();
    }
}
