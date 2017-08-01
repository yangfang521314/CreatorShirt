package com.example.yf.creatorshirt.mvp.ui.adapter.viewholder;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.ui.adapter.base.BaseViewHolder;

/**
 * Created by yang on 01/08/2017.
 */

public class HotDesignViewHolder extends BaseViewHolder{
    public ImageView mDesignerIV;
    public TextView mDesignerName;
    public TextView mDesignerNum;
    public HotDesignViewHolder(ViewGroup parent, @LayoutRes int resId) {
        super(parent, resId);
        mDesignerIV = getView(R.id.designer_photo);
        mDesignerName = getView(R.id.designer_name);
        mDesignerNum = getView(R.id.designer_num);
    }
}
