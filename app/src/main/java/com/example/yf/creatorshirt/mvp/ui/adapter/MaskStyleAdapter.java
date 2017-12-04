package com.example.yf.creatorshirt.mvp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.listener.ItemClickListener;
import com.example.yf.creatorshirt.mvp.model.detaildesign.DetailColorStyle;
import com.example.yf.creatorshirt.mvp.ui.adapter.base.BaseAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.viewholder.design.ItemViewHolder;

/**
 * Created by yangfang on 2017/11/14.
 */

public class MaskStyleAdapter extends BaseAdapter<DetailColorStyle,ItemViewHolder>{

    private ItemClickListener.OnItemClickListener clickListener;

    public MaskStyleAdapter(Context context) {
        super(context);
    }

    @Override
    protected ItemViewHolder createItemViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(parent,R.layout.item_mask_layout);
    }

    @Override
    protected void bindCustomViewHolder(ItemViewHolder holder, final int position) {
        holder.mStyleImageView.setImageResource(mData.get(position).getImage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(v,position);
            }
        });
    }

    public void setOnClickListener(ItemClickListener.OnItemClickListener onClickListener) {
        this.clickListener = onClickListener;
    }
}
