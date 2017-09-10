package com.example.yf.creatorshirt.mvp.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.yf.creatorshirt.R;

/**
 * Created by yangfang on 2017/9/10.
 */

public class CircleView extends View {

    private Bitmap mSrc;
    private int mWidth;
    private int mHeight;
    private int inColor;
    private int outColor;
    private int outStrokeWidth;
    private Canvas canvas;
    Paint paint;
    private int min;

    public CircleView(Context context) {
        this(context, null);
    }


    public CircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.StockManage);
        mSrc = BitmapFactory.decodeResource(getResources(), array.getResourceId(R.styleable.StockManage_icon, -1));
        if (mSrc == null) {
            inColor = array.getColor(R.styleable.StockManage_inColor, Color.WHITE);
        }
        outColor = array.getColor(R.styleable.StockManage_outColor, -1);
        outStrokeWidth = array.getDimensionPixelSize(R.styleable.StockManage_stroke, 0);
        mWidth = array.getDimensionPixelSize(R.styleable.StockManage_width, 0);
        mHeight = array.getDimensionPixelSize(R.styleable.StockManage_height, 0);
        array.recycle();
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        min = Math.min(mWidth, mHeight);
        /**
         110          * 长度如果不一致，按小的值进行压缩
         111          */
        if (mSrc != null) {
            mSrc = Bitmap.createScaledBitmap(mSrc, min, min, false);
            canvas.drawBitmap(createCircleImage(mSrc, min), 0, 0, null);
        } else {
            canvas.drawBitmap(createCircleImage(null, min), 0, 0, null);
        }
    }

    private Bitmap createCircleImage(Bitmap source, int min) {
        paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(target);
        canvas.drawCircle(min / 2, min / 2, min / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        /**
         * 绘制图片
         *
         */
        if (source != null)// 画图片
            canvas.drawBitmap(source, 0, 0, paint);
        else { // 画圆
            paint.setColor(inColor);
            canvas.drawCircle(min / 2, min / 2, min / 2, paint);
        }
        if (outColor != 0) {
            //圆形
            paint.setStyle(Paint.Style.FILL);
            paint.setStrokeWidth(1);
            paint.setColor(outColor);
            canvas.drawCircle(min / 2, min / 2, min / 2, paint);
        }

        return target;
    }

    public int getOutColor() {
        return outColor;
    }

    public void setOutColor(int outColor) {
        this.outColor = outColor;
    }
}
