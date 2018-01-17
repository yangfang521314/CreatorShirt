package com.example.yf.creatorshirt.mvp.presenter.contract;

import android.graphics.Bitmap;
import android.support.v4.util.ArrayMap;

import com.example.yf.creatorshirt.mvp.model.VersionStyle;
import com.example.yf.creatorshirt.mvp.model.detaildesign.DetailColorStyle;
import com.example.yf.creatorshirt.mvp.model.detaildesign.StyleBean;
import com.example.yf.creatorshirt.mvp.presenter.base.BasePresenter;
import com.example.yf.creatorshirt.mvp.view.BaseView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangfang on 2017/8/19.
 */

public interface DetailDesignContract {

    interface DetailDesignView extends BaseView {

        void showSuccessData(List<StyleBean> newList, List<String> clotheKey, ArrayMap<String, List<VersionStyle>> mColorData, ArrayMap<String, List<DetailColorStyle>> mPatternData, ArrayMap<String, List<DetailColorStyle>> mMaskData, ArrayMap<String, List<DetailColorStyle>> mSignatureData);


        void showMaskView(Bitmap maskBitmap);
    }

    interface Presenter extends BasePresenter<DetailDesignView> {
        void getDetailDesign(ArrayList<VersionStyle> mClothesList, boolean b);

    }

}
