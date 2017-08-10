package com.example.yf.creatorshirt.mvp.presenter.contract;

import com.example.yf.creatorshirt.mvp.model.design.DesignBaseBean;
import com.example.yf.creatorshirt.mvp.presenter.base.BasePresenter;
import com.example.yf.creatorshirt.mvp.view.BaseView;

import java.util.List;
import java.util.Map;

/**
 * Created by yang on 10/08/2017.
 */

public interface DesignBaseContract {

    interface DesignBaseView extends BaseView {
        void showBaseDesignSuccess(Map<String, List<DesignBaseBean>> designBean);
    }

    interface Presenter extends BasePresenter<DesignBaseView> {
        void getBaseData();
    }

}
