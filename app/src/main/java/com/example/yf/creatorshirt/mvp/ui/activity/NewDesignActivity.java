package com.example.yf.creatorshirt.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.app.GlideApp;
import com.example.yf.creatorshirt.mvp.listener.CommonListener;
import com.example.yf.creatorshirt.mvp.listener.ItemClickListener;
import com.example.yf.creatorshirt.mvp.model.VersionStyle;
import com.example.yf.creatorshirt.mvp.model.detaildesign.DetailColorStyle;
import com.example.yf.creatorshirt.mvp.model.detaildesign.StyleBean;
import com.example.yf.creatorshirt.mvp.presenter.CommonAvatarPresenter;
import com.example.yf.creatorshirt.mvp.presenter.DetailDesignPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.CommonAvatarContract;
import com.example.yf.creatorshirt.mvp.presenter.contract.DetailDesignContract;
import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;
import com.example.yf.creatorshirt.mvp.ui.adapter.design.BaseStyleAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.design.ColorStyleAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.design.MaskStyleAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.design.PatternStyleAdapter;
import com.example.yf.creatorshirt.mvp.ui.view.ClothesBackView;
import com.example.yf.creatorshirt.mvp.ui.view.ClothesFrontView;
import com.example.yf.creatorshirt.mvp.ui.view.DialogAlert;
import com.example.yf.creatorshirt.mvp.ui.view.EditUserPopupWindow;
import com.example.yf.creatorshirt.mvp.ui.view.TextItemView;
import com.example.yf.creatorshirt.mvp.ui.view.sticker.TextSticker;
import com.example.yf.creatorshirt.utils.Constants;
import com.example.yf.creatorshirt.utils.FileUtils;
import com.example.yf.creatorshirt.utils.ToastUtil;
import com.example.yf.creatorshirt.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class NewDesignActivity extends BaseActivity<DetailDesignPresenter> implements
        ItemClickListener.OnItemClickListener, DetailDesignContract.DetailDesignView, CommonAvatarContract.CommonAvatarView {
    public static final String COLOR = "color";
    public static final String PATTERN = "pattern";
    public static final String MASK = "icon_select_mask";
    public static final String SIGNATURE = "signature";
    private static final String TAG = NewDesignActivity.class.getSimpleName();


    @BindView(R.id.design_choice_style)
    RecyclerView mRecyclerDetailStyle;//具体设计的recyclerView
    @BindView(R.id.choice_style)
    RecyclerView mRecyclerStyle;//style的recyclerView
    @BindView(R.id.ll_choice_back)
    LinearLayout mChoiceReturn;
    @BindView(R.id.btn_choice_finish)
    Button mBtnFinish;
    @BindView(R.id.btn_choice_other)
    Button mBtnFinishOther;
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
    @BindView(R.id.text_size_layout)
    TextItemView mTextColorView;

    private CommonAvatarPresenter mAvatarPresenter;//相机图片选择代码块

    private ArrayMap<String, List<DetailColorStyle>> mPatternData = new ArrayMap<>();
    private ArrayMap<String, List<VersionStyle>> mClothesData = new ArrayMap<>();
    private ArrayMap<String, List<DetailColorStyle>> mMaskData = new ArrayMap<>();
    private ArrayMap<String, List<DetailColorStyle>> mSignatureData = new ArrayMap<>();
    private List<StyleBean> newList = new ArrayList<>();    //总样式的集合
    private List<String> clotheKey = new ArrayList<>();    //具体样式的字段名

    private ArrayList<VersionStyle> mListClothes;
    private View mBeforeView;
    private View mCurrentView;
    private int mCurrentPosition;
    private View mDesCurrentView;
    private String patternFrontUrl;    //自定义的贴图
    private String patternBackUrl;    //自定义的贴图
    private int prePosition;
    private VersionStyle mInitData;//默认显示第一件衣服
    private VersionStyle mOrderBaseInfo; //衣服信息
    private boolean isFirst;
    private VersionStyle mCurrentClothes;//显示当前的衣服字段
    private VersionStyle mBeforeClothes;//显示之前的字段
    private String finishBackImage;//最终背景衣服
    private String finishFrontImage;
    private Bitmap currentFrontMask;
    private Bitmap currentBackMask;
    private DialogAlert dialogAlert;
    private String maskA;
    private String maskB;
    private boolean isClick = false;
    private boolean isMotion = false;
    private boolean isMotionBack = false;
    private ColorStyleAdapter colorStyleAdapter;
    private PatternStyleAdapter patternStyleAdapter;
    private MaskStyleAdapter maskStyleAdapter;

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
        mAppBarTitle.setText("设计定制");
        mRecyclerStyle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        patternStyleAdapter = new PatternStyleAdapter(this);
        maskStyleAdapter = new MaskStyleAdapter(this);
        colorStyleAdapter = new ColorStyleAdapter(this);
        //默认显示正面
        isFirst = true;
        initFront();
        mContainerFront.setContext(this);
        mContainerBack.setContext(this);
        dialogAlert = new DialogAlert.Builder()
                .setContext(this)
                .setTitle("你确认要退出编辑吗")
                .setCancel("取消")
                .setConfirm("确定")
                .builder();
    }

    private void initFront() {
        mButtonFront.setSelected(true);
        if (mInitData != null) {
            getCurrentFrontClothes(mInitData);
        }
        mOrderBaseInfo = new VersionStyle();
    }

    public void clickBack() {
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
        if (mTextColorView.getVisibility() == View.VISIBLE) {
            mTextColorView.setVisibility(View.GONE);
//            if (mContainerFront.getCurrentSticker() != null) {
//                mContainerFront.setLocked(true);
//            }
        }
    }

    @OnClick({R.id.btn_choice_finish, R.id.choice_done, R.id.choice_back, R.id.tv_front, R.id.tv_back,
            R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_choice_finish:
                if (!isClick) {
                    clickBack();
                } else {
                    generateBitmap();//生成衣服mask的图片
                    if (finishFrontImage != null && finishBackImage != null) {
                        startNewActivity();
                    }
                }
                break;
            case R.id.back:
                getDialog();
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
                if (mTextColorView.getVisibility() == View.VISIBLE) {
                    mTextColorView.setVisibility(View.GONE);
//                    if (mContainerFront.getCurrentSticker() != null) {
//                        mContainerFront.setLocked(true);
//                    }
                }
                break;
            case R.id.tv_back:
                isClick = true;
                clickBack();
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
                        mClothesData.get(newList.get(mCurrentPosition).getTitle()).get(prePosition).setSelect(false);
                        colorStyleAdapter.clearSelect();
                        break;
                    case PATTERN:
                        if (mButtonFront.isSelected()) {
                            mContainerFront.setImageSource(null);
                        }
                        if (mButtonBack.isSelected()) {
                            mContainerBack.setImageSource(null);
                        }
                        mPatternData.get(newList.get(mCurrentPosition).getTitle()).get(prePosition).setSelect(false);
                        patternStyleAdapter.clearSelect();
                        break;
                    case MASK:
                        if (mButtonFront.isSelected()) {
                            mContainerFront.setImageMask(null);

                        }
                        if (mButtonBack.isSelected()) {
                            mContainerBack.setImageMask(null);
                        }
                        mMaskData.get(newList.get(mCurrentPosition).getTitle()).get(prePosition).setSelect(false);
                        maskStyleAdapter.clearSelect();
                        break;
                    case SIGNATURE:
                        if (mButtonFront.isSelected()) {
                            if (mContainerFront.getCurrentSticker() != null) {
                                TextSticker textSticker = (TextSticker) mContainerFront.getCurrentSticker();
                                mContainerFront.remove(textSticker);
                                mContainerFront.removeText(textSticker.getText());
                            }
                            if (!mContainerFront.isNoneSticker()) {
                                mContainerFront.removeCurrentSticker();
                                mContainerFront.setLocked(true);
                            }

                        }
                        if (mButtonBack.isSelected()) {
                            if (mContainerBack.getCurrentSticker() != null) {
                                TextSticker textSticker = (TextSticker) mContainerBack.getCurrentSticker();
                                mContainerBack.remove(textSticker);
                                mContainerBack.removeText(textSticker.getText());
                            }
                            if (!mContainerBack.isNoneSticker()) {
                                mContainerBack.removeCurrentSticker();
                                mContainerBack.setLocked(true);
                            }
                        }
                        break;

                }
                mTextColorView.setVisibility(View.GONE);
                mChoiceReturn.setVisibility(View.GONE);
                mRecyclerDetailStyle.setVisibility(View.GONE);
                mRecyclerStyle.setVisibility(View.VISIBLE);
                break;
            case R.id.choice_done://点击每个样式的完成保存到数据
                switch (clotheKey.get(mCurrentPosition)) {
                    case COLOR:
                        mOrderBaseInfo.setColor(mCurrentClothes.getColor());
                        mClothesData.get(newList.get(mCurrentPosition).getTitle()).get(prePosition).setSelect(false);
                        colorStyleAdapter.clearSelect();
                        break;
                    case PATTERN:
                        if (mButtonFront.isSelected()) {
                            mContainerFront.setTouchFlag(false);
                        }
                        if (mButtonBack.isSelected()) {
                            mContainerBack.setTouchFlag(false);
                        }
                        patternStyleAdapter.clearSelect();
                        break;
                    case MASK:
                        if (mButtonFront.isSelected()) {
                            mContainerFront.setTouchFlag(false);
                        }
                        if (mButtonBack.isSelected()) {
                            mContainerBack.setTouchFlag(false);
                        }
                        maskStyleAdapter.clearSelect();
                        break;
                    case SIGNATURE:
                        if (mButtonFront.isSelected()) {
                            if (mContainerFront.getCurrentSticker() != null) {
                                TextSticker textSticker = (TextSticker) mContainerFront.getCurrentSticker();
                                if (!Constants.ADD_TEXT.equals(textSticker.getText())) {
                                    mContainerFront.saveText(textSticker);
                                } else {
                                    return;
                                }
                            }

                        }
                        if (mButtonBack.isSelected()) {
                            if (mContainerBack.getCurrentSticker() != null) {
                                TextSticker textSticker = (TextSticker) mContainerBack.getCurrentSticker();
                                if (!Constants.ADD_TEXT.equals(textSticker.getText())) {
                                    mContainerBack.saveText(textSticker);
                                } else {
                                    return;
                                }
                            }
                        }
                        if (mDesCurrentView == null) {
                            mRecyclerStyle.setVisibility(View.VISIBLE);
                            mRecyclerDetailStyle.setVisibility(View.GONE);
                            mBtnFinish.setVisibility(View.VISIBLE);
                            mChoiceReturn.setVisibility(View.GONE);
                        }
                        mContainerBack.setLocked(true);
                        mContainerFront.setLocked(true);
                        mTextColorView.setVisibility(View.GONE);
                        break;
                }
                if (mDesCurrentView != null) {
                    mChoiceReturn.setVisibility(View.GONE);
                    mRecyclerDetailStyle.setVisibility(View.GONE);
                    mRecyclerStyle.setVisibility(View.VISIBLE);
                    mDesCurrentView.setSelected(false);
                }
                break;
        }
        mBtnFinish.setVisibility(View.VISIBLE);
    }

    private void getDialog() {
        dialogAlert.show();
        dialogAlert.setOnPositionClickListener(NewDesignActivity.this::finish);
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
        mPresenter.setClothesBg(getClothesBackBG(data));
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
        mOrderBaseInfo.setColor(mCurrentClothes.getColor());
        mOrderBaseInfo.setMaskA(maskA == null ? "" : maskA);
        mOrderBaseInfo.setMaskB(maskB == null ? "" : maskB);
        mOrderBaseInfo.setGender(mCurrentClothes.getGender());//显示衣服的中文名称
        mOrderBaseInfo.setPicture1(patternFrontUrl);
        mOrderBaseInfo.setPicture2(patternBackUrl);
        if (mContainerFront.getTextEntities() != null && mContainerFront.getTextEntities().size() != 0) {
            mOrderBaseInfo.setText(mContainerFront.getTextEntities());
        } else {
            mOrderBaseInfo.setText(null);
        }
        if (mContainerBack.getTextEntities() != null && mContainerBack.getTextEntities().size() != 0) {
            mOrderBaseInfo.setBackText(mContainerBack.getTextEntities());
        } else {
            mOrderBaseInfo.setBackText(null);
        }
        bundle.putParcelable("clothesInfo", mOrderBaseInfo);
        startCommonActivity(this, bundle, ShowImageActivity.class);
    }


    @Override
    public void onItemClick(View currentView, int position, Object o) {

        switch (clotheKey.get(mCurrentPosition)) {
            case COLOR:
                mCurrentClothes = mClothesData.get(newList.get(mCurrentPosition).getTitle()).get(position);
                if (mButtonFront.isSelected()) {
                    setColorBg(getClothesFrontBG(mClothesData.get(newList.get(mCurrentPosition).getTitle()).get(position)));

                }
                if (mButtonBack.isSelected()) {
                    setColorBackBg(getClothesBackBG(mClothesData.get(newList.get(mCurrentPosition).getTitle()).get(position)));
                }
                mBeforeClothes = mCurrentClothes;
                isFirst = false;
                break;
            case PATTERN:
                int resourcePattern;
                if (mButtonFront.isSelected()) {
                    resourcePattern = FileUtils.getResource((String) o);
                    setPatternUrl(resourcePattern);
                    patternFrontUrl = (String) o;
                }
                if (mButtonBack.isSelected()) {
                    resourcePattern = FileUtils.getResource((String) o);
                    setPatternUrl(resourcePattern);
                    patternBackUrl = (String) o;
                }
                break;
            case MASK:
                if (mButtonFront.isSelected()) {
                    currentFrontMask = Utils.getBitmapResource(FileUtils.getResource((String) o));
                    Bitmap source = Utils.getBitmapResource(
                            FileUtils.getResource(mCurrentClothes.getSex() + mCurrentClothes.getType()
                                    + "_" + mCurrentClothes.getColorName() + "_a"));
                    mPresenter.setImageMask(currentFrontMask, source);
                    maskA = (String) o;
                }
                if (mButtonBack.isSelected()) {
                    currentBackMask = Utils.getBitmapResource(FileUtils.getResource((String) o));
                    mPresenter.setImageMask(currentBackMask,
                            Utils.getBitmapResource(
                                    FileUtils.getResource(mCurrentClothes.getSex() + mCurrentClothes.getType()
                                            + "_" + mCurrentClothes.getColorName() + "_b")));
                    maskB = (String) o;
                }

                break;
            case SIGNATURE://字体处理

                break;

        }
        prePosition = position;
        mDesCurrentView = currentView;
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
            mPresenter.setClothesBg(image);
        } else {
            mPresenter.setClothesBg(image);
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
            mPresenter.setClothesBg(resource);
        } else {
            mPresenter.setClothesBg(resource);
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
        mBaseDesignAdapter.setItemClickListener(new ItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object object) {
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
        });
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
    public void showClothesBg(Bitmap clothesBitmap) {
        if (mButtonFront.isSelected()) {
            mContainerFront.setColorBg(clothesBitmap);
        }
        if (mButtonBack.isSelected()) {
            mContainerBack.setColorBg(clothesBitmap);
            if (currentBackMask != null) {
                mPresenter.setImageMask(currentBackMask, clothesBitmap);
            }
            if (!isClick) {
                generateBitmap();
                if (finishBackImage != null && finishFrontImage != null) {
                    startNewActivity();
                }
            }
        }

    }

    private void clickItem(int position) {
        mRecyclerDetailStyle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        if (mClothesData.containsKey((newList.get(position).getTitle()))) {
            colorStyleAdapter.setData(mClothesData.get(newList.get(position).getTitle()));
            colorStyleAdapter.setOnItemClickListener(this);
            mRecyclerDetailStyle.setAdapter(colorStyleAdapter);
            colorStyleAdapter.notifyDataSetChanged();
        }
        if (mPatternData.containsKey(newList.get(position).getTitle())) {
            if (patternStyleAdapter == null) {
                return;
            }
            patternStyleAdapter.setData(mPatternData.get(newList.get(position).getTitle()));
            patternStyleAdapter.setOnItemClickListener(this);
            patternStyleAdapter.setOnComClickListener(new ChoiceAvatarListener());
            patternStyleAdapter.setMoreClickListener(new MoreAdapterListener());
            mRecyclerDetailStyle.setAdapter(patternStyleAdapter);
            patternStyleAdapter.notifyDataSetChanged();
            if (mButtonFront.isSelected()) {
                isMotion = true;
                mContainerFront.setTouchFlag(true);
            }
            if (mButtonBack.isSelected()) {
                isMotionBack = true;
                mContainerBack.setTouchFlag(true);
            }
        }
        if (mMaskData.containsKey(newList.get(position).getTitle())) {
            maskStyleAdapter.setData(mMaskData.get(newList.get(position).getTitle()));
            maskStyleAdapter.setOnItemClickListener(this);
            mRecyclerDetailStyle.setAdapter(maskStyleAdapter);
            maskStyleAdapter.notifyDataSetChanged();
            if (mButtonFront.isSelected()) {
                if (isMotion) {
                    Constants.ISTOKEN = false;
                }
                mContainerFront.setTouchFlag(true);
            }
            if (mButtonBack.isSelected()) {
                if (isMotionBack) {
                    Constants.IS_BACK_TOKEN = false;
                }
                mContainerBack.setTouchFlag(true);

            }
        }
        if (mSignatureData.containsKey((newList.get(position).getTitle()))) {
            mTextColorView.setVisibility(View.VISIBLE);
            mTextColorView.setSizeClickListener(mSizeClickListener);
            if (mButtonFront.isSelected()) {
                mContainerFront.setSignatureText(null, false);//文字贴图

            }
            if (mButtonBack.isSelected()) {
                mContainerBack.setSignatureText(null, false);//文字贴图
            }
        }
        mRecyclerStyle.setVisibility(View.GONE);
        mRecyclerDetailStyle.setVisibility(View.VISIBLE);
        mBtnFinish.setVisibility(View.GONE);
        mChoiceReturn.setVisibility(View.VISIBLE);
    }

    public CommonListener.TextSizeClickListener mSizeClickListener = new CommonListener.TextSizeClickListener() {

        @Override
        public void onClickListener(int color, Typeface typeface, int textSize) {
            if (mButtonFront.isSelected()) {
                TextSticker sticker = (TextSticker) mContainerFront.getCurrentSticker();
                if (sticker != null) {
                    if (typeface != null) {
                        sticker.setTypeface(typeface);
                    }
                    if (color != 0) {
                        sticker.setTextColor(App.getInstance().getResources().getColor(color));
                    }
                    if (textSize != 0) {
                        sticker.setTextSize(textSize);
                    }
                    mContainerFront.replace(sticker);
                    mContainerFront.invalidate();
                    mContainerFront.setLocked(false);
                }
            }
            if (mButtonBack.isSelected()) {
                TextSticker sticker = (TextSticker) mContainerBack.getCurrentSticker();
                if (sticker != null) {
                    if (typeface != null) {
                        sticker.setTypeface(typeface);
                    }
                    if (color != 0) {
                        sticker.setTextColor(App.getInstance().getResources().getColor(color));
                    }
                    if (textSize != 0) {
                        sticker.setTextSize(textSize);
                    }
                    mContainerBack.replace(sticker);
                    mContainerBack.invalidate();
                    mContainerBack.setLocked(false);
                }
            }
        }
    };

    public class ChoiceAvatarListener implements ItemClickListener.OnItemClickListener {

        @Override
        public void onItemClick(View view, int position, Object object) {
            EditUserPopupWindow mPopupWindow = mAvatarPresenter.initPopupWindow();
            mPopupWindow.showAtLocation(mDesign, Gravity.CENTER | Gravity.BOTTOM, 0, 0);
            mAvatarPresenter.setParams(Constants.CHANGE_ALPHA);
            mDesCurrentView = view;
        }
    }

    public class MoreAdapterListener implements ItemClickListener.OnItemClickListener {
        @Override
        public void onItemClick(View view, int position, Object object) {
            Bundle bundle = new Bundle();
            bundle.putString("url", Constants.PHOTO_URL);
            startCommonActivity(NewDesignActivity.this, bundle, ServerActivity.class);
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
        ToastUtil.showToast(this, msg, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mContainerFront.onDestroy();
    }

    @Override
    public void onBackPressed() {
        getDialog();
    }
}
