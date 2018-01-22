package com.example.yf.creatorshirt.mvp.ui.view.popupwindow;

import android.view.View;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.ui.view.BasePopupWindow;

import butterknife.ButterKnife;

/**
 * Created by yangfang on 2018/1/22.
 */

public class RecyclerPopupWindow extends BasePopupWindow {
    @Override
    public View getView() {
        View view = layoutInflater.inflate(R.layout.recycler_view, null);
        ButterKnife.bind(this, view);
        return view;
    }
}
