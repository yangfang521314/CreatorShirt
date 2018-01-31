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


}
