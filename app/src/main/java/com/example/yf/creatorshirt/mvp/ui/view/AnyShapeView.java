package com.example.yf.creatorshirt.mvp.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.yf.creatorshirt.mvp.model.HollowModel;
import com.example.yf.creatorshirt.mvp.model.PictureModel;

/**
 * Created by yangfang on 2017/11/14.
 */

public class AnyShapeView extends View {
    private Bitmap mask;
    private Bitmap source;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mColorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Rect srcRect;
    private Rect sourceRect;
    private Rect dstRect;
    private int width;
    private int height;
    private Context mContext;
    private PictureModel jigsawPictureModel;
    private boolean isContain;
    private double mLastFingerDistance;
    private double mLastDegree;
    private HollowModel hollowModel;
    private boolean mIsDoubleFinger;
    private float mLastX;
    private float mLastY;

    private float mDownX;
    private float mDownY;


    public AnyShapeView(Context context) {
        super(context);
        init();
    }

    private void init() {
        jigsawPictureModel = new PictureModel();
        mColorPaint.setColor(Color.BLUE);
        mColorPaint.setStrokeWidth(2);
        mColorPaint.setStyle(Paint.Style.STROKE);
    }

    public AnyShapeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AnyShapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setImageSource(String path) {
        source = BitmapFactory.decodeFile(path);
        jigsawPictureModel.setBitmapPicture(source);
        postInvalidate();
    }
    public void setImageNetSource(Bitmap res) {
        source = res;
        jigsawPictureModel.setBitmapPicture(source);
        postInvalidate();

    }

    public void setImageMask(int maskImage) {
        mask = BitmapFactory.decodeResource(getResources(), maskImage);
        postInvalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mask == null) {
            if (source != null) {
                srcRect = new Rect(0, 0, source.getWidth(), source.getHeight());
                dstRect = new Rect(0 + width / 4, 0 + height / 4, width - width / 4, height - height / 4);
                canvas.drawBitmap(source, srcRect, dstRect, mPaint);
            }
        } else {
            sourceRect = new Rect(0 + width / 4, 0 + height / 4, width - width / 4, height - height / 4);
            srcRect = new Rect(0, 0, mask.getWidth(), mask.getHeight());
            dstRect = new Rect(0, 0, width, height);
            if (source != null) {
                int saved = canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG);
                canvas.scale(1.4f, 1.4f, width / 2, height / 2);
                canvas.drawBitmap(mask, srcRect, dstRect, mPaint);
                mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                canvas.scale(jigsawPictureModel.getScaleX(), jigsawPictureModel.getScaleY(), width / 2, height / 2);
                canvas.rotate(jigsawPictureModel.getRotate(), width / 2, height / 2);
                canvas.translate(jigsawPictureModel.getPictureX(), jigsawPictureModel.getPictureY());
                canvas.drawBitmap(source, srcRect, sourceRect, mPaint);
                mPaint.setXfermode(null);
                canvas.restoreToCount(saved);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (source == null && mask == null) {
            return false;
        }
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_POINTER_DOWN:
                //双指模式
                if (event.getPointerCount() == 2) {
                    //mPicModelTouch为当前触摸到的操作图片模型
                    isContain = getHandlePicModel(event);
                    if (isContain) {
                        //两手指的距离
                        mLastFingerDistance = distanceBetweenFingers(event);
                        //两手指间的角度
                        mLastDegree = rotation(event);
                        mIsDoubleFinger = true;
                        invalidate();
                    }
                }
                break;
            //单指模式
            case MotionEvent.ACTION_DOWN:
                //记录上一次事件的位置
                mLastX = event.getX();
                mLastY = event.getY();
                //记录Down事件的位置
                mDownX = event.getX();
                mDownY = event.getY();
                //获取被点击的图片模型
                isContain = getHandlePicModel(event);
                if (isContain) {
                    //每次down重置其他picture选中状态
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                switch (event.getPointerCount()) {
                    //单指模式
                    case 1:
                        if (!mIsDoubleFinger) {
                            if (isContain) {
                                //记录每次事件在x,y方向上移动
                                int dx = (int) (event.getX() - mLastX);
                                int dy = (int) (event.getY() - mLastY);
                                int tempX = jigsawPictureModel.getPictureX() + dx;
                                int tempY = jigsawPictureModel.getPictureY() + dy;
                                if (checkPictureLocation(jigsawPictureModel, tempX, tempY)) {
                                    //检查到没有越出镂空部分才真正赋值给mPicModelTouch
                                    jigsawPictureModel.setPictureX(tempX);
                                    jigsawPictureModel.setPictureY(tempY);
                                    //保存上一次的位置，以便下次事件算出相对位移
                                    mLastX = event.getX();
                                    mLastY = event.getY();
                                    //修改了mPicModelTouch的位置后刷新View
                                    invalidate();
                                }
                            }
                        }
                        break;
                    //双指模式
                    case 2:
                        if (isContain) {
                            //算出两根手指的距离
                            double fingerDistance = distanceBetweenFingers(event);
                            //当前的旋转角度
                            double currentDegree = rotation(event);
                            //当前手指距离和上一次的手指距离的比即为图片缩放比
                            float scaleRatioDelta = (float) (fingerDistance / mLastFingerDistance);
                            float rotateDelta = (float) (currentDegree - mLastDegree);

                            float tempScaleX = scaleRatioDelta * jigsawPictureModel.getScaleX();
                            float tempScaleY = scaleRatioDelta * jigsawPictureModel.getScaleY();
                            //对缩放比做限制
                            if (Math.abs(tempScaleX) < 2.5 && Math.abs(tempScaleX) > 1 &&
                                    Math.abs(tempScaleY) < 2.5 && Math.abs(tempScaleY) > 1) {
                                jigsawPictureModel.setScaleX(tempScaleX);
                                jigsawPictureModel.setScaleY(tempScaleY);
                                jigsawPictureModel.setRotate(jigsawPictureModel.getRotate() + rotateDelta);
                                //记录上一次的两手指距离以便下次计算出相对的位置以算出缩放系数
                                invalidate();
                                mLastFingerDistance = fingerDistance;
                                //记录上次的角度以便下一个事件计算出角度变化值
                            }
                            mLastDegree = currentDegree;

                        }
                        break;
                }
                break;
            //两手指都离开屏幕
            case MotionEvent.ACTION_UP:
//                for (PictureModel pictureModel : mPictureModels) {
//                    pictureModel.setSelect(false);
//                }
                mIsDoubleFinger = false;
                break;
        }
        return true;

    }

