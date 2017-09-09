package com.example.yf.creatorshirt.mvp.view;

/**
 * Created by yang on 19/05/2017.
 * <p>
 * MVP模式的View；
 */

public interface BaseView {

    //请求数据失败
    void showErrorMsg(String msg);

    //加载时动作
    void showLoading();

    void hideLoading();

    //加载出错
    void stateError();
}
