package com.example.yf.creatorshirt.mvp.presenter;

import com.example.yf.creatorshirt.mvp.view.BaseView;

/**
 * Created by yang on 19/05/2017.
 */

public interface BasePresenter<T extends BaseView> {
    void attach(T view);
}
