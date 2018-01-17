package com.example.yf.creatorshirt.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.app.GlideApp;
import com.example.yf.creatorshirt.common.ChangeSelectEvent;
import com.example.yf.creatorshirt.mvp.listener.ItemClickListener;
import com.example.yf.creatorshirt.mvp.model.VersionStyle;
import com.example.yf.creatorshirt.mvp.model.detaildesign.DetailColorStyle;
import com.example.yf.creatorshirt.mvp.model.detaildesign.StyleBean;
import com.example.yf.creatorshirt.mvp.model.detaildesign.TextEntity;
import com.example.yf.creatorshirt.mvp.presenter.CommonAvatarPresenter;
import com.example.yf.creatorshirt.mvp.presenter.DetailDesignPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.CommonAvatarContract;
import com.example.yf.creatorshirt.mvp.presenter.contract.DetailDesignContract;
import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;
import com.example.yf.creatorshirt.mvp.ui.adapter.MaskStyleAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.TextStyleAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.design.BaseStyleAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.design.ColorStyleAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.design.PatternStyleAdapter;
import com.example.yf.creatorshirt.mvp.ui.view.ClothesBackView;
import com.example.yf.creatorshirt.mvp.ui.view.ClothesFrontView;
import com.example.yf.creatorshirt.mvp.ui.view.EditUserPopupWindow;
import com.example.yf.creatorshirt.mvp.ui.view.sticker.SignatureDialog;
import com.example.yf.creatorshirt.mvp.ui.view.sticker.Sticker;
import com.example.yf.creatorshirt.mvp.ui.view.sticker.StickerView;
import com.example.yf.creatorshirt.mvp.ui.view.sticker.TextSticker;
import com.example.yf.creatorshirt.utils.Constants;
import com.example.yf.creatorshirt.utils.FileUtils;
import com.example.yf.creatorshirt.utils.ToastUtil;
import com.example.yf.creatorshirt.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class NewDesignActivity extends BaseActivity<DetailDesignPresenter> implements ItemClickListener.OnItemClickListener,
        ItemClickListener.OnClickListener, DetailDesignContract.DetailDesignView, CommonAvatarContract.CommonAvatarView {
    public static final String COLOR = "color";
    public static final String PATTERN = "pattern";
    public static final String MASK = "mask";
    public static final String SIGNATURE = "signature";


    @BindView(R.id.design_choice_style)
    RecyclerView mRecyclerDetailStyle;//具体设计的recyclerView
    @BindView(R.id.choice_style)
    RecyclerView mRecyclerStyle;//style的recyclerView
    @BindView(R.id.ll_choice_back)
    LinearLayout mChoiceReturn;
    @BindView(R.id.btn_choice_finish)
    Button mBtnFinish;
    @BindView(R.id.rl_design)
    RelativeLayout mDesign;
    @BindView(R.id.rl_clothes_root)
    ClothesFrontView mContainerFront;
    @BindView(R.id.tv_back)
    TextView mButtonBack;
    @BindView(R.id.tv_front)
    TextView mButtonFront;
    @BindView(R.id.rl_bg)
    RelativeLayout mRelative;
    @BindView(R.id.rl_clothes_root_back)
    ClothesBackView mContainerBack;

    private CommonAvatarPresenter mAvatarPresenter;//相机图片选择代码块

    private ArrayMap<String, List<DetailColorStyle>> mPatternData = new ArrayMap<>();
    private ArrayMap<String, List<VersionStyle>> mClothesData = new ArrayMap<>();
    private ArrayMap<String, List<DetailColorStyle>> mMaskData = new ArrayMap<>();
    private ArrayMap<String, List<DetailColorStyle>> mSignatureData = new ArrayMap<>();
    private List<TextEntity> textEntities = new ArrayList<>();//保存字体
    private List<StyleBean> newList = new ArrayList<>();    //总样式的集合
    private List<String> clotheKey = new ArrayList<>();    //具体样式的字段名

    private ArrayList<VersionStyle> mListClothes;
    private View mBeforeView;
    private View mCurrentView;
    private int mCurrentPosition;
    private View mDesCurrentView;
    private View mDesBeforeView;
    private String mImagecolor;    //字体的颜色
    private String patternFrontUrl;    //自定义的贴图
    private String patternBackUrl;    //自定义的贴图
    private int prePosition;
    private TextSticker textSticker;    //自定义文字框
    private Typeface mUpdateType;    //字体
    private VersionStyle mInitData;//默认显示第一件衣服
    private VersionStyle mOrderBaseInfo; //衣服信息
    private boolean isFirst;
    private VersionStyle mCurrentClothes;//显示当前的衣服字段
    private VersionStyle mBeforeClothes;//显示之前的字段
    private String finishBackImage;//最终背景衣服
    private String finishFrontImage;
    private Bitmap currentFrontMask;
    private Bitmap currentBackMask;
//    private String maskA;
//    private String maskB;


    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getView() {
        return R.layout.activity_news_design;
    }

    @Override
    public void initData() {
        super.initData();
        if (getIntent() != null) {
            mListClothes = getIntent().getParcelableArrayListExtra("clothes");
            mInitData = getIntent().getParcelableExtra("choice");
        }

        mPresenter.getDetailDesign(mListClothes, true);
        mAvatarPresenter = new CommonAvatarPresenter(this);
        mAvatarPresenter.attachView(this);
    }

    @Override
    protected void initView() {
        mRecyclerStyle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        //默认显示正面
        isFirst = true;
        initFront();
        initSignature();//默认文字编辑
    }

    private void initSignature() {
        textSticker = new TextSticker(this);
        mContainerFront.setBackgroundColor(Color.WHITE);
        mContainerFront.setLocked(false);
        mContainerFront.setConstrained(true);
        mContainerFront.setOnStickerOperationListener(new StickerView.OnStickerOperationListener() {
            @Override
            public void onStickerAdded(@NonNull Sticker sticker) {
            }

            @Override
            public void onStickerClicked(@NonNull Sticker sticker) {
                if (sticker instanceof TextSticker) {
                    mUpdateType = ((TextSticker) sticker).getTypeface();
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

    private void initFront() {
        mButtonFront.setSelected(true);
        if (mInitData != null) {
            getCurrentFrontClothes(mInitData);
        }
        mOrderBaseInfo = new VersionStyle();

    }

    @OnClick({R.id.btn_choice_finish, R.id.choice_done, R.id.choice_back, R.id.tv_front, R.id.tv_back,
            R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_choice_finish:
                if (mContainerBack.getWidth() <= 0) {
                    ToastUtil.showToast(mContext, "没有定制背面,请先定制背面", 0);
                } else {
                    generateBitmap();//生成衣服mask的图片
                    if (finishBackImage != null && finishFrontImage != null) {
                        startNewActivity();
                    }
                }
                break;
            case R.id.back:
                finish();
                break;
            case R.id.tv_front:
                if (isFirst) {//默认刚进来的第一件衣服
                    if (mInitData != null) {
                        getCurrentFrontClothes(mInitData);
                    }
                } else {
                    getCurrentFrontClothes(mCurrentClothes);
                }
                mContainerFront.setVisibility(View.VISIBLE);
                mContainerBack.setVisibility(View.GONE);
                mButtonFront.setSelected(true);
                mButtonBack.setSelected(false);
                mPresenter.getDetailDesign(mListClothes, true);
                mRecyclerStyle.setVisibility(View.VISIBLE);
                mRecyclerDetailStyle.setVisibility(View.GONE);
                mBtnFinish.setVisibility(View.VISIBLE);
                mChoiceReturn.setVisibility(View.GONE);
                break;
            case R.id.tv_back:
                if (isFirst) {//默认刚进来的第一件衣服
                    if (mInitData != null) {
                        getCurrentBackClothes(mInitData);
                    }
                } else {
                    getCurrentBackClothes(mCurrentClothes);
                }
                mContainerBack.setVisibility(View.VISIBLE);
                mContainerFront.setVisibility(View.GONE);
                mButtonBack.setSelected(true);
                mButtonFront.setSelected(false);
                mPresenter.getDetailDesign(mListClothes, false);
                mRecyclerStyle.setVisibility(View.VISIBLE);
                mRecyclerDetailStyle.setVisibility(View.GONE);
                mBtnFinish.setVisibility(View.VISIBLE);
                mChoiceReturn.setVisibility(View.GONE);
                break;
            case R.id.choice_back:
                switch (clotheKey.get(mCurrentPosition)) {
                    case COLOR:
                        //choice back ,the clothe is before data
                        if (mBeforeClothes != null) {
                            mCurrentClothes = mBeforeClothes;
                            if (mButtonFront.isSelected()) {
                                setColorBg(getClothesFrontBG(mBeforeClothes));
                                if (mButtonBack.isSelected()) {
                                    setColorBackBg(getClothesBackBG(mBeforeClothes));
                                }
                            }
                        }
                        break;
                    case PATTERN:
                        if (mButtonFront.isSelected()) {
                            mContainerFront.setImageSource(null);
                        }
                        if (mButtonBack.isSelected()) {
                            mContainerBack.setImageSource(null);
                        }
                        break;
                    case MASK:
                        if (mButtonFront.isSelected()) {
                            mContainerFront.setImageMask(null);
                        }
                        if (mButtonBack.isSelected()) {
                            mContainerBack.setImageMask(null);
                        }
                        break;
                    case SIGNATURE:
                        if (!mContainerFront.isNoneSticker()) {
                            mContainerFront.removeCurrentSticker();
                            mContainerFront.setLocked(true);
                        }
                        break;

                }
                mChoiceReturn.setVisibility(View.GONE);
                mRecyclerDetailStyle.setVisibility(View.GONE);
                mRecyclerStyle.setVisibility(View.VISIBLE);
                break;
            case R.id.choice_done://点击每个样式的完成保存到数据
                switch (clotheKey.get(mCurrentPosition)) {
                    case COLOR:
                        mOrderBaseInfo.setColor(mCurrentClothes.getColor());
                        break;
                    case PATTERN:
//                        if (patternBackUrl != null) {
//                            avatarList.add(patternBackUrl);
//                        }
//                        if (patternFrontUrl != null) {
//                            avatarList.add(patternFrontUrl);
//                        }
                        break;
                    case SIGNATURE:
                        if (mContainerFront.getCurrentSticker() != null) {
                            TextSticker textSticker = (TextSticker) mContainerFront.getCurrentSticker();
                            if (!Constants.ADD_TEXT.equals(textSticker.getText())) {
                                saveText(textSticker);
                            } else {
                                return;
                            }
                        }
                        if (mDesCurrentView == null) {
                            mRecyclerStyle.setVisibility(View.VISIBLE);
                            mRecyclerDetailStyle.setVisibility(View.GONE);
                            mBtnFinish.setVisibility(View.VISIBLE);
                            mChoiceReturn.setVisibility(View.GONE);
                            mContainerFront.setLocked(true);
                            mUpdateType = null;
                        }
                        break;
                }
                if (mDesCurrentView != null) {
                    mChoiceReturn.setVisibility(View.GONE);
                    mRecyclerDetailStyle.setVisibility(View.GONE);
                    mRecyclerStyle.setVisibility(View.VISIBLE);
                    mContainerFront.setLocked(true);
                }
                break;
        }
        mBtnFinish.setVisibility(View.VISIBLE);
    }

    private void generateBitmap() {
        if (mContainerFront.getWidth() > 0) {
            Bitmap bitmap = Bitmap.createBitmap(mContainerFront.getWidth(), mContainerFront.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            mContainerFront.draw(canvas);
            finishFrontImage = FileUtils.saveBitmap(bitmap, this, "front");

        }
        if (mContainerBack.getWidth() > 0) {
            Bitmap bitmapBack = Bitmap.createBitmap(mContainerBack.getWidth(), mContainerBack.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmapBack);
            mContainerBack.draw(canvas);
            finishBackImage = FileUtils.saveBitmap(bitmapBack, this, "back");
        }

    }

    private void getCurrentBackClothes(VersionStyle data) {
        mContainerBack.setColorBg(getClothesBackBG(data));
    }

    private void getCurrentFrontClothes(VersionStyle data) {
        setColorBg(getClothesFrontBG(data));
        mCurrentClothes = data;
        mBeforeClothes = mCurrentClothes;
    }

    private void startNewActivity() {
        Bundle bundle = new Bundle();
        mOrderBaseInfo.setBackUrl(finishBackImage);
        mOrderBaseInfo.setFrontUrl(finishFrontImage);
        mOrderBaseInfo.setClothesType(mCurrentClothes.getClothesType());
        mOrderBaseInfo.setColorName(mCurrentClothes.getColorName());
        mOrderBaseInfo.setType(mCurrentClothes.getType());
//        mOrderBaseInfo.setMaskA();
        if (patternFrontUrl != null) {
            mOrderBaseInfo.setPicture1(patternFrontUrl);
        }
        if (patternBackUrl != null) {
            mOrderBaseInfo.setPicture2(patternBackUrl);
        }
        bundle.putParcelable("clothesInfo", mOrderBaseInfo);
        startCommonActivity(this, bundle, ShowImageActivity.class);
    }


    @Override
    public void onItemClick(View currentView, int position) {
        if (mDesBeforeView != null) {
            mDesBeforeView.setSelected(false);
        }
        switch (clotheKey.get(mCurrentPosition)) {
            case COLOR:
                if (mButtonFront.isSelected()) {
                    setColorBg(getClothesFrontBG(mClothesData.get(newList.get(mCurrentPosition).getTitle()).get(position)));

                }
                if (mButtonBack.isSelected()) {
                    setColorBackBg(getClothesBackBG(mClothesData.get(newList.get(mCurrentPosition).getTitle()).get(position)));
                }
                mBeforeClothes = mCurrentClothes;
                isFirst = false;
                mCurrentClothes = mClothesData.get(newList.get(mCurrentPosition).getTitle()).get(position);
                break;
            case PATTERN:
                int resourcePattern;
                if (mButtonFront.isSelected()) {
                    resourcePattern = FileUtils.getResource(mPatternData.get(newList.get(mCurrentPosition).getTitle()).get(position).getName());
                    setPatternUrl(resourcePattern);
                    patternFrontUrl = FileUtils.saveBitmap(Utils.getBitmapResource(resourcePattern), mContext, "pattern");
                }
                if (mButtonBack.isSelected()) {
                    resourcePattern = FileUtils.getResource(mPatternData.get(newList.get(mCurrentPosition).getTitle()).get(position).getName());
                    setPatternUrl(resourcePattern);
                    patternBackUrl = FileUtils.saveBitmap(Utils.getBitmapResource(resourcePattern), mContext, "pattern");
                }
                break;
            case MASK:
                if (mButtonFront.isSelected()) {
                    currentFrontMask = Utils.getBitmapResource(R.mipmap.quan);
                    Bitmap source = Utils.getBitmapResource(
                            FileUtils.getResource(mCurrentClothes.getSex() + mCurrentClothes.getType()
                                    + "_" + mCurrentClothes.getColorName() + "_a"));
                    mPresenter.setImageMask(currentFrontMask, source);
                }
                if (mButtonBack.isSelected()) {
                    currentBackMask = Utils.getBitmapResource(R.mipmap.quan);
                    mPresenter.setImageMask(currentBackMask,
                            Utils.getBitmapResource(
                                    FileUtils.getResource(mCurrentClothes.getSex() + mCurrentClothes.getType()
                                            + "_" + mCurrentClothes.getColorName() + "_b")));

                }

                break;
            case SIGNATURE://签名处理
                mImagecolor = "#" + mSignatureData.get(newList.get(mCurrentPosition).getTitle()).get(position).getValue();
                int color = Color.parseColor(mImagecolor);
                textSticker.setTextColor(color);
                TextSticker sticker = (TextSticker) mContainerFront.getCurrentSticker();
                if (sticker != null) {
                    sticker.setTextColor(color);
                    mContainerFront.replace(sticker);
                    mContainerFront.invalidate();
                }
                break;

        }
        prePosition = position;
        currentView.setSelected(true);
        mDesCurrentView = currentView;
        mDesBeforeView = currentView;
    }

    private void setPatternUrl(int imageUrl) {
        GlideApp.with(App.getInstance()).asBitmap().load(imageUrl).error(R.mipmap.ic_launcher).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                if (mButtonBack.isSelected()) {
                    mContainerBack.setImageSource(resource);
                }
                if (mButtonFront.isSelected()) {
                    mContainerFront.setImageSource(resource);
                }
            }
        });
    }

    /**
     * 分为没有mask和有mask时候的区别
     * 正面
     *
     * @param image
     */
    private void setColorBg(int image) {
        if (currentFrontMask == null) {
            mContainerFront.setColorBg(image);
        } else {
            mContainerFront.setColorBg(image);
            mPresenter.setImageMask(currentFrontMask, Utils.getBitmapResource(image));
        }
    }

    /**
     * 分为没有mask和有mask的区别
     *
     * @param resource
     */
    private void setColorBackBg(int resource) {
        if (currentBackMask == null) {
            mContainerBack.setColorBg(resource);
        } else {
            mContainerBack.setColorBg(resource);
            mPresenter.setImageMask(currentBackMask, Utils.getBitmapResource(resource));
        }
    }

    @Override
    public void showSuccessData(List<StyleBean> newList, List<String> clotheKey, ArrayMap<String, List<VersionStyle>> mColorData, ArrayMap<String, List<DetailColorStyle>> mPatternData, ArrayMap<String, List<DetailColorStyle>> mMaskData, ArrayMap<String, List<DetailColorStyle>> mSignatureData) {
        this.newList = newList;
        this.mPatternData = mPatternData;
        this.mClothesData = mColorData;
        this.clotheKey = clotheKey;
        this.mMaskData = mMaskData;
        this.mSignatureData = mSignatureData;
        mRecyclerStyle.setVisibility(View.VISIBLE);
        BaseStyleAdapter mBaseDesignAdapter = new BaseStyleAdapter(this);
        mBaseDesignAdapter.setItemClickListener(this);
        mBaseDesignAdapter.setData(newList);
        mRecyclerStyle.setAdapter(mBaseDesignAdapter);
        mBaseDesignAdapter.notifyDataSetChanged();
    }

    /**
     * 生成遮罩图片
     *
     * @param maskBitmap
     */
    @Override
    public void showMaskView(Bitmap maskBitmap) {
        if (mButtonFront.isSelected()) {
            mContainerFront.setImageMask(maskBitmap);
        }
        if (mButtonBack.isSelected()) {
            mContainerBack.setImageMask(maskBitmap);
        }
    }


    @Override
    public void onClick(View view, int position) {
        if (mBeforeView != null) {
            mBeforeView.setSelected(false);
        }
        view.setSelected(true);
        mCurrentView = view;
        mCurrentPosition = position;
        mBeforeView = view;
        if (mCurrentView.isSelected()) {
            clickItem(mCurrentPosition);//点击进入详情设计界面
        }

    }

    private void clickItem(int position) {
        mRecyclerDetailStyle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        if (mClothesData.containsKey((newList.get(position).getTitle()))) {
            ColorStyleAdapter colorStyleAdapter = new ColorStyleAdapter(this);
            colorStyleAdapter.setData(mClothesData.get(newList.get(position).getTitle()));
            colorStyleAdapter.setOnClickListener(this);
            mRecyclerDetailStyle.setAdapter(colorStyleAdapter);
            colorStyleAdapter.notifyDataSetChanged();
        }
        if (mPatternData.containsKey(newList.get(position).getTitle())) {
            PatternStyleAdapter patternStyleAdapter = new PatternStyleAdapter(this);
            patternStyleAdapter.setData(mPatternData.get(newList.get(position).getTitle()));
            patternStyleAdapter.setOnClickListener(this);
            patternStyleAdapter.setOnComClickListener(new ChoiceAvatarListener());
            mRecyclerDetailStyle.setAdapter(patternStyleAdapter);
            patternStyleAdapter.notifyDataSetChanged();
        }
        if (mMaskData.containsKey(newList.get(position).getTitle())) {
            MaskStyleAdapter adapter = new MaskStyleAdapter(this);
            adapter.setData(mMaskData.get(newList.get(position).getTitle()));
            adapter.setOnClickListener(this);
            mRecyclerDetailStyle.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        if (mSignatureData.containsKey((newList.get(position).getTitle()))) {
            TextStyleAdapter adapter = new TextStyleAdapter(this);
            adapter.setData(mSignatureData.get(newList.get(position).getTitle()));
            adapter.setOnClickListener(this);
            mRecyclerDetailStyle.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            mContainerFront.setLocked(false);
            setSignatureText(null, false);//文字贴图
        }
        mRecyclerStyle.setVisibility(View.GONE);
        mRecyclerDetailStyle.setVisibility(View.VISIBLE);
        mBtnFinish.setVisibility(View.GONE);
        mChoiceReturn.setVisibility(View.VISIBLE);
    }

    private class ChoiceAvatarListener implements ItemClickListener.OnItemComClickListener {
        @Override
        public void onItemClick(Object o, View view) {
            EditUserPopupWindow mPopupWindow = mAvatarPresenter.initPopupWindow();
            mPopupWindow.showAtLocation(mDesign, Gravity.CENTER | Gravity.BOTTOM, 0, 0);
            mAvatarPresenter.setParams(Constants.CHANGE_ALPHA);
            mDesCurrentView = view;
        }
    }

    /**
     * 设置自定义字体
     *
     * @param message
     * @param isNew
     */
    private void setSignatureText(String message, final boolean isNew) {
        final SignatureDialog dialog = new SignatureDialog(this, mUpdateType);
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
            public void onClickChoiceOrBack(View view, String s, Typeface typeface) {
                if (isNew) {
                    if (textSticker != null) {
                        textSticker.setText(s);
                        textSticker.resizeText();
                        textSticker.setTypeface(typeface);
                        mContainerFront.replace(textSticker);
                        mContainerFront.invalidate();
                    }

                } else {
                    textSticker = new TextSticker(NewDesignActivity.this);
                    textSticker.setText(s);
                    textSticker.setTypeface(typeface);
                    textSticker.setTextColor(Color.BLACK);
                    textSticker.setTextAlign(Layout.Alignment.ALIGN_CENTER);
                    textSticker.resizeText();
                    mContainerFront.addSticker(textSticker);
                }
            }
        });
    }

    public void saveText(TextSticker textSticker) {
        if (!TextUtils.isEmpty(textSticker.getText()) && textEntities != null) {
            for (TextEntity t : textEntities) {
                if (t.getText().equals(textSticker.getText())) {
                    textEntities.remove(t);
                }
            }
        }
        TextEntity textEntity;
        if (!TextUtils.isEmpty(textSticker.getText())) {
            textEntity = new TextEntity();
            textEntity.setText(textSticker.getText());
            textEntity.setColor(mImagecolor);
            textEntities.add(textEntity);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mAvatarPresenter.callback(requestCode, resultCode, data);
    }

    @Override
    public void showSuccessAvatar(File cover) {
        if (cover != null) {
            setPatternAvatar(cover.getPath());
        }
    }

    private void setPatternAvatar(String path) {
        if (mButtonFront.isSelected()) {
            mContainerFront.setImageSource(BitmapFactory.decodeFile(path));
            patternFrontUrl = path;
        }
        if (mButtonBack.isSelected()) {
            mContainerBack.setImageSource(BitmapFactory.decodeFile(path));
            patternBackUrl = path;
        }
    }

    /**
     * 固定获取衣服的方法
     *
     * @param mCurrentClothes
     * @return
     */
    private int getClothesFrontBG(VersionStyle mCurrentClothes) {
        String type = mCurrentClothes.getType();
        String colorName = mCurrentClothes.getColorName();
        String current = mCurrentClothes.getSex() + type + "_" + colorName + "_" + "a";
        return FileUtils.getResource(current);
    }


    private int getClothesBackBG(VersionStyle versionStyle) {
        String type = versionStyle.getType();
        String colorName = versionStyle.getColorName();
        String current = versionStyle.getSex() + type + "_" + colorName + "_" + "b";
        return FileUtils.getResource(current);
    }

    @Override
    public void showErrorMsg(String msg) {
        super.showErrorMsg(msg);
        ToastUtil.showToast(mContext, msg, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().post(new ChangeSelectEvent(true));
    }

}
