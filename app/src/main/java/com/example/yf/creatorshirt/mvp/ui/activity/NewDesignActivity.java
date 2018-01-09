package com.example.yf.creatorshirt.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    private Typeface mUpdateType;
    private ArrayList<VersionStyle> mListClothes;
    private VersionStyle mInitData;//默认显示第一件衣服
    private String maskFront;
    private String maskBack;
    private OrderBaseInfo mOrderBaseInfo; //订单信息
    private String colorClothes;
    private String gender;//性别
    private ArrayList<String> avatarList = new ArrayList<>();
    private boolean isFirst;
    private VersionStyle mCurrentClothes;
    private String imageFront;
    private String imageBack;


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
            gender = mInitData.getGender();
        }
        mOrderBaseInfo = new OrderBaseInfo();

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
                    if (imageFront != null && imageBack != null) {
                        startNewActivity();
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
                        mContainerFront.setImageSource(null);
                        break;
                    case MASK:
                        mContainerFront.setImageMask(BitmapFactory.decodeResource(getResources(), R.mipmap.quan), null);
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
                        getCurrentFrontClothes(mInitData);
                    }
                } else {
                    getCurrentFrontClothes(mCurrentClothes);
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
                        getCurrentBackClothes(mInitData);
                    }
                } else {
                    getCurrentBackClothes(mCurrentClothes);
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

    private void generateBitmap() {
//        if (mAnyShapeView.getDrawable() != null) {
//            Bitmap bitmapFront = ((BitmapDrawable) mAnyShapeView.getDrawable()).getBitmap();
//            maskFront = FileUtils.saveBitmap(bitmapFront, this, "front");
//
//        }
//        if (mContainerBack.getSource().getDrawable() != null) {
//            Bitmap bitmapBack = ((BitmapDrawable) mContainerBack.getSource().getDrawable()).getBitmap();
//            maskBack = FileUtils.saveBitmap(bitmapBack, this, "back");
//        }
    }

    private void getCurrentBackClothes(VersionStyle data) {
        String type = data.getType();
        String colorName = data.getColorName();
        imageBack = data.getSex() + type + "_" + colorName + "_" + "b";
        mContainerBack.setColorBg(FileUtils.getResource(imageBack, this));
    }

    private void getCurrentFrontClothes(VersionStyle data) {
        String type = data.getType();
        String colorName = data.getColorName();
        imageFront = data.getSex() + type + "_" + colorName + "_" + "a";
        setColorBg(FileUtils.getResource(imageFront, this));
        mCurrentClothes = data;
    }

    private void startNewActivity() {
        Bundle bundle = new Bundle();
        mOrderBaseInfo.setBackUrl(imageBack);
        mOrderBaseInfo.setFrontUrl(imageFront);
        mOrderBaseInfo.setGender(gender);
        mOrderBaseInfo.setType(mCurrentClothes.getGender());
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
        if (maskFront != null) {
            bundle.putString("front", maskFront);
        }
        if (maskBack != null) {
            bundle.putString("back", maskBack);
        }
        startCommonActivity(this, bundle, ChoiceSizeActivity.class);
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
                if (mButtonFront.isSelected()) {
                    imageFront = mClothesData.get(newList.get(mCurrentPosition).getTitle()).get(position).getSex() + type + "_" + colorName + "_a";
                    setColorBg(FileUtils.getResource(imageFront, this));

                }
                if (mButtonBack.isSelected()) {
                    imageBack = mClothesData.get(newList.get(mCurrentPosition).getTitle()).get(position).getSex() + type + "_" + colorName + "_b";
                    mContainerBack.setColorBg(FileUtils.getResource(imageBack, this));
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
                    mContainerFront.setImageMask(BitmapFactory.decodeResource(getResources(), R.mipmap.quan),
                            BitmapFactory.decodeResource(getResources(),
                                    FileUtils.getResource(mCurrentClothes.getSex() + mCurrentClothes.getType()
                                            + "_" + mCurrentClothes.getColorName() + "_a", this)));
                }
                if (mButtonBack.isSelected()) {
                    mContainerBack.setImageMask(BitmapFactory.decodeResource(getResources(), R.mipmap.quan),
                            BitmapFactory.decodeResource(getResources(),
                                    FileUtils.getResource(mCurrentClothes.getSex() + mCurrentClothes.getType()
                                            + "_" + mCurrentClothes.getColorName() + "_a", this)));

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
                    mContainerBack.setImageSource(resource);
                }
                if (mButtonFront.isSelected()) {
                    mContainerFront.setImageSource(resource);
                }
            }
        });
    }

    private void setColorBg(int image) {
        mContainerFront.setColorBg(image);
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
        mContainerFront.setImageSource(BitmapFactory.decodeFile(path));
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
