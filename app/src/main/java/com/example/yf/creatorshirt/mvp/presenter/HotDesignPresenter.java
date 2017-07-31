package com.example.yf.creatorshirt.mvp.presenter;

import com.example.yf.creatorshirt.mvp.model.HotDesignsBean;
import com.example.yf.creatorshirt.http.DataManager;
import com.example.yf.creatorshirt.mvp.presenter.base.RxPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.HotDesignContract;
import com.example.yf.creatorshirt.utils.RxUtils;
import com.example.yf.creatorshirt.widget.CommonSubscriber;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by yang on 31/07/2017.
 */

public class HotDesignPresenter extends RxPresenter<HotDesignContract.HotDesignView>
        implements HotDesignContract.Presenter {
    private DataManager dataManager;

    @Inject
    public HotDesignPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void getHotDesign() {
        addSubscribe(dataManager.getHotDesign()
        .compose(RxUtils.<HotDesignsBean>rxSchedulerHelper())
                .map(new Function<HotDesignsBean, List<HotDesignsBean.HotDesign>>() {

                    @Override
                    public List<HotDesignsBean.HotDesign> apply(@NonNull HotDesignsBean hotDesignsBean) throws Exception {
                        return hotDesignsBean.getResult();
                    }
                })
                .subscribeWith(new CommonSubscriber<List<HotDesignsBean.HotDesign>>(mView, "请求失败") {
                    @Override
                    public void onNext(List<HotDesignsBean.HotDesign> hotDesigns) {
                        mView.showSuccess(hotDesigns);
                    }
                })
        );
    }
}
