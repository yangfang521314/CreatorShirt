package com.example.yf.creatorshirt.mvp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.listener.ItemClickListener;
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
    private ItemClickListener.OnItemComClickListener listener;
    private ImageView mCurrentView;

    @Override
    protected AddressViewHolder createItemViewHolder(ViewGroup parent, int viewType) {
        return new AddressViewHolder(parent, R.layout.item_address_layout);
    }

    @Override
    protected void bindCustomViewHolder(AddressViewHolder holder, final int position) {
        holder.mReceiverName.setText(mData.get(position).getUserName());
        holder.mAddress.setText(mData.get(position).getAddress());
        holder.mReceiverPhone.setText(mData.get(position).getMobile());
        holder.mDefaultAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentView.setImageResource(R.mipmap.choice_not_address);
                listener.onItemClick(mData.get(position),v);
            }
        });
        holder.mEditAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(mData.get(position),v);
            }
        });
        if(position == 0){
            mCurrentView = holder.mDefaultAddress;
            mCurrentView.setImageResource(R.mipmap.choice_address);
        }

    }

    public void setOnClickListener(ItemClickListener.OnItemComClickListener listener) {
        this.listener = listener;

    }
}
