package com.example.yf.creatorshirt.mvp.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.mvp.presenter.MotionEventPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.MotionEventContract;
import com.example.yf.creatorshirt.mvp.ui.view.sticker.SignatureDialog;
import com.example.yf.creatorshirt.mvp.ui.view.sticker.Sticker;
import com.example.yf.creatorshirt.mvp.ui.view.sticker.StickerView;
import com.example.yf.creatorshirt.mvp.ui.view.sticker.TextSticker;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Creatd by yangfang on 2017/8/26.
 */

public class ClothesBackView extends StickerView implements MotionEventContract.MotionEventView {
    @BindView(R.id.clothes)
    ImageView mClothes;//衣服
    @BindView(R.id.source_back)
    BackPatterImage mSource;//自定义图
    @BindView(R.id.mask)//遮罩
            ImageView mMask;

    private MotionEventPresenter mPresenter;

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
        mPresenter = new MotionEventPresenter();
        mPresenter.attachView(this);
        setBackgroundColor(Color.WHITE);
        setLocked(false);
        setConstrained(true);
        initSignature();//默认文字编辑
    }

    private void initSignature() {
        textSticker = new TextSticker(App.getInstance());
        setBackgroundColor(Color.WHITE);
        setLocked(false);
        setConstrained(true);
        setOnStickerOperationListener(new StickerView.OnStickerOperationListener() {
            @Override
            public void onStickerAdded(@NonNull Sticker sticker) {
            }

            @Override
            public void onStickerClicked(@NonNull Sticker sticker) {
                if (sticker instanceof TextSticker) {
                    setSignatureText(((TextSticker) sticker).getText(), true);
                }
            }

            @Override
            public void onStickerDeleted(@NonNull Sticker sticker) {
                if (sticker instanceof TextSticker) {
                    removeText(((TextSticker) sticker).getText());
                }
            }

            @Override
            public void onStickerDragFinished(@NonNull Sticker sticker) {
            }

            @Override
            public void onStickerZoomFinished(@NonNull Sticker sticker) {
            }

            @Override
            public void onStickerFlipped(@NonNull Sticker sticker) {
            }

            @Override
            public void onStickerDoubleTapped(@NonNull Sticker sticker) {
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mPresenter.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 设置自定义字体
     *
     * @param message
     * @param isNew
     */
    public void setSignatureText(String message, final boolean isNew) {
        final SignatureDialog dialog = new SignatureDialog(mContext);
        setLocked(false);
        setConstrained(true);
        setTouchFlag(false);
        dialog.show();
        if (message != null) {
            dialog.setMessage(message);
        }
        Window win = dialog.getWindow();
        if (win == null) {
            return;
        }
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        win.setAttributes(lp);
        dialog.setCompleteCallBack(new SignatureDialog.CompleteCallBack() {
            @Override
            public void onClickChoiceOrBack(View view, String s) {
                if (isNew) {
                    if (textSticker != null) {
                        textSticker.setText(s);
                        textSticker.resizeText();
                        replace(textSticker);
                        invalidate();
                    }

                } else {
                    textSticker = new TextSticker(getContext());
                    textSticker.setText(s);
                    textSticker.setTextAlign(Layout.Alignment.ALIGN_CENTER);
                    textSticker.resizeText();
                    addSticker(textSticker);
                }
            }
        });
    }

    public void saveText(TextSticker textSticker) {
        if (!TextUtils.isEmpty(textSticker.getText()) && textEntities != null) {
            for (String t : textEntities) {
                if (t.equals(textSticker.getText())) {
                    textEntities.remove(t);
                }
            }
        }
        if (!TextUtils.isEmpty(textSticker.getText())) {
            textEntities.add(textSticker.getText());
        }
    }

    public List<String> getTextEntities() {
        return textEntities;
    }

    /**
     * 背景衣服变化
     *
     * @param resource
     */
    public void setColorBg(Bitmap resource) {
        mClothes.setImageBitmap(resource);
    }

    /**
     * 形成遮罩图片
     *
     * @param maskBitmap
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
        mSource.setImageBitmap(resource);

    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public void setTouchFlag(boolean b) {
        mPresenter.setTouchFlag(b);
    }

    public void removeText(String text) {
        if (textEntities != null) {
            if (textEntities.contains(text)) {
                textEntities.remove(text);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView(this);
    }


    @Override
    public void applyMatrix() {
        mSource.applyMatrix();
    }

    @Override
    public void setScaleMatrix(float scaleFactor, float scaleFactor1, float x, float y) {
        mSource.setScaleMatrix(scaleFactor, scaleFactor1, x, y);
    }

    @Override
    public void postRotate(float degree) {
        mSource.setRotate(degree);
    }

    @Override
    public void setCurrentScale(float mScaleFactor) {
        mSource.setCurrentScale(mScaleFactor);
    }

    @Override
    public void refreshImageRect() {

    }

    @Override
    public RectF getImageRect() {
        return mSource.getImageRect();

    }

    @Override
    public void setTransMartix(float dx, float dy) {
        mSource.setTransMartix(dx, dy);
    }

    @Override
    public void mapVectors(float[] xAxis) {
        mSource.mapVectors(xAxis);
    }

    @Override
    public float getScaleCurrentF() {
        return mSource.getScaleFactor();
    }


    @Override
    public void showErrorMsg(String msg) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void stateError() {

    }

}
