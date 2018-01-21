package com.example.yf.creatorshirt.mvp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.model.AddressBean;
import com.example.yf.creatorshirt.mvp.ui.adapter.base.BaseAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.viewholder.AddressViewHolder;
import com.example.yf.creatorshirt.utils.DisplayUtil;

/**
 * Created by yang on 29/06/2017.
 */

public class AddressAdapter extends BaseAdapter<AddressBean, AddressViewHolder> {

    public AddressAdapter(Context context) {
        super(context);
    }

    private ImageView mCurrentView;
    private boolean isChoice;

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
                clickListener.onItemClick(v, position, mData.get(position));
            }
        });
        holder.mEditAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(v, position, mData.get(position));
            }
        });
        if (position == 0) {
            mCurrentView = holder.mDefaultAddress;
            mCurrentView.setImageResource(R.mipmap.choice_address);
        }
        if (isChoice) {
            holder.mDefaultAddress.setVisibility(View.GONE);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.mReceiverName.getLayoutParams();
            layoutParams.leftMargin = DisplayUtil.Dp2Px(mContext, 10);
            holder.mReceiverName.setLayoutParams(layoutParams);
            RelativeLayout.LayoutParams Params = (RelativeLayout.LayoutParams) holder.mAddress.getLayoutParams();
            Params.leftMargin = DisplayUtil.Dp2Px(mContext, 10);
            holder.mAddress.setLayoutParams(Params);
            holder.mDefaultAddress.clearFocus();
            holder.mAddressChoice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickListener != null)
                        clickListener.onItemClick(view, position, mData.get(position));
                }
            });
        }
    }


    public void setChoice(boolean b) {
        isChoice = b;
    }
}
