package com.example.yf.creatorshirt.mvp.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.model.BombStyleBean;
import com.example.yf.creatorshirt.mvp.ui.adapter.base.BaseAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.viewholder.StyleViewHolder;
import com.example.yf.creatorshirt.utils.DisplayUtil;

/**
 * Created by yang on 31/07/2017.
 */

public class BombStyleAdapter extends BaseAdapter<BombStyleBean, StyleViewHolder> {
    private Context mContext;

    public BombStyleAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected StyleViewHolder createItemViewHolder(ViewGroup parent, int viewType) {
        return new StyleViewHolder(parent, R.layout.item_square_style);
    }

    @Override
    protected void bindCustomViewHolder(StyleViewHolder holder, int position) {
        holder.mCreatorName.setText(mData.get(position).getType());
        holder.mClothePrice.setText("￥" + String.valueOf(mData.get(position).getPrice()));
        holder.mClothePriseNum.setText(String.valueOf(mData.get(position).getPriseNum()) + "人赞");
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .override(DisplayUtil.getScreenW(mContext) / 2, DisplayUtil.getScreenW(mContext) / 2)
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(mContext)
                .load(mData.get(position).getImg())
                .apply(options)
                .into(holder.mImageView);
    }
}
