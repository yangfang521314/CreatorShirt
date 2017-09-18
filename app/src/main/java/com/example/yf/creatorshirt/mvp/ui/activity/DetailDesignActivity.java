package com.example.yf.creatorshirt.mvp.ui.activity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.mvp.listener.ItemClickListener;
import com.example.yf.creatorshirt.mvp.model.detaildesign.CommonStyleData;
import com.example.yf.creatorshirt.mvp.model.detaildesign.DetailColorStyle;
import com.example.yf.creatorshirt.mvp.model.detaildesign.DetailCommonData;
import com.example.yf.creatorshirt.mvp.model.detaildesign.DetailOtherStyle;
import com.example.yf.creatorshirt.mvp.model.detaildesign.DetailPatterStyle;
import com.example.yf.creatorshirt.mvp.model.detaildesign.DetailStyleBean;
import com.example.yf.creatorshirt.mvp.model.detaildesign.StyleBean;
import com.example.yf.creatorshirt.mvp.model.orders.OrderBaseInfo;
import com.example.yf.creatorshirt.mvp.model.orders.OrderData;
import com.example.yf.creatorshirt.mvp.presenter.DetailDesignPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.DetailDesignContract;
import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;
import com.example.yf.creatorshirt.mvp.ui.adapter.design.BaseStyleAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.design.ColorStyleAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.design.DetailStyleAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.design.PatternStyleAdapter;
import com.example.yf.creatorshirt.mvp.ui.view.ClothesBackView;
import com.example.yf.creatorshirt.utils.Constants;
import com.example.yf.creatorshirt.utils.DisplayUtil;
import com.example.yf.creatorshirt.utils.FileUtils;
import com.example.yf.creatorshirt.utils.LogUtil;
import com.example.yf.creatorshirt.utils.ToastUtil;
import com.example.yf.creatorshirt.widget.stickerview.StickerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 衣服具体设计样式界面
 */
