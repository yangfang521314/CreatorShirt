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

import java.util.List;

/**
 * Created by yang on 19/06/2017.
 */

public class DetailStyleAdapter extends BaseAdapter<DetailOtherStyle, ItemViewHolder> {
    private ItemClickListener.OnItemClickListener clickListener;
    private View mBeforeView;


    public DetailStyleAdapter(Context context) {
        super(context);
    }

    @Override
    protected ItemViewHolder createItemViewHolder(ViewGroup parent, int viewType) {
        ItemViewHolder holder = null;
        holder = new ItemViewHolder(parent, R.layout.item_style_layout);
        return holder;
    }

    @Override
    protected void bindCustomViewHolder(final ItemViewHolder holder, final int position) {
        holder.mStyleTextView.setText(mData.get(position).getName());
        holder.mCommonStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(holder.mCommonStyle, position);
                v.setSelected(true);
                mBeforeView.setSelected(false);
                mBeforeView = v;
            }
        });
        Glide.with(mContext).load(Constants.ImageUrl + mData.get(position).getIcon()).into(holder.mStyleImageView);
        if (position == 0) {
            mBeforeView = holder.mCommonStyle;
            mBeforeView.setSelected(true);
        }
    }

    @Override
    public void setData(List<DetailOtherStyle> data) {
        super.setData(data);
        if (mData.size() > 2) {
            //// TODO: 20/06/2017 如size不同可能需要分类
        }
    }


    public void setOnClickListener(ItemClickListener.OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
