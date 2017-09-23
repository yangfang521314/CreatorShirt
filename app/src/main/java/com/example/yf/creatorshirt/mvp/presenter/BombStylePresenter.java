package com.example.yf.creatorshirt.mvp.presenter;

import com.example.yf.creatorshirt.http.DataManager;
import com.example.yf.creatorshirt.http.HttpResponse;
import com.example.yf.creatorshirt.mvp.model.BombStyleBean;
import com.example.yf.creatorshirt.mvp.presenter.base.RxPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.BombStylesContract;
import com.example.yf.creatorshirt.utils.GsonUtils;
import com.example.yf.creatorshirt.utils.RxUtils;
import com.example.yf.creatorshirt.widget.CommonSubscriber;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.RequestBody;

/**
 * Created by yang on 31/07/2017.
 */

public class BombStylePresenter extends RxPresenter<BombStylesContract.BombView> implements BombStylesContract.Presenter {

    private DataManager dataManager;
    private int pageIndex = 0;

    @Inject
    public BombStylePresenter(DataManager manager) {
        this.dataManager = manager;
    }

    @Override
    public void getBombData() {
        pageIndex = 0;
        Map<String, Integer> map = new HashMap<>();
        map.put("pageIndex", 0);
        RequestBody body = GsonUtils.getGson(map);
        addSubscribe(dataManager.getBombData(body)
                .compose(RxUtils.<HttpResponse<List<BombStyleBean>>>rxSchedulerHelper())
                .compose(RxUtils.<List<BombStyleBean>>handleResult())
                .subscribeWith(new CommonSubscriber<List<BombStyleBean>>(mView) {
                    @Override
                    public void onNext(List<BombStyleBean> bombStyleBeen) {
                        mView.showSuccess(bombStyleBeen);
                    }
                }));

    }

    public void getMoreBombData() {
        Map<String, Integer> map = new HashMap<>();
        map.put("pageIndex", ++pageIndex);
        RequestBody body = GsonUtils.getGson(map);
        addSubscribe(dataManager.getBombData(body)
                .compose(RxUtils.<HttpResponse<List<BombStyleBean>>>rxSchedulerHelper())
                .compose(RxUtils.<List<BombStyleBean>>handleResult())
                .subscribeWith(new CommonSubscriber<List<BombStyleBean>>(mView, "加载更多失败",false) {
                    @Override
                    public void onNext(List<BombStyleBean> bombStyleBeen) {
                        mView.showMoreSuccessData(bombStyleBeen);
                    }
                }));
    }
}
