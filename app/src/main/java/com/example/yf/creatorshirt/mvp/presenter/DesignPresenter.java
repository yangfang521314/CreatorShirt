package com.example.yf.creatorshirt.mvp.presenter;

import com.example.yf.creatorshirt.http.DataManager;
import com.example.yf.creatorshirt.http.HttpResponse;
import com.example.yf.creatorshirt.mvp.model.basechoice.DesignBaseBean;
import com.example.yf.creatorshirt.mvp.model.basechoice.DesignBaseInfo;
import com.example.yf.creatorshirt.mvp.presenter.base.RxPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.DesignBaseContract;
import com.example.yf.creatorshirt.utils.RxUtils;
import com.example.yf.creatorshirt.widget.CommonSubscriber;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by yang on 10/08/2017.
 */

public class DesignPresenter extends RxPresenter<DesignBaseContract.DesignBaseView> implements DesignBaseContract.Presenter {

    private DataManager manager;

    @Inject
    public DesignPresenter(DataManager manager) {
        this.manager = manager;
    }

    @Override
    public void getBaseData() {
        addSubscribe(manager.getBaseDesign()
                .compose(RxUtils.<HttpResponse<DesignBaseInfo>>rxSchedulerHelper())
                .compose(RxUtils.<DesignBaseInfo>handleResult())
                .map(new Function<DesignBaseInfo, Map<String, List<DesignBaseBean>>>() {
                    @Override
                    public Map<String, List<DesignBaseBean>> apply(@NonNull DesignBaseInfo value) throws Exception {
                        Map<String, List<DesignBaseBean>> map = new HashMap<>();
                        map.put("m", value.getM());
                        map.put("w", value.getW());
                        return map;
                    }
                })
                .subscribeWith(new CommonSubscriber<Map<String, List<DesignBaseBean>>>(mView) {
                    @Override
                    public void onNext(Map<String, List<DesignBaseBean>> stringListMap) {
                        if (stringListMap != null) {
                            if (stringListMap.size() != 0) {
                                mView.showBaseDesignSuccess(stringListMap);
                            }
                        } else {
                            mView.showErrorMsg("没有数据");
                        }
                    }
                })
        );
    }

}
