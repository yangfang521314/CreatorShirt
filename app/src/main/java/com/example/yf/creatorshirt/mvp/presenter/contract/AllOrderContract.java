package com.example.yf.creatorshirt.mvp.presenter.contract;

import com.example.yf.creatorshirt.mvp.model.orders.SaveOrderInfo;
import com.example.yf.creatorshirt.mvp.presenter.base.BasePresenter;
import com.example.yf.creatorshirt.mvp.view.BaseView;

import java.util.List;

/**
 * Created by yangfang on 2018/1/30.
 */

public interface AllOrderContract {
    interface AllOrderView extends BaseView {


        void showSuccess(List<SaveOrderInfo> myOrderInfo);
    }

    interface Presenter extends BasePresenter<AllOrderContract.AllOrderView> {

    }
}
