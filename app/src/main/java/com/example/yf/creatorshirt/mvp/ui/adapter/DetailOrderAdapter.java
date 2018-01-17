package com.example.yf.creatorshirt.mvp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.model.orders.ClothesSize;
import com.example.yf.creatorshirt.mvp.ui.adapter.base.BaseAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.viewholder.DetailOrderHolder;

/**
 * Created by yangfang on 2017/12/17.
 */

public class DetailOrderAdapter extends BaseAdapter<ClothesSize, DetailOrderHolder> {


    public DetailOrderAdapter(Context context) {
        super(context);
    }

    @Override
    protected DetailOrderHolder createItemViewHolder(ViewGroup parent, int viewType) {
        return new DetailOrderHolder(parent, R.layout.item_detail_order);
    }

    @Override
    protected void bindCustomViewHolder(DetailOrderHolder holder, final int position) {
        holder.mTvPosition.setText(String.valueOf(position + 1));
        holder.mTvGender.setText("性别：" + mData.get(position).getLetter());
        holder.mTvSize.setText("尺码：" + mData.get(position).getSize());
        holder.mTvNumber.setText("数量：" + mData.get(position).getCount());
//        holder.mTvPrice.setText("¥：" + mData.get(position).getNumbers() * mData.get(position).getPrices());
        holder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objectClickListener.onItemClick(mData.get(position));
            }
        });
    }

}
