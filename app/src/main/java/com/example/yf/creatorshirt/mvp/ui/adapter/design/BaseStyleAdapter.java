package com.example.yf.creatorshirt.mvp.ui.adapter.design;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.model.detaildesign.StyleBean;
import com.example.yf.creatorshirt.mvp.ui.adapter.base.BaseAdapter;
import com.example.yf.creatorshirt.mvp.listener.ItemClickListener;
import com.example.yf.creatorshirt.mvp.ui.adapter.viewholder.design.ItemViewHolder;
import com.example.yf.creatorshirt.utils.LogUtil;

import java.util.List;

/**
 * Created by yang on 15/06/2017.
 */

public class BaseStyleAdapter extends BaseAdapter<StyleBean, ItemViewHolder> {

    private ItemClickListener.OnClickListener onClickListener;

    public BaseStyleAdapter(Context context) {
        super(context);
    }

    @Override
    protected ItemViewHolder createItemViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(parent, R.layout.item_style_layout);
    }

    @Override
    protected void bindCustomViewHolder(final ItemViewHolder holder, final int position) {
//        holder.mStyleImageView.setImageResource(mData.get(position).getImageId());
        holder.mStyleTextView.setText(mData.get(position).getTitle());
        holder.mCommonStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mCommonStyle.setPressed(true);
                onClickListener.onClick(holder.mCommonStyle,position);
            }
        });
    }

    @Override
    public void setData(List<StyleBean> data) {
        LogUtil.e("TAG", "SIZE" + data.size() + data.get(1).getTitle());
        super.setData(data);
    }

    public void setItemClickListener(ItemClickListener.OnClickListener itemClickListener) {
        this.onClickListener = itemClickListener;
    }
}
