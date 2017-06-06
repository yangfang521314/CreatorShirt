package com.example.yf.creatorshirt.mvp.presenter;

import android.util.Log;

import com.example.yf.creatorshirt.mvp.model.bean.GirlData;
import com.example.yf.creatorshirt.mvp.model.bean.PhotoGirl;
import com.example.yf.creatorshirt.mvp.model.db.DataManager;
import com.example.yf.creatorshirt.utils.RxUtils;
import com.example.yf.creatorshirt.widget.CommonSubscriber;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * Created by yang on 27/05/2017.
 */

public class GirlPresenterImpl implements BasePresenter<GirlContract.GirlView>, GirlContract.GirlPresenter {
    GirlContract.GirlView mView;
    private DataManager mDataManager;
    public static final int NUM_OF_PAGE = 20;

    @Override
    public void attach(GirlContract.GirlView view) {
        mView = view;
    }

    @Inject
    public GirlPresenterImpl(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void getGirlData() {
        Disposable disposable = mDataManager.getPhotoList(20, 1)
                .map(new Function<GirlData, List<PhotoGirl>>() {
                    @Override
                    public List<PhotoGirl> apply(@NonNull GirlData girlData) throws Exception {
                        Log.e("TAG", "FUCK " + girlData.getResults().size());
                        return girlData.getResults();
                    }
                })
                .compose(RxUtils.<List<PhotoGirl>>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<List<PhotoGirl>>(mView) {
                    @Override
                    public void onNext(List<PhotoGirl> photoGirls) {
                        mView.showData(photoGirls);
                    }
                });
    }
}
