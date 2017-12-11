package com.example.yf.creatorshirt.mvp.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.mvp.model.VersionStyle;
import com.example.yf.creatorshirt.mvp.ui.adapter.base.BaseAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.viewholder.DesignBaseHolder;
import com.example.yf.creatorshirt.mvp.ui.fragment.NewDesignFragment;

/**
 * Created by yangfang on 2017/12/5.
 */

public class NewMClothesAdapter extends BaseAdapter<VersionStyle, DesignBaseHolder> {
    private NewDesignFragment.OnObjectClickListener onItemClickListener;
    private String gender;

    public NewMClothesAdapter(Context context) {
        super(context);
    }

    @Override
    protected DesignBaseHolder createItemViewHolder(ViewGroup parent, int viewType) {
        return new DesignBaseHolder(parent, R.layout.item_new_clothes);
    }

    @Override
    protected void bindCustomViewHolder(final DesignBaseHolder holder, final int position) {
        String colorName = mData.get(position).getColorName();
        String imageName = gender + mData.get(position).getType() + "_" + colorName + "_a";
        final int resId = App.getInstance().getResources().getIdentifier(imageName, "mipmap", App.getInstance().getPackageName());
        if (resId != 0) {
            RequestOptions options = new RequestOptions();
            options.centerInside();
            options.diskCacheStrategy(DiskCacheStrategy.NONE);
            options.override(300, 600);
            options.centerInside();
            Glide.with(mContext).asBitmap().load(resId).apply(options).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                    holder.mClothesT.setImageBitmap(resource);
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClickListener(mData.get(position), mData);
                }
            });
        }

    }

    public void setGender(String sex) {
        gender = sex;
    }


    public String getGender() {
        return gender;
    }

    public void setOnItemClickListener(NewDesignFragment.OnObjectClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
