package com.example.yf.creatorshirt.mvp.ui.adapter.design;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.GlideApp;
import com.example.yf.creatorshirt.mvp.model.detaildesign.DetailColorStyle;
import com.example.yf.creatorshirt.mvp.ui.activity.NewDesignActivity;
import com.example.yf.creatorshirt.mvp.ui.adapter.base.BaseAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.viewholder.design.ItemViewHolder;
import com.example.yf.creatorshirt.utils.FileUtils;

/**
 * Created by yangfang on 2017/8/21.
 */

public class PatternStyleAdapter extends BaseAdapter<DetailColorStyle, ItemViewHolder> {
    private NewDesignActivity.ChoiceAvatarListener choiceAvatarListener;

    private View preView;
    private int prePosition;

    public PatternStyleAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemCount() {
        if (mData == null) {
            Log.e("TAG", "数据为空");
            return 0;
        }
        return mData.size() + 1;
    }


    @Override
    protected ItemViewHolder createItemViewHolder(ViewGroup parent, int viewType) {
        ItemViewHolder holder;
        holder = new ItemViewHolder(parent, R.layout.item_style_layout);
        return holder;
    }

    @Override
    protected void bindCustomViewHolder(final ItemViewHolder holder, final int position) {

        if (position == 0) {
            holder.mStyleTextView.setVisibility(View.VISIBLE);
            holder.mStyleImageView.setImageResource(R.mipmap.add_clothes);
            holder.mStyleTextView.setText("添加本地图片");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    choiceAvatarListener.onItemClick(v, 0, mData.get(position));
                }
            });
        } else {
            if (mData.get(position-1).isSelect()) {
                holder.itemView.setSelected(true);
                preView = holder.itemView;
                prePosition = position;
            } else {
                holder.itemView.setSelected(false);
            }
            if (clickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickListener.onItemClick(holder.mCommonStyle, position, mData.get(position - 1).getName());
                        if (preView != null) {
                            preView.setSelected(false);
                            if (prePosition >= 1 && prePosition < mData.size()) {
                                mData.get(prePosition - 1).setSelect(false);
                            }
                        }
                        prePosition = position;
                        preView = v;
                        v.setSelected(true);
                        mData.get(position - 1).setSelect(true);
                    }
                });
            }
            holder.mStyleTextView.setVisibility(View.GONE);
            GlideApp.with(mContext).load(FileUtils.getResource(mData.get(position - 1).getName()))
                    .override(140, 140)
                    .skipMemoryCache(true)
                    .into(holder.mStyleImageView);
        }
    }

    public void setOnComClickListener(NewDesignActivity.ChoiceAvatarListener choiceAvatarListener) {
        this.choiceAvatarListener = choiceAvatarListener;
    }
}
