package com.example.yf.creatorshirt.mvp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.ui.adapter.base.BaseAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.viewholder.DesignBaseHolder;

/**
 * Created by yangfang on 2017/11/2.
 */

public class NewClothesAdapter extends BaseAdapter<Integer,DesignBaseHolder>{
    public NewClothesAdapter(Context context) {
        super(context);
    }

    @Override
    protected DesignBaseHolder createItemViewHolder(ViewGroup parent, int viewType) {
        return new DesignBaseHolder(parent, R.layout.item_new_clothes);
    }

    @Override
    protected void bindCustomViewHolder(DesignBaseHolder holder, final int position) {
        holder.mClothesT.setImageResource(mData.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objectClickListener.onItemClick(mData.get(position));
            }
        });
    }
}
