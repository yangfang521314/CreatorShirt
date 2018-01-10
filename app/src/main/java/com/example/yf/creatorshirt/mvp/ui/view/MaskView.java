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

import com.example.yf.creatorshirt.utils.Constants;


public class MaskView extends ImageView {
    Rect dstRect;
    Bitmap source;
    Bitmap mask;
    private Paint paint;
    private int width;
    private int height;

    public MaskView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    public void setImageSource(Bitmap source) {
        this.source = source;
        invalidate();
    }

    public void setImageMask(Bitmap mask) {
        this.mask = mask;
        invalidate();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect rectF = new Rect((getMeasuredWidth() - Constants.WIDTH_MASK) / 2, 0, Constants.WIDTH_MASK + ((getMeasuredWidth() - Constants.WIDTH_MASK) / 2), Constants.HEIGHT_MASK);
        if (source != null && mask != null) {
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            canvas.setBitmap(bitmap);
            dstRect = new Rect(0, 0, source.getWidth(), source.getHeight());
            int layer = canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG);
            canvas.drawBitmap(source, dstRect, rectF, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
            Rect maskRect = new Rect(0, 0, mask.getWidth(), mask.getHeight());
            Rect dstRect = new Rect(width / 2 - mask.getWidth() / 2, height / 2 - mask.getHeight() / 2,
                    mask.getWidth() / 2 + width / 2, mask.getHeight() / 2 + height / 2);
            canvas.drawBitmap(mask, maskRect, dstRect, paint);
            paint.setXfermode(null);
            canvas.restoreToCount(layer);
            setImageBitmap(bitmap);
        } else if (source == null && mask != null) {
            Rect maskRect = new Rect(0, 0, mask.getWidth(), mask.getHeight());
            Rect dstRect = new Rect(width / 2 - mask.getWidth() / 2, height / 2 - mask.getHeight() / 2,
                    mask.getWidth() / 2 + width / 2, mask.getHeight() / 2 + height / 2);
            canvas.drawBitmap(mask, maskRect, dstRect, paint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        height = getMeasuredHeight();
        width = getMeasuredWidth();
    }
}