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
import com.example.yf.creatorshirt.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Creatd by yangfang on 2017/8/26.
 */

public class ClothesBackView extends StickerView {
    @BindView(R.id.clothes)
    ClothesView mClothes;
    @BindView(R.id.source)
    PatterImage mSource;
    @BindView(R.id.mask)
    ImageView mMask;

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


    public void setChoiceDone() {
        setLocked(true);
    }

    public void removeStickerView() {
        if (getCurrentSticker() != null) {
            removeCurrentSticker();
            setLocked(true);
        }
    }


    /**
     * 背景衣服变化
     *
     * @param resource
     */
    public void setColorBg(int resource) {
        mClothes.setImageSource(Utils.getBitmapResource(resource));

    }

    /**
     * 形成遮罩图片
     *  @param maskBitmap
     *
     */
    public void setImageMask(final Bitmap maskBitmap) {
        mMask.setImageBitmap(maskBitmap);
    }

    /**
     * 放大缩小自定义图片
     *
     * @param resource
     */
    public void setImageSource(Bitmap resource) {
        mSource.setImageNetSource(resource);
    }
}
