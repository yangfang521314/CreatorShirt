package com.example.yf.creatorshirt.mvp.presenter;

import com.example.yf.creatorshirt.http.DataManager;
import com.example.yf.creatorshirt.mvp.model.HotDesignsBean;
import com.example.yf.creatorshirt.mvp.presenter.base.RxPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.HotDesignContract;
import com.example.yf.creatorshirt.utils.GsonUtils;
import com.example.yf.creatorshirt.utils.RxUtils;
import com.example.yf.creatorshirt.utils.SharedPreferencesUtil;
import com.example.yf.creatorshirt.widget.CommonSubscriber;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.RequestBody;

/**
 * Created by yang on 31/07/2017.
 */

public class HotDesignPresenter extends RxPresenter<HotDesignContract.HotDesignView>
        implements HotDesignContract.Presenter {
    private DataManager dataManager;
    private int pageIndex = 0;//默认1

    @Inject
    public HotDesignPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void getHotDesign() {
        pageIndex = 0;
        Map<String, Integer> map = new HashMap<>();
        map.put("pageIndex", pageIndex);
        RequestBody body = GsonUtils.getGson(map);
        addSubscribe(dataManager.getHotDesign(SharedPreferencesUtil.getUserToken(), body)
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleResult())
                .subscribeWith(new CommonSubscriber<List<HotDesignsBean>>(mView) {
                    @Override
                    public void onNext(List<HotDesignsBean> hotDesigns) {
                        if (hotDesigns == null) {
                            mView.showErrorMsg("没有设计师");
                            return;
                        }
                        if (hotDesigns.size() == 0) {
                            mView.showErrorMsg("没有设计师");
                            return;
                        }
                        mView.showSuccess(hotDesigns);
                    }
                })
        );
    }

    public void loadMore() {
        Map<String, Integer> map = new HashMap<>();
        map.put("pageIndex", ++pageIndex);
        RequestBody body = GsonUtils.getGson(map);
        addSubscribe(dataManager.getHotDesign(SharedPreferencesUtil.getUserToken(), body)
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleResult())
                .subscribeWith(new CommonSubscriber<List<HotDesignsBean>>(mView) {
                    @Override
                    public void onNext(List<HotDesignsBean> hotDesigns) {
                        if (hotDesigns != null) {
                            if (hotDesigns.size() == 0) {
                                mView.showErrorMsg("没有更多数据");
                            } else {
                                mView.showMoreSuccess(hotDesigns);

                            }
                        }
                    }
                })
        );
    }
}
