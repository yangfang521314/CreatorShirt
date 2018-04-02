package com.example.yf.creatorshirt.mvp.presenter.contract;

import android.graphics.RectF;

import com.example.yf.creatorshirt.mvp.presenter.base.BasePresenter;
import com.example.yf.creatorshirt.mvp.view.BaseView;

/**
 * Created by yangfang on 2018/4/2.
 */

public interface MotionEventContract {
    interface MotionEventView extends BaseView {

        void applyMatrix();

        void setScaleMatrix(float scaleFactor, float scaleFactor1, float x, float y);

        void postRotate(float degree);

        void setCurrentScale(float mScaleFactor);

        void refreshImageRect();

        RectF getImageRect();

        void setTransMartix(float dx, float dy);

        void mapVectors(float[] xAxis);

        float getScaleCurrentF();
    }

    interface Presenter extends BasePresenter<MotionEventView> {

    }
}
