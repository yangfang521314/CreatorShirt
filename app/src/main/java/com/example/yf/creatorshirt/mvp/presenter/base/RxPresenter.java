package com.example.yf.creatorshirt.mvp.presenter.base;

import com.example.yf.creatorshirt.mvp.view.BaseView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by yang on 12/06/2017.
 */

public class RxPresenter<T extends BaseView> implements BasePresenter<T> {
    protected T mView;
    protected CompositeDisposable mCompositeDisposable;

    @Override
    public void attachView(T view) {
        mView = view;
    }

    /**
     * 绑定Disposable
     *
     * @param disposable
     */
    protected void addSubscribe(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    /**
     * 解除绑定
     */
    private void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    /**
     * 记得解除presenter的绑定
     * 考虑到生命周期
     *
     * @param view
     */
    @Override
    public void detachView(T view) {
        this.mView = null;
        unSubscribe();
    }


}
