package com.example.yf.creatorshirt.mvp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.app.GlideApp;
import com.example.yf.creatorshirt.mvp.model.VersionStyle;
import com.example.yf.creatorshirt.mvp.ui.adapter.base.BaseAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.viewholder.DesignBaseHolder;
import com.example.yf.creatorshirt.mvp.ui.fragment.NewDesignFragment;

/**
 * Created by yangfang on 2017/11/2.
 */

public class NewClothesAdapter extends BaseAdapter<VersionStyle, DesignBaseHolder> {
    private NewDesignFragment.OnObjectClickListener onItemClickListener;

    public NewClothesAdapter(Context context) {
        super(context);
    }

    @Override
    protected DesignBaseHolder createItemViewHolder(ViewGroup parent, int viewType) {
        return new DesignBaseHolder(parent, R.layout.item_new_clothes);
    }

    @Override
    protected void bindCustomViewHolder(final DesignBaseHolder holder, final int position) {
        String colorName = mData.get(position).getColorName();
        String imageName = mData.get(position).getSex() + mData.get(position).getType() + "_" + colorName + "_a";
        if (mData.size() > 1) {
            holder.mClothesName.setVisibility(View.VISIBLE);
            if (mData.get(position).getSex().equals("m")) {
                holder.mClothesName.setText("男");
            }
            if (mData.get(position).getSex().equals("w")) {
                holder.mClothesName.setText("女");
            }
        } else {
            holder.mClothesName.setVisibility(View.GONE);
        }

        int resId = App.getInstance().getResources().getIdentifier(imageName, "mipmap", App.getInstance().getPackageName());
        if (resId != 0) {
            GlideApp.with(mContext).asBitmap().load(resId)
                    .centerInside()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .override(300, 600).into(holder.mClothesT);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClickListener(mData.get(position), mData.get(position).getSex());
                }
            });
        }

    }


    public void setOnItemClickListener(NewDesignFragment.OnObjectClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
