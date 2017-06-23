package com.example.yf.creatorshirt.mvp.ui.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.App;

/**
 * Created by yang on 23/06/2017.
 */

public abstract class BasePopupWindow extends PopupWindow{
    public LayoutInflater layoutInflater;

    public BasePopupWindow() {
        layoutInflater = (LayoutInflater) App.getInstance().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        setContentView(getView());
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setFocusable(true);
        setAnimationStyle(R.style.AnimBottom);
        this.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        ColorDrawable drawable = new ColorDrawable(0x0000000);
        this.setBackgroundDrawable(drawable);
    }

    public abstract View getView();
}
