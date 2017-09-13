package com.example.yf.creatorshirt.mvp.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.model.AddressBean;
import com.example.yf.creatorshirt.mvp.ui.adapter.base.BaseAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.viewholder.AddressViewHolder;

/**
 * Created by yang on 29/06/2017.
 */

public class AddressAdapter extends BaseAdapter<AddressBean, AddressViewHolder> {

    public AddressAdapter(Context context) {
        super(context);
    }

    @Override
    protected AddressViewHolder createItemViewHolder(ViewGroup parent, int viewType) {
        return new AddressViewHolder(parent, R.layout.item_address_layout);
    }

    @Override
    protected void bindCustomViewHolder(AddressViewHolder holder, int position) {
        holder.mReceiverName.setText(mData.get(position).getUserName());
        holder.mAddress.setText(mData.get(position).getAddress());
        holder.mReceiverPhone.setText(mData.get(position).getMobile());
    }
}
