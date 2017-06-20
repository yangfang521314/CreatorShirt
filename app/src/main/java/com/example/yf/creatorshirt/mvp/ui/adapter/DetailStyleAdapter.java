package com.example.yf.creatorshirt.mvp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.model.bean.StyleBean;
import com.example.yf.creatorshirt.mvp.ui.adapter.base.BaseAdapter;
import com.example.yf.creatorshirt.mvp.ui.view.ItemClickListener;

import java.util.List;

/**
 * Created by yang on 19/06/2017.
 */

public class DetailStyleAdapter extends BaseAdapter<StyleBean, ItemViewHolder> {
    public static int TWO_ITEM = 1;
    public static int ONE_ITEM = 2;
    private ItemClickListener.OnItemClickListener clickListener;

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
        holder.mStyleImageView.setImageResource(mData.get(position).getImageId());
        holder.mStyleTextView.setText(mData.get(position).getTitle());
        holder.mCommonStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(holder.mCommonStyle, position);
            }
        });
    }

    @Override
    public void setData(List<StyleBean> data) {
        super.setData(data);
        if (mData.size() > 2) {
            //// TODO: 20/06/2017 如数两不同可能需要分类 
        }
    }


    public void setOnClickListener(ItemClickListener.OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
