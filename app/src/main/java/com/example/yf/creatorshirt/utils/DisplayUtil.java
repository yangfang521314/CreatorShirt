package com.example.yf.creatorshirt.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.yf.creatorshirt.mvp.ui.view.sticker.StickerView;

import static com.umeng.socialize.utils.DeviceConfig.context;

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

    public static int getScreenHWithoutStateBar(Context context) {
        DisplayMetrics metric = context.getResources().getDisplayMetrics();
        Rect frame = new Rect();
        ((Activity) context).getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        // 状态栏高度
        int statusBarHeight = frame.top;
        return metric.heightPixels - statusBarHeight;
    }

    /**
     * 动态设置对话框的宽为屏幕四分之三
     */
    public static void calculateDialogWidth(Context context, LinearLayout linearLayout) {
        int width = getScreenW(context);
        ViewGroup.LayoutParams layoutParams = linearLayout.getLayoutParams();
        layoutParams.width = width / 2 + width / 4;
        linearLayout.setLayoutParams(layoutParams);
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
        layoutParams.height = width / 3;
        linearLayout.setLayoutParams(layoutParams);

    }

    public static void calculateHHWidth(Context context, RelativeLayout mContainerBackground) {
        ViewGroup.LayoutParams layoutParams = mContainerBackground.getLayoutParams();
        layoutParams.width = getScreenW(context) / 3;
        layoutParams.height = getScreenW(context) / 3;
        mContainerBackground.setLayoutParams(layoutParams);
    }

    /**
     * 设置衣服背景的高度
     *
     * @param mContainerBackground
     */
    public static void calculateBGWidth(LinearLayout mContainerBackground) {
        int w = getScreenW(context);
        ViewGroup.LayoutParams layoutParams = mContainerBackground.getLayoutParams();
        layoutParams.width = w / 6;
        layoutParams.height = w / 6;
        mContainerBackground.setLayoutParams(layoutParams);
    }

    public static void calculateRelative(Context context, RelativeLayout mRelative) {
        ViewGroup.LayoutParams layoutParams = mRelative.getLayoutParams();
        layoutParams.height = 592;
        mRelative.setLayoutParams(layoutParams);
    }

    public static void calculateItemSizeWidth(Context context, LinearLayout mll) {
        int width = getScreenW(context);
        ViewGroup.LayoutParams layoutParams = mll.getLayoutParams();
        layoutParams.width = (int) (width / 6.5);
        mll.setLayoutParams(layoutParams);
    }

    public static void calculateFrontSize(ImageView imageView) {
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        layoutParams.width = 592;
        imageView.setLayoutParams(layoutParams);
    }

    public static void calculateBackSize(ImageView mBackImageView) {
        ViewGroup.LayoutParams layoutParams = mBackImageView.getLayoutParams();
        layoutParams.width = 592;
        layoutParams.height = 592;
        mBackImageView.setLayoutParams(layoutParams);
    }

    public static void calculateBigRelative(Context context, RelativeLayout mRelative) {
        ViewGroup.LayoutParams layoutParams = mRelative.getLayoutParams();
        layoutParams.height = 750;
        mRelative.setLayoutParams(layoutParams);
    }

    public static void calculateRl(RelativeLayout relativeLayout) {
        ViewGroup.LayoutParams layoutParams = relativeLayout.getLayoutParams();
        layoutParams.height = 750;
        layoutParams.width = 750;
        relativeLayout.setLayoutParams(layoutParams);
    }

    public static void calculateSmallRl(RelativeLayout relativeLayout) {
        ViewGroup.LayoutParams layoutParams = relativeLayout.getLayoutParams();
        layoutParams.height = 592;
        layoutParams.width = 592;
        relativeLayout.setLayoutParams(layoutParams);
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
