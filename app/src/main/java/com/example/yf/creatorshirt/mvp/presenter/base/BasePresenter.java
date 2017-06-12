package com.example.yf.creatorshirt.mvp.presenter.base;

import com.example.yf.creatorshirt.mvp.view.BaseView;

/**
 * Created by yang on 19/05/2017.
 */

public interface BasePresenter<T extends BaseView> {
    //关联
    void attach(T view);

    //释放
    void detach(T view);
}
