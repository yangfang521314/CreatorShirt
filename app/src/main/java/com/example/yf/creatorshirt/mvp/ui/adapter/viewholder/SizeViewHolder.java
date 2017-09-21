package com.example.yf.creatorshirt.mvp.ui.adapter.viewholder;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.ui.adapter.base.BaseViewHolder;

/**
 * Created by yangfang on 2017/9/1.
 */

public class SizeViewHolder extends BaseViewHolder {
    public TextView mSizeNumber;
    public LinearLayout mll;

    public SizeViewHolder(ViewGroup parent, @LayoutRes int resId) {
        super(parent, resId);
        mSizeNumber = getView(R.id.size_number);
        mll = getView(R.id.ll_item_size);
//        DisplayUtil.calculateBGWidth(mll);
    }
}