public class DetailDesignActivity extends BaseActivity<DetailDesignPresenter> implements ItemClickListener.OnItemClickListener,
        ItemClickListener.OnClickListener, DetailDesignContract.DetailDesignView {

    private static final String TAG = DetailDesignActivity.class.getSimpleName();
    public static final String NECK = "neck";
    public static final String ARM = "arm";
    public static final String COLOR = "color";
    public static final String PATTERN = "pattern";
    public static final String ORNAMENT = "ornament";


    @BindView(R.id.design_choice_style)
    RecyclerView mRecyclerDetailStyle;//具体设计的recyclerView
    @BindView(R.id.choice_style)
    RecyclerView mRecyclerStyle;//style的recyclerView
    @BindView(R.id.choice_select_neck)
    ImageView mChoiceNeck;
    @BindView(R.id.clothes_container_background)
    ImageView mClothes;
    @BindView(R.id.ll_choice_back)
    LinearLayout mChoiceReturn;
    @BindView(R.id.btn_choice_finish)
    Button mBtnFinish;
    @BindView(R.id.choice_back)
    ImageView mChoiceDelete;
    @BindView(R.id.choice_done)
    ImageView mChoiceDone;
    @BindView(R.id.choice_ornament)
    ImageView mChoiceOrnament;
    @BindView(R.id.choice_select_arm)
    ImageView mChoiceArm;
    @BindView(R.id.rl_clothes_root)
    RelativeLayout mContainerFrontBackground;//正面
    //    @BindView(R.id.clothes_pattern_bounds)
//    RelativeLayout mPatternBounds;
//    @BindView(R.id.clothes_signature)
//    TextView mClothesSignature;
    @BindView(R.id.clothes_back)
    TextView mButtonBack;
    @BindView(R.id.clothes_front)
    TextView mButtonFront;
    @BindView(R.id.rl_clothes_back_root)
    ClothesBackView mContainerBackBackground;//背面背景
    @BindView(R.id.rl_bg)
    RelativeLayout mRelative;

    //总的样式
    private View mBeforeView;
    private View mCurrentView;
    //每个具体的样式
    private View mDesBeforeView;
    private View mDesCurrentView;
    private int mCurrentPosition = 0;

    //总样式和每一个具体的样式列表形成ArrayMap;
    private ArrayMap<String, List<DetailOtherStyle>> NewDetailData = new ArrayMap<>();
    private ArrayMap<String, List<DetailPatterStyle>> mPatternData = new ArrayMap<>();
    private ArrayMap<String, List<DetailColorStyle>> mColorData = new ArrayMap<>();

    //总样式的集合
    List<StyleBean> newList = new ArrayList<>();

    //处于编辑的贴纸
    private StickerView mCurrentStickerView;

    //是否要编辑标签
    private boolean isEditSign = false;

    // 存储贴纸列表
    private ArrayList<View> mViews = new ArrayList<>();

    //标签名
    private String mSignatureText;

    //定制完成后图片的路径
    private String imageFrontPath;
    private String imageBackPath;

    private String gender;//gender
    private String type;//类型
    private String mBackgroundUrl;//背景url

    private DetailCommonData mDetailStyleFrontData;//正面数据
    private DetailCommonData mDetailStyleBackData;//背面数据
    private BaseStyleAdapter mBaseDesignAdapter;
    private List<String> clotheKey = new ArrayList<>();//具体样式的字段名
    private CommonStyleData commonStyleData;//保存样式设计的url和颜色值
    private CommonStyleData mBackStyleData;//保存样式设计的url和颜色值

    private OrderBaseInfo mOrderBaseInfo;//保存正面和反面图片

    private ColorMatrix cm = new ColorMatrix();
    private Paint paint;

    private String imageUrl = null;//图片url
    private String mImagecolor = "#FFFFFF";//背景颜色 默认白色

    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getView() {
        return R.layout.activity_detail_design;
    }

    @Override
    protected void initView() {
        DisplayUtil.calculateRelative(this, mRelative);
        DisplayUtil.calculateBGWidth(App.getInstance(), mContainerFrontBackground);
        //默认显示正面
        initFrontBg();
        mRecyclerStyle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mBaseDesignAdapter = new BaseStyleAdapter(this);
        commonStyleData = new CommonStyleData();
        mBackStyleData = new CommonStyleData();
        mOrderBaseInfo = new OrderBaseInfo();
        String imageBackUrl = Constants.ImageUrl + gender + type + "B" + ".png";
        mContainerBackBackground.setImageUrl(imageBackUrl);
        mContainerBackBackground.setContext(this);
    }

    private void initFrontBg() {
        mBackgroundUrl = Constants.ImageUrl + gender + type + "A" + ".png";
        if (DisplayUtil.getScreenW(this) < 1080) {
            ViewGroup.LayoutParams params = mClothes.getLayoutParams();
            params.width = 592;
            mClothes.setLayoutParams(params);
            Glide.with(this).load(mBackgroundUrl).into(mClothes);
        } else {
            Glide.with(this).load(mBackgroundUrl).into(mClothes);
        }
        mAppBarTitle.setText(R.string.design);
        mAppBarBack.setVisibility(View.VISIBLE);
        mRecyclerStyle.setVisibility(View.VISIBLE);
        mBtnFinish.setVisibility(View.VISIBLE);
    }

    @Override
    public void initData() {
        if (getIntent().getExtras() != null) {
            gender = getIntent().getExtras().getString("gender");
            type = getIntent().getExtras().getString("type");
        }
        mPresenter.getDetailDesign(gender, type);

    }


    @Override
    public void showSuccessData(DetailStyleBean detailStyleBean) {
        if (detailStyleBean != null) {
            mDetailStyleBackData = detailStyleBean.getData().getB();
            mDetailStyleFrontData = detailStyleBean.getData().getA();
            getNameDeign(mDetailStyleFrontData);
            mButtonFront.setSelected(true);
        }

    }

    /**
     * 形成数组
     *
     * @param mData
     */
    private void getNameDeign(DetailCommonData mData) {
        StyleBean styleBean;
        String name;
        if (newList != null) {
            newList.removeAll(newList);
        }
        if (NewDetailData != null) {
            NewDetailData.clear();
        }
        if (clotheKey != null) {
            clotheKey.removeAll(clotheKey);
        }
        if (mData.getNeck().getFileList() != null && mData.getNeck().getFileList().size() != 0) {
            styleBean = new StyleBean();
            name = mData.getNeck().getName();
            styleBean.setTitle(name);
            newList.add(styleBean);
            clotheKey.add(NECK);
            addNewData(name, mData.getNeck().getFileList());
            setNeckImage(mData.getNeck().getFileList().get(0).getFile());//初始化显示
            commonStyleData.setNeckUrl(mData.getNeck().getFileList().get(0).getFile());
            mBackStyleData.setNeckUrl(mData.getNeck().getFileList().get(0).getFile());
        }
        if (mData.getArm().getFileList().size() != 0 && mData.getArm().getFileList() != null) {
            styleBean = new StyleBean();
            name = mData.getArm().getName();
            styleBean.setTitle(name);
            newList.add(styleBean);
            clotheKey.add(ARM);
            addNewData(name, mData.getArm().getFileList());
            setArmImage(mData.getArm().getFileList().get(0).getFile());//初始化显示
            commonStyleData.setArmUrl(mData.getArm().getFileList().get(0).getFile());
            mBackStyleData.setArmUrl(mData.getArm().getFileList().get(0).getFile());
        }
        if (mData.getOrnament().getFileList() != null && mData.getOrnament().getFileList().size() != 0) {
            styleBean = new StyleBean();
            name = mData.getOrnament().getName();
            styleBean.setTitle(name);
            newList.add(styleBean);
            clotheKey.add(ORNAMENT);
            addNewData(name, mData.getOrnament().getFileList());
            setOrnametUrl(mData.getOrnament().getFileList().get(0).getFile());//初始化显示
            commonStyleData.setOrnametUrl(mData.getOrnament().getFileList().get(0).getFile());
            mBackStyleData.setOrnametUrl(mData.getOrnament().getFileList().get(0).getFile());
        }
        if (mData.getColor().getFileList() != null && mData.getColor().getFileList().size() != 0) {
            styleBean = new StyleBean();
            name = mData.getColor().getName();
            styleBean.setTitle(name);
            newList.add(styleBean);
            clotheKey.add(COLOR);
            addColorData(name, mData.getColor().getFileList());
        }
        if (mData.getPattern().getFileList() != null && mData.getPattern().getFileList().size() != 0) {
            styleBean = new StyleBean();
            name = mData.getPattern().getName();
            styleBean.setTitle(name);
            newList.add(styleBean);
            clotheKey.add(PATTERN);
            addPatternData(name, mData.getPattern().getFileList());
        }
        mBaseDesignAdapter.setItemClickListener(this);
        mBaseDesignAdapter.setData(newList);
        mRecyclerStyle.setAdapter(mBaseDesignAdapter);
        mBaseDesignAdapter.notifyDataSetChanged();

    }

    /**
     * color data
     *
     * @param s
     * @param fileList
     */
    private void addColorData(String s, List<DetailColorStyle> fileList) {
        mColorData.put(s, fileList);
    }

    /**
     * patter data
     *
     * @param title
     * @param fileList
     */
    private void addPatternData(String title, List<DetailPatterStyle> fileList) {
        mPatternData.put(title, fileList);
    }

    /**
     * ornamet/arm/neck
     *
     * @param title
     * @param fileList
     */
    private void addNewData(String title, List<DetailOtherStyle> fileList) {
        if (!NewDetailData.containsKey(title) && fileList != null) {
            NewDetailData.put(title, fileList);
            Log.e(TAG, "news detail" + NewDetailData.size());
        }
    }

    @OnClick({R.id.btn_choice_finish, R.id.choice_done, R.id.choice_back, R.id.clothes_front, R.id.clothes_back,
            R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_choice_finish:
                if (mContainerBackBackground.getWidth() <= 0) {
                    ToastUtil.showToast(this, "没有定制背面,请先定制背面", 0);
                } else {
                    //// TODO: 2017/8/27 没有图片时会报null
                    generateBitmap();//生成衣服的图片
                    if (imageBackPath != null && imageFrontPath != null) {
                        startNewActivity();
                    }
                }
                break;
            case R.id.back:
                finish();
                break;
            case R.id.choice_back:
                switch (clotheKey.get(mCurrentPosition)) {
                    case ARM:
                        if (mContainerBackBackground.getVisibility() == View.VISIBLE) {
                            mContainerBackBackground.setArmVisibility(View.GONE);
                        }
                        if (mButtonFront.isSelected()) {
                            mChoiceArm.setVisibility(View.GONE);
                        }
                        break;
                    case NECK:
                        //衣领样式，点击back，返回最初设置的样式
                        if (mButtonFront.isSelected()) {
                            mChoiceNeck.setVisibility(View.GONE);
                        }
                        if (mButtonBack.isSelected()) {
                            mContainerBackBackground.setNeckVisibility(View.GONE);
                        }
                        break;
                    case COLOR:
                        //不选择默认是白色
                        commonStyleData.setColor("#ffffff");
                        mBackStyleData.setColor("#ffffff");
                        if (mButtonFront.isSelected()) {
                            mClothes.setBackgroundResource(R.color.white);
                        }
                        if (mButtonBack.isSelected()) {
                            mContainerBackBackground.setColor(R.color.white);
                        }
                        break;
                    case ORNAMENT:
                        if (mButtonFront.isSelected()) {
                            mChoiceOrnament.setVisibility(View.GONE);
                        }
                        if (mButtonBack.isSelected()) {
                            mContainerBackBackground.setOrnamentVisibility(View.GONE);
                        }
                        mChoiceOrnament.setVisibility(View.GONE);
                        break;
                    case PATTERN:
                        if (mButtonFront.isSelected()) {
                            if (mCurrentStickerView != null) {
                                mViews.remove(mCurrentStickerView);
                                mContainerFrontBackground.removeView(mCurrentStickerView);
                            }
                        }
                        if (mButtonBack.isSelected()) {
                            mContainerBackBackground.removeStickerView();
                        }
                        break;
//                    case SIGNATURE:
//                        mClothesSignature.setVisibility(View.GONE);
//                        break;
                }
//                mPatternBounds.setVisibility(View.GONE);
                mRecyclerStyle.setVisibility(View.VISIBLE);
                mRecyclerDetailStyle.setVisibility(View.GONE);
                mBtnFinish.setVisibility(View.VISIBLE);
                mChoiceReturn.setVisibility(View.GONE);
                break;
            case R.id.choice_done://点击每个样式的完成保存到数据
                switch (clotheKey.get(mCurrentPosition)) {
                    case ARM:
                        if (mButtonFront.isSelected()) {
                            commonStyleData.setArmUrl(imageUrl);
                        }
                        if (mButtonBack.isSelected()) {
                            mBackStyleData.setArmUrl(imageUrl);
                        }
                        break;
                    case NECK:
                        if (mButtonFront.isSelected()) {
                            commonStyleData.setNeckUrl(imageUrl);
                        }
                        if (mButtonBack.isSelected()) {
                            mBackStyleData.setNeckUrl(imageUrl);
                        }
                        break;
                    case COLOR:
                        if (mButtonFront.isSelected()) {
                            commonStyleData.setColor(mImagecolor);
                        }
                        if (mButtonBack.isSelected()) {
                            mBackStyleData.setColor(mImagecolor);
                        }
                        break;
                    case ORNAMENT:
                        if (mButtonFront.isSelected()) {
                            commonStyleData.setOrnametUrl(imageUrl);
                        }
                        if (mButtonBack.isSelected()) {
                            mBackStyleData.setOrnametUrl(imageUrl);
                        }
                        break;
                    case PATTERN:
                        if (mButtonFront.isSelected()) {
                            commonStyleData.setPattern(imageUrl);
                        }
                        if (mButtonBack.isSelected()) {
                            mBackStyleData.setPattern(imageUrl);
                        }
                        break;
//                    case SIGNATURE:
//                        mClothesSignature.setVisibility(View.GONE);
//                        break;
                }
                if (mDesCurrentView != null && mDesCurrentView.isSelected()) {
                    mRecyclerStyle.setVisibility(View.VISIBLE);
                    mRecyclerDetailStyle.setVisibility(View.GONE);
                    mBtnFinish.setVisibility(View.VISIBLE);
                    mChoiceReturn.setVisibility(View.GONE);
                }
                if (mCurrentStickerView != null) {
                    mCurrentStickerView.setInEdit(false);
//                    mPatternBounds.setVisibility(View.GONE);
                    // TODO: 22/06/2017 完成后禁止StickerView的点击事件
                    mCurrentStickerView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return true;
                        }
                    });
                }
