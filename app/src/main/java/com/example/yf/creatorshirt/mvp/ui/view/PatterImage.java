package com.example.yf.creatorshirt.mvp.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.example.yf.creatorshirt.utils.SharedPreferencesUtil;

/**
 * Created by yangfang on 2018/1/3.
 */

public class PatterImage extends android.support.v7.widget.AppCompatImageView {

    private static final String TAG = PatterImage.class.getSimpleName();
    private Matrix mMatrix = new Matrix();
    private RectF mImageRect = new RectF();// 保存图片所在区域矩形，坐标为相对于本View的坐标
    private float mScaleFactor = 0.8f; //图片放大倍数
    static {
        SharedPreferencesUtil.setMoFlag(true);
    }

    public PatterImage(Context context) {
        super(context);
        init();
    }

    private void init() {
        setScaleType(ScaleType.MATRIX);
    }

    public PatterImage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PatterImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        initImgPositionAndSize();
    }

    private void initImgPositionAndSize() {
        if (SharedPreferencesUtil.getMoFlag()) {
            mMatrix.reset();
            // 初始化ImageRect
            refreshImageRect();

            float scaleFactor = mScaleFactor;
            // 初始图片缩放比例比最小缩放比例稍大
            mScaleFactor = scaleFactor;
            mMatrix.postScale(scaleFactor, scaleFactor, mImageRect.centerX(), mImageRect.centerY());
            refreshImageRect();
            // 移动图片到中心
            mMatrix.postTranslate((getRight() - getLeft()) / 2 - mImageRect.centerX(),
                    (getBottom() - getTop()) / 2 - mImageRect.centerY());
            applyMatrix();
            SharedPreferencesUtil.setMoFlag(false);
        } else {
            applyMatrix();
        }
    }

    /**
     * 图片使用矩阵变换后，刷新图片所对应的mImageRect所指示的区域
     */
    protected void refreshImageRect() {
        if (getDrawable() != null) {
            mImageRect.set(getDrawable().getBounds());
            mMatrix.mapRect(mImageRect, mImageRect);
        }
    }


    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    public void applyMatrix() {
        refreshImageRect(); /*将矩阵映射到ImageRect*/
        setImageMatrix(mMatrix);
    }

    public void setTransMartix(float dx, float dy) {
        mMatrix.postTranslate(dx, dy);
    }

    public void setScaleMatrix(float scaleFactor, float scaleFactor1, float x, float y) {
        mMatrix.postScale(scaleFactor, scaleFactor1, x, y);
    }

    public void mapVectors(float[] xAxis) {
        mMatrix.mapVectors(xAxis);
    }

    public void setCurrentScale(float currentScale) {
        mScaleFactor = currentScale;
    }

    public RectF getImageRect() {
        return mImageRect;
    }

    public void setImageRect(RectF mImageRect) {
        this.mImageRect = mImageRect;
    }

    public void setRotate(float degree) {
        mMatrix.postRotate(degree, mImageRect.centerX(), mImageRect.centerY());
    }

    public float getScaleFactor() {
        return mScaleFactor;
    }
}
