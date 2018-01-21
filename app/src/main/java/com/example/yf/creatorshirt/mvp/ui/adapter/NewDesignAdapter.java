package com.example.yf.creatorshirt.mvp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.GlideApp;
import com.example.yf.creatorshirt.mvp.model.VersionStyle;
import com.example.yf.creatorshirt.mvp.ui.adapter.base.BaseAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.viewholder.DesignBaseHolder;
import com.example.yf.creatorshirt.utils.FileUtils;
import com.example.yf.creatorshirt.utils.RxUtils;
import com.example.yf.creatorshirt.widget.CommonObserver;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by yan
 * gfang on 2017/11/2.
 */

public class NewDesignAdapter extends BaseAdapter<VersionStyle, DesignBaseHolder> {


    public NewDesignAdapter(Context context) {
        super(context);
    }

    @Override
    protected DesignBaseHolder createItemViewHolder(ViewGroup parent, int viewType) {
        return new DesignBaseHolder(parent, R.layout.item_new_design);
    }

    @Override
    protected void bindCustomViewHolder(final DesignBaseHolder holder, final int position) {
        holder.mClothesName.setText(mData.get(position).getType());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(v,0,mData.get(position).getType());
            }
        });
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                int resId = FileUtils.getResource(mData.get(position).getColorName());
                e.onNext(resId);
            }
        }).compose(RxUtils.<Integer>rxObScheduleHelper())
                .subscribeWith(new CommonObserver<Integer>(null) {
                    @Override
                    public void onNext(Integer bitmap) {
                        if (bitmap != null) {
                            GlideApp.with(mContext).load(bitmap)
                                    .override(700,900)
                                    .skipMemoryCache(false)
                                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                                    .error(R.mipmap.mbaseball_white_a)
                                    .placeholder(R.mipmap.mpolo_azure_a)
                                    .into(holder.mClothesT);

                        }
                    }
                });


    }

}
