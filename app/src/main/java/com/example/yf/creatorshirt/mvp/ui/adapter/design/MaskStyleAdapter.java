package com.example.yf.creatorshirt.mvp.ui.adapter.design;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.GlideApp;
import com.example.yf.creatorshirt.mvp.model.detaildesign.DetailColorStyle;
import com.example.yf.creatorshirt.mvp.ui.adapter.base.BaseAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.viewholder.design.ItemViewHolder;
import com.example.yf.creatorshirt.utils.FileUtils;

/**
 * Created by yangfang on 2017/11/14.
 */

public class MaskStyleAdapter extends BaseAdapter<DetailColorStyle, ItemViewHolder> {
    private View preView;
    private int prePosition;

    public MaskStyleAdapter(Context context) {
        super(context);
    }

    @Override
    protected ItemViewHolder createItemViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(parent, R.layout.item_mask_layout);
    }

    @Override
    protected void bindCustomViewHolder(ItemViewHolder holder, final int position) {
        if (mData.get(position).isSelect()) {
            holder.itemView.setSelected(true);
            preView = holder.itemView;
            prePosition = position;
        } else {
            holder.itemView.setSelected(false);
        }
        holder.mStyleTextView.setVisibility(View.GONE);
        GlideApp.with(mContext).load(FileUtils.getResource(mData.get(position).getName()))
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .override(600, 800)
                .into(holder.mStyleImageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (preView != null) {
                    preView.setSelected(false);
                    if (prePosition >= 0 && prePosition < mData.size()) {
                        mData.get(prePosition).setSelect(false);
                    }
                }
                prePosition = position;
                preView = v;
                v.setSelected(true);
                mData.get(position).setSelect(true);
                clickListener.onItemClick(v, position, mData.get(position).getName());
            }
        });
    }
}
