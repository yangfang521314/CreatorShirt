package com.example.yf.creatorshirt.mvp.presenter;

import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;

import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.mvp.presenter.base.RxPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.MotionEventContract;
import com.example.yf.creatorshirt.utils.Constants;
import com.example.yf.creatorshirt.utils.DisplayUtil;

import static org.greenrobot.eventbus.EventBus.TAG;

/**
 * Created by yangfang on 2018/4/2.
 */

public class MotionEventPresenter extends RxPresenter<MotionEventContract.MotionEventView> implements MotionEventContract.Presenter {
    private PointF mMidPointF = new PointF();//初始化的中间点
    private PointF mLastMidPointF = new PointF();//记录上一次的中间点
    private boolean mCanScale;//是否缩放
    private boolean mCanRotate;//是否旋转
    private boolean mCanDrag;//是否移动
    private PointF mLastPoint1 = new PointF();//上一次触摸的2个点
    private PointF mLastPoint2 = new PointF();
    private PointF mLastVector = new PointF();//上次图片的角度
    private PointF mCurrentVector = new PointF();//当前角度
    private PointF scaleCenter = new PointF();//中心点坐标
    private PointF mCurrentPoint1 = new PointF();//本次触摸的2个点
    private PointF mCurrentPoint2 = new PointF();
    private static final int HORIZONTAL = 0;
    private static final int VERTICAL = 1;
    private float mHorizontalMinScaleFactor = 0.1f;
    private float mVerticalMinScaleFactor = 0.1f;
    private float mMaxScaleFactor = 1.0f;//最大放大倍数
    private float[] xAxis = new float[]{1f, 0f};
    private boolean mOpenScaleRevert = true; // 是否开启旋转回弹
    private boolean mOpenTranslateRevert = false; // 是否开启旋转回弹
    private boolean isFlag;

    public boolean onTouchEvent(MotionEvent event) {

        PointF midPoint = getMidPointOfFinger(event);//按下的坐标
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                mLastMidPointF.set(midPoint);
                mCanDrag = false;
                mCanRotate = false;
                mCanScale = false;
                if (event.getPointerCount() == 2) {
                    //两个指触摸时的接触点
                    mCanScale = true;
                    mLastPoint1.set(event.getX(0), event.getY(0));
                    mLastPoint2.set(event.getX(1), event.getY(1));
                    mCanRotate = true;
                    mLastVector.set(event.getX(1) - event.getX(0),
                            event.getY(1) - event.getY(0));
                    mOpenTranslateRevert = false;
                } else if (event.getPointerCount() == 1) {
                    mCanDrag = true;
                    mOpenTranslateRevert = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isFlag) {
                    if (mCanDrag) translate(midPoint);
                    if (mCanScale) scale(event);
                    if (mCanRotate) rotate(event);
                    if (mCanDrag || mCanScale || mCanRotate) {
                        mView.applyMatrix();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mOpenScaleRevert) {
                    checkScale();
                }
                if (mOpenTranslateRevert) {
                    checkTrans();
                }
                mView.applyMatrix();
            case MotionEvent.ACTION_POINTER_UP:
                mCanScale = false;
                mCanDrag = false;
                mCanRotate = false;
                break;
        }
        if (isFlag) {
            return true;
        }
        return false;

    }

