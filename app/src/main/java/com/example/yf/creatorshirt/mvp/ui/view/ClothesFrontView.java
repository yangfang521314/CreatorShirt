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
 * Created by yangfang on 2018/1/3.
 */

public class ClothesFrontView extends StickerView {
    @BindView(R.id.clothes)
    ClothesView mClothes;
    @BindView(R.id.source)
    PatterImage mSource;
    @BindView(R.id.mask)
    ImageView mMask;

    public ClothesFrontView(Context context) {
        super(context);
        initView(context);
    }

    public ClothesFrontView(Context context, AttributeSet attrs) {
        super(context, attrs);
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