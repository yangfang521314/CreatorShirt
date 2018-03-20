package com.example.yf.creatorshirt.mvp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.App;
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
    protected void bindCustomViewHolder(final DetailOrderHolder holder, final int position) {
        String[] VALUE = mData.get(position).getValue().split("c");
        if (VALUE.length == 2) {
            holder.mSizeNumber.setText(VALUE[0]);
        }
        holder.mSizeLetter.setText(mData.get(position).getSize());
        if (mData.get(position).getCount() == 0) {
            holder.mClothesNumber.setTextColor(App.getInstance().getResources().getColor(R.color.taupegray_3));

        } else {
            holder.mClothesNumber.setTextColor(App.getInstance().getResources().getColor(R.color.red_e73a3d));
        }
        holder.mClothesNumber.setText("已选：" + mData.get(position).getCount());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(null, position, mData.get(position));
            }
        });
    }

}
