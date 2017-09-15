package com.example.yf.creatorshirt.mvp.presenter;

import com.example.yf.creatorshirt.common.UserInfoManager;
import com.example.yf.creatorshirt.http.DataManager;
import com.example.yf.creatorshirt.http.HttpResponse;
import com.example.yf.creatorshirt.mvp.model.PraiseEntity;
import com.example.yf.creatorshirt.mvp.presenter.base.RxPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.DetailClothesContract;
import com.example.yf.creatorshirt.utils.GsonUtils;
import com.example.yf.creatorshirt.utils.RxUtils;
import com.example.yf.creatorshirt.widget.CommonSubscriber;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by yang on 10/08/2017.
 */

public class DetailClothesPresenter extends RxPresenter<DetailClothesContract.DetailClothesView> implements DetailClothesContract.Presenter {

    private DataManager manager;

    @Inject
    public DetailClothesPresenter(DataManager manager) {
        this.manager = manager;
    }


    @Override
    public void requestOrdersPraise(int id) {
        Map<String, Integer> map = new HashMap<>();
        map.put("orderId", id);
        addSubscribe(manager.requestOrdersPraise(UserInfoManager.getInstance().getLoginResponse().getToken(),
                GsonUtils.getGson(map))
                .compose(RxUtils.<HttpResponse<Integer>>rxSchedulerHelper())
                .compose(RxUtils.<Integer>handleResult())
                .subscribeWith(new CommonSubscriber<Integer>(mView) {
                    @Override
                    public void onNext(Integer integer) {
                        mView.showPraise(integer);
                    }
                })
        );
    }

    public void OrderPraise(int id) {
        Map<String, Integer> map = new HashMap<>();
        map.put("orderId", id);
        addSubscribe(manager.OrderPraise(UserInfoManager.getInstance().getLoginResponse().getToken(),
                GsonUtils.getGson(map))
                .compose(RxUtils.<HttpResponse<PraiseEntity>>rxSchedulerHelper())
                .compose(RxUtils.<PraiseEntity>handleResult())
                .subscribeWith(new CommonSubscriber<PraiseEntity>(mView) {
                    @Override
                    public void onNext(PraiseEntity integer) {
                        mView.addPraiseNum(integer);
                    }
                })
        );
    }
}