    /**
     * 放大
     *
     * @param event
     */
    private void scale(MotionEvent event) {
        PointF scaleCenter = getScaleCenter();
        // 初始化当前两指触点
        mCurrentPoint1.set(event.getX(0), event.getY(0));
        mCurrentPoint2.set(event.getX(1), event.getY(1));
        // 计算缩放比例
        float scaleFactor = distance(mCurrentPoint1, mCurrentPoint2)
                / distance(mLastPoint1, mLastPoint2);
        // 更新当前图片的缩放比例
//        float scaleCurrentF = mView.getScaleCurrentF();
        int imgOrientation = imgOrientation();
        // 超过设置的上限或下限则回弹到设置的限制值
        float mScaleFactor = mView.getScaleCurrentF();
        // 除以当前图片缩放比例mScaleFactor，postScale()方法执行后的图片的缩放比例即为被除数大小
        if (imgOrientation == HORIZONTAL
                && mView.getScaleCurrentF() < mHorizontalMinScaleFactor) {
            scaleFactor = mHorizontalMinScaleFactor / mScaleFactor;
        } else if (imgOrientation == VERTICAL
                && mScaleFactor < mVerticalMinScaleFactor) {
            scaleFactor = mVerticalMinScaleFactor / mScaleFactor;
        } else if (mScaleFactor > mMaxScaleFactor) {
            scaleFactor = mMaxScaleFactor / mScaleFactor;
        }
        mScaleFactor *= scaleFactor;
        mView.setCurrentScale(mScaleFactor);
        mView.setScaleMatrix(scaleFactor, scaleFactor,
                scaleCenter.x, scaleCenter.y);
        mLastPoint1.set(mCurrentPoint1);
        mLastPoint2.set(mCurrentPoint2);

    }


    private void checkScale() {
        PointF scaleCenter = getScaleCenter();
        float scaleFactor = 1.0f;
        // 获取图片当前是水平还是垂直
        int imgOrientation = imgOrientation();
        // 超过设置的上限或下限则回弹到设置的限制值
        float mScaleFactor = mView.getScaleCurrentF();
        // 除以当前图片缩放比例mScaleFactor，postScale()方法执行后的图片的缩放比例即为被除数大小
        if (imgOrientation == HORIZONTAL
                && mView.getScaleCurrentF() < mHorizontalMinScaleFactor) {
            scaleFactor = mHorizontalMinScaleFactor / mScaleFactor;
        } else if (imgOrientation == VERTICAL
                && mScaleFactor < mVerticalMinScaleFactor) {
            scaleFactor = mVerticalMinScaleFactor / mScaleFactor;
        } else if (mScaleFactor > mMaxScaleFactor) {
            scaleFactor = mMaxScaleFactor / mScaleFactor;
        }

        mView.setScaleMatrix(scaleFactor, scaleFactor, scaleCenter.x, scaleCenter.y);
        mScaleFactor *= scaleFactor;
        mView.setCurrentScale(mScaleFactor);
    }

    /**
     * 旋转
     *
     * @param event
     */
    private void rotate(MotionEvent event) {
        // 计算当前两指触点所表示的向量
        mCurrentVector.set(event.getX(1) - event.getX(0),
                event.getY(1) - event.getY(0));
        // 获取旋转角度
        float degree = getRotateDegree(mLastVector, mCurrentVector);
        mView.postRotate(degree);
        mLastVector.set(mCurrentVector);
    }

    /**
     * 使用Math#atan2(double y, double x)方法求上次触摸事件两指所示向量与x轴的夹角，
     * 再求出本次触摸事件两指所示向量与x轴夹角，最后求出两角之差即为图片需要转过的角度
     *
     * @param lastVector    上次触摸事件两指间连线所表示的向量
     * @param currentVector 本次触摸事件两指间连线所表示的向量
     * @return 两向量夹角，单位“度”，顺时针旋转时为正数，逆时针旋转时返回负数
     */
    private float getRotateDegree(PointF lastVector, PointF currentVector) {
        //上次触摸事件向量与x轴夹角
        double lastRad = Math.atan2(lastVector.y, lastVector.x);
        //当前触摸事件向量与x轴夹角
        double currentRad = Math.atan2(currentVector.y, currentVector.x);
        // 两向量与x轴夹角之差即为需要旋转的角度
        double rad = currentRad - lastRad;
        //“弧度”转“度”
        return (float) Math.toDegrees(rad);
    }

    /**
     * 判断图片当前是水平还是垂直
     *
     * @return 水平则返回 {@code HORIZONTAL}，垂直则返回 {@code VERTICAL}
     */
    private int imgOrientation() {
        float degree = Math.abs(getCurrentRotateDegree());
        int orientation = HORIZONTAL;
        if (degree > 45f && degree <= 135f) {
            orientation = VERTICAL;
        }
        return orientation;
    }


