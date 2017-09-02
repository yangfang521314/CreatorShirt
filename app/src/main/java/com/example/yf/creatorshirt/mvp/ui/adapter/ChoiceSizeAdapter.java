package com.example.yf.creatorshirt.mvp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.listener.ItemClickListener;
import com.example.yf.creatorshirt.mvp.model.orders.ClothesSize;
import com.example.yf.creatorshirt.mvp.ui.adapter.base.BaseAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.viewholder.SizeViewHolder;
import com.example.yf.creatorshirt.utils.DisplayUtil;

/**
 * Created by yangfang on 2017/9/1.
 */

public class ChoiceSizeAdapter extends BaseAdapter<ClothesSize, SizeViewHolder> {

    private ItemClickListener.OnItemClickListener clickListener;

    public ChoiceSizeAdapter(Context context) {
        super(context);
    }

    @Override
    protected SizeViewHolder createItemViewHolder(ViewGroup parent, int viewType) {
        return new SizeViewHolder(parent, R.layout.item_clothes_size);
    }

    @Override
    protected void bindCustomViewHolder(SizeViewHolder holder, final int position) {
        holder.mSizeX.setText(mData.get(position).getLetter());
        holder.mSizeNumber.setText(mData.get(position).getSize());
        DisplayUtil.calculateItemSizeWidth(mContext,holder.mll);
        holder.mll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(v,position);
            }
        });

    }

    public void setOnItemClickListener(ItemClickListener.OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
