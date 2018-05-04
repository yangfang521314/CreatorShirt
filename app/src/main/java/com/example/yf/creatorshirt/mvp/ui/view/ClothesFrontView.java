package com.example.yf.creatorshirt.mvp.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
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
import com.example.yf.creatorshirt.utils.DisplayUtil;
import com.example.yf.creatorshirt.utils.LogUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.http.PATCH;

/**
 * Created by yangfang on 2018/1/3.
 */

public class ClothesFrontView extends StickerView implements MotionEventContract.MotionEventView {
    private static final String TAG = "ClothesFrontView";
    @BindView(R.id.clothes)
    ImageView mClothes;
    @BindView(R.id.source)
    PatterImage mSource;
    @BindView(R.id.mask)
    ImageView mMask;

    private MotionEventPresenter mPresenter;


    public ClothesFrontView(Context context) {
        super(context);
        initView(context);
    }

    public ClothesFrontView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.clothes_front_layout, this);
        ButterKnife.bind(this, view);
        mPresenter = new MotionEventPresenter();
        mPresenter.attachView(this);
        setBackgroundColor(Color.WHITE);
        initSignature();

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

    public void initSignature() {
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
                LogUtil.e("ClothesFrontView", "remove");
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
        dialog.setCompleteCallBack((view, s) -> {
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


    public void setContext(Context context) {
        this.mContext = context;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mPresenter.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);

    }

    public TextSticker getTextSticker() {
        return textSticker;
    }

    public void setTextSticker(TextSticker textSticker) {
        this.textSticker = textSticker;
    }

    /**
     * 设置view的触摸模式
     *
     * @param b
     */
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