package com.example.yf.creatorshirt.mvp.presenter;

import com.example.yf.creatorshirt.common.manager.UserInfoManager;
import com.example.yf.creatorshirt.http.DataManager;
import com.example.yf.creatorshirt.http.HttpResponse;
import com.example.yf.creatorshirt.mvp.model.MyOrderInfo;
import com.example.yf.creatorshirt.mvp.presenter.base.RxPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.AllOrderContract;
import com.example.yf.creatorshirt.utils.GsonUtils;
import com.example.yf.creatorshirt.utils.RxUtils;
import com.example.yf.creatorshirt.widget.CommonSubscriber;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by yangfang on 2018/1/30.
 */

public class AllOrderPresenter extends RxPresenter<AllOrderContract.AllOrderView> implements AllOrderContract.Presenter {
    DataManager manager;

    @Inject
    AllOrderPresenter(DataManager manager) {
        this.manager = manager;
    }

    public void getMyAllOrder() {
        Map<String, String> keyValues = new HashMap<>();
        if(UserInfoManager.getInstance().getLoginResponse().getUserInfo().getMobile() != null) {
            keyValues.put("partner", String.valueOf(UserInfoManager.getInstance().getLoginResponse().getUserInfo().getMobile()));
            addSubscribe(manager.requestMyOrder(UserInfoManager.getInstance().getToken(), GsonUtils.getGson(keyValues))
                    .compose(RxUtils.<HttpResponse<List<MyOrderInfo>>>rxSchedulerHelper())
                    .compose(RxUtils.<List<MyOrderInfo>>handleResult())
                    .subscribeWith(new CommonSubscriber<List<MyOrderInfo>>(mView) {
                        @Override
                        public void onNext(List<MyOrderInfo> myOrderInfo) {
                            if (myOrderInfo != null) {
                                mView.showSuccess(myOrderInfo);
                            }
                        }
                    })
            );
        }

    }

}
