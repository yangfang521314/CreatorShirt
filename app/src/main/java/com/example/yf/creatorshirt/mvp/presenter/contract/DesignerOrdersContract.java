package com.example.yf.creatorshirt.mvp.presenter.contract;

import com.example.yf.creatorshirt.mvp.model.BombStyleBean;
import com.example.yf.creatorshirt.mvp.presenter.base.BasePresenter;
import com.example.yf.creatorshirt.mvp.view.BaseView;

import java.util.List;

/**
 * Created by yangfang on 2017/9/12.
 */

public interface DesignerOrdersContract {

    interface DesignerDesignView extends BaseView {


        void showSuccessData(List<BombStyleBean> orderStyleBeen);

        void showMoreData(List<BombStyleBean> orderStyleBeen);

        void showRefreshData(List<BombStyleBean> orderStyleBeen);

        void showUpdateZero(int i);
    }

    interface Presenter extends BasePresenter<DesignerDesignView> {
        void getTotalDesigner();
    }
}
