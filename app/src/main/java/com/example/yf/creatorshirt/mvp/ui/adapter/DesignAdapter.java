package com.example.yf.creatorshirt.mvp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.listener.ItemClickListener;
import com.example.yf.creatorshirt.mvp.model.basechoice.DesignBaseBean;
import com.example.yf.creatorshirt.mvp.ui.adapter.base.BaseAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.viewholder.DesignBaseHolder;
import com.example.yf.creatorshirt.utils.Constants;

/**
 * Created by Administrator on 2017/8/13.
 */

public class DesignAdapter extends BaseAdapter<DesignBaseBean, DesignBaseHolder> {
    private ItemClickListener.OnItemClickListener clickListener;
    private int oldPosition;
    private View oldContainer;


    public DesignAdapter(Context context) {
        super(context);
    }

    @Override
    protected DesignBaseHolder createItemViewHolder(ViewGroup parent, int viewType) {
        return new DesignBaseHolder(parent, R.layout.item_base_design);
    }

    @Override
    protected void bindCustomViewHolder(final DesignBaseHolder holder, final int position) {
        holder.mClothesName.setText(mData.get(position).getBaseName());
        if (mData.get(position).isSelect()) {
            holder.llClothes.setSelected(true);
        } else {
            holder.llClothes.setSelected(false);
        }
        if (clickListener == null) {
            return;
        }
        holder.llClothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (oldContainer != null) {
                    oldContainer.setSelected(false);
                    if (oldPosition >= 0 && oldPosition < mData.size()) {
                        mData.get(oldPosition).setSelect(false);
                    }
                }
                oldContainer = v;
                oldPosition = position;
                oldContainer.setSelected(true);
                mData.get(oldPosition).setSelect(true);
                if(clickListener != null)
                clickListener.onItemClick(v, position);
            }
        });

        Glide.with(mContext).load(Constants.ImageUrl +
                mData.get(position).getGender() + mData.get(position).getBaseId() + "A_icon.png").into
                (holder.mClothesT);

    }

    public void setItemClickListener(ItemClickListener.OnItemClickListener clickListener) {
        this.clickListener = clickListener;

    }
}
