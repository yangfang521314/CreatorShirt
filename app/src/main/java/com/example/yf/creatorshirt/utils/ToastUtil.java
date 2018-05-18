package com.example.yf.creatorshirt.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yf.creatorshirt.R;


/**
 * Created by yf on 2016/1/15 0015.
 */
public class ToastUtil {
    private static Toast mToast;
    /**
     * 显示Toast的消息标记
     */
    private static final int SHOW_TOAST = 0;
    private static final long TOAST_SHORT_DELAY = 1000;

    @SuppressLint("HandlerLeak")
    private static Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_TOAST:
                    if (mToast != null) {
                        mToast.show();
                    }
                    mHandler.sendEmptyMessageDelayed(SHOW_TOAST, TOAST_SHORT_DELAY);

                    break;
            }
        }
    };

    @Deprecated
    public static void make(Context context, CharSequence cs, int resId) {
        showToast(context, cs, resId);
    }

    public static void showToasts(Context context, CharSequence cs, int resId) {

        destroy();

        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.custom_toast, null);

        ProgressBar pro = layout.findViewById(R.id.progressIconToast);
        pro.setVisibility(View.GONE);

        // 设置toast文字
        TextView tv = layout.findViewById(R.id.tvTextToast);
        tv.setText(cs);

        mToast = new Toast(context);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setView(layout);

        LinearLayout toastView = (LinearLayout) mToast.getView();
        ImageView imageCodeProject = new ImageView(context);
        imageCodeProject.setImageResource(resId);
        toastView.addView(imageCodeProject, 0);
        mToast.show();
    }

    /**
     * @param context
     * @param cs
     * @param duration //时间
     */
    public static void showToast(Context context, CharSequence cs, int duration) {

        destroy();

        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.custom_toast,  null);

        ProgressBar pro = layout.findViewById(R.id.progressIconToast);
        pro.setVisibility(View.GONE);

        // 设置toast文字
        TextView tv = layout.findViewById(R.id.tvTextToast);
        tv.setText(cs);

        mToast = new Toast(context);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setDuration(duration);
        mToast.setView(layout);

//        LinearLayout toastView = (LinearLayout) mToast.getView();
//        ImageView imageCodeProject = new ImageView(context);
//        imageCodeProject.setImageResource(resId);
//        toastView.addView(imageCodeProject, 0);
        mToast.show();
    }


    public static void showProgressToast(Context context, String text, int icon) {

        destroy();

        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.custom_toast, null);

        // 设置ProgressBar的Icon
        ProgressBar pro = layout.findViewById(R.id.progressIconToast);
        pro.setVisibility(View.VISIBLE);
        pro.setIndeterminateDrawable(context.getResources().getDrawable(icon));

        // 设置toast文字
        TextView tv = layout.findViewById(R.id.tvTextToast);
        tv.setText(text);

        mToast = new Toast(context);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setView(layout);

        mToast.show();
        mHandler.sendEmptyMessageDelayed(SHOW_TOAST, Toast.LENGTH_LONG);

    }

    private static void destroy() {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
    }

    public static void cancel() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
    }

}
