package com.example.yf.creatorshirt.mvp.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.yf.creatorshirt.utils.Constants;

/**
 * Created by yangfang on 2018/1/9.
 */

public class ClothesView extends ImageView {
    Rect dstRect;
    Bitmap source;
    private Paint paint;

    public ClothesView(Context context, @Nullable AttributeSet attrs) {
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
        int width = Constants.WIDTH_MASK;
        int height = Constants.HEIGHT_MASK;
        Rect rectF = new Rect((getMeasuredWidth() - width) / 2, 0, width + ((getMeasuredWidth() - width) / 2), height);
        if (source != null) {
            dstRect = new Rect(0, 0, source.getWidth(), source.getHeight());
            canvas.drawBitmap(source, dstRect, rectF, paint);
            paint.setXfermode(null);
        }
    }
}
