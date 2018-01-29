package com.example.yf.creatorshirt.mvp.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.example.yf.creatorshirt.mvp.model.PictureModel;

/**
 * Created by yangfang on 2018/1/3.
 */

public class PatterImage extends android.support.v7.widget.AppCompatImageView {
    private Bitmap source;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mColorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Rect srcRect;
    private Rect sourceRect;
    private Rect dstRect;
    private int width;
    private int height;
    private PictureModel jigsawPictureModel;
    private boolean isContain;
    private double mLastFingerDistance;
    private double mLastDegree;
    private boolean mIsDoubleFinger;
    private float mLastX;
    private float mLastY;

    private float mDownX;
    private float mDownY;


    public PatterImage(Context context) {
        super(context);
        init();
    }

    private void init() {
        jigsawPictureModel = new PictureModel();
        mColorPaint.setColor(Color.BLUE);
        mColorPaint.setStrokeWidth(2);
        mColorPaint.setStyle(Paint.Style.STROKE);
    }

    public PatterImage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PatterImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public void setImageNetSource(Bitmap res) {
        source = res;
        postInvalidate();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        if (source != null) {
            srcRect = new Rect(0, 0, source.getWidth(), source.getHeight());
            sourceRect = new Rect(width / 2 - source.getWidth() / 2,
                    height / 2 - source.getHeight() / 2, width / 2 + source.getHeight() / 2,
                    height / 2 + source.getHeight() / 2);
            canvas.scale(jigsawPictureModel.getScaleX(), jigsawPictureModel.getScaleY(), width / 2, height / 2);
            canvas.rotate(jigsawPictureModel.getRotate(), width / 2, height / 2);
            canvas.translate(jigsawPictureModel.getPictureX(), jigsawPictureModel.getPictureY());
            canvas.drawBitmap(source, srcRect, sourceRect, mPaint);
            mPaint.setXfermode(null);
        }
        super.onDraw(canvas);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }

    public void invalidateMode(PictureModel jigsawPictureModel) {
        this.jigsawPictureModel = jigsawPictureModel;
        invalidate();
    }

//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if (source == null) {
//            return false;
//        }
//        switch (event.getActionMasked()) {
//            case MotionEvent.ACTION_POINTER_DOWN:
//                //双指模式
//                if (event.getPointerCount() == 2) {
//                    //两手指的距离
//                    mLastFingerDistance = distanceBetweenFingers(event);
//                    //两手指间的角度
//                    mLastDegree = rotation(event);
//                    mIsDoubleFinger = true;
//                    invalidate();
//                }
//                break;
//            //单指模式
//            case MotionEvent.ACTION_DOWN:
//                Log.e("down", "DDD" + event.getX());
//                //记录上一次事件的位置
//                mLastX = event.getX();
//                mLastY = event.getY();
//                //记录Down事件的位置
//                mDownX = event.getX();
//                mDownY = event.getY();
//                invalidate();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                switch (event.getPointerCount()) {
//                    //单指模式
//                    case 1:
//                        if (!mIsDoubleFinger) {
//                            //记录每次事件在x,y方向上移动
//                            int dx = (int) (event.getX() - mLastX);
//                            int dy = (int) (event.getY() - mLastY);
//                            int tempX = jigsawPictureModel.getPictureX() + dx;
//                            int tempY = jigsawPictureModel.getPictureY() + dy;
//                            Log.e("Tag", "dx" + dx + "::::" + dy);
//                            if (checkPictureLocation(jigsawPictureModel, tempX, tempY)) {
//                                //检查到没有越出镂空部分才真正赋值给mPicModelTouch
//                                jigsawPictureModel.setPictureX(tempX);
//                                jigsawPictureModel.setPictureY(tempY);
//                                //保存上一次的位置，以便下次事件算出相对位移
//                                mLastX = event.getX();
//                                mLastY = event.getY();
//                                //修改了mPicModelTouch的位置后刷新View
//                                invalidate();
//                            }
//                        }
//                        break;
//                    //双指模式
//                    case 2:
//                        //算出两根手指的距离
//                        double fingerDistance = distanceBetweenFingers(event);
//                        //当前的旋转角度
//                        double currentDegree = rotation(event);
//                        //当前手指距离和上一次的手指距离的比即为图片缩放比
//                        float scaleRatioDelta = (float) (fingerDistance / mLastFingerDistance);
//                        float rotateDelta = (float) (currentDegree - mLastDegree);
//
//                        float tempScaleX = scaleRatioDelta * jigsawPictureModel.getScaleX();
//                        float tempScaleY = scaleRatioDelta * jigsawPictureModel.getScaleY();
//                        //对缩放比做限制
//                        if (Math.abs(tempScaleX) < 2.5 && Math.abs(tempScaleX) > 0.1 &&
//                                Math.abs(tempScaleY) < 2.5 && Math.abs(tempScaleY) > 0.1) {
//                            jigsawPictureModel.setScaleX(tempScaleX);
//                            jigsawPictureModel.setScaleY(tempScaleY);
//                            jigsawPictureModel.setRotate(jigsawPictureModel.getRotate() + rotateDelta);
//                            //记录上一次的两手指距离以便下次计算出相对的位置以算出缩放系数
//                            invalidate();
//                            mLastFingerDistance = fingerDistance;
//                            //记录上次的角度以便下一个事件计算出角度变化值
//                        }
//                        mLastDegree = currentDegree;
//                        break;
//                }
//                break;
//            //两手指都离开屏幕
//            case MotionEvent.ACTION_UP:
//                mIsDoubleFinger = false;
//                invalidate();
//                break;
//        }
//        return true;
//
//    }
//
//    /**
//     * 检查图片范围是否超出窗口,此方法还要完善
//     */
//    private boolean checkPictureLocation(PictureModel mPictureModel, int tempX, int tempY) {
//        Bitmap picture = mPictureModel.getBitmapPicture();
//        return (tempY < picture.getHeight() / 2 && tempY > -picture.getHeight() / 2)
//                && (tempX < picture.getWidth() / 2) && (tempX > -picture.getWidth() / 2);
//    }
//
//
//    /**
//     * 计算两个手指之间的距离。
//     *
//     * @param event
//     * @return 两个手指之间的距离
//     */
//    private double distanceBetweenFingers(MotionEvent event) {
//        float disX = Math.abs(event.getX(0) - event.getX(1));
//        float disY = Math.abs(event.getY(0) - event.getY(1));
//        return Math.sqrt(disX * disX + disY * disY);
//    }
//
//
//    // 取旋转角度
//    private float rotation(MotionEvent event) {
//        double disX = (event.getX(0) - event.getX(1));
//        double disY = (event.getY(0) - event.getY(1));
//        double radians = Math.atan2(disY, disX);
//        return (float) Math.toDegrees(radians);
//    }


}
