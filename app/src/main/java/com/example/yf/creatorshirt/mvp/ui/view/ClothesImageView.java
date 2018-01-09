package com.example.yf.creatorshirt.mvp.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by yangfang on 2018/1/9.
 */

public class ClothesImageView extends ImageView {
    Rect dstRect;
    Bitmap source;
    private Paint paint;

    public ClothesImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();

    }


    public void setImageSource(Bitmap source) {
        this.source = source;
        invalidate();
    }


    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect rectF = new Rect((getMeasuredWidth() - 700) / 2, 0, 700 + ((getMeasuredWidth() - 700) / 2), 900);
        if (source != null) {
            dstRect = new Rect(0, 0, source.getWidth(), source.getHeight());
            canvas.drawBitmap(source, dstRect, rectF, paint);

            paint.setXfermode(null);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


}
