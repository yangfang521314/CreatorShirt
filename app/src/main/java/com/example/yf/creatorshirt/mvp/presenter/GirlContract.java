package com.example.yf.creatorshirt.mvp.presenter;

import com.example.yf.creatorshirt.mvp.model.bean.GirlData;
import com.example.yf.creatorshirt.mvp.view.BaseView;

import java.util.List;

/**
 * Created by yang on 19/05/2017.
 * 妹纸数据的    presenter 和Views
 */

public interface GirlContract {
    interface GirlView extends BaseView {
        void showData(List<GirlData> list);
    }

    interface GirlPresenter extends BasePresenter<GirlView> {
        void getGirlData();
    }
}
