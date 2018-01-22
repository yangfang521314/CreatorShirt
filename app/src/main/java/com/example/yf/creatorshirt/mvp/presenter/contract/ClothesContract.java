package com.example.yf.creatorshirt.mvp.presenter.contract;

import com.example.yf.creatorshirt.mvp.model.ClothesStyleBean;
import com.example.yf.creatorshirt.mvp.presenter.base.BasePresenter;
import com.example.yf.creatorshirt.mvp.view.BaseView;

/**
 * Created by yangfang on 2017/11/30.
 */

public interface ClothesContract  {

     interface  ClothesView extends BaseView{

         void showTotalClothes(ClothesStyleBean clothesStyleBean);
     }

    interface Presenter extends BasePresenter<ClothesView> {
        void getClothesVersion();
    }

}
