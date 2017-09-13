package com.example.yf.creatorshirt.mvp.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.model.orders.OrderStyleBean;
import com.example.yf.creatorshirt.mvp.ui.adapter.base.BaseAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.viewholder.DesignerOrdersViewHolder;

/**
 * Created by yangfang on 2017/9/13.
 */

public class DesignerOrdersAdapter extends BaseAdapter<OrderStyleBean,DesignerOrdersViewHolder>{
    public DesignerOrdersAdapter(Context context) {
        super(context);
    }

    @Override
    protected DesignerOrdersViewHolder createItemViewHolder(ViewGroup parent, int viewType) {
        return new DesignerOrdersViewHolder(parent, R.layout.item_designer_orders);
    }

    @Override
    protected void bindCustomViewHolder(DesignerOrdersViewHolder holder, int position) {
        if(!TextUtils.isEmpty(mData.get(position).getFinishimage())) {
            holder.mDesignerName.setText(mData.get(position).getBaseName());
            holder.mDesignerPraise.setText(mData.get(position).getPraise() + "人赞");
            Glide.with(mContext).load(mData.get(position).getFinishimage()).into(holder.mDesignerIv);
        }
    }
}
