package com.example.yf.creatorshirt.mvp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.mvp.model.orders.TextureEntity;
import com.example.yf.creatorshirt.mvp.ui.adapter.base.BaseAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.viewholder.SizeViewHolder;
import com.example.yf.creatorshirt.utils.DisplayUtil;

/**
 * Created by yangfang on 2017/9/28.
 */

public class TextureAdapter extends BaseAdapter<TextureEntity, SizeViewHolder> {

    public TextureAdapter(Context context) {
        super(context);
    }

    @Override
    protected SizeViewHolder createItemViewHolder(ViewGroup parent, int viewType) {
        return new SizeViewHolder(parent, R.layout.item_clothes_size);
    }

    @Override
    protected void bindCustomViewHolder(SizeViewHolder holder, final int position) {
        holder.mSizeNumber.setText(mData.get(position).getTexture());
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.mSizeNumber.getLayoutParams();
        params.width = DisplayUtil.Dp2Px(App.getInstance(), 50);
        params.height = DisplayUtil.Dp2Px(App.getInstance(), 30);
        params.rightMargin = DisplayUtil.Dp2Px(App.getInstance(), 10);
        params.leftMargin = DisplayUtil.Dp2Px(App.getInstance(), 10);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (comClickListener != null) {
                    comClickListener.onItemClick(mData.get(position).getTexture(), v);
                }
            }
        });
        if (position == mData.size() - 1) {
            holder.mDivider.setVisibility(View.GONE);
        }
    }

}
