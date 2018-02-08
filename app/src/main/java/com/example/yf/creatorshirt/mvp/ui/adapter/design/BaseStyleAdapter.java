package com.example.yf.creatorshirt.mvp.ui.adapter.design;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.GlideApp;
import com.example.yf.creatorshirt.mvp.listener.ItemClickListener;
import com.example.yf.creatorshirt.mvp.model.detaildesign.StyleBean;
import com.example.yf.creatorshirt.mvp.ui.adapter.base.BaseAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.viewholder.design.ItemViewHolder;
import com.example.yf.creatorshirt.utils.Constants;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by yang on 15/06/2017.
 */

public class BaseStyleAdapter extends BaseAdapter<StyleBean, ItemViewHolder> {

    private ItemClickListener.OnItemClickListener onClickListener;
    private View preView;
    private int prePosition;

    public BaseStyleAdapter(Context context) {
        super(context);
    }

    @Override
    protected ItemViewHolder createItemViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(parent, R.layout.item_style_layout);
    }

    @Override
    protected void bindCustomViewHolder(final ItemViewHolder holder, final int position) {
        if (mData.get(position).isSelect()) {
            holder.itemView.setSelected(true);
            preView = holder.itemView;
            prePosition = position;
        } else {
            holder.itemView.setSelected(false);
        }
        holder.mStyleTextView.setText(mData.get(position).getTitle());
        holder.mCommonStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (preView != null) {
                    preView.setSelected(false);
                    if (prePosition >= 0 && prePosition < mData.size()) {
                        mData.get(prePosition).setSelect(false);
                    }
                }
                prePosition = position;
                preView = v;
                preView.setSelected(true);
                mData.get(position).setSelect(true);
                onClickListener.onItemClick(holder.mCommonStyle, position, null);

            }
        });
        String name = mData.get(position).getTitle();
        String result;
        if (name == null) {
            return;
        }
        if (!"文字".equals(name)) {
            try {
                result = URLEncoder.encode(name, "UTF-8");
                if (!TextUtils.isEmpty(result)) {
                    GlideApp.with(mContext).load(Constants.ImageDetailUrl + result + ".png")
                            .centerInside()
                            .error(R.mipmap.icon_select_mask)
                            .placeholder(R.mipmap.icon_select_mask)
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .into(holder.mStyleImageView);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            setImage(R.mipmap.icon_select_text, holder.mStyleImageView);
        }


    }

    private void setImage(int image, ImageView mStyleImageView) {
        GlideApp.with(mContext).load(image)
                .centerInside()
                .skipMemoryCache(true)
                .override(300,300)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(mStyleImageView);
    }

    @Override
    public void setData(List<StyleBean> data) {
        super.setData(data);
    }

    public void setItemClickListener(ItemClickListener.OnItemClickListener itemClickListener) {
        this.onClickListener = itemClickListener;
    }
}
