package com.example.yf.creatorshirt.mvp.presenter;

import com.example.yf.creatorshirt.http.DataManager;
import com.example.yf.creatorshirt.mvp.presenter.base.RxPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.DetailDesignContract;

import javax.inject.Inject;

/**
 * Created by yangfang on 2017/8/19.
 */

public class DetailDesignPresenter extends RxPresenter<DetailDesignContract.DetailDesignView>
        implements DetailDesignContract.Presenter {

    private DataManager manager;

    @Inject
    public DetailDesignPresenter(DataManager dataManager) {
    }

    @Override
    public void getDetailDesign(String gender, String type) {
        addSubscribe(manager.getDetailDesign(gender,type));
    }
}