//                if (CLOTHES_STYLE.equals(SIGNATURE) && isEditSign) {
//                    setSignatureText();//签名处理
//                }
                break;
            case R.id.clothes_front://点击正面
                mContainerFrontBackground.setVisibility(View.VISIBLE);
                mContainerBackBackground.setVisibility(View.GONE);
                mButtonFront.setSelected(true);
                mButtonBack.setSelected(false);
                if (commonStyleData != null) {
                    if (commonStyleData.getNeckUrl() != null) {
                        setNeckImage(commonStyleData.getNeckUrl());
                    }
                    if (commonStyleData.getArmUrl() != null) {
                        setArmImage(commonStyleData.getArmUrl());
                    }
                    if (commonStyleData.getPattern() != null) {
                        addStickerView(commonStyleData.getPattern());
                    }
                    if (commonStyleData.getOrnametUrl() != null) {
                        setOrnametUrl(commonStyleData.getOrnametUrl());
                    }
                    if (commonStyleData.getColor() != null) {
                        setColorBg(commonStyleData.getColor());
                    }
                }
                String imageUrl = Constants.ImageUrl + gender + type + "A" + ".png";
                Glide.with(this).load(imageUrl).into(mClothes);
                getNameDeign(mDetailStyleFrontData);
//                mPatternBounds.setVisibility(View.GONE);
                mRecyclerStyle.setVisibility(View.VISIBLE);
                mRecyclerDetailStyle.setVisibility(View.GONE);
                mBtnFinish.setVisibility(View.VISIBLE);
                mChoiceReturn.setVisibility(View.GONE);
                break;
            case R.id.clothes_back://点击反面
                mContainerFrontBackground.setVisibility(View.GONE);
                mContainerBackBackground.setVisibility(View.VISIBLE);
                mButtonBack.setSelected(true);
                mButtonFront.setSelected(false);
                getNameDeign(mDetailStyleBackData);
                String imageBackUrl = Constants.ImageUrl + gender + type + "B" + ".png";
                mContainerBackBackground.setImageUrl(imageBackUrl);
                if (mBackStyleData != null) {
                    mContainerBackBackground.setBackData(mBackStyleData);
                }
