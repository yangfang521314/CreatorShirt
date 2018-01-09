package com.example.yf.creatorshirt.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.yf.creatorshirt.mvp.ui.view.sticker.StickerView;

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


    public static void calculateItemSizeWidth(Context context, LinearLayout mll) {
        int width = getScreenW(context);
        ViewGroup.LayoutParams layoutParams = mll.getLayoutParams();
        layoutParams.width = (int) (width / 6.5);
        mll.setLayoutParams(layoutParams);
    }


    public static void calculateSmallRl(StickerView stickerView) {
        ViewGroup.LayoutParams layoutParams = stickerView.getLayoutParams();
        layoutParams.height = 592;
        layoutParams.width = 592;
        stickerView.setLayoutParams(layoutParams);
    }

    public static void calculateRl(StickerView stickerView) {
        ViewGroup.LayoutParams layoutParams = stickerView.getLayoutParams();
        layoutParams.height = 750;
        layoutParams.width = 750;
        stickerView.setLayoutParams(layoutParams);
    }
}
