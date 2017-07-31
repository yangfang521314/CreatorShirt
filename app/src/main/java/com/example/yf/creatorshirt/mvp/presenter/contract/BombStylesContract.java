package com.example.yf.creatorshirt.mvp.presenter.contract;

import com.example.yf.creatorshirt.mvp.model.BombStyleBean;
import com.example.yf.creatorshirt.mvp.presenter.base.BasePresenter;
import com.example.yf.creatorshirt.mvp.view.BaseView;

import java.util.List;

/**
 * Created by yang on 31/07/2017.
 */

public interface BombStylesContract {
    interface BombView extends BaseView {
        void showSuccess(List<BombStyleBean> bombStyles);
    }

    interface Presenter extends BasePresenter<BombView> {
        void getBombData(int pagerNumber);
    }

}