//                mPatternBounds.setVisibility(View.GONE);
                mRecyclerStyle.setVisibility(View.VISIBLE);
                mRecyclerDetailStyle.setVisibility(View.GONE);
                mBtnFinish.setVisibility(View.VISIBLE);
                mChoiceReturn.setVisibility(View.GONE);
                break;

        }
    }

    private void startNewActivity() {
        Bundle bundle = new Bundle();
        mOrderBaseInfo.setBackUrl(imageBackPath);
        mOrderBaseInfo.setFrontUrl(imageFrontPath);
        mOrderBaseInfo.setGender(gender);
        mOrderBaseInfo.setType(type);
        mOrderBaseInfo.setColor(mImagecolor);
        bundle.putParcelable("allImage", mOrderBaseInfo);
        OrderData orderData = new OrderData();
        orderData.setBackData(mBackStyleData);
        orderData.setFrontData(commonStyleData);
        String styleContext = orderData.getJsonObject();
        bundle.putString("styleContext", styleContext);
        startCommonActivity(this, bundle, ChoiceSizeActivity.class);
    }

    /**
     * @param color
     */
    private void setColorBg(String color) {
        int colorN = Color.parseColor(color);
        if (mContainerFrontBackground.getVisibility() == View.VISIBLE) {
            Log.e("Tag", "dddd" + mClothes.getHeight() + ":" + mClothes.getWidth());
            mClothes.setBackgroundColor(colorN);
        }
        if (mContainerBackBackground.getVisibility() == View.VISIBLE) {
            mContainerBackBackground.setColorResources(colorN);
        }
    }

    /**
     * ornametUrl
     *
     * @param ornametUrl
     */
    private void setOrnametUrl(String ornametUrl) {
        ornametUrl = Constants.ImageUrl + ornametUrl;
        if (mContainerFrontBackground.getVisibility() == View.VISIBLE) {
            Glide.with(this).load(ornametUrl).into(mChoiceOrnament);
            mChoiceOrnament.setVisibility(View.VISIBLE);
        }
        if (mContainerBackBackground.getVisibility() == View.VISIBLE) {
            mContainerBackBackground.setOrnameUrl(ornametUrl);
        }
    }

    /**
     * @param armUrl
     */
    private void setArmImage(String armUrl) {
        armUrl = Constants.ImageUrl + armUrl;
        if (mContainerFrontBackground.getVisibility() == View.VISIBLE) {
            mChoiceArm.setVisibility(View.VISIBLE);
            Glide.with(this).load(armUrl).into(mChoiceArm);
        }
        if (mContainerBackBackground.getVisibility() == View.VISIBLE) {
            mContainerBackBackground.setArmUrl(armUrl);
        }
    }

    /**
     * neck image
     *
     * @param neckUrl
     */
    private void setNeckImage(String neckUrl) {
        neckUrl = Constants.ImageUrl + neckUrl;
        if (mContainerFrontBackground.getVisibility() == View.VISIBLE) {
            mChoiceNeck.setVisibility(View.VISIBLE);
            Glide.with(this).load(neckUrl).into(mChoiceNeck);
        }
        if (mContainerBackBackground.getVisibility() == View.VISIBLE) {
            mContainerBackBackground.setNeckUrl(neckUrl);
        }
    }

    /**
     * 增加贴图
     *
     * @param patternUrl
     */
    private void setPatternUrl(String patternUrl) {
        patternUrl = Constants.ImageUrl + patternUrl;
        if (mContainerFrontBackground.getVisibility() == View.VISIBLE) {
            addStickerView(patternUrl);
        }
        if (mContainerBackBackground.getVisibility() == View.VISIBLE) {
            mContainerBackBackground.addStickerView(patternUrl);
        }
    }


    private void generateBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(mContainerBackBackground.getWidth(),
                mContainerBackBackground.getHeight()
                , Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        mContainerBackBackground.draw(canvas);
        imageBackPath = FileUtils.saveBitmap(bitmap, this, "back");

        Bitmap bitmapFront = Bitmap.createBitmap(mContainerFrontBackground.getWidth(),
                mContainerFrontBackground.getHeight()
                , Bitmap.Config.ARGB_8888);
        Canvas canvasFront = new Canvas(bitmapFront);
        mContainerFrontBackground.draw(canvasFront);
        imageFrontPath = FileUtils.saveBitmap(bitmapFront, this, "front");
    }

    /**
     * 签名
     */
