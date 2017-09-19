package com.example.yf.creatorshirt.mvp.presenter.contract;

import com.example.yf.creatorshirt.mvp.model.AddressBean;
import com.example.yf.creatorshirt.mvp.presenter.base.BasePresenter;
import com.example.yf.creatorshirt.mvp.view.BaseView;

import java.util.List;

/**
 * Created by yang on 07/08/2017.
 * address的control
 */

public interface AddressContract {

    interface AddressView extends BaseView {
        void showSuccess(List<AddressBean> addressBean);

        void SuccessSaveAddress(String message);


        void successDefaultAddress(String flag);
    }

    interface Presenter extends BasePresenter<AddressView> {
        void getAddressData();
    }
}
