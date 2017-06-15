package com.example.yf.creatorshirt.mvp.ui.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by yang on 06/06/2017.
 */

public abstract class AbsAdapter<M, VH extends BaseViewHolder> extends RecyclerView.Adapter<BaseViewHolder> {
    protected Context mContext;

    public AbsAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return createItemViewHolder(parent, viewType);
    }

    /**
     * 创建自定义的ViewHolder
     *
     * @param parent
     * @param viewType
     * @return
     */
    protected abstract VH createItemViewHolder(ViewGroup parent, int viewType);


    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        bindCustomViewHolder((VH) holder, position);
    }

    /**
     * 绑定自定义的ViewHolder
     *
     * @param holder
     * @param position
     */
    protected abstract void bindCustomViewHolder(VH holder, int position);

    @Override
    public int getItemCount() {
        return 0;
    }
}
