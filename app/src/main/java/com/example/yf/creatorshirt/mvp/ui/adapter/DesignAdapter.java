package com.example.yf.creatorshirt.mvp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.listener.ItemClickListener;
import com.example.yf.creatorshirt.mvp.model.basechoice.DesignBaseBean;
import com.example.yf.creatorshirt.mvp.ui.adapter.base.BaseAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.viewholder.DesignBaseHolder;

/**
 * Created by Administrator on 2017/8/13.
 */

public class DesignAdapter extends BaseAdapter<DesignBaseBean, DesignBaseHolder> {
    private ItemClickListener.OnItemClickListener clickListener;

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

        holder.llClothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(v, position);
            }
        });
//        Glide.with(mContext).asBitmap().load(com.example.yf.creatorshirt.utils.Constants.ImageUrl+
//        "A"+mData.get(position).getBaseId()+mData.get(position).getGender()+"_icon.png").listener(new SimpleTarget<Bitmap>() {
//
//            @Override
//            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
////                holder.mClothesName.setCompoundDrawablesWithIntrinsicBounds(0,);
//            }
//        });
    }

    public void setItemClickListener(ItemClickListener.OnItemClickListener clickListener) {
        this.clickListener = clickListener;

    }
}
