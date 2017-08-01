package com.example.yf.creatorshirt.mvp.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.model.HotDesignsBean;
import com.example.yf.creatorshirt.mvp.ui.adapter.base.BaseAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.viewholder.HotDesignViewHolder;

/**
 * Created by yang on 31/07/2017.
 */

public class HotDesignStyleAdapter extends BaseAdapter<HotDesignsBean, HotDesignViewHolder> {
    private Context mContext;

    public HotDesignStyleAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected HotDesignViewHolder createItemViewHolder(ViewGroup parent, int viewType) {
        return new HotDesignViewHolder(parent, R.layout.item_hot_design);
    }

    @Override
    protected void bindCustomViewHolder(HotDesignViewHolder holder, int position) {
        Glide.with(mContext)
                .load(mData.get(position).getHeadImage()).into(holder.mDesignerIV);
        holder.mDesignerNum.setText(String.valueOf(mData.get(position).getDesignNum()));
        holder.mDesignerName.setText(mData.get(position).getNickname());
    }
}
