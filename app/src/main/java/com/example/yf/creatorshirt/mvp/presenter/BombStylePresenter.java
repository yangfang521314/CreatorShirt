package com.example.yf.creatorshirt.mvp.presenter;

import com.example.yf.creatorshirt.http.DataManager;
import com.example.yf.creatorshirt.http.HttpResponse;
import com.example.yf.creatorshirt.mvp.model.BombStyleBean;
import com.example.yf.creatorshirt.mvp.presenter.base.RxPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.BombStylesContract;
import com.example.yf.creatorshirt.utils.RxUtils;
import com.example.yf.creatorshirt.widget.CommonSubscriber;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by yang on 31/07/2017.
 */

public class BombStylePresenter extends RxPresenter<BombStylesContract.BombView> implements BombStylesContract.Presenter {

    private DataManager dataManager;

    @Inject
    public BombStylePresenter(DataManager manager) {
        this.dataManager = manager;
    }

    @Override
    public void getBombData(int pagerNumber) {
        addSubscribe(dataManager.getBombData()
                .compose(RxUtils.<HttpResponse<List<BombStyleBean>>>rxSchedulerHelper())
                .compose(RxUtils.<List<BombStyleBean>>handleResult())
                .subscribeWith(new CommonSubscriber<List<BombStyleBean>>(mView, "数据获取失败") {
                    @Override
                    public void onNext(List<BombStyleBean> bombStyleBeen) {
                            mView.showSuccess(bombStyleBeen);
                    }
                }));

    }
}
