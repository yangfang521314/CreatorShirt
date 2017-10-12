package com.example.yf.creatorshirt.mvp.ui.adapter.design;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.listener.ItemClickListener;
import com.example.yf.creatorshirt.mvp.model.detaildesign.DetailOtherStyle;
import com.example.yf.creatorshirt.mvp.ui.adapter.base.BaseAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.viewholder.design.ItemViewHolder;
import com.example.yf.creatorshirt.utils.Constants;

/**
 * Created by yang on 19/06/2017.
 */

public class DetailStyleAdapter extends BaseAdapter<DetailOtherStyle, ItemViewHolder> {
    private ItemClickListener.OnItemClickListener clickListener;
    private View preView;
    private int prePosition;


    public DetailStyleAdapter(Context context) {
        super(context);
    }

    @Override
    protected ItemViewHolder createItemViewHolder(ViewGroup parent, int viewType) {
        ItemViewHolder holder;
        holder = new ItemViewHolder(parent, R.layout.item_style_layout);
        return holder;
    }

    @Override
    protected void bindCustomViewHolder(final ItemViewHolder holder, final int position) {
        if (mData.get(position).isSelect()) {
            holder.itemView.setSelected(true);
            preView = holder.itemView;
            prePosition = position;
        } else {
            holder.itemView.setSelected(false);
        }
        holder.mStyleTextView.setText(mData.get(position).getName());
        if (clickListener != null) {
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
                    preView.setSelected(true);
                    mData.get(prePosition).setSelect(true);
                    clickListener.onItemClick(holder.mCommonStyle, position);

                }
            });
        }
        Glide.with(mContext).load(Constants.ImageUrl + mData.get(position).getIcon()).into(holder.mStyleImageView);

    }

    public void setOnClickListener(ItemClickListener.OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
