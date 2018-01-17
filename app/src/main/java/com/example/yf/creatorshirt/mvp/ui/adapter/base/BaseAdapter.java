package com.example.yf.creatorshirt.mvp.ui.adapter.base;

import android.content.Context;
import android.util.Log;

import com.example.yf.creatorshirt.mvp.listener.ItemClickListener;

import java.util.List;

/**
 * Created by yang on 06/06/2017.
 */

public abstract class BaseAdapter<M, VH extends BaseViewHolder> extends AbsAdapter<M, VH> {
    protected List<M> mData;
    protected ItemClickListener.OnItemClickListener clickListener;
    protected ItemClickListener.OnItemObjectClickListener objectClickListener;
    protected ItemClickListener.OnItemComClickListener comClickListener;

    public BaseAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemCount() {
        if (mData == null) {
            Log.e("BaseAdapter", "数据为空");
            return 0;
        }
        return mData.size();
    }


    public void setOnItemClickListener(ItemClickListener.OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setOnItemClickListener(ItemClickListener.OnItemObjectClickListener clickListener) {
        this.objectClickListener = clickListener;
    }

    public void setOnComClickListener(ItemClickListener.OnItemComClickListener clickListener) {
        this.comClickListener = clickListener;
    }

    public void setData(List<M> data) {
        this.mData = data;
    }

    /**
     * 根据position获取数据
     *
     * @param position
     * @return
     */
    public M getItem(int position) {
        return mData.get(position);
    }

    public M getItem(VH viewHolder) {
        return getItem(viewHolder.getAdapterPosition());
    }

    /**
     * add one data
     *
     * @param data
     */
    public void add(M data) {
        mData.add(data);
        int index = mData.indexOf(data);
        notifyItemChanged(index);
    }

    public void add(int index, M data) {
        mData.add(index, data);
        notifyItemInserted(index);
    }

    /**
     * remove a data
     *
     * @param data
     */
    public void remove(M data) {
        int indexOfCell = mData.indexOf(data);
        remove(indexOfCell);
    }

    public void remove(int index) {
        mData.remove(index);
        notifyItemRemoved(index);
    }

    /**
     * @param start
     * @param count
     */
    public void remove(int start, int count) {
        if ((start + count) > mData.size()) {
            return;
        }
        int size = getItemCount();
        for (int i = start; i < size; i++) {
            mData.remove(i);
        }

        notifyItemRangeRemoved(start, count);
    }


    public void addMore(List<M> items) {
        int startPosition = mData.size();
        mData.addAll(items);
        notifyItemRangeInserted(startPosition, mData.size());
    }

}
