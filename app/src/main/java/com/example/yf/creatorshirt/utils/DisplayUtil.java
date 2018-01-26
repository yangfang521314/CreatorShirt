package com.example.yf.creatorshirt.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.ColorInt;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.yf.creatorshirt.mvp.ui.view.ClothesFrontView;

/**
 * dp与px单位间的相互转换
 */
public class DisplayUtil {
    public static int Dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int Px2Dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    public static int getScreenW(Context context) {
        DisplayMetrics metric = context.getResources().getDisplayMetrics();
        return metric.widthPixels;
    }

    public static int getScreenH(Context context) {
        DisplayMetrics metric = context.getResources().getDisplayMetrics();
        return metric.heightPixels;
    }
    /**
     * 设置定制布局的宽和高
     *
     * @param context
     * @param linearLayout
     */
    public static void calculateItemWidth(Context context, LinearLayout linearLayout) {
        int width = getScreenW(context);
        int height = getScreenH(context);
        ViewGroup.LayoutParams layoutParams = linearLayout.getLayoutParams();
        layoutParams.width = width / 3;
        layoutParams.height = height / 6;
        linearLayout.setLayoutParams(layoutParams);

    }

    /**
     * 设置定制布局的宽和高
     *
     * @param context
     * @param linearLayout
     */
    public static void calculateItem2Width(Context context, LinearLayout linearLayout) {
        int width = getScreenW(context);
        ViewGroup.LayoutParams layoutParams = linearLayout.getLayoutParams();
        layoutParams.width = width / 3;
        linearLayout.setLayoutParams(layoutParams);

    }

    /**
     * 设置定制布局的宽和高
     *
     * @param context
     * @param linearLayout
     */
    public static void calculateDesignerClothesWidth(Context context, ImageView linearLayout) {
        int width = getScreenW(context);
        ViewGroup.LayoutParams layoutParams = linearLayout.getLayoutParams();
        layoutParams.width = 1080;
        layoutParams.height = 900;
        linearLayout.setLayoutParams(layoutParams);

    }

    public static void calculateHHWidth(Context context, RelativeLayout mContainerBackground) {
        ViewGroup.LayoutParams layoutParams = mContainerBackground.getLayoutParams();
        layoutParams.width = getScreenW(context) / 3;
        layoutParams.height = getScreenW(context) / 3;
        mContainerBackground.setLayoutParams(layoutParams);
    }

    /**
     * 动态设置对话框的宽为屏幕四分之三
     */
    public static void calculateDialogWidth(Context context, LinearLayout linearLayout) {
        int width = getScreenW(context);
        ViewGroup.LayoutParams layoutParams = linearLayout.getLayoutParams();
        layoutParams.width = width/2 + width/4;
        linearLayout.setLayoutParams(layoutParams);
    }

    public static void calculateClothesWidth(Context context, ClothesFrontView mContainerFront) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mContainerFront.getLayoutParams();
        layoutParams.width  = Constants.WIDTH_MASK;
        layoutParams.height = Constants.HEIGHT_MASK;
        mContainerFront.setLayoutParams(layoutParams);
    }

    /**
     * 对TextView、Button等设置不同状态时其文字颜色。
     * 参见：http://blog.csdn.net/sodino/article/details/6797821
     */

    public static ColorStateList toColorStateList(@ColorInt int normalColor, @ColorInt int pressedColor) {
        return toColorStateList(normalColor, pressedColor, pressedColor, normalColor);
    }
    /**
     * 对TextView、Button等设置不同状态时其文字颜色。
     * 参见：http://blog.csdn.net/sodino/article/details/6797821
     */
    public static ColorStateList toColorStateList(@ColorInt int normalColor, @ColorInt int pressedColor,
                                                  @ColorInt int focusedColor, @ColorInt int unableColor) {
        int[] colors = new int[]{pressedColor, focusedColor, normalColor, focusedColor, unableColor, normalColor};
        int[][] states = new int[6][];
        states[0] = new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled};
        states[1] = new int[]{android.R.attr.state_enabled, android.R.attr.state_focused};
        states[2] = new int[]{android.R.attr.state_enabled};
        states[3] = new int[]{android.R.attr.state_focused};
        states[4] = new int[]{android.R.attr.state_window_focused};
        states[5] = new int[]{};
        return new ColorStateList(states, colors);
    }

}
