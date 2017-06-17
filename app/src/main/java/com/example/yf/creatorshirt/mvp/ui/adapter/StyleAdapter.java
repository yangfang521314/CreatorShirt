package com.example.yf.creatorshirt.mvp.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.ViewGroup;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.mvp.model.bean.StyleBean;
import com.example.yf.creatorshirt.mvp.ui.adapter.base.BaseAdapter;
import com.example.yf.creatorshirt.utils.LogUtil;

import java.util.List;

/**
 * Created by yang on 15/06/2017.
 */

public class StyleAdapter extends BaseAdapter<StyleBean, ItemViewHolder> {

    public StyleAdapter(Context context) {
        super(context);
    }

    @Override
    protected ItemViewHolder createItemViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(parent, R.layout.item_style_layout);
    }

    @Override
    protected void bindCustomViewHolder(ItemViewHolder holder, int position) {
        holder.mStyleImageView.setImageResource(mData.get(position).getImageId());
        holder.mStyleTextView.setText(mData.get(position).getTitle());
    }

    @Override
    public void setData(List<StyleBean> data) {
        LogUtil.e("TAG","SIZE"+data.size()+data.get(1).getTitle());
        super.setData(data);
    }
}
