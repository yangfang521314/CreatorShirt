package com.example.yf.creatorshirt.mvp.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.model.design.DesignBaseBean;
import com.example.yf.creatorshirt.mvp.ui.adapter.base.BaseAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.viewholder.DesignBaseHolder;

/**
 * Created by Administrator on 2017/8/13.
 */

public class DesignAdapter extends BaseAdapter<DesignBaseBean,DesignBaseHolder> {
    public DesignAdapter(Context context) {
        super(context);
    }

    @Override
    protected DesignBaseHolder createItemViewHolder(ViewGroup parent, int viewType) {
        return new DesignBaseHolder(parent, R.layout.item_base_design);
    }

    @Override
    protected void bindCustomViewHolder(DesignBaseHolder holder, int position) {

    }
}
