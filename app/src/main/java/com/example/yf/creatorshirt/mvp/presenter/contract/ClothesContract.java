package com.example.yf.creatorshirt.mvp.presenter.contract;

import android.support.v4.util.ArrayMap;

import com.example.yf.creatorshirt.mvp.model.VersionStyle;
import com.example.yf.creatorshirt.mvp.presenter.base.BasePresenter;
import com.example.yf.creatorshirt.mvp.view.BaseView;

import java.util.List;

/**
 * Created by yangfang on 2017/11/30.
 */

public interface ClothesContract  {

     interface  ClothesView extends BaseView{


         void showTotalClothes(ArrayMap<String, List<VersionStyle>> totalManMap, ArrayMap<String, List<VersionStyle>> totalWomanMap, List<String> mListVerName);
     }

    interface Presenter extends BasePresenter<ClothesView> {
        void getClothesVersion();
    }

}
