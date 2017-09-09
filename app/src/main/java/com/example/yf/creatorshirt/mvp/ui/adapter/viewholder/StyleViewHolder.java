package com.example.yf.creatorshirt.mvp.ui.adapter.viewholder;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.ui.adapter.base.BaseViewHolder;

/**
 * Created by yang on 31/07/2017.
 */

public class StyleViewHolder extends BaseViewHolder{
    public ImageView mImageView;
    public TextView mCreatorName;
    public TextView mClothePrice;
    public TextView mClothePriseNum;
    public LinearLayout mLinearLayout;
    public StyleViewHolder(ViewGroup parent, @LayoutRes int resId) {
        super(parent, resId);
        mImageView = getView(R.id.clothes_image);
        mClothePriseNum = getView(R.id.clothes_priseNum);
        mClothePrice = getView(R.id.clothes_price);
        mCreatorName = getView(R.id.creator_name);
        mLinearLayout = getView(R.id.ll_designer_clothes);
    }
}
