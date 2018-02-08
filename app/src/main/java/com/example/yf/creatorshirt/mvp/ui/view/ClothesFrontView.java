package com.example.yf.creatorshirt.mvp.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import com.example.yf.creatorshirt.mvp.model.PictureModel;
import com.example.yf.creatorshirt.mvp.ui.view.sticker.SignatureDialog;
import com.example.yf.creatorshirt.mvp.ui.view.sticker.Sticker;
import com.example.yf.creatorshirt.mvp.ui.view.sticker.StickerView;
import com.example.yf.creatorshirt.mvp.ui.view.sticker.TextSticker;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yangfang on 2018/1/3.
 */

public class ClothesFrontView extends StickerView {
    @BindView(R.id.clothes)
    ImageView mClothes;
    @BindView(R.id.source)
    PatterImage mSource;
    @BindView(R.id.mask)
    ImageView mMask;
    private TextSticker textSticker;
    private List<String> textEntities = new ArrayList<>();//保存字体
    private Context mContext;
    //    private Bitmap source;
    private PictureModel jigsawPictureModel;
    private boolean isContain;
    private double mLastFingerDistance;
    private double mLastDegree;
    private boolean mIsDoubleFinger;
    private float mLastX;
    private float mLastY;

    private float mDownX;
    private float mDownY;
    private boolean isFlag;

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
        jigsawPictureModel = new PictureModel();
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
        mSource.setImageNetSource(resource);
        jigsawPictureModel.setBitmapPicture(resource);
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


    public void setContext(Context context) {
        this.mContext = context;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_POINTER_DOWN:
                //双指模式
                if (event.getPointerCount() == 2) {
                    //两手指的距离
                    mLastFingerDistance = distanceBetweenFingers(event);
                    //两手指间的角度
                    mLastDegree = rotation(event);
                    mIsDoubleFinger = true;
//                    mSource.invalidateMode(jigsawPictureModel);
                }
                break;
            //单指模式
            case MotionEvent.ACTION_DOWN:
                Log.e("down", "DDD" + event.getX());
                //记录上一次事件的位置
                mLastX = event.getX();
                mLastY = event.getY();
                //记录Down事件的位置
                mDownX = event.getX();
                mDownY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                switch (event.getPointerCount()) {
                    //单指模式
                    case 1:
                        if (!mIsDoubleFinger) {
                            //记录每次事件在x,y方向上移动
                            int dx = (int) (event.getX() - mLastX);
                            int dy = (int) (event.getY() - mLastY);
                            int tempX = jigsawPictureModel.getPictureX() + dx;
                            int tempY = jigsawPictureModel.getPictureY() + dy;
                            Log.e("Tag", "dx" + dx + "::::" + dy);
                            if (checkPictureLocation(jigsawPictureModel, tempX, tempY)) {
                                //检查到没有越出镂空部分才真正赋值给mPicModelTouch
                                jigsawPictureModel.setPictureX(tempX);
                                jigsawPictureModel.setPictureY(tempY);
                                //保存上一次的位置，以便下次事件算出相对位移
                                mLastX = event.getX();
                                mLastY = event.getY();
                                //修改了mPicModelTouch的位置后刷新View
//                                invalidate();
                                if (isFlag) {
                                    mSource.invalidateMode(jigsawPictureModel);
                                }
                            }
                        }
                        break;
                    //双指模式
                    case 2:
                        //算出两根手指的距离
                        double fingerDistance = distanceBetweenFingers(event);
                        //当前的旋转角度
                        double currentDegree = rotation(event);
                        //当前手指距离和上一次的手指距离的比即为图片缩放比
                        float scaleRatioDelta = (float) (fingerDistance / mLastFingerDistance);
                        float rotateDelta = (float) (currentDegree - mLastDegree);

                        float tempScaleX = scaleRatioDelta * jigsawPictureModel.getScaleX();
                        float tempScaleY = scaleRatioDelta * jigsawPictureModel.getScaleY();
                        //对缩放比做限制
                        if (Math.abs(tempScaleX) < 2.5 && Math.abs(tempScaleX) > 0.1 &&
                                Math.abs(tempScaleY) < 2.5 && Math.abs(tempScaleY) > 0.1) {
                            jigsawPictureModel.setScaleX(tempScaleX);
                            jigsawPictureModel.setScaleY(tempScaleY);
                            jigsawPictureModel.setRotate(jigsawPictureModel.getRotate() + rotateDelta);
                            //记录上一次的两手指距离以便下次计算出相对的位置以算出缩放系数
//                            invalidate();
                            if (isFlag) {
                                mSource.invalidateMode(jigsawPictureModel);
                            }
                            mLastFingerDistance = fingerDistance;
                            //记录上次的角度以便下一个事件计算出角度变化值
                        }
                        mLastDegree = currentDegree;
                        break;
                }
                break;
            //两手指都离开屏幕
            case MotionEvent.ACTION_UP:
                mIsDoubleFinger = false;
//                invalidate();
                if (isFlag) {
                    mSource.invalidateMode(jigsawPictureModel);
                }
                break;
        }
        if (isFlag) {
            return true;
        } else {
            return super.onTouchEvent(event);
        }

    }

    public TextSticker getTextSticker() {
        return textSticker;
    }

    public void setTextSticker(TextSticker textSticker) {
        this.textSticker = textSticker;
    }

    /**
     * 检查图片范围是否超出窗口,此方法还要完善
     */
    private boolean checkPictureLocation(PictureModel mPictureModel, int tempX, int tempY) {
//        Bitmap picture = mPictureModel.getBitmapPicture();
        return (tempY < mSource.getHeight() / 2 && tempY > -mSource.getHeight() / 2)
                && (tempX < mSource.getWidth() / 2) && (tempX > -mSource.getWidth() / 2);
    }


    /**
     * 计算两个手指之间的距离。
     *
     * @param event
     * @return 两个手指之间的距离
     */
    private double distanceBetweenFingers(MotionEvent event) {
        float disX = Math.abs(event.getX(0) - event.getX(1));
        float disY = Math.abs(event.getY(0) - event.getY(1));
        return Math.sqrt(disX * disX + disY * disY);
    }


    // 取旋转角度
    private float rotation(MotionEvent event) {
        double disX = (event.getX(0) - event.getX(1));
        double disY = (event.getY(0) - event.getY(1));
        double radians = Math.atan2(disY, disX);
        return (float) Math.toDegrees(radians);
    }

    /**
     * 设置view的触摸模式
     *
     * @param b
     */
    public void setTouchFlag(boolean b) {
        isFlag = b;
    }
}