package com.example.yf.creatorshirt.mvp.ui.adapter.viewholder;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.ui.adapter.base.BaseViewHolder;

/**
 * Created by yang on 03/07/2017.
 */

public class OrderViewHolder extends BaseViewHolder{
    public RelativeLayout mRlOrderLayout;
    public OrderViewHolder(ViewGroup parent, @LayoutRes int resId) {
        super(parent, resId);
        mRlOrderLayout = getView(R.id.rl_order_layout);
    }
}
