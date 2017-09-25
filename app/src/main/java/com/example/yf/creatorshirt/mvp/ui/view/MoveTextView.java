package com.example.yf.creatorshirt.mvp.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.utils.DisplayUtil;


/**
 * Create by yangfang on 2017/9/25.
 */

public class MoveTextView extends android.support.v7.widget.AppCompatTextView {
    private static final String TAG = "MoveTextView";

    private int lastX = 0;
    private int lastY = 0;

    private static int screenWidth;
    private static int screenHeight;

    public MoveTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        screenWidth = DisplayUtil.getScreenW(App.getInstance());
        screenHeight = DisplayUtil.getScreenH(App.getInstance());

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = (int) event.getRawX() - lastX;
                int dy = (int) event.getRawY() - lastY;

                int left = getLeft() + dx;
                int top = getTop() + dy;
                int right = getRight() + dx;
                int bottom = getBottom() + dy;
                if (left < 0) {
                    left = 0;
                    right = left + getWidth();
                }
                if (right > screenWidth) {
                    right = screenWidth;
                    left = right - getWidth();
                }
                if (top < 0) {
                    top = 0;
                    bottom = top + getHeight();
                }
                if (bottom > screenHeight) {
                    bottom = screenHeight;
                    top = bottom - getHeight();
                }
                layout(left, top, right, bottom);
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return true;
    }
}
