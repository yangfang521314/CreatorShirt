package com.example.yf.creatorshirt.mvp.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.ViewGroup;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.mvp.ui.adapter.base.BaseAdapter;

import java.util.List;

/**
 * Created by yang on 15/06/2017.
 */

public class StyleAdapter extends BaseAdapter<Integer, ItemViewHolder> {

    public StyleAdapter(Context context) {
        super(context);
    }

    @Override
    protected ItemViewHolder createItemViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(parent, R.layout.item_style_layout);
    }

    @Override
    protected void bindCustomViewHolder(ItemViewHolder holder, int position) {
        Drawable drawable = ContextCompat.getDrawable(App.getInstance(),mData.get(position));
        Log.e("TAG","DDDDD"+drawable);
        holder.mStyleImageView.setCompoundDrawables(null, null, drawable, null);
    }

    @Override
    public void setData(List<Integer> data) {
        super.setData(data);
    }
}