    /**
     * 检查图片范围是否超出窗口,此方法还要完善
     */
    private boolean checkPictureLocation(PictureModel mPictureModel, int tempX, int tempY) {
        Bitmap picture = mPictureModel.getBitmapPicture();
        return (tempY < picture.getHeight() / 2 && tempY > -picture.getHeight() / 2)
                && (tempX < picture.getWidth() / 2) && (tempX > -picture.getWidth() / 2);
    }


    /**
     * 计算两个手指之间的距离。
     *
     * @param event
     * @return 两个手指之间的距离
     */
    private double distanceBetweenFingers(MotionEvent event) {
        float disX = Math.abs(event.getX(0) - event.getX(1));
        float disY = Math.abs(event.getY(0) - event.getY(1));
        return Math.sqrt(disX * disX + disY * disY);
    }


    // 取旋转角度
    private float rotation(MotionEvent event) {
        double disX = (event.getX(0) - event.getX(1));
        double disY = (event.getY(0) - event.getY(1));
        double radians = Math.atan2(disY, disX);
        return (float) Math.toDegrees(radians);
    }

    private boolean getHandlePicModel(MotionEvent event) {
        switch (event.getPointerCount()) {
            case 1:
                int x = (int) event.getX();
                int y = (int) event.getY();

                Rect rect = new Rect(getLeft(), getTop(), getRight(), getBottom());
                //点在矩形区域中
                if (rect.contains(x, y)) {
                    return true;
                }
                break;
            case 2:
                int x0 = (int) event.getX(0);
                int y0 = (int) event.getY(0);
                int x1 = (int) event.getX(1);
                int y1 = (int) event.getY(1);

                Rect rect1 = new Rect(getLeft(), getTop(), getRight(), getBottom());
                //两个点都在该矩形区域
                if (rect1.contains(x0, y0) || rect1.contains(x1, y1)) {
                    return true;
                }
                break;
            default:
                break;
        }
        return false;

    }
}
