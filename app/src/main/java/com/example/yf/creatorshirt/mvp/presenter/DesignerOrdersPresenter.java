package com.example.yf.creatorshirt.mvp.presenter;

import com.example.yf.creatorshirt.http.DataManager;
import com.example.yf.creatorshirt.mvp.presenter.base.RxPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.DesignerOrdersContract;

import javax.inject.Inject;

/**
 * Created by yangfang on 2017/9/12.
 */

public class DesignerOrdersPresenter extends RxPresenter<DesignerOrdersContract.DesignerDesignView> implements
        DesignerOrdersContract.Presenter {
    private DataManager mDataManager;
    private int userID;
    private int pageIndex = 0;

    @Inject
    public DesignerOrdersPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;

    }

    @Override
    public void getTotalDesigner() {
//        Map<String, Integer> map = new HashMap<>();
//        map.put("desginUserId", userID);
//        map.put("pageIndex", pageIndex);
//        addSubscribe(mDataManager.getDesignOrders(GsonUtils.getGson(map))
//                .compose(RxUtils.<HttpResponse<OrderStyleBean>>rxSchedulerHelper())
//                .compose(RxUtils.<OrderStyleBean>handleResult())
//                .subscribeWith()
//        );
    }

    public void setUserId(int userId) {
        this.userID = userId;
    }
}
