package com.example.yf.creatorshirt.mvp.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.ui.adapter.base.BaseAdapter;

/**
 * Created by yang on 15/06/2017.
 */

public class StyleAdapter extends BaseAdapter<Integer, ItemViewHolder> {

    public StyleAdapter(Context context) {
        super(context);
    }

    @Override
    protected ItemViewHolder createItemViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(parent, R.layout.item_style_layout);
    }

    @Override
    protected void bindCustomViewHolder(ItemViewHolder holder, int position) {
        holder.mStyleImageView.setImageResource(R.mipmap.mm);
    }

}
