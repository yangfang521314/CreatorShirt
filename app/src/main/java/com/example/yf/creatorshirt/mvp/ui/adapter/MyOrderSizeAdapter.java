package com.example.yf.creatorshirt.mvp.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.model.orders.ClothesSize;
import com.example.yf.creatorshirt.mvp.ui.adapter.base.BaseAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.viewholder.design.ItemViewHolder;

/**
 * Created by yang on 31/07/2017.
 */

public class MyOrderSizeAdapter extends BaseAdapter<ClothesSize, ItemViewHolder> {

    public MyOrderSizeAdapter(Context context) {
        super(context);
    }

    @Override
    protected ItemViewHolder createItemViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(parent, R.layout.item_order_size);
    }

    /**
     * mStyleTextView = getView(R.id.tv_order_position);
     * mShowTypeClothes = getView(R.id.tv_gender);
     * mShowPriceClothes = getView(R.id.tv_size);
     * mCommTextView = getView(R.id.tv_number);
     *
     * @param holder
     * @param position
     */
    @Override
    protected void bindCustomViewHolder(ItemViewHolder holder, final int position) {
        holder.mStyleTextView.setText("" + position);
        holder.mShowTypeClothes.setText("性别：" + getType(mData.get(position).getSex()));
        holder.mShowPriceClothes.setText("尺码：" + mData.get(position).getSize());
        holder.mCommTextView.setText("数量：" + mData.get(position).getCount());
    }

    private String getType(int sex) {
        switch (sex) {
            case 0:
                return "男";
            case 1:
                return "女";
            case 2:
                return "儿童";
            default:
                return "男";
        }
    }

}
