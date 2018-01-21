package com.example.yf.creatorshirt.mvp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
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
    protected void bindCustomViewHolder(HotDesignViewHolder holder, final int position) {

        RequestOptions options = new RequestOptions()
                .error(R.mipmap.mm)
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(mContext)
                .load(mData.get(position).getHeadimage()).apply(options).into(holder.mDesignerIV);
        holder.mDesignerNum.setText(mData.get(position).getCounts()+"个原创设计");
        holder.mDesignerName.setText(mData.get(position).getName());
        holder.mDesigner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickListener != null)
                clickListener.onItemClick(v,position,null);
            }
        });
    }

}
