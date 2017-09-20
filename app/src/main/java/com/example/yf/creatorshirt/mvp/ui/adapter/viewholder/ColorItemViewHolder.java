package com.example.yf.creatorshirt.mvp.ui.adapter.viewholder;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.mvp.ui.adapter.base.BaseViewHolder;
import com.example.yf.creatorshirt.mvp.ui.view.CircleView;
import com.example.yf.creatorshirt.utils.DisplayUtil;

/**
 * Created by yangfang on 2017/9/19.
 */

public class ColorItemViewHolder extends BaseViewHolder {
    public LinearLayout mCommonStyle;
    public CircleView mStyleImageView;
    public TextView mStyleTextView;

    public ColorItemViewHolder(ViewGroup parent, @LayoutRes int resId) {
        super(parent, resId);
        mStyleImageView = getView(R.id.design_icon_style);
        mStyleTextView = getView(R.id.design_text_style);
        mCommonStyle = getView(R.id.common_style);
        DisplayUtil.calculateItemWidth(App.getInstance(), mCommonStyle);
    }
}
