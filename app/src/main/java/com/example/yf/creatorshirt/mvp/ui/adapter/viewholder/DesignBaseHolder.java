package com.example.yf.creatorshirt.mvp.ui.adapter.viewholder;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.ui.adapter.base.BaseViewHolder;
import com.example.yf.creatorshirt.utils.DisplayUtil;

/**
 * Created by Administrator on 2017/8/13.
 */

public class DesignBaseHolder extends BaseViewHolder {
    public RelativeLayout llClothes;
    public TextView mClothesName;

    public DesignBaseHolder(ViewGroup parent, @LayoutRes int resId) {
        super(parent, resId);
        llClothes = getView(R.id.rl_clothes);
        mClothesName = getView(R.id.clothes_name);
        DisplayUtil.calculateHHWidth(getContext(),llClothes);
    }
}
