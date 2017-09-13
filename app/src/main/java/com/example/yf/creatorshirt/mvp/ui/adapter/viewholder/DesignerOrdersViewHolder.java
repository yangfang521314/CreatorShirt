package com.example.yf.creatorshirt.mvp.ui.adapter.viewholder;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.ui.adapter.base.BaseViewHolder;
import com.example.yf.creatorshirt.utils.DisplayUtil;

/**
 * Created by yangfang on 2017/9/13.
 **/
public class DesignerOrdersViewHolder extends BaseViewHolder {
    public ImageView mDesignerIv;
    public TextView mDesignerName;
    public TextView mDesignerPraise;
    public LinearLayout mLinearDesigner;

    public DesignerOrdersViewHolder(ViewGroup parent, @LayoutRes int resId) {
        super(parent, resId);
        mDesignerIv = getView(R.id.designer_clothes_iv);
        mDesignerName = getView(R.id.designer_clothes_name);
        mDesignerPraise = getView(R.id.designer_clothes_praise);
        mLinearDesigner = getView(R.id.ll_designer_item);
        DisplayUtil.calculateDesignerClothesWidth(getContext(),mLinearDesigner);
    }
}
