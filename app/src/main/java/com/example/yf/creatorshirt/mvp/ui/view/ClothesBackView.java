package com.example.yf.creatorshirt.mvp.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.ui.view.sticker.StickerView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Creatd by yangfang on 2017/8/26.
 */

public class ClothesBackView extends StickerView {
    private Context mContext;
    @BindView(R.id.clothes)
    ImageView mClothes;
    @BindView(R.id.source)
    AnyShapeView mSource;


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
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.clothes_back_layout, this);
        ButterKnife.bind(this, view);
        setBackgroundColor(Color.WHITE);
        setLocked(false);
        setConstrained(true);
    }


    public void setContext(Context context) {
        mContext = context;
    }

    public void setChoiceDone() {
        setLocked(true);
    }

    public void removeStickerView() {
        if (getCurrentSticker() != null) {
            removeCurrentSticker();
            setLocked(true);
        }
    }

    public void setColorBg(int resource) {
        mClothes.setImageResource(resource);
    }

    public void setImageNetSource(Bitmap resource) {
        mSource.setImageNetSource(resource);
    }

    public void setImageMask(int gao) {
        mSource.setImageMask(gao);
    }
}
