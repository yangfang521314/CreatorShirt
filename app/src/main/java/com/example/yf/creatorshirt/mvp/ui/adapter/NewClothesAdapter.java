package com.example.yf.creatorshirt.mvp.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.mvp.model.VersionStyle;
import com.example.yf.creatorshirt.mvp.ui.adapter.base.BaseAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.viewholder.DesignBaseHolder;
import com.example.yf.creatorshirt.utils.FileUtils;

/**
 * Created by yangfang on 2017/11/2.
 */

public class NewClothesAdapter extends BaseAdapter<VersionStyle, DesignBaseHolder> {
    private String gender;
    private Bitmap bitmap;

    public NewClothesAdapter(Context context) {
        super(context);
    }

    @Override
    protected DesignBaseHolder createItemViewHolder(ViewGroup parent, int viewType) {
        return new DesignBaseHolder(parent, R.layout.item_new_clothes);
    }

    @Override
    protected void bindCustomViewHolder(DesignBaseHolder holder, final int position) {
        String colorName = mData.get(position).getColorName();
        String imageName = gender + mData.get(position).getType() + "_" + colorName + "_a";
        int resId = App.getInstance().getResources().getIdentifier(imageName, "mipmap", App.getInstance().getPackageName());
        bitmap = FileUtils.getSmallBitmap(App.getInstance(), resId);
        if (bitmap != null) {
            holder.mClothesT.setImageBitmap(bitmap);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    objectClickListener.onItemClick(mData);
                }
            });
        }

    }

    public void setGender(String sex) {
        gender = sex;
    }

    public void setBitmapDestory() {
        if (bitmap != null) {
            bitmap.recycle();
        }
    }
}
