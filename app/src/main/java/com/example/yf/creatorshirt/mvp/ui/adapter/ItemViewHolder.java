package com.example.yf.creatorshirt.mvp.ui.adapter;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.ui.adapter.base.BaseViewHolder;

/**
 * Created by yang on 15/06/2017.
 */

class ItemViewHolder extends BaseViewHolder {
    public RelativeLayout mCommonStyle;
    public ImageView mStyleImageView;
    public TextView mStyleTextView;
    public ItemViewHolder(ViewGroup parent, @LayoutRes int resId) {
        super(parent, resId);
        mStyleImageView = getView(R.id.design_icon_style);
        mStyleTextView = getView(R.id.design_text_style);
        mCommonStyle = getView(R.id.common_style);

    }
}
