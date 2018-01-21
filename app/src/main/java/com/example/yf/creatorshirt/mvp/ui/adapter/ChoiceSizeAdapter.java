package com.example.yf.creatorshirt.mvp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.model.orders.ClothesSize;
import com.example.yf.creatorshirt.mvp.ui.adapter.base.BaseAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.viewholder.SizeViewHolder;

/**
 * Created by yangfang on 2017/9/1.
 */

public class ChoiceSizeAdapter extends BaseAdapter<ClothesSize, SizeViewHolder> {


    public ChoiceSizeAdapter(Context context) {
        super(context);
    }

    @Override
    protected SizeViewHolder createItemViewHolder(ViewGroup parent, int viewType) {
        return new SizeViewHolder(parent, R.layout.item_clothes_size);
    }

    @Override
    protected void bindCustomViewHolder(SizeViewHolder holder, final int position) {
        holder.mSizeNumber.setText(mData.get(position).getSize());
        holder.mll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickListener != null)
                clickListener.onItemClick(v,0,mData.get(position).getLetter());
            }
        });
        holder.mSize.setText(mData.get(position).getLetter());
        if(position == mData.size()-1){
            holder.mDivider.setVisibility(View.GONE);
        }
        holder.mSize.setVisibility(View.VISIBLE);

    }

}