    /**
     * 平移
     *
     * @param midCurrentPoint
     */
    private void translate(PointF midCurrentPoint) {
        float dx = midCurrentPoint.x - mLastMidPointF.x;
        float dy = midCurrentPoint.y - mLastMidPointF.y;
        float limitWidth = 150 + (DisplayUtil.getScreenW(App.getInstance()) - Constants.WIDTH_MASK) / 2;
        float limitHeight = DisplayUtil.getScreenW(App.getInstance()) / 2 + 150;
        if (mView.getImageRect().centerX() < limitWidth) {
            dx = limitWidth - mView.getImageRect().centerX();
        } else if ((mView.getImageRect().centerX() > limitHeight)) {
            dx = limitHeight - mView.getImageRect().centerX();
        }
        if (mView.getImageRect().centerY() < 0) {
            dy = -mView.getImageRect().centerY();
        } else if (mView.getImageRect().centerY() > Constants.HEIGHT_MASK) {
            dy = Constants.HEIGHT_MASK - mView.getImageRect().centerY();
        }
        mView.setTransMartix(dx, dy);
        mLastMidPointF.set(midCurrentPoint);

    }


    //检测移动边界
    private void checkTrans() {
        mView.refreshImageRect();
        float dx = 0;
        float dy = 0;
        mView.getImageRect();
        if (mView.getImageRect().centerX() < 300) {
            dx = 300 - mView.getImageRect().centerX();
        } else if ((mView.getImageRect().centerX() > DisplayUtil.getScreenW(App.getInstance()))) {
            dx = DisplayUtil.getScreenW(App.getInstance()) - mView.getImageRect().centerX();
        }
        if (mView.getImageRect().centerY() < 0) {
            dy = -mView.getImageRect().centerY();
        } else if (mView.getImageRect().centerY() > Constants.HEIGHT_MASK) {
            dy = Constants.HEIGHT_MASK - mView.getImageRect().centerY();
        }
        mView.setTransMartix(dx, dy);
    }


    /**
     * 获取当前图片旋转角度
     *
     * @return 图片当前的旋转角度
     */
    private float getCurrentRotateDegree() {
        // 每次重置初始向量的值为与x轴同向
        xAxis[0] = 1f;
        xAxis[1] = 0f;
        // 初始向量通过矩阵变换后的向量
        mView.mapVectors(xAxis);
        // 变换后向量与x轴夹角
        double rad = Math.atan2(xAxis[1], xAxis[0]);
        return (float) Math.toDegrees(rad);

    }


    /**
     * 计算缩放比例
     *
     * @param point1
     * @param point2
     * @return
     */
    private float distance(PointF point1, PointF point2) {
        float dx = point2.x - point1.x;
        float dy = point2.y - point1.y;
        return (float) Math.sqrt(dx * dx + dy * dy);

    }

    /**
     * 获取图片的缩放中心，该属性可在外部设置，或通过xml文件设置
     * 默认中心点为图片中心
     *
     * @return 图片的缩放中心点
     */
    private PointF getScaleCenter() {
        // 使用全局变量避免频繁创建变量
        scaleCenter.set(mView.getImageRect().centerX(), mView.getImageRect().centerY());
        return scaleCenter;
    }


    /**
     * 取两个点击的中间处
     *
     * @param event
     * @return
     */
    private PointF getMidPointOfFinger(MotionEvent event) {
        mMidPointF.set(0, 0);
        int countPoint = event.getPointerCount();
        for (int i = 0; i < countPoint; i++) {
            mMidPointF.x += event.getX(i);
            mMidPointF.y += event.getY(i);

        }
        mMidPointF.x /= countPoint;
        mMidPointF.y /= countPoint;
        return mMidPointF;
    }


    public void setTouchFlag(boolean b) {
        isFlag = b;
    }

}
