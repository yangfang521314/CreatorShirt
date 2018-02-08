package com.example.yf.creatorshirt.mvp.ui.adapter.viewholder.design;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.mvp.ui.adapter.base.BaseViewHolder;
import com.example.yf.creatorshirt.utils.DisplayUtil;

/**
 * Created by yang on 15/06/2017.
 */

public class ItemViewHolder extends BaseViewHolder {
    public LinearLayout mCommonStyle;
    public ImageView mStyleImageView;
    public TextView mStyleTextView;
    public TextView mShowTypeClothes;
    public TextView mShowPriceClothes;
    public TextView mCommTextView;
    public TextView mPayState;

    public ItemViewHolder(ViewGroup parent, @LayoutRes int resId) {
        super(parent, resId);
        if(resId == R.layout.item_style_layout) {
            mStyleImageView = getView(R.id.design_icon_style);
            mStyleTextView = getView(R.id.design_text_style);
            mCommonStyle = getView(R.id.common_style);
            DisplayUtil.calculateItemWidth(App.getInstance(), mCommonStyle);
        }
        if(resId == R.layout.item_order_layout){
            mStyleImageView = getView(R.id.clothes_picture);
            mStyleTextView = getView(R.id.pay_state);
            mShowTypeClothes = getView(R.id.show_type_clothes);
            mShowPriceClothes = getView(R.id.show_clothes_price);
            mPayState = getView(R.id.pay_state);
        }
        if(resId == R.layout.item_order_size){
            mStyleTextView = getView(R.id.tv_order_position);
            mShowTypeClothes = getView(R.id.tv_gender);
            mShowPriceClothes = getView(R.id.tv_size);
            mCommTextView = getView(R.id.tv_number);
        }
    }

}
