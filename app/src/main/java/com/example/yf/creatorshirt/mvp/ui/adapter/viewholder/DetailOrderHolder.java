package com.example.yf.creatorshirt.mvp.ui.adapter.viewholder;

import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.ui.adapter.base.BaseViewHolder;

/**
 * Created by yangfang on 2017/12/17.
 */

public class DetailOrderHolder extends BaseViewHolder {
    public TextView mTvPosition;
    public TextView mTvGender;
    public TextView mTvSize;
    public TextView mTvPrice;
    public TextView mTvNumber;
    public TextView mDelete;

    public DetailOrderHolder(ViewGroup parent, int resId) {
        super(parent, resId);
        mTvPosition = getView(R.id.tv_order_position);
        mTvGender = getView(R.id.tv_gender);
        mTvSize = getView(R.id.tv_size);
        mTvPrice = getView(R.id.tv_price);
        mTvNumber = getView(R.id.tv_number);
        mDelete = getView(R.id.tv_delete);
    }
}
