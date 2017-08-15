package com.example.yf.creatorshirt.mvp.ui.adapter.viewholder;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.ui.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/8/13.
 */

public class DesignBaseHolder extends BaseViewHolder {
    public RelativeLayout llClothes;

    public DesignBaseHolder(ViewGroup parent, @LayoutRes int resId) {
        super(parent, resId);
        llClothes = getView(R.id.rl_clothes);
    }
}
