package com.example.yf.creatorshirt.mvp.ui.adapter.design;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.listener.ItemClickListener;
import com.example.yf.creatorshirt.mvp.model.detaildesign.DetailColorStyle;
import com.example.yf.creatorshirt.mvp.ui.adapter.base.BaseAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.viewholder.ColorItemViewHolder;

/**
 * Created by yangfang on 2017/8/21.
 */

public class ColorStyleAdapter extends BaseAdapter<DetailColorStyle, ColorItemViewHolder> {
    private ItemClickListener.OnItemClickListener clickListener;

    public ColorStyleAdapter(Context context) {
        super(context);
    }

    @Override
    protected ColorItemViewHolder createItemViewHolder(ViewGroup parent, int viewType) {
        return new ColorItemViewHolder(parent, R.layout.item_stylecolor_layout);
    }

    @Override
    protected void bindCustomViewHolder(final ColorItemViewHolder holder, final int position) {
        holder.mStyleTextView.setText(mData.get(position).getName());
        holder.mCommonStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(holder.mCommonStyle, position);
            }
        });
        holder.mStyleImageView.setOutColor(Color.parseColor("#"+mData.get(position).getValue()));
    }

    public void setOnClickListener(ItemClickListener.OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
