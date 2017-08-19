package com.example.yf.creatorshirt.mvp.presenter;

import android.util.Log;

import com.example.yf.creatorshirt.http.DataManager;
import com.example.yf.creatorshirt.http.HttpResponse;
import com.example.yf.creatorshirt.mvp.model.DesignBase;
import com.example.yf.creatorshirt.mvp.model.design.DesignBaseBean;
import com.example.yf.creatorshirt.mvp.presenter.base.RxPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.DesignBaseContract;
import com.example.yf.creatorshirt.utils.RxUtils;
import com.example.yf.creatorshirt.widget.CommonSubscriber;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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
                .compose(RxUtils.<HttpResponse<DesignBase>>rxSchedulerHelper())
                .compose(RxUtils.<DesignBase>handleResult())
                .map(new Function<DesignBase, Map<String, List<DesignBaseBean>>>() {
                    @Override
                    public Map<String, List<DesignBaseBean>> apply(@NonNull DesignBase value) throws Exception {
                        Map<String,List<DesignBaseBean>> map = new HashMap<>();
                        map.put("m",value.getM());
                        map.put("w",value.getW());
                        return map;
                    }
                })
                .subscribeWith(new CommonSubscriber<Map<String, List<DesignBaseBean>>>(mView, "为什么") {
                    @Override
                    public void onNext(Map<String, List<DesignBaseBean>> stringListMap) {
                        mView.showBaseDesignSuccess(stringListMap);
                    }
                })
        );
    }

}
