package com.example.yf.creatorshirt.mvp.ui.adapter.viewholder;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    public ImageView mClothesT;

    public DesignBaseHolder(ViewGroup parent, @LayoutRes int resId) {
        super(parent, resId);
        if(resId == R.layout.item_base_design) {
            llClothes = getView(R.id.rl_clothes);
            mClothesName = getView(R.id.clothes_name);
            mClothesT = getView(R.id.clothes_total_iv);
            DisplayUtil.calculateHHWidth(getContext(), llClothes);
        }else if(resId ==R.layout.item_new_design) {
            mClothesName = getView(R.id.tv_new_title);
            mClothesT = getView(R.id.iv_base_clothes);
        }else if(resId == R.layout.item_new_clothes){
            mClothesT = getView(R.id.clothes_image);
            mClothesName =getView(R.id.tv_show_sex);
        }
    }
}
