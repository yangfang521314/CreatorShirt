package com.example.yf.creatorshirt.mvp.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangfang on 2017/9/11.
 */

public class ImageViewAdapter extends PagerAdapter {
    private List<View> list = new ArrayList<>();
    private Context mContext;

    public ImageViewAdapter(Context detailClothesActivity) {
        mContext = detailClothesActivity;
    }

    @Override
    public int getCount() {
        if (list == null && list.size() == 0) {
            return 0;
        }
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == object;
    }

    public void setData(List<View> urlList) {
        this.list = urlList;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list.get(position));
        return list.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        object = null;
    }
}
