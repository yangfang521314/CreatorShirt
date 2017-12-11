package com.example.yf.creatorshirt.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
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
import com.example.yf.creatorshirt.common.ChangeSelectEvent;
import com.example.yf.creatorshirt.mvp.listener.ItemClickListener;
import com.example.yf.creatorshirt.mvp.model.VersionStyle;
import com.example.yf.creatorshirt.mvp.model.detaildesign.DetailColorStyle;
import com.example.yf.creatorshirt.mvp.model.detaildesign.DetailPatterStyle;
import com.example.yf.creatorshirt.mvp.model.detaildesign.DetailStyleBean;
import com.example.yf.creatorshirt.mvp.model.detaildesign.StyleBean;
import com.example.yf.creatorshirt.mvp.model.detaildesign.TextEntity;
import com.example.yf.creatorshirt.mvp.model.orders.OrderBaseInfo;
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
import com.example.yf.creatorshirt.mvp.ui.view.AnyShapeView;
import com.example.yf.creatorshirt.mvp.ui.view.ClothesBackView;
import com.example.yf.creatorshirt.mvp.ui.view.EditUserPopupWindow;
import com.example.yf.creatorshirt.mvp.ui.view.sticker.SignatureDialog;
import com.example.yf.creatorshirt.mvp.ui.view.sticker.Sticker;
import com.example.yf.creatorshirt.mvp.ui.view.sticker.StickerView;
import com.example.yf.creatorshirt.mvp.ui.view.sticker.TextSticker;
import com.example.yf.creatorshirt.utils.Constants;
import com.example.yf.creatorshirt.utils.DisplayUtil;
import com.example.yf.creatorshirt.utils.FileUtils;
import com.example.yf.creatorshirt.utils.ToastUtil;

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
    @BindView(R.id.clothes)
    ImageView mClothes;
    @BindView(R.id.source)
    AnyShapeView mAnyShapeView;
    @BindView(R.id.rl_clothes_root)
    StickerView mContainerFront;
    @BindView(R.id.tv_back)
    TextView mButtonBack;
    @BindView(R.id.tv_front)
    TextView mButtonFront;
    @BindView(R.id.rl_bg)
    RelativeLayout mRelative;
    @BindView(R.id.rl_clothes_root_back)
    ClothesBackView mContainerBack;

    CommonAvatarPresenter mAvatarPresenter;//自定义图片

    private BaseStyleAdapter mBaseDesignAdapter;
    private ArrayMap<String, List<DetailPatterStyle>> mPatternData = new ArrayMap<>();
    private ArrayMap<String, List<VersionStyle>> mClothesData = new ArrayMap<>();
    private ArrayMap<String, List<DetailColorStyle>> mMaskData = new ArrayMap<>();
    private ArrayMap<String, List<DetailColorStyle>> mSignatureData = new ArrayMap<>();
    private List<TextEntity> textEntities = new ArrayList<>();//保存字体
    //    总样式的集合
    private List<StyleBean> newList = new ArrayList<>();
    private List<String> clotheKey = new ArrayList<>();//具体样式的字段名
    private View mBeforeView;
    private View mCurrentView;
    private int mCurrentPosition;
    private PatternStyleAdapter patternStyleAdapter;
    private View mDesCurrentView;
    private View mDesBeforeView;
    private String mImagecolor;
    private String imageUrl;
    private int prePosition;
    private TextSticker textSticker;
    private String content;
    private Typeface mUpdateType;
    private ArrayList<VersionStyle> mListClothes;
    private VersionStyle mInitData;//默认显示第一件衣服
    private String imageFrontPath;
    private String imageBackPath;
    private OrderBaseInfo mOrderBaseInfo; //订单信息
    private String colorClothes;
    private String gender;//性别
    private ArrayList<String> avatarList = new ArrayList<>();
    private boolean isFirst;
    private VersionStyle mCurrentClothes;


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

        mPresenter.getDetailDesign("A", "11", mListClothes, true);
        mAvatarPresenter = new CommonAvatarPresenter(this);
        mAvatarPresenter.attachView(this);
    }

    @Override
    protected void initView() {
        mRecyclerStyle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mBaseDesignAdapter = new BaseStyleAdapter(this);
        //默认显示正面
        isFirst = true;
        initFront();
        initBack();
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
        if (DisplayUtil.getScreenW(this) < 1080) {
            DisplayUtil.calculateSmallRl(mContainerFront);
        } else {
            DisplayUtil.calculateRl(mContainerFront);
        }
        //初始化显示第一件正面衣服款式
        if (mInitData != null) {
            getCurrentFrontClothes(mInitData,"a");
            gender = mInitData.getGender();
        }
        mOrderBaseInfo = new OrderBaseInfo();
    }

    private void initBack() {
        if (DisplayUtil.getScreenW(this) < 1080) {
            DisplayUtil.calculateSmallRl(mContainerBack);
        } else {
            DisplayUtil.calculateRl(mContainerBack);
        }
    }


    @OnClick({R.id.btn_choice_finish, R.id.choice_done, R.id.choice_back, R.id.tv_front, R.id.tv_back,
            R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_choice_finish:
                if (mContainerBack.getWidth() <= 0) {
                    ToastUtil.showToast(mContext, "没有定制背面,请先定制背面", 0);
                } else {
                    generateBitmap();//生成衣服的图片
                    if (imageBackPath != null && imageFrontPath != null) {
//                        startNewActivity();
                    }
                }
                break;
            case R.id.back:
                finish();
                break;
            case R.id.choice_back:
                switch (clotheKey.get(mCurrentPosition)) {
                    case COLOR:
                        break;
                    case PATTERN:
                        mAnyShapeView.setImageSource(null);
                        break;
                    case MASK:
                        mAnyShapeView.setImageMask(0);

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
            case R.id.tv_front:
                if (isFirst) {//默认刚进来的第一件衣服
                    if (mInitData != null) {
                        getCurrentFrontClothes(mInitData, "a");
                    }
                } else {
                    getCurrentFrontClothes(mCurrentClothes, "a");
                }
                mContainerFront.setVisibility(View.VISIBLE);
                mContainerBack.setVisibility(View.GONE);
                mButtonFront.setSelected(true);
                mButtonBack.setSelected(false);
                mPresenter.getDetailDesign("A", "11", mListClothes, true);
                mRecyclerStyle.setVisibility(View.VISIBLE);
                mRecyclerDetailStyle.setVisibility(View.GONE);
                mBtnFinish.setVisibility(View.VISIBLE);
                mChoiceReturn.setVisibility(View.GONE);
                break;
            case R.id.tv_back:
                if (isFirst) {//默认刚进来的第一件衣服
                    if (mInitData != null) {
                        getCurrentBackClothes(mInitData, "b");
                    }
                } else {
                    getCurrentBackClothes(mCurrentClothes, "b");
                }
                mContainerBack.setVisibility(View.VISIBLE);
                mContainerFront.setVisibility(View.GONE);
                mButtonBack.setSelected(true);
                mButtonFront.setSelected(false);
                mPresenter.getDetailDesign("A", "11", mListClothes, false);
                mRecyclerStyle.setVisibility(View.VISIBLE);
                mRecyclerDetailStyle.setVisibility(View.GONE);
                mBtnFinish.setVisibility(View.VISIBLE);
                mChoiceReturn.setVisibility(View.GONE);
                break;

            case R.id.choice_done://点击每个样式的完成保存到数据
                switch (clotheKey.get(mCurrentPosition)) {

                    case COLOR:

                        break;

                    case PATTERN:

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

    private void getCurrentBackClothes(VersionStyle data, String flag) {
        String type = data.getType();
        String colorName = data.getColorName();
        String imageName = data.getSex() + type + "_" + colorName + "_" + flag;
        mContainerBack.setColorBg(getResource(imageName));
    }

    private void getCurrentFrontClothes(VersionStyle data, String flag) {
        String type = data.getType();
        String colorName = data.getColorName();
        String imageName = data.getSex() + type + "_" + colorName + "_" + flag;
        setColorBg(getResource(imageName));
    }

    private void startNewActivity() {
        Bundle bundle = new Bundle();
        mOrderBaseInfo.setBackUrl(imageBackPath);
        mOrderBaseInfo.setFrontUrl(imageFrontPath);
        mOrderBaseInfo.setGender(gender);
//        mOrderBaseInfo.setType(type);
        mOrderBaseInfo.setColor(colorClothes);
        bundle.putParcelable("allImage", mOrderBaseInfo);
//        OrderData orderData = new OrderData();
//        orderData.setBackData(mBackStyleData);
//        orderData.setFrontData(commonStyleData);
//        String styleContext = orderData.getJsonObject();
//        bundle.putString("styleContext", styleContext);
        if (avatarList != null) {
            if (avatarList.size() != 0) {
                bundle.putStringArrayList("avatar", avatarList);
            }
        }
        startCommonActivity(this, bundle, ChoiceSizeActivity.class);
    }

    private void generateBitmap() {
        Bitmap mask = getBitmapFromView(mAnyShapeView, mAnyShapeView.getWidth(), mAnyShapeView.getHeight());
//        Bitmap bitmap = Bitmap.createBitmap(mContainerFront.getWidth(),
//                mContainerFront.getHeight()
//                , Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bitmap);
//        mContainerFront.draw(canvas);
//        imageFrontPath = FileUtils.saveBitmap(mask, this, "front");
        Bitmap bitmapFront = Bitmap.createBitmap(mContainerBack.getWidth(),
                mContainerBack.getHeight()
                , Bitmap.Config.ARGB_8888);
        Canvas canvasFront = new Canvas(bitmapFront);
        mContainerBack.draw(canvasFront);
        imageBackPath = FileUtils.saveBitmap(bitmapFront, this, "back");
    }

    /**
     * 由View得到对应的指定尺寸的Bitmap
     *
     * @param view
     * @return
     */
    public static Bitmap getBitmapFromView(View view, int bitmapWidth, int bitmapHeight) {
        //Define a bitmap with the same size as the view
        Bitmap tempBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(tempBitmap);
        //Get the view's background
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        } else {
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.TRANSPARENT);
        }
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        Bitmap resultBitmap = Bitmap.createScaledBitmap(tempBitmap, bitmapWidth, bitmapHeight, true);
        tempBitmap.recycle();
        return resultBitmap;
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
    public void onItemClick(View currentView, int position) {
        if (mDesBeforeView != null) {
            mDesBeforeView.setSelected(false);
        }
        switch (clotheKey.get(mCurrentPosition)) {
            case COLOR:
                colorClothes = mClothesData.get(newList.get(mCurrentPosition).getTitle()).get(position).getColor();
                String type = mClothesData.get(newList.get(mCurrentPosition).getTitle()).get(position).getType();
                String colorName = mClothesData.get(newList.get(mCurrentPosition).getTitle()).get(position).getColorName();
                String imageName;
                if (mButtonFront.isSelected()) {
                    imageName = mClothesData.get(newList.get(mCurrentPosition).getTitle()).get(position).getSex() + type + "_" + colorName + "_a";
                    setColorBg(getResource(imageName));

                }
                if (mButtonBack.isSelected()) {
                    imageName = mClothesData.get(newList.get(mCurrentPosition).getTitle()).get(position).getSex() + type + "_" + colorName + "_b";
                    mContainerBack.setColorBg(getResource(imageName));
                }
                isFirst = false;
                mCurrentClothes = mClothesData.get(newList.get(mCurrentPosition).getTitle()).get(position);
                break;
            case PATTERN:
                imageUrl = mPatternData.get(newList.get(mCurrentPosition).getTitle()).get(position).getFile();
                setPatternUrl(imageUrl);
                break;
            case MASK:
                if (mButtonFront.isSelected()) {
                    mAnyShapeView.setImageMask(getResource("gao"));
                }
                if (mButtonBack.isSelected()) {
                    mContainerBack.setImageMask(getResource("gao"));

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

    private void setPatternUrl(String imageUrl) {
        RequestOptions options = new RequestOptions();
        options.error(R.mipmap.ic_launcher);
        Glide.with(App.getInstance()).asBitmap().apply(options).load(Constants.ImageUrl + imageUrl).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                if (mButtonBack.isSelected()) {
                    mContainerBack.setImageNetSource(resource);
                }
                if (mButtonFront.isSelected()) {
                    mAnyShapeView.setImageNetSource(resource);
                }
            }
        });
    }

    private void setColorBg(int image) {
        mClothes.setImageResource(image);
    }

    @Override
    public void showSuccessData(DetailStyleBean detailStyleBean) {

    }

    @Override
    public void showSuccessData(List<StyleBean> newList, List<String> clotheKey, ArrayMap<String, List<VersionStyle>> mColorData, ArrayMap<String, List<DetailPatterStyle>> mPatternData, ArrayMap<String, List<DetailColorStyle>> mMaskData, ArrayMap<String, List<DetailColorStyle>> mSignatureData) {
        this.newList = newList;
        this.mPatternData = mPatternData;
        this.mClothesData = mColorData;
        this.clotheKey = clotheKey;
        this.mMaskData = mMaskData;
        this.mSignatureData = mSignatureData;
        mRecyclerStyle.setVisibility(View.VISIBLE);
        mBaseDesignAdapter.setItemClickListener(this);
        mBaseDesignAdapter.setData(newList);
        mRecyclerStyle.setAdapter(mBaseDesignAdapter);
        mBaseDesignAdapter.notifyDataSetChanged();
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
            patternStyleAdapter = new PatternStyleAdapter(this);
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
                content = s;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mAvatarPresenter.callback(requestCode, resultCode, data);
    }

    @Override
    public void showSuccessAvatar(File cover) {

        if (cover != null) {
            setPatternAvatar(cover.getPath());
            avatarList.add(cover.getPath());
        }
    }

    private void setPatternAvatar(String path) {
        mAnyShapeView.setImageSource(path);
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

    public int getResource(String imageName) {
        Context ctx = getBaseContext();
        int resId = getResources().getIdentifier(imageName, "mipmap", ctx.getPackageName());
        //如果没有在"mipmap"下找到imageName,将会返回0
        return resId;
    }

}
