package com.example.yf.creatorshirt.mvp.ui.adapter.design;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.listener.ItemClickListener;
import com.example.yf.creatorshirt.mvp.model.detaildesign.DetailPatterStyle;
import com.example.yf.creatorshirt.mvp.ui.adapter.base.BaseAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.viewholder.design.ItemViewHolder;
import com.example.yf.creatorshirt.utils.Constants;
import com.example.yf.creatorshirt.utils.DisplayUtil;

/**
 * Created by yangfang on 2017/8/21.
 */

public class PatternStyleAdapter extends BaseAdapter<DetailPatterStyle, ItemViewHolder> {
    private ItemClickListener.OnItemClickListener clickListener;

    public PatternStyleAdapter(Context context) {
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
        holder.mCommonStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(holder.mCommonStyle, position);
            }
        });
        holder.mStyleTextView.setVisibility(View.GONE);
        ViewGroup.LayoutParams params = holder.mStyleImageView.getLayoutParams();
        if (DisplayUtil.getScreenW(mContext) < 1080) {
            params.height = 120;
            params.width = 120;
        } else {
            params.height = 200;
            params.width = 200;
        }
        holder.mStyleImageView.setLayoutParams(params);
        RequestOptions options = new RequestOptions();
        options.fitCenter();
        Glide.with(mContext).load(Constants.ImageDetailUrl + mData.get(position).getFile()).
                into(holder.mStyleImageView);

    }

    public void setOnClickListener(ItemClickListener.OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
