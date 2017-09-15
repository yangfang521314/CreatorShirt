package com.example.yf.creatorshirt.mvp.presenter;

import android.util.Log;

import com.example.yf.creatorshirt.http.DataManager;
import com.example.yf.creatorshirt.http.HttpResponse;
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
                .compose(RxUtils.<HttpResponse<List<HotDesignsBean>>>rxSchedulerHelper())
                .compose(RxUtils.<List<HotDesignsBean>>handleResult())
                .subscribeWith(new CommonSubscriber<List<HotDesignsBean>>(mView) {
                    @Override
                    public void onNext(List<HotDesignsBean> hotDesigns) {
                        if (hotDesigns == null) {
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
                .compose(RxUtils.<HttpResponse<List<HotDesignsBean>>>rxSchedulerHelper())
                .compose(RxUtils.<List<HotDesignsBean>>handleResult())
                .subscribeWith(new CommonSubscriber<List<HotDesignsBean>>(mView, "请求失败") {
                    @Override
                    public void onNext(List<HotDesignsBean> hotDesigns) {
                        if (hotDesigns == null) {
                            return;
                        }
                        mView.showMoreSuccess(hotDesigns);
                    }
                })
        );
    }
}
