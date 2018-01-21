package com.example.yf.creatorshirt.mvp.ui.adapter.viewholder;

import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.ui.adapter.base.BaseViewHolder;

/**
 * Created by yangfang on 2017/12/17.
 */

public class DetailOrderHolder extends BaseViewHolder {
    public TextView mSizeLetter;
    public TextView mSizeNumber;
    public TextView mClothesNumber;

    public DetailOrderHolder(ViewGroup parent, int resId) {
        super(parent, resId);
        mSizeLetter = getView(R.id.order_size_letter);
        mSizeNumber = getView(R.id.order_size_number);
        mClothesNumber = getView(R.id.clothes_number);
    }
}
