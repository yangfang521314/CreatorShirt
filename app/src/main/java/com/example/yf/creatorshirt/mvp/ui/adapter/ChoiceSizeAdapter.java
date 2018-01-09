package com.example.yf.creatorshirt.mvp.ui.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.mvp.listener.ItemClickListener;
import com.example.yf.creatorshirt.mvp.model.orders.ClothesSize;
import com.example.yf.creatorshirt.mvp.ui.adapter.base.BaseAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.viewholder.SizeViewHolder;
import com.example.yf.creatorshirt.utils.DisplayUtil;

/**
 * Created by yangfang on 2017/9/1.
 */

public class ChoiceSizeAdapter extends BaseAdapter<ClothesSize, SizeViewHolder> {

    private ItemClickListener.OnItemComClickListener clickListener;

    public ChoiceSizeAdapter(Context context) {
        super(context);
    }

    @Override
    protected SizeViewHolder createItemViewHolder(ViewGroup parent, int viewType) {
        return new SizeViewHolder(parent, R.layout.item_clothes_size);
    }

    @Override
    protected void bindCustomViewHolder(SizeViewHolder holder, final int position) {
        holder.mSizeNumber.setText(mData.get(position).getSize());
        DisplayUtil.calculateItemSizeWidth(mContext,holder.mll);
        holder.mll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickListener != null)
                clickListener.onItemClick(mData.get(position).getLetter(),v);
            }
        });
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.mSizeNumber.getLayoutParams();
        params.width = DisplayUtil.Dp2Px(App.getInstance(), 60);
        params.height = DisplayUtil.Dp2Px(App.getInstance(), 30);
        params.addRule(Gravity.CENTER);
        holder.mSizeNumber.setLayoutParams(params);
        holder.mSize.setText(mData.get(position).getLetter());
        if(position == mData.size()-1){
            holder.mDivider.setVisibility(View.GONE);
        }
        holder.mSize.setVisibility(View.VISIBLE);

    }

    public void setOnItemClickListener(ItemClickListener.OnItemComClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