//    private void setSignatureText() {
//        final SignatureDialog dialog = new SignatureDialog(this);
//        dialog.show();
//        Window win = dialog.getWindow();
//        win.getDecorView().setPadding(0, 0, 0, 0);
//        WindowManager.LayoutParams lp = win.getAttributes();
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        win.setAttributes(lp);
//        dialog.setCompleteCallBack(new SignatureDialog.CompleteCallBack() {
//            @Override
//            public void onClickChoiceOrBack(View view, String s) {
//                switch (view.getId()) {
//                    case R.id.choice_done:
//                        mSignatureText = s;
//                        mClothesSignature.setVisibility(View.VISIBLE);
//                        mClothesSignature.setText(mSignatureText);
//                        dialog.dismiss();
//                        break;
//                    case R.id.choice_back:
//                        mSignatureText = "";
//                        dialog.dismiss();
//                        break;
//                }
//            }
//        });
//    }


    /**
     * 点击total style
     *
     * @param position
     */
    @Override
    public void onClick(View view, int position) {
        if (mBeforeView != null) {
            mBeforeView.setSelected(false);
        }
        view.setSelected(true);
        mCurrentView = view;
        mCurrentPosition = position;
        LogUtil.e(TAG, "currentPosition" + mCurrentPosition);
        mBeforeView = view;
        if (mCurrentView != null && mCurrentView.isSelected()) {
            clickItem(mCurrentPosition);//点击进入详情设计界面
        }

    }

    /**
     * detail a design style
     *
     * @param currentView
     * @param position    点击的位置
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onItemClick(View currentView, int position) {
        if (mDesBeforeView != null) {
            mDesBeforeView.setSelected(false);
        }
        switch (clotheKey.get(mCurrentPosition)) {
            case NECK:
                String neck = newList.get(mCurrentPosition).getTitle();
                imageUrl = NewDetailData.get(neck).get(position).getFile();
                Log.e(TAG, "image" + imageUrl);
                setNeckImage(imageUrl);
                break;
            case ARM:
                imageUrl = NewDetailData.get(newList.get(mCurrentPosition).getTitle()).get(position).getFile();
                setArmImage(imageUrl);
                break;
            case ORNAMENT:
                imageUrl = NewDetailData.get(newList.get(mCurrentPosition).getTitle()).get(position).getFile();
                setOrnametUrl(imageUrl);
                break;
            case COLOR:
                mImagecolor = "#" + mColorData.get(newList.get(mCurrentPosition).getTitle()).get(position).getValue();
                Log.e(TAG, "COLOR:" + mImagecolor);
                setColorBg(mImagecolor);
                break;
            case PATTERN:
//                mPatternBounds.setVisibility(View.VISIBLE);
                imageUrl = mPatternData.get(newList.get(mCurrentPosition).getTitle()).get(position).getFile();
                setPatternUrl(imageUrl);
                break;
//            case SIGNATURE://签名处理
//                if (position == 0) {
//                    mClothesSignature.setVisibility(View.GONE);
//                    isEditSign = false;
//                } else if (position == 1) {
//                    Log.e("TAG", "DDDDD");
//                    mClothesSignature.setVisibility(View.VISIBLE);
//                    isEditSign = true;
//                }
//                break;
        }
        currentView.setSelected(true);
        mDesCurrentView = currentView;
        mDesBeforeView = currentView;

    }

    /**
     * 添加贴纸效果
     *
     * @param imageId
     */
    private void addStickerView(final String imageId) {
        final StickerView stickerView = new StickerView(this);
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        Glide.with(this).asBitmap().apply(options).load(imageId).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                stickerView.setImageBitmap(resource);
            }
        });

        stickerView.setOperationListener(new StickerView.OperationListener() {
            @Override
            public void onDeleteClick() {
                mViews.remove(stickerView);
                mContainerFrontBackground.removeView(stickerView);
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
        //正面
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        mContainerFrontBackground.addView(stickerView, lp);
        mViews.add(stickerView);
        setStickerViewEdit(stickerView);
    }

    /**
     * 设置当前处于编辑模式的贴纸
     *
     * @param stickerView
     */
    private void setStickerViewEdit(StickerView stickerView) {
        if (mCurrentStickerView != null) {
            mCurrentStickerView.setInEdit(false);
        }
        mCurrentStickerView = stickerView;
        stickerView.setInEdit(true);

    }

    /**
     * click a design style
     *
     * @param position
     */
    private void clickItem(int position) {
        mRecyclerDetailStyle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        if (NewDetailData.containsKey(newList.get(position).getTitle())) {
            DetailStyleAdapter detailAdapter = new DetailStyleAdapter(this);
            detailAdapter.setData(NewDetailData.get(newList.get(position).getTitle()));
            detailAdapter.setOnClickListener(this);
            mRecyclerDetailStyle.setAdapter(detailAdapter);
            if (commonStyleData.getArmUrl() != null)
                detailAdapter.notifyDataSetChanged();
        }
        if (mColorData.containsKey((newList.get(position).getTitle()))) {
            ColorStyleAdapter adapter = new ColorStyleAdapter(this);
            adapter.setData(mColorData.get(newList.get(position).getTitle()));
            adapter.setOnClickListener(this);
            mRecyclerDetailStyle.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        if (mPatternData.containsKey(newList.get(position).getTitle())) {
            PatternStyleAdapter patternStyleAdapter = new PatternStyleAdapter(this);
            patternStyleAdapter.setData(mPatternData.get(newList.get(position).getTitle()));
            patternStyleAdapter.setOnClickListener(this);
            mRecyclerDetailStyle.setAdapter(patternStyleAdapter);
            patternStyleAdapter.notifyDataSetChanged();
        }
        mRecyclerStyle.setVisibility(View.GONE);
        mRecyclerDetailStyle.setVisibility(View.VISIBLE);
        mBtnFinish.setVisibility(View.GONE);
        mChoiceReturn.setVisibility(View.VISIBLE);
    }

    @Override
    public void stateError() {

    }
}
