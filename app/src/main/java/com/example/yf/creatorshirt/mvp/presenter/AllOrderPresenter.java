package com.example.yf.creatorshirt.mvp.presenter;

import android.util.Log;

import com.example.yf.creatorshirt.common.manager.UserInfoManager;
import com.example.yf.creatorshirt.http.DataManager;
import com.example.yf.creatorshirt.http.HttpResponse;
import com.example.yf.creatorshirt.mvp.model.orders.SaveOrderInfo;
import com.example.yf.creatorshirt.mvp.presenter.base.RxPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.AllOrderContract;
import com.example.yf.creatorshirt.utils.GsonUtils;
import com.example.yf.creatorshirt.utils.RxUtils;
import com.example.yf.creatorshirt.widget.CommonSubscriber;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import static android.content.ContentValues.TAG;

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
        if (UserInfoManager.getInstance().getLoginResponse().getUserInfo().getMobile() != null) {
            keyValues.put("partner", String.valueOf(UserInfoManager.getInstance().getLoginResponse().getUserInfo().getMobile()));
            addSubscribe(manager.requestMyOrder(UserInfoManager.getInstance().getToken(), GsonUtils.getGson(keyValues))
                    .compose(RxUtils.<HttpResponse<List<SaveOrderInfo>>>rxSchedulerHelper())
                    .compose(RxUtils.<List<SaveOrderInfo>>handleResult())
                    .subscribeWith(new CommonSubscriber<List<SaveOrderInfo>>(mView) {
                        @Override
                        public void onNext(List<SaveOrderInfo> myOrderInfo) {
                            if (myOrderInfo != null && myOrderInfo.size() != 0) {
                                mView.showSuccess(myOrderInfo);

                            }else {
                                mView.showErrorMsg("没有数据");
                            }
                        }
                    })
            );
        }

    }

}
