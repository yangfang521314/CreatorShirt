package com.example.yf.creatorshirt.mvp.ui.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.yf.creatorshirt.utils.DisplayUtil;

/**
 * Created by ${yf} on 2017/2/8.
 * 设置在viewpager显示的圆点指示器
 */

public class ShapeView extends LinearLayout implements LooperView {
    //圆点的集合
    private ImageView[] mDots;
    //圆点的个数
    private int length = 0;
    private int lastPosition = 0;


    private Drawable dot_normal;
    private Drawable dot_focus;

    public ShapeView(Context context) {
        super(context);
    }

    public ShapeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private Drawable makeNormalDrawable(int normalColor) {
        GradientDrawable dotNormal = new GradientDrawable();
        dotNormal.setColor(normalColor);
        dotNormal.setCornerRadius(DisplayUtil.Dp2Px(getContext(), 4));
        dotNormal.setSize(DisplayUtil.Dp2Px(getContext(), 8), DisplayUtil.Dp2Px(getContext(), 8));
        return dotNormal;
    }

    private Drawable makeFocusDrawable(int focusColor) {
        GradientDrawable dotFoucs = new GradientDrawable();
        dotFoucs.setColor(focusColor);
        dotFoucs.setCornerRadius(DisplayUtil.Dp2Px(getContext(), 4));
        dotFoucs.setSize(DisplayUtil.Dp2Px(getContext(), 8), DisplayUtil.Dp2Px(getContext(), 8));
        return dotFoucs;
    }

    /**
     * 轮播显示的位置
     *
     * @param current
     */
    @Override
    public void setCurrent(int current) {
        if (current < 0 || current > length - 1) {
            return;
        }
        mDots[lastPosition].setImageDrawable(dot_normal);
        mDots[current].setImageDrawable(dot_focus);
        //将显示后position赋值给lastPosition;
        lastPosition = current;
    }

    @Override
    public void initView(int length) {
        removeAllViews();
        this.length = length;
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        mDots = new ImageView[length];
        for (int i = 0; i < mDots.length; i++) {
            mDots[i] = new ImageView(getContext());
            LayoutParams dotlp = new LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);
            dotlp.setMargins(10, 0, 10, 0);
            mDots[i].setLayoutParams(dotlp);
            mDots[i].setImageDrawable(dot_normal);
            addView(mDots[i]);
        }
        setCurrent(0);
    }

    public void setColor(int focusColor, int normalColor) {
        dot_focus = makeFocusDrawable(focusColor);
        dot_normal = makeNormalDrawable(normalColor);
    }
}
