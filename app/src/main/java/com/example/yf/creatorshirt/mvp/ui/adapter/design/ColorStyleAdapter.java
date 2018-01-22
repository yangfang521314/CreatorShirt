package com.example.yf.creatorshirt.mvp.ui.adapter.design;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.model.VersionStyle;
import com.example.yf.creatorshirt.mvp.ui.adapter.base.BaseAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.viewholder.ColorItemViewHolder;

/**
 * Created by yangfang on 2017/8/21.
 */

public class ColorStyleAdapter extends BaseAdapter<VersionStyle, ColorItemViewHolder> {
    private View preView;
    private int prePosition;

    public ColorStyleAdapter(Context context) {
        super(context);
    }

    @Override
    protected ColorItemViewHolder createItemViewHolder(ViewGroup parent, int viewType) {
        return new ColorItemViewHolder(parent, R.layout.item_stylecolor_layout);
    }

    @Override
    protected void bindCustomViewHolder(final ColorItemViewHolder holder, final int position) {
        if (mData.get(position).isSelect()) {
            holder.itemView.setSelected(true);
            preView = holder.itemView;
            prePosition = position;
        } else {
            holder.itemView.setSelected(false);
        }
        holder.mStyleTextView.setVisibility(View.GONE);
        holder.mCommonStyle.setOnClickListener(new View.OnClickListener() {
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
                clickListener.onItemClick(holder.mCommonStyle, position,null);
            }
        });
        holder.mStyleImageView.setOutColor(Color.parseColor("#" + mData.get(position).getColor()));
    }


}
