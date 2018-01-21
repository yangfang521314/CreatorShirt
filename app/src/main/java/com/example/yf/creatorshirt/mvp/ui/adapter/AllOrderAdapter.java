package com.example.yf.creatorshirt.mvp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.model.AllOrderBean;
import com.example.yf.creatorshirt.mvp.ui.adapter.base.BaseAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.viewholder.OrderViewHolder;

/**
 * Created by yang on 03/07/2017.
 * 订单详情页的Adapter
 */

public class AllOrderAdapter extends BaseAdapter<AllOrderBean, OrderViewHolder> {

    public AllOrderAdapter(Context context) {
        super(context);
    }

    @Override
    protected OrderViewHolder createItemViewHolder(ViewGroup parent, int viewType) {
        return new OrderViewHolder(parent, R.layout.item_order_layout);
    }

    @Override
    protected void bindCustomViewHolder(OrderViewHolder holder, final int position) {
        //// TODO: 03/07/2017 真实数据的绑定
        holder.mRlOrderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickListener != null)
                clickListener.onItemClick(v,position,null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 5;
    }

}
