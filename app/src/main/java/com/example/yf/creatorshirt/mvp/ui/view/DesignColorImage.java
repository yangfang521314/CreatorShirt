package com.example.yf.creatorshirt.mvp.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.yf.creatorshirt.R;

/**
 * Created by yang on 20/06/2017.
 */

public class DesignColorImage extends ImageView {
    private Paint mPaint;
    private int color;
    private float radius;

    public DesignColorImage(Context context) {
        this(context, null);
        init();
    }

    public DesignColorImage(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DesignColorImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SetCircle, defStyleAttr, 0);
        try {
            color = typedArray.getColor(R.styleable.SetCircle_showColor, context.getResources().getColor(R.color.white));
            radius = typedArray.getFloat(R.styleable.SetCircle_radius, 16.0f);
        } finally {
            typedArray.recycle();
        }

    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(1f);
        mPaint.setColor(color);
    }

    //    EXACTLY：一般是设置了明确的值或者是MATCH_PARENT
//    AT_MOST：表示子布局限制在一个最大值内，一般为WARP_CONTENT
//    UNSPECIFIED：表示子布局想要多大就多大，很少使用
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        canvas.drawCircle(centerX,centerY,radius,mPaint);
    }
}
