package com.example.yf.creatorshirt.mvp.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.mvp.model.detaildesign.CommonStyleData;
import com.zhy.android.percent.support.PercentRelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yangfang on 2017/8/26.
 */

public class ClothesBackView extends PercentRelativeLayout {
    private Context mContext;
    @BindView(R.id.choice_ornament)
    ImageView mBackOrnament;
    @BindView(R.id.choice_select_arm)
    ImageView mBackArm;
    @BindView(R.id.clothes_pattern_bounds)
    RelativeLayout mBackBounds;
    @BindView(R.id.choice_select_neck)
    ImageView mBackNeck;
    @BindView(R.id.clothes_container_background)
    ImageView mBackClothes;

    public ClothesBackView(Context context) {
        super(context);
        initView(context);
    }

    public ClothesBackView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ClothesBackView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.clothes_back_layout, this);
        ButterKnife.bind(this, view);
    }

    public void setImageUrl(String imageBackUrl) {
        Log.e("tag", "ddd" + imageBackUrl);
        Glide.with(App.getInstance()).load(imageBackUrl).into(mBackClothes);
    }

    public void setArmVisibility(int visibility) {
        mBackArm.setVisibility(visibility);
    }
    public void setNeckVisibility(int visibility) {
        mBackNeck.setVisibility(visibility);
    }

    public void setOrnamentVisibility(int visibility) {
        mBackOrnament.setVisibility(visibility);
    }

    public void setNeckUrl(String neckUrl) {
        Glide.with(App.getInstance()).load(neckUrl).into(mBackNeck);
        mBackNeck.setVisibility(VISIBLE);
    }

    public void setOrnameUrl(String ornametUrl) {
        mBackOrnament.setVisibility(VISIBLE);
        Glide.with(App.getInstance()).load(ornametUrl).into(mBackNeck);
    }

    public void setArmUrl(String armUrl) {
        mBackArm.setVisibility(VISIBLE);
        Glide.with(App.getInstance()).load(armUrl).into(mBackNeck);
    }

    public void setColor(int color) {
        Log.e("ClothesBackView","c"+color);
        mBackClothes.setBackgroundResource(color);
    }

    public void setBackData(CommonStyleData mBackStyleData) {
        if (mBackStyleData.getNeckUrl() != null) {
            setNeckUrl(mBackStyleData.getNeckUrl());
        }
        if (mBackStyleData.getOrnametUrl() != null) {
            setOrnameUrl(mBackStyleData.getOrnametUrl());
        }
        if (mBackStyleData.getArmUrl() != null) {
            setArmUrl(mBackStyleData.getArmUrl());
        }
    }

    public void setColorResources(int color) {
        mBackClothes.setBackgroundColor(color);
    }
}
