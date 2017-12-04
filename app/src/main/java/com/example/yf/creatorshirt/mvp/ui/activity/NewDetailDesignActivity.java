//package com.example.yf.creatorshirt.mvp.ui.activity;
//
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Typeface;
//import android.graphics.drawable.Drawable;
//import android.os.Build;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.RequiresApi;
//import android.support.v4.util.ArrayMap;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.text.Layout;
//import android.text.TextUtils;
//import android.view.Gravity;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.RequestOptions;
//import com.bumptech.glide.request.target.SimpleTarget;
//import com.bumptech.glide.request.transition.Transition;
//import com.example.yf.creatorshirt.R;
//import com.example.yf.creatorshirt.app.App;
//import com.example.yf.creatorshirt.common.ChangeSelectEvent;
//import com.example.yf.creatorshirt.mvp.listener.ItemClickListener;
//import com.example.yf.creatorshirt.mvp.model.detaildesign.CommonStyleData;
//import com.example.yf.creatorshirt.mvp.model.detaildesign.DetailColorStyle;
//import com.example.yf.creatorshirt.mvp.model.detaildesign.DetailOtherStyle;
//import com.example.yf.creatorshirt.mvp.model.detaildesign.DetailPatterStyle;
//import com.example.yf.creatorshirt.mvp.model.detaildesign.DetailStyleBean;
//import com.example.yf.creatorshirt.mvp.model.detaildesign.StyleBean;
//import com.example.yf.creatorshirt.mvp.model.detaildesign.TextEntity;
//import com.example.yf.creatorshirt.mvp.model.orders.OrderBaseInfo;
//import com.example.yf.creatorshirt.mvp.model.orders.OrderData;
//import com.example.yf.creatorshirt.mvp.presenter.CommonAvatarPresenter;
//import com.example.yf.creatorshirt.mvp.presenter.contract.CommonAvatarContract;
//import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;
//import com.example.yf.creatorshirt.mvp.ui.adapter.TextStyleAdapter;
//import com.example.yf.creatorshirt.mvp.ui.adapter.design.BaseStyleAdapter;
//import com.example.yf.creatorshirt.mvp.ui.adapter.design.ColorStyleAdapter;
//import com.example.yf.creatorshirt.mvp.ui.adapter.design.DetailStyleAdapter;
//import com.example.yf.creatorshirt.mvp.ui.adapter.design.PatternStyleAdapter;
//import com.example.yf.creatorshirt.mvp.ui.view.ClothesBackView;
//import com.example.yf.creatorshirt.mvp.ui.view.EditUserPopupWindow;
//import com.example.yf.creatorshirt.mvp.ui.view.sticker.DrawableSticker;
//import com.example.yf.creatorshirt.mvp.ui.view.sticker.SignatureDialog;
//import com.example.yf.creatorshirt.mvp.ui.view.sticker.Sticker;
//import com.example.yf.creatorshirt.mvp.ui.view.sticker.StickerView;
//import com.example.yf.creatorshirt.mvp.ui.view.sticker.TextSticker;
//import com.example.yf.creatorshirt.utils.Constants;
//import com.example.yf.creatorshirt.utils.DisplayUtil;
//import com.example.yf.creatorshirt.utils.FileUtils;
//import com.example.yf.creatorshirt.utils.NetworkUtils;
//import com.example.yf.creatorshirt.utils.ToastUtil;
//
//import org.greenrobot.eventbus.EventBus;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.OnClick;
//
//
///**
// * 衣服具体设计样式界面
// */
//public class NewDetailDesignActivity extends BaseActivity<DetailDesign2Presenter> implements ItemClickListener.OnItemClickListener,
//        ItemClickListener.OnClickListener, DetailDesignContract2.DetailDesignView, CommonAvatarContract.CommonAvatarView {
//
//    private static final String TAG = NewDetailDesignActivity.class.getSimpleName();
//    public static final String NECK = "neck";
//    public static final String ARM = "arm";
//    public static final String COLOR = "color";
//    public static final String PATTERN = "pattern";
//    public static final String ORNAMENT = "ornament";
//    public static final String SIGNATURE = "signature";
//
//
//    @BindView(R.id.design_choice_style)
//    RecyclerView mRecyclerDetailStyle;//具体设计的recyclerView
//    @BindView(R.id.choice_style)
//    RecyclerView mRecyclerStyle;//style的recyclerView
//    @BindView(R.id.choice_select_neck)
//    ImageView mChoiceNeck;
//    @BindView(R.id.clothes_container_background)
//    ImageView mClothes;
//    @BindView(R.id.ll_choice_back)
//    LinearLayout mChoiceReturn;
//    @BindView(R.id.btn_choice_finish)
//    Button mBtnFinish;
//    @BindView(R.id.choice_back)
//    ImageView mChoiceDelete;
//    @BindView(R.id.choice_done)
//    ImageView mChoiceDone;
//    @BindView(R.id.choice_ornament)
//    ImageView mChoiceOrnament;
//    @BindView(R.id.choice_select_arm)
//    ImageView mChoiceArm;
//    @BindView(R.id.rl_clothes_root)
//    com.example.yf.creatorshirt.mvp.ui.view.sticker.StickerView mContainerFrontBackground;//正面
//    @BindView(R.id.clothes_back)
//    TextView mButtonBack;
//    @BindView(R.id.clothes_front)
//    TextView mButtonFront;
//    @BindView(R.id.rl_clothes_back_root)
//    ClothesBackView mContainerBackBackground;//背面背景
//    @BindView(R.id.rl_bg)
//    RelativeLayout mRelative;
//    @BindView(R.id.rl_design)
//    RelativeLayout mDesign;
//
//    CommonAvatarPresenter mAvatarPresenter;
//    //总的样式
//    private View mBeforeView;
//    private View mCurrentView;
//    //每个具体的样式
//    private View mDesBeforeView;
//    private View mDesCurrentView;
//    private int mCurrentPosition = 0;
//
//    //总样式和每一个具体的样式列表形成ArrayMap;
//    private ArrayMap<String, List<DetailOtherStyle>> NewDetailData = new ArrayMap<>();
//    private ArrayMap<String, List<DetailPatterStyle>> mPatternData = new ArrayMap<>();
//    private ArrayMap<String, List<DetailColorStyle>> mColorData = new ArrayMap<>();
//    private ArrayMap<String, List<DetailColorStyle>> mSignatureData = new ArrayMap<>();
//
//    //    总样式的集合
//    private List<StyleBean> newList = new ArrayList<>();
//    private List<String> clotheKey = new ArrayList<>();//具体样式的字段名
//    private ArrayList<String> avatarList = new ArrayList<>();
//
//    //定制完成后图片的路径
//    private String imageFrontPath;
//    private String imageBackPath;
//
//    private String gender;//gender
//    private String type;//类型
//    private String mBackgroundUrl;//背景url
//
//    private BaseStyleAdapter mBaseDesignAdapter;
//    private CommonStyleData commonStyleData;//保存样式设计的url和颜色值
//    private CommonStyleData mBackStyleData;//保存样式设计的url和颜色值
//    private OrderBaseInfo mOrderBaseInfo;//保存正面和反面图片
//
//    private String imageUrl = null;//图片url
//    private String mImagecolor = "#FFFFFF";//背景颜色 默认白色
//    private boolean isBack = false;
//    private String content;
//    private TextSticker sticker;//文字贴图
//    private Typeface mUpdateType;
//    private int prePosition;
//    private PatternStyleAdapter patternStyleAdapter;
//
//    private List<TextEntity> textEntities = new ArrayList<>();
//
//    @Override
//    protected void inject() {
//        getActivityComponent().inject(this);
//    }
//
//    @Override
//    protected int getView() {
//        return R.layout.activity_new_detail_design;
//    }
//
//    @Override
//    protected void initView() {
//        if (DisplayUtil.getScreenW(this) < 1080) {
//            DisplayUtil.calculateRelative(this, mRelative);
//        } else {
//            DisplayUtil.calculateBigRelative(this, mRelative);
//        }
//        mButtonFront.setSelected(true);
//        //默认显示正面
//        initFrontBg();
//        mRecyclerStyle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        mBaseDesignAdapter = new BaseStyleAdapter(this);
//        commonStyleData = new CommonStyleData();
//        mBackStyleData = new CommonStyleData();
//        mOrderBaseInfo = new OrderBaseInfo();
//        initBackBg();
//        sticker = new TextSticker(this);
//        mContainerFrontBackground.setBackgroundColor(Color.WHITE);
//        mContainerFrontBackground.setLocked(false);
//        mContainerFrontBackground.setConstrained(true);
//        mContainerFrontBackground.setOnStickerOperationListener(new StickerView.OnStickerOperationListener() {
//            @Override
//            public void onStickerAdded(@NonNull Sticker sticker) {
//            }
//
//            @Override
//            public void onStickerClicked(@NonNull Sticker sticker) {
//                if (sticker instanceof TextSticker) {
//                    mUpdateType = ((TextSticker) sticker).getTypeface();
//                    setSignatureText(((TextSticker) sticker).getText(), true);
//                }
//            }
//
//            @Override
//            public void onStickerDeleted(@NonNull Sticker sticker) {
//            }
//
//            @Override
//            public void onStickerDragFinished(@NonNull Sticker sticker) {
//            }
//
//            @Override
//            public void onStickerZoomFinished(@NonNull Sticker sticker) {
//            }
//
//            @Override
//            public void onStickerFlipped(@NonNull Sticker sticker) {
//            }
//
//            @Override
//            public void onStickerDoubleTapped(@NonNull Sticker sticker) {
//            }
//        });
//    }
//
//    private void initBackBg() {
//        if (DisplayUtil.getScreenW(App.getInstance()) < 1080) {
//            DisplayUtil.calculateSmallRl(mContainerBackBackground);
//        } else {
//            DisplayUtil.calculateRl(mContainerBackBackground);
//        }
//        mContainerBackBackground.setContext(this);
//    }
//
//    private void initFrontBg() {
//        mBackgroundUrl = Constants.ImageUrl + gender + type + "A" + ".png";
//        if (DisplayUtil.getScreenW(this) < 1080) {
//            DisplayUtil.calculateFrontSize(mClothes);
//            DisplayUtil.calculateFrontSize(mChoiceOrnament);
//            DisplayUtil.calculateFrontSize(mChoiceArm);
//            DisplayUtil.calculateFrontSize(mChoiceNeck);
//            Glide.with(this).load(mBackgroundUrl).into(mClothes);
//            DisplayUtil.calculateSmallRl(mContainerFrontBackground);
//        } else {
//            DisplayUtil.calculateRl(mContainerFrontBackground);
//            Glide.with(this).load(mBackgroundUrl).into(mClothes);
//        }
//        mAppBarTitle.setText(R.string.design);
//        mAppBarBack.setVisibility(View.VISIBLE);
//        mRecyclerStyle.setVisibility(View.VISIBLE);
//        mBtnFinish.setVisibility(View.VISIBLE);
//    }
//
//    @Override
//    public void initData() {
//        if (getIntent().getExtras() != null) {
//            gender = getIntent().getExtras().getString("gender");
//            type = getIntent().getExtras().getString("type");
//        }
//        mPresenter.getDetailDesign(gender, type);
//        mAvatarPresenter = new CommonAvatarPresenter(this);
//        mAvatarPresenter.attachView(this);
//        mPresenter.setFront(false);
//    }
//
//
//    @Override
//    public void showSuccessData(List<StyleBean> newList, List<String> clotheKey, ArrayMap<String, List<DetailOtherStyle>> newDetailData, ArrayMap<String, List<DetailColorStyle>> mColorData, ArrayMap<String, List<DetailPatterStyle>> mPatternData, ArrayMap<String, List<DetailColorStyle>> mSignatureData) {
//        this.newList = newList;
//        this.NewDetailData = newDetailData;
//        this.mPatternData = mPatternData;
//        this.mColorData = mColorData;
//        this.mSignatureData = mSignatureData;
//        this.clotheKey = clotheKey;
//        mBaseDesignAdapter.setItemClickListener(this);
//        mBaseDesignAdapter.setData(newList);
//        mRecyclerStyle.setAdapter(mBaseDesignAdapter);
//        mBaseDesignAdapter.notifyDataSetChanged();
//    }
//
//
//    @Override
//    public void setArm(String file) {
//        setArmImage(file);
//        commonStyleData.setNeckUrl(file);
//    }
//
//    @Override
//    public void setNeck(String file) {
//        setNeckImage(file);
//        commonStyleData.setNeckUrl(file);
//    }
//
//    @Override
//    public void setOrnament(String file) {
//        setOrnametUrl(file);
//        commonStyleData.setOrnametUrl(file);
//    }
//
//    @Override
//    public void showBackData(String neck, String arm, String ornament) {
//        mBackStyleData.setNeckUrl(neck);
//        mBackStyleData.setArmUrl(arm);
//        mBackStyleData.setOrnametUrl(ornament);
//        mContainerBackBackground.setBackData(mBackStyleData);
//    }
//
//    @OnClick({R.id.btn_choice_finish, R.id.choice_done, R.id.choice_back, R.id.clothes_front, R.id.clothes_back,
//            R.id.back})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.btn_choice_finish:
//                if (mContainerBackBackground.getWidth() <= 0) {
//                    ToastUtil.showToast(mContext, "没有定制背面,请先定制背面", 0);
//                } else {
//                    generateBitmap();//生成衣服的图片
//                    if (imageBackPath != null && imageFrontPath != null) {
//                        startNewActivity();
//                    }
//                }
//                break;
//            case R.id.back:
//                finish();
//                break;
//            case R.id.choice_back:
//                switch (clotheKey.get(mCurrentPosition)) {
//                    case ARM:
//                        if (mContainerBackBackground.getVisibility() == View.VISIBLE) {
//                            mContainerBackBackground.setArmVisibility(View.GONE);
//                        }
//                        if (mButtonFront.isSelected()) {
//                            mChoiceArm.setVisibility(View.GONE);
//                        }
//                        break;
//                    case NECK:
//                        //衣领样式，点击back，返回最初设置的样式
//                        if (mButtonFront.isSelected()) {
//                            mChoiceNeck.setVisibility(View.GONE);
//                        }
//                        if (mButtonBack.isSelected()) {
//                            mContainerBackBackground.setNeckVisibility(View.GONE);
//                        }
//                        break;
//                    case COLOR:
//                        //不选择默认是白色
//                        commonStyleData.setColor("#ffffff");
//                        mBackStyleData.setColor("#ffffff");
//                        if (mButtonFront.isSelected()) {
//                            mClothes.setBackgroundResource(R.color.white);
//                        }
//                        int colorN = Color.parseColor("#ffffff");
//                        mContainerBackBackground.setColorResources(colorN);
//                        break;
//                    case ORNAMENT:
//                        if (mButtonFront.isSelected()) {
//                            mChoiceOrnament.setVisibility(View.GONE);
//                        }
//                        if (mButtonBack.isSelected()) {
//                            mContainerBackBackground.setOrnamentVisibility(View.GONE);
//                        }
//                        mChoiceOrnament.setVisibility(View.GONE);
//                        break;
//                    case PATTERN:
//                        if (mButtonFront.isSelected()) {
//                            if (mContainerFrontBackground.getCurrentSticker() != null) {
//                                mContainerFrontBackground.removeCurrentSticker();
//                                mContainerFrontBackground.setLocked(true);
//                            }
//                        }
//                        if (mButtonBack.isSelected()) {
//                            mContainerBackBackground.removeStickerView();
//                        }
//                        break;
//                    case SIGNATURE:
//                        if (!mContainerFrontBackground.isNoneSticker()) {
//                            mContainerFrontBackground.removeCurrentSticker();
//                            mContainerFrontBackground.setLocked(true);
//                        }
//                        break;
//                }
//                mRecyclerStyle.setVisibility(View.VISIBLE);
//                mRecyclerDetailStyle.setVisibility(View.GONE);
//                mBtnFinish.setVisibility(View.VISIBLE);
//                mChoiceReturn.setVisibility(View.GONE);
//                break;
//            case R.id.choice_done://点击每个样式的完成保存到数据
//                switch (clotheKey.get(mCurrentPosition)) {
//                    case ARM:
//                        if (mButtonFront.isSelected()) {
//                            commonStyleData.setArmUrl(imageUrl);
//                            mPresenter.setFront(true);
//
//                        }
//                        if (mButtonBack.isSelected()) {
//                            mBackStyleData.setArmUrl(imageUrl);
//                        }
//                        break;
//                    case NECK:
//                        if (mButtonFront.isSelected()) {
//                            commonStyleData.setNeckUrl(imageUrl);
//                            mPresenter.setFront(true);
//
//                        }
//                        if (mButtonBack.isSelected()) {
//                            mBackStyleData.setNeckUrl(imageUrl);
//                        }
//                        break;
//                    case COLOR:
//                        if (mButtonFront.isSelected()) {
//                            commonStyleData.setColor(mImagecolor);
//                        }
//                        mBackStyleData.setColor(mImagecolor);
//                        break;
//                    case ORNAMENT:
//                        if (mButtonFront.isSelected()) {
//                            commonStyleData.setOrnametUrl(imageUrl);
//                            mPresenter.setFront(true);
//
//                        }
//                        if (mButtonBack.isSelected()) {
//                            mBackStyleData.setOrnametUrl(imageUrl);
//                        }
//                        break;
//                    case PATTERN:
//                        if (mButtonFront.isSelected()) {
//                            commonStyleData.setPattern(imageUrl);
//                        }
//                        if (mButtonBack.isSelected()) {
//                            mBackStyleData.setPattern(imageUrl);
//                        }
//                        break;
//                    case SIGNATURE:
//                        if (mContainerFrontBackground.getCurrentSticker() != null) {
//                            TextSticker textSticker = (TextSticker) mContainerFrontBackground.getCurrentSticker();
//                            if (!Constants.ADD_TEXT.equals(textSticker.getText())) {
//                                saveText(textSticker);
//                            } else {
//                                return;
//                            }
//                        }
//                        if (mDesCurrentView == null) {
//                            mRecyclerStyle.setVisibility(View.VISIBLE);
//                            mRecyclerDetailStyle.setVisibility(View.GONE);
//                            mBtnFinish.setVisibility(View.VISIBLE);
//                            mChoiceReturn.setVisibility(View.GONE);
//                            mContainerFrontBackground.setLocked(true);
//                            mUpdateType = null;
//                        }
//                        break;
//                }
//                if (mDesCurrentView != null) {
//                    mRecyclerStyle.setVisibility(View.VISIBLE);
//                    mRecyclerDetailStyle.setVisibility(View.GONE);
//                    mBtnFinish.setVisibility(View.VISIBLE);
//                    mChoiceReturn.setVisibility(View.GONE);
//                    mDesCurrentView.setSelected(false);
//                    mContainerFrontBackground.setLocked(true);
//                }
//
//                mContainerBackBackground.setChoiceDone();//反面处理
//                break;
//            case R.id.clothes_front://点击正面
//                if (!NetworkUtils.isNetWorkConnected()) {
//                    showErrorMsg("无网络连接，请重试");
//                    return;
//                }
//
//                mPresenter.setFront(true);
//                mContainerFrontBackground.setVisibility(View.VISIBLE);
//                mContainerBackBackground.setVisibility(View.GONE);
//                mButtonFront.setSelected(true);
//                mButtonBack.setSelected(false);
//                String imageUrl = Constants.ImageUrl + gender + type + "A" + ".png";
//                Glide.with(this).load(imageUrl).into(mClothes);
//                mPresenter.getFrontDeign("front");
//                if (mContainerBackBackground.getCurrentSticker() != null) {
//                    mContainerBackBackground.setLocked(true);
//                }
//                mRecyclerStyle.setVisibility(View.VISIBLE);
//                mRecyclerDetailStyle.setVisibility(View.GONE);
//                mBtnFinish.setVisibility(View.VISIBLE);
//                mChoiceReturn.setVisibility(View.GONE);
//                break;
//            case R.id.clothes_back://点击反面
//                if (!NetworkUtils.isNetWorkConnected()) {
//                    showErrorMsg("无网络连接，请重试");
//                    return;
//                }
//                mContainerFrontBackground.setVisibility(View.GONE);
//                mContainerBackBackground.setVisibility(View.VISIBLE);
//                mButtonFront.setSelected(false);
//                mButtonBack.setSelected(true);
//                String imageBackUrl = Constants.ImageUrl + gender + type + "B" + ".png";
//                mContainerBackBackground.setImageUrl(imageBackUrl);
//                if (!isBack) {
//                    mPresenter.initShowStyle();
//                }
//                mPresenter.getBackDeign("back");
//                if (mContainerFrontBackground.getCurrentSticker() != null) {
//                    mContainerFrontBackground.setLocked(true);
//                }
//                mRecyclerStyle.setVisibility(View.VISIBLE);
//                mRecyclerDetailStyle.setVisibility(View.GONE);
//                mBtnFinish.setVisibility(View.VISIBLE);
//                mChoiceReturn.setVisibility(View.GONE);
//                isBack = true;
//                break;
//
//        }
//    }
//
//    public void saveText(TextSticker textSticker) {
//        if (!TextUtils.isEmpty(textSticker.getText()) && textEntities != null) {
//            for (TextEntity t : textEntities) {
//                if (t.getText().equals(textSticker.getText())) {
//                    textEntities.remove(t);
//                }
//            }
//        }
//        TextEntity textEntity;
//        if (!TextUtils.isEmpty(textSticker.getText())) {
//            textEntity = new TextEntity();
//            textEntity.setText(textSticker.getText());
//            textEntity.setColor(mImagecolor);
//            textEntities.add(textEntity);
//        }
//    }
//
//    private void startNewActivity() {
//        Bundle bundle = new Bundle();
//        mOrderBaseInfo.setBackUrl(imageBackPath);
//        mOrderBaseInfo.setFrontUrl(imageFrontPath);
//        mOrderBaseInfo.setGender(gender);
//        mOrderBaseInfo.setType(type);
//        mOrderBaseInfo.setColor(mImagecolor);
//        bundle.putParcelable("allImage", mOrderBaseInfo);
//        OrderData orderData = new OrderData();
//        orderData.setBackData(mBackStyleData);
//        orderData.setFrontData(commonStyleData);
//        String styleContext = orderData.getJsonObject();
//        bundle.putString("styleContext", styleContext);
//        if (avatarList != null) {
//            if (avatarList.size() != 0) {
//                bundle.putStringArrayList("avatar", avatarList);
//            }
//        }
//        startCommonActivity(this, bundle, ChoiceSizeActivity.class);
//    }
//
//    /**
//     * @param color
//     */
//    private void setColorBg(String color) {
//        int colorN = Color.parseColor(color);
//        if (mContainerFrontBackground.getVisibility() == View.VISIBLE) {
//            mClothes.setBackgroundColor(colorN);
//
//        }
//        mContainerBackBackground.setColorResources(colorN);
//    }
//
//    /**
//     * ornametUrl
//     *
//     * @param ornametUrl
//     */
//    private void setOrnametUrl(String ornametUrl) {
//        ornametUrl = Constants.ImageUrl + ornametUrl;
//        if (mContainerFrontBackground.getVisibility() == View.VISIBLE) {
//            Glide.with(this).load(ornametUrl).into(mChoiceOrnament);
//            mChoiceOrnament.setVisibility(View.VISIBLE);
//        }
//        if (mContainerBackBackground.getVisibility() == View.VISIBLE) {
//            mContainerBackBackground.setOrnameUrl(ornametUrl);
//        }
//    }
//
//    /**
//     * @param armUrl
//     */
//    private void setArmImage(String armUrl) {
//        armUrl = Constants.ImageUrl + armUrl;
//        if (mContainerFrontBackground.getVisibility() == View.VISIBLE) {
//            mChoiceArm.setVisibility(View.VISIBLE);
//            Glide.with(this).load(armUrl).into(mChoiceArm);
//        }
//        if (mContainerBackBackground.getVisibility() == View.VISIBLE) {
//            mContainerBackBackground.setArmUrl(armUrl);
//        }
//    }
//
//    /**
//     * neck image
//     *
//     * @param neckUrl
//     */
//    private void setNeckImage(String neckUrl) {
//        neckUrl = Constants.ImageUrl + neckUrl;
//        if (mContainerFrontBackground.getVisibility() == View.VISIBLE) {
//            mChoiceNeck.setVisibility(View.VISIBLE);
//            Glide.with(this).load(neckUrl).into(mChoiceNeck);
//        }
//        if (mContainerBackBackground.getVisibility() == View.VISIBLE) {
//            mContainerBackBackground.setNeckUrl(neckUrl);
//        }
//    }
//
//    /**
//     * 增加贴图
//     *
//     * @param patternUrl
//     */
//    private void setPatternUrl(String patternUrl) {
//        patternUrl = Constants.ImageUrl + patternUrl;
//        if (mContainerFrontBackground.getVisibility() == View.VISIBLE) {
//            addStickerView(patternUrl);
//        }
//        if (mContainerBackBackground.getVisibility() == View.VISIBLE) {
//            mContainerBackBackground.addStickerView(patternUrl);
//        }
//    }
//
//
//    private void generateBitmap() {
//        Bitmap bitmap = Bitmap.createBitmap(mContainerBackBackground.getWidth(),
//                mContainerFrontBackground.getHeight()
//                , Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bitmap);
//        mContainerBackBackground.draw(canvas);
//        imageBackPath = FileUtils.saveBitmap(bitmap, this, "back");
//        Bitmap bitmapFront = Bitmap.createBitmap(mContainerFrontBackground.getWidth(),
//                mContainerFrontBackground.getHeight()
//                , Bitmap.Config.ARGB_8888);
//        Canvas canvasFront = new Canvas(bitmapFront);
//        mContainerFrontBackground.draw(canvasFront);
//        imageFrontPath = FileUtils.saveBitmap(bitmapFront, this, "front");
//    }
//
//
//    /**
//     * 点击total style
//     *
//     * @param position
//     */
//    @Override
//    public void onClick(View view, int position) {
//        if (mBeforeView != null) {
//            mBeforeView.setSelected(false);
//        }
//        view.setSelected(true);
//        mCurrentView = view;
//        mCurrentPosition = position;
//        mBeforeView = view;
//        if (mCurrentView.isSelected()) {
//            clickItem(mCurrentPosition);//点击进入详情设计界面
//        }
//
//    }
//
//    /**
//     * detail a design style
//     *
//     * @param currentView
//     * @param position    点击的位置
//     */
//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//    @Override
//    public void onItemClick(View currentView, int position) {
//        if (mDesBeforeView != null) {
//            mDesBeforeView.setSelected(false);
//        }
//        switch (clotheKey.get(mCurrentPosition)) {
//            case NECK:
//                String neck = newList.get(mCurrentPosition).getTitle();
//                imageUrl = NewDetailData.get(neck).get(position).getFile();
//                setNeckImage(imageUrl);
//                break;
//            case ARM:
//                imageUrl = NewDetailData.get(newList.get(mCurrentPosition).getTitle()).get(position).getFile();
//                setArmImage(imageUrl);
//                break;
//            case ORNAMENT:
//                imageUrl = NewDetailData.get(newList.get(mCurrentPosition).getTitle()).get(position).getFile();
//                setOrnametUrl(imageUrl);
//                break;
//            case COLOR:
//                mImagecolor = "#" + mColorData.get(newList.get(mCurrentPosition).getTitle()).get(position).getValue();
//                setColorBg(mImagecolor);
//                break;
//            case PATTERN:
//                imageUrl = mPatternData.get(newList.get(mCurrentPosition).getTitle()).get(position).getFile();
//                setPatternUrl(imageUrl);
//                break;
//            case SIGNATURE://签名处理
//                mImagecolor = "#" + mSignatureData.get(newList.get(mCurrentPosition).getTitle()).get(position).getValue();
//                int color = Color.parseColor(mImagecolor);
//                sticker.setTextColor(color);
//                TextSticker sticker = (TextSticker) mContainerFrontBackground.getCurrentSticker();
//                if (sticker != null) {
//                    sticker.setTextColor(color);
//                    mContainerFrontBackground.replace(sticker);
//                    mContainerFrontBackground.invalidate();
//                }
//                break;
//        }
//        prePosition = position;
//        currentView.setSelected(true);
//        mDesCurrentView = currentView;
//        mDesBeforeView = currentView;
//    }
//
//    /**
//     * 添加贴纸效果
//     *
//     * @param imageId
//     */
//    private void addStickerView(final String imageId) {
//        RequestOptions options = new RequestOptions();
//        Glide.with(this).asDrawable().apply(options).load(imageId).into(new SimpleTarget<Drawable>() {
//            @Override
//            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
//                if (resource != null) {
//                    mContainerFrontBackground.addSticker(new DrawableSticker(resource), Sticker.Position.CENTER);
//                }
//            }
//        });
//
//    }
//
//    /**
//     * click a design style
//     *
//     * @param position
//     */
//    private void clickItem(int position) {
//        mRecyclerDetailStyle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        if (NewDetailData.containsKey(newList.get(position).getTitle())) {
//            DetailStyleAdapter detailAdapter = new DetailStyleAdapter(this);
//            detailAdapter.setData(NewDetailData.get(newList.get(position).getTitle()));
//            detailAdapter.setOnClickListener(this);
//            mRecyclerDetailStyle.setAdapter(detailAdapter);
//            if (commonStyleData.getArmUrl() != null)
//                detailAdapter.notifyDataSetChanged();
//        }
//        if (mColorData.containsKey((newList.get(position).getTitle()))) {
//            ColorStyleAdapter colorStyleAdapter = new ColorStyleAdapter(this);
//            colorStyleAdapter.setData(mColorData.get(newList.get(position).getTitle()));
//            colorStyleAdapter.setOnClickListener(this);
//            mRecyclerDetailStyle.setAdapter(colorStyleAdapter);
//            colorStyleAdapter.notifyDataSetChanged();
//        }
//        if (mSignatureData.containsKey((newList.get(position).getTitle()))) {
//            TextStyleAdapter adapter = new TextStyleAdapter(this);
//            adapter.setData(mSignatureData.get(newList.get(position).getTitle()));
//            adapter.setOnClickListener(this);
//            mRecyclerDetailStyle.setAdapter(adapter);
//            adapter.notifyDataSetChanged();
//            mContainerFrontBackground.setLocked(false);
//            setSignatureText(null, false);//文字贴图
//        }
//        if (mPatternData.containsKey(newList.get(position).getTitle())) {
//            patternStyleAdapter = new PatternStyleAdapter(this);
//            patternStyleAdapter.setData(mPatternData.get(newList.get(position).getTitle()));
//            patternStyleAdapter.setOnClickListener(this);
//            patternStyleAdapter.setOnComClickListener(new ChoiceAvatarListener());
//            mRecyclerDetailStyle.setAdapter(patternStyleAdapter);
//            patternStyleAdapter.notifyDataSetChanged();
//            mContainerFrontBackground.setLocked(false);
//            mContainerBackBackground.setLocked(false);
//        }
//        mRecyclerStyle.setVisibility(View.GONE);
//        mRecyclerDetailStyle.setVisibility(View.VISIBLE);
//        mBtnFinish.setVisibility(View.GONE);
//        mChoiceReturn.setVisibility(View.VISIBLE);
//    }
//
//    private void setSignatureText(String message, final boolean isNew) {
//        final SignatureDialog dialog = new SignatureDialog(this, mUpdateType);
//        dialog.show();
//        if (message != null) {
//            dialog.setMessage(message);
//        }
//        Window win = dialog.getWindow();
//        if (win == null) {
//            return;
//        }
//        win.getDecorView().setPadding(0, 0, 0, 0);
//        WindowManager.LayoutParams lp = win.getAttributes();
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        win.setAttributes(lp);
//        dialog.setCompleteCallBack(new SignatureDialog.CompleteCallBack() {
//            @Override
//            public void onClickChoiceOrBack(View view, String s, Typeface typeface) {
//                content = s;
//                if (isNew) {
//                    if (sticker != null) {
//                        sticker.setText(s);
//                        sticker.resizeText();
//                        sticker.setTypeface(typeface);
//                        mContainerFrontBackground.replace(sticker);
//                        mContainerFrontBackground.invalidate();
//                    }
//
//                } else {
//                    sticker = new TextSticker(NewDetailDesignActivity.this);
//                    sticker.setText(s);
//                    sticker.setTypeface(typeface);
//                    sticker.setTextColor(Color.BLACK);
//                    sticker.setTextAlign(Layout.Alignment.ALIGN_CENTER);
//                    sticker.resizeText();
//                    mContainerFrontBackground.addSticker(sticker);
//                }
//            }
//        });
//    }
//
//    @Override
//    public void stateError() {
//
//    }
//
//    @Override
//    public void showErrorMsg(String msg) {
//        super.showErrorMsg(msg);
//        ToastUtil.showToast(mContext, msg, 0);
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        EventBus.getDefault().post(new ChangeSelectEvent(true));
//    }
//
//    @Override
//    public void showSuccessData(DetailStyleBean detailStyleBean) {
//
//    }
//
//    private class ChoiceAvatarListener implements ItemClickListener.OnItemComClickListener {
//        @Override
//        public void onItemClick(Object o, View view) {
//            EditUserPopupWindow mPopupWindow = mAvatarPresenter.initPopupWindow();
//            mPopupWindow.showAtLocation(mDesign, Gravity.CENTER | Gravity.BOTTOM, 0, 0);
//            mAvatarPresenter.setParams(Constants.CHANGE_ALPHA);
//            mDesCurrentView = view;
//        }
//    }
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        mAvatarPresenter.callback(requestCode, resultCode, data);
//    }
//
//    @Override
//    public void showSuccessAvatar(File cover) {
//
//        if (cover != null) {
//            setPatternAvatar(cover.getPath());
//            avatarList.add(cover.getPath());
//        }
//    }
//
//    private void setPatternAvatar(String path) {
//        if (mContainerFrontBackground.getVisibility() == View.VISIBLE) {
//            addStickerView(path);
//        }
//        if (mContainerBackBackground.getVisibility() == View.VISIBLE) {
//            mContainerBackBackground.addStickerView(path);
//        }
//    }
//
//}
