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
        final RequestBody body = GsonUtils.getGson(map);
        addSubscribe(dataManager.getBombData(body)
                .compose(RxUtils.<HttpResponse<List<BombStyleBean>>>rxSchedulerHelper())
                .compose(RxUtils.<List<BombStyleBean>>handleResult())
                .subscribeWith(new CommonSubscriber<List<BombStyleBean>>(mView) {
                    @Override
                    public void onNext(List<BombStyleBean> bombStyleBeen) {
                        if (bombStyleBeen != null) {
                            if (bombStyleBeen.size() != 0) {
                                mView.showSuccess(bombStyleBeen);
                            } else {
                                mView.showErrorMsg("没有爆款数据");
                            }
                        } else {
                            mView.showErrorMsg("没有数据");
                        }
                    }
                }));

    }

    public void getMoreBombData() {
        Map<String, Integer> map = new HashMap<>();
        map.put("pageIndex", ++pageIndex);
        final RequestBody body = GsonUtils.getGson(map);
        addSubscribe(dataManager.getBombData(body)
                .compose(RxUtils.<HttpResponse<List<BombStyleBean>>>rxSchedulerHelper())
                .compose(RxUtils.<List<BombStyleBean>>handleResult())
                .subscribeWith(new CommonSubscriber<List<BombStyleBean>>(mView, false) {
                    @Override
                    public void onNext(List<BombStyleBean> bombStyleBeen) {
                        if (bombStyleBeen != null) {
                            if (bombStyleBeen.size() != 0) {
                                mView.showMoreSuccessData(bombStyleBeen);
                            }
                        } else {
                            mView.showErrorMsg("没有更多数据");
                        }
                    }
                }));
    }
}
