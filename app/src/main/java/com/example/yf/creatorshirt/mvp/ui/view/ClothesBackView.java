package com.example.yf.creatorshirt.mvp.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.mvp.model.detaildesign.CommonStyleData;
import com.example.yf.creatorshirt.mvp.ui.activity.DetailDesignActivity;
import com.example.yf.creatorshirt.utils.Constants;
import com.example.yf.creatorshirt.utils.DisplayUtil;
import com.example.yf.creatorshirt.widget.stickerview.MyRelativeLayout;
import com.example.yf.creatorshirt.widget.stickerview.StickerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Creatd by yangfang on 2017/8/26.
 */

public class ClothesBackView extends MyRelativeLayout {
    private Context mContext;
    @BindView(R.id.choice_ornament)
    ImageView mBackOrnament;
    @BindView(R.id.choice_select_arm)
    ImageView mBackArm;
    //    @BindView(R.id.clothes_pattern_bounds)
//    RelativeLayout mBackBounds;
    @BindView(R.id.choice_select_neck)
    ImageView mBackNeck;
    @BindView(R.id.clothes_container_background)
    ImageView mBackClothes;
    @BindView(R.id.rl_back_root)
    RelativeLayout mContainerBackBackground;
    // 存储贴纸列表
    private ArrayList<View> mViews = new ArrayList<>();
    //处于编辑的贴纸
    private StickerView mCurrentStickerView;

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
    }

    public void setImageUrl(String imageBackUrl) {
        if (DisplayUtil.getScreenW(App.getInstance()) < 1080) {
            ViewGroup.LayoutParams params = mBackClothes.getLayoutParams();
            params.width = 592;
            params.height = 592;
            mBackClothes.setLayoutParams(params);
            DisplayUtil.calculateBackSize(mBackArm);
            DisplayUtil.calculateBackSize(mBackNeck);
            DisplayUtil.calculateBackSize(mBackOrnament);
            Glide.with(this).load(imageBackUrl).into(mBackClothes);
        } else {
            Glide.with(this).load(imageBackUrl).into(mBackClothes);
        }
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
        Glide.with(App.getInstance()).load(ornametUrl).into(mBackOrnament);
    }

    public void setArmUrl(String armUrl) {
        mBackArm.setVisibility(VISIBLE);
        Glide.with(App.getInstance()).load(armUrl).into(mBackArm);
    }

    public void setColor(int color) {
        mBackClothes.setBackgroundResource(color);
    }

    public void setBackData(CommonStyleData mBackStyleData) {
        if (mBackStyleData.getNeckUrl() != null) {
            setNeckUrl(Constants.ImageUrl + mBackStyleData.getNeckUrl());
        }
        if (mBackStyleData.getOrnametUrl() != null) {
            setOrnameUrl(Constants.ImageUrl + mBackStyleData.getOrnametUrl());
        }
        if (mBackStyleData.getArmUrl() != null) {
            setArmUrl(Constants.ImageUrl + mBackStyleData.getArmUrl());
        }
        if (mBackStyleData.getPattern() != null) {
            addStickerView(Constants.ImageUrl + mBackStyleData.getPattern());
        }
    }

    public void setColorResources(int color) {
        mBackClothes.setBackgroundColor(color);
    }

    /**
     * add Pattern
     *
     * @param patternUrl
     */
    public void addStickerView(String patternUrl) {
        final StickerView stickerView = new StickerView(mContext);
        RequestOptions options = new RequestOptions();
        Glide.with(App.getInstance()).asBitmap().apply(options).load(patternUrl).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                stickerView.setImageBitmap(resource);
            }
        });

        stickerView.setOperationListener(new StickerView.OperationListener() {
            @Override
            public void onDeleteClick() {
                mViews.remove(stickerView);
                mContainerBackBackground.removeView(stickerView);
            }

            @Override
            public void onEdit(StickerView stickerView) {
                mCurrentStickerView.setInEdit(false);
                mCurrentStickerView = stickerView;
                mCurrentStickerView.setInEdit(true);
            }

            @Override
            public void onTop(StickerView stickerView) {
                int position = mViews.indexOf(stickerView);
                if (position == mViews.size() - 1) {
                    return;
                }
                StickerView stickerTemp = (StickerView) mViews.remove(position);
                mViews.add(mViews.size(), stickerTemp);
            }
        });
        //反面
        //// TODO: 2017/8/27 设计高度
        RelativeLayout.LayoutParams lp;
        if (DisplayUtil.getScreenW(App.getInstance()) < 1080) {
            //正面
            lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                    , ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.height = 592;
            lp.width = 592;
        } else {
            //正面
            lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                    , ViewGroup.LayoutParams.MATCH_PARENT);
        }
        mContainerBackBackground.addView(stickerView, lp);
        mViews.add(stickerView);
        setStickerViewEdit(stickerView);
    }

    private void setStickerViewEdit(StickerView stickerView) {
        if (mCurrentStickerView != null) {
            mCurrentStickerView.setInEdit(false);
        }
        mCurrentStickerView = stickerView;
        stickerView.setInEdit(true);
    }

    public void setContext(DetailDesignActivity detailDesignActivity) {
        mContext = detailDesignActivity;
    }

    public void removeStickerView() {
        if (mCurrentStickerView != null) {
            mViews.remove(mCurrentStickerView);
            mContainerBackBackground.removeView(mCurrentStickerView);
        }

    }

    public void setChoiceDone() {
        if (mCurrentStickerView != null) {
            mCurrentStickerView.setInEdit(false);
            // TODO: 22/06/2017 完成后禁止StickerView的点击事件
            mCurrentStickerView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }
    }
}
