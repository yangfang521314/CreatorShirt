package com.example.yf.creatorshirt.widget;

import android.text.TextUtils;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.mvp.view.BaseView;
import com.example.yf.creatorshirt.utils.NetworkUtils;

import io.reactivex.subscribers.ResourceSubscriber;
import retrofit2.HttpException;

/**
 * Created by yang on 05/06/2017.
 * Flowable的观察者封装
 */

public abstract class CommonSubscriber<T> extends ResourceSubscriber<T> {
    private BaseView mView;
    private String mErrorMsg;
    private boolean isShowErrorState = true;

    protected CommonSubscriber(BaseView view) {
        this.mView = view;
    }

    protected CommonSubscriber(BaseView view, String errorMsg) {
        this.mView = view;
        this.mErrorMsg = errorMsg;
    }

    protected CommonSubscriber(BaseView view, boolean isShowErrorState) {
        this.mView = view;
        this.isShowErrorState = isShowErrorState;
    }

    protected CommonSubscriber(BaseView view, String errorMsg, boolean isShowErrorState) {
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
        if (!NetworkUtils.isNetWorkConnected()) {
            mView.showErrorMsg(App.getInstance().getString(R.string.no_net));
            return;
        }
        if (mErrorMsg != null && !TextUtils.isEmpty(mErrorMsg)) {
            mView.showErrorMsg(mErrorMsg);
        } else if (e instanceof HttpException) {
            mView.showErrorMsg(e.getMessage());
        } else {
            mView.showErrorMsg(App.getInstance().getString(R.string.load_failure));
        }
        if (isShowErrorState) {
            mView.stateError();
        }
    }
}
