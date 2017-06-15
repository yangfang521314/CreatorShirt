package com.example.yf.creatorshirt.mvp.ui.adapter.base;

import android.content.Context;

import java.util.List;

/**
 * Created by yang on 06/06/2017.
 */

public abstract class BaseAdapter<M, VH extends BaseViewHolder> extends AbsAdapter<M, VH> {
    protected List<M> mData;

    public BaseAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemCount() {

        if(mData != null){
            return mData.size();
        }else {
            return 6;
        }
    }

    public void setData(List<M> data) {
        if (mData != null)
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
        notifyItemChanged(index);
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

    /**
     * add a cell list
     *
     * @param lists
     */
    public void addAll(List<M> lists) {
        if (lists == null || lists.size() == 0) {
            return;
        }
        mData.addAll(lists);
        notifyItemRangeChanged(mData.size() - lists.size(), mData.size());
    }

    public void addAll(int index, List<M> lists) {
        if (lists == null || lists.size() == 0) {
            return;
        }
        mData.addAll(index, lists);
        notifyItemRangeChanged(index, index + lists.size());
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

}
