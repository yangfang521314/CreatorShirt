package com.example.yf.creatorshirt.widget;

import android.text.TextUtils;

import com.example.yf.creatorshirt.mvp.view.BaseView;

import io.reactivex.observers.ResourceObserver;
import retrofit2.HttpException;

/**
 * Created by yang on 12/06/2017.
 * Observable的观察者封装
 */

public abstract class CommonObserver<T> extends ResourceObserver<T> {

    private BaseView mView;
    private String mErrorMsg;
    private boolean isShowErrorState = true;

    protected CommonObserver(BaseView view) {
        this.mView = view;
    }

    protected CommonObserver(BaseView view, String errorMsg) {
        this.mView = view;
        this.mErrorMsg = errorMsg;
    }

    protected CommonObserver(BaseView view, boolean isShowErrorState) {
        this.mView = view;
        this.isShowErrorState = isShowErrorState;
    }

    protected CommonObserver(BaseView view, String errorMsg, boolean isShowErrorState) {
        this.mView = view;
        this.mErrorMsg = errorMsg;
        this.isShowErrorState = isShowErrorState;
    }

    @Override
    public void onComplete() {

    }

    /**
     * 数据发生错误时的处理
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        if (mView == null) {
            return;
        }
        if (mErrorMsg != null && !TextUtils.isEmpty(mErrorMsg)) {
            mView.showErrorMsg(mErrorMsg);
        } else if (e instanceof HttpException) {
            mView.showErrorMsg("数据加载失败");
        } else {
            mView.showErrorMsg(e.getMessage());
        }
        if (isShowErrorState) {
            mView.stateError();
        }
    }
}
