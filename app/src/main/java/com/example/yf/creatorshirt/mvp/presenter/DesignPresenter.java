package com.example.yf.creatorshirt.mvp.presenter;

import android.util.Log;

import com.example.yf.creatorshirt.http.DataManager;
import com.example.yf.creatorshirt.http.HttpResponse;
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

    private Map<String, List<DesignBaseBean>> map = new HashMap<>();

    private DataManager manager;

    @Inject
    public DesignPresenter(DataManager manager) {
        this.manager = manager;
    }

    @Override
    public void getBaseData() {
        Log.e("TAG", "FFFFF" + "fffff");
        addSubscribe(manager.getBaseDesign()
                .compose(RxUtils.<HttpResponse<String>>rxSchedulerHelper())
                .compose(RxUtils.<String>handleResult())
                .map(new Function<String, JSONObject>() {
                    @Override
                    public JSONObject apply(@NonNull String value) throws Exception {
                        return new JSONObject(value);
                    }
                })
                .map(new Function<JSONObject, Map<String, List<DesignBaseBean>>>() {
                    @Override
                    public Map<String, List<DesignBaseBean>> apply(@NonNull JSONObject jsonObject) throws Exception {
                        if (jsonObject.has("M")) {
                            changeData(jsonObject, "M");
                        }
                        if (jsonObject.has("W")) {
                            changeData(jsonObject, "W");
                        }
                        return map;
                    }
                })

                .subscribeWith(new CommonSubscriber<Map<String, List<DesignBaseBean>>>(mView, "为什么") {
                    @Override
                    public void onNext(Map<String, List<DesignBaseBean>> stringListMap) {
                        mView.showBaseDesignSuccess(map);
                    }
                })
        );
    }

    private Map<String, List<DesignBaseBean>> changeData(@NonNull JSONObject jsonObject, String key) throws JSONException {
        JSONArray jsonM = jsonObject.getJSONArray(key);
        List<DesignBaseBean> bean = new ArrayList<>();
        DesignBaseBean designBean;
        for (int i = 0; i < jsonM.length(); i++) {
            designBean = new DesignBaseBean();
            JSONObject m = jsonM.getJSONObject(i);
            designBean.setBaseId(m.getString("baseId"));
            Log.e("TAG", "MMMM" + m.getString("baseId"));
            designBean.setBaseName(m.getString("baseName"));
            designBean.setGender(m.getString("Gender"));
            bean.add(i, designBean);
        }
        map.put(key, bean);
        return map;
    }
}
