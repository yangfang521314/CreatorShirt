package com.example.yf.creatorshirt.mvp.ui.adapter;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.ui.adapter.base.BaseViewHolder;

/**
 * Created by yang on 15/06/2017.
 */

class ItemViewHolder extends BaseViewHolder {
    public ImageView mStyleImageView;
    public ItemViewHolder(ViewGroup parent, @LayoutRes int resId) {
        super(parent, resId);
        mStyleImageView = getView(R.id.style_image);
    }
}
