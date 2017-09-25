package com.example.yf.creatorshirt.mvp.presenter.contract;

import com.example.yf.creatorshirt.mvp.presenter.base.BasePresenter;
import com.example.yf.creatorshirt.mvp.view.BaseView;

/**
 * Created by yang on 07/08/2017.
 * address的control
 */

public interface AddressEditContract {

    interface AddressEditView extends BaseView {

        void SuccessSaveAddress(String message);

    }

    interface Presenter extends BasePresenter<AddressEditView> {
        //保存地址
        void saveAddressData();
        //更新地址
        void setUpdateAddress();
    }
}
