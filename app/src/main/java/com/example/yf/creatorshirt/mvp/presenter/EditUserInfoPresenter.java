package com.example.yf.creatorshirt.mvp.presenter;

import com.example.yf.creatorshirt.http.DataManager;
import com.example.yf.creatorshirt.mvp.presenter.base.RxPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.EditUserInfoContract;

import javax.inject.Inject;

/**
 * Created by yangfang on 2017/9/4.
 */

public class EditUserInfoPresenter extends RxPresenter<EditUserInfoContract.EditUserInfoView> implements EditUserInfoContract.Presenter {
    private DataManager manager;

    @Inject
    public EditUserInfoPresenter(DataManager manager) {
        this.manager = manager;

    }

    @Override
    public void saveUserInfo() {

    }
}
