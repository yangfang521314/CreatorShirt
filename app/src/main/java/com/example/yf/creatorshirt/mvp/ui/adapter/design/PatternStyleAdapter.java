package com.example.yf.creatorshirt.mvp.ui.adapter.design;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.listener.ItemClickListener;
import com.example.yf.creatorshirt.mvp.model.detaildesign.DetailPatterStyle;
import com.example.yf.creatorshirt.mvp.ui.adapter.base.BaseAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.viewholder.design.ItemViewHolder;
import com.example.yf.creatorshirt.utils.Constants;
import com.example.yf.creatorshirt.utils.DisplayUtil;

/**
 * Created by yangfang on 2017/8/21.
 */

public class PatternStyleAdapter extends BaseAdapter<DetailPatterStyle, ItemViewHolder> {
    private ItemClickListener.OnItemClickListener clickListener;
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
        if (position != 0) {
            if (mData.get(position - 1).isSelect()) {
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
                        clickListener.onItemClick(holder.mCommonStyle, position);
                        if (preView != null) {
                            preView.setSelected(false);
                            if (prePosition >= 0 && prePosition < mData.size()) {
                                mData.get(prePosition).setSelect(false);
                            }
                        }
                        prePosition = position;
                        preView = v;
                        preView.setSelected(true);
                        mData.get(prePosition).setSelect(true);
                    }
                });
            }
            holder.mStyleTextView.setVisibility(View.GONE);
            ViewGroup.LayoutParams params = holder.mStyleImageView.getLayoutParams();
            if (DisplayUtil.getScreenW(mContext) < 1080) {
                params.height = 120;
                params.width = 120;
            } else {
                params.height = 200;
                params.width = 200;
            }
            holder.mStyleImageView.setLayoutParams(params);
            RequestOptions options = new RequestOptions();
            options.fitCenter();
            Glide.with(mContext).load(Constants.ImageDetailUrl + mData.get(position).getFile()).
                    into(holder.mStyleImageView);
        } else {
            holder.mStyleImageView.setImageResource(R.mipmap.add);
            holder.mStyleTextView.setText("添加本地图片");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (preView != null) {
                        preView.setSelected(false);
                        mData.get(prePosition).setSelect(false);
                    }
                    comClickListener.onItemClick(null, v);
                }
            });
        }

    }

    public void setOnClickListener(ItemClickListener.OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
