package com.example.yf.creatorshirt.mvp.ui.adapter;

import android.content.Context;
import android.support.v4.util.SimpleArrayMap;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.GlideApp;
import com.example.yf.creatorshirt.mvp.model.orders.SaveOrderInfo;
import com.example.yf.creatorshirt.mvp.ui.adapter.base.BaseAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.viewholder.design.ItemViewHolder;
import com.example.yf.creatorshirt.utils.Utils;

/**
 * Created by yang on 03/07/2017.
 * 订单详情页的Adapter
 */

public class AllOrderAdapter extends BaseAdapter<SaveOrderInfo, ItemViewHolder> {

    public AllOrderAdapter(Context context) {
        super(context);
    }

    @Override
    protected ItemViewHolder createItemViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(parent, R.layout.item_order_layout);
    }

    @Override
    protected void bindCustomViewHolder(ItemViewHolder holder, final int position) {
        GlideApp.with(mContext).load(mData.get(position).getAllimage1())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.mipmap.mcshort_orange_a)
                .error(R.mipmap.mcshort_orange_a)
                .into(holder.mStyleImageView);
        holder.mShowPriceClothes.setText("¥" + mData.get(position).getOrderPrice());
        SimpleArrayMap<String, String> map = Utils.getClothesName();
        if (map.containsKey(mData.get(position).getBaseId())) {
            holder.mShowTypeClothes.setText(map.get(mData.get(position).getBaseId()));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null)
                    clickListener.onItemClick(v, position, mData.get(position));
            }
        });
        if (mData.get(position).getDetailList() != null && mData.get(position).getDetailList().size() != 0) {
            if (mData.get(position).getStatus() != null) {
                if ("new".equals(mData.get(position).getStatus())) {
                    holder.mPayState.setText("未付款");
                } else {
                    holder.mPayState.setText("已付款");
                }
            }
        } else {
            holder.mPayState.setText("未选择尺寸");
        }
    }

}
