package com.example.yf.creatorshirt.mvp.ui.activity;

import android.content.Intent;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.common.ChangeSelectEvent;
import com.example.yf.creatorshirt.mvp.listener.ItemClickListener;
import com.example.yf.creatorshirt.mvp.model.detaildesign.DetailColorStyle;
import com.example.yf.creatorshirt.mvp.model.detaildesign.DetailPatterStyle;
import com.example.yf.creatorshirt.mvp.model.detaildesign.DetailStyleBean;
import com.example.yf.creatorshirt.mvp.model.detaildesign.StyleBean;
import com.example.yf.creatorshirt.mvp.presenter.CommonAvatarPresenter;
import com.example.yf.creatorshirt.mvp.presenter.DetailDesignPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.CommonAvatarContract;
import com.example.yf.creatorshirt.mvp.presenter.contract.DetailDesignContract;
import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;
import com.example.yf.creatorshirt.mvp.ui.adapter.MaskStyleAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.design.BaseStyleAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.design.ColorStyleAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.design.PatternStyleAdapter;
import com.example.yf.creatorshirt.mvp.ui.view.AnyShapeView;
import com.example.yf.creatorshirt.mvp.ui.view.EditUserPopupWindow;
import com.example.yf.creatorshirt.utils.Constants;
import com.example.yf.creatorshirt.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class NewsDesignActivity extends BaseActivity<DetailDesignPresenter> implements ItemClickListener.OnItemClickListener,
        ItemClickListener.OnClickListener, DetailDesignContract.DetailDesignView, CommonAvatarContract.CommonAvatarView {
    public static final String COLOR = "color";
    public static final String PATTERN = "pattern";
    public static final String MASK = "mask";


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
    private String gender;
    private String type;
    CommonAvatarPresenter mAvatarPresenter;

    private BaseStyleAdapter mBaseDesignAdapter;
    private ArrayMap<String, List<DetailPatterStyle>> mPatternData = new ArrayMap<>();
    private ArrayMap<String, List<DetailColorStyle>> mColorData = new ArrayMap<>();
    private ArrayMap<String, List<DetailColorStyle>> mMaskData = new ArrayMap<>();
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
    private int[] clothes = {R.mipmap.short_red, R.mipmap.short_gray, R.mipmap.short_white_men, R.mipmap.short_black_men};
    private int[] maskes = {R.mipmap.quan};

    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {
        mRecyclerStyle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mBaseDesignAdapter = new BaseStyleAdapter(this);

    }

    @Override
    protected int getView() {
        return R.layout.activity_news_design;
    }

    @Override
    public void initData() {
        super.initData();
        if (getIntent().getExtras() != null) {
            gender = getIntent().getExtras().getString("gender");
            type = getIntent().getExtras().getString("type");
        }
        mPresenter.getDetailDesign(gender, type);
        mAvatarPresenter = new CommonAvatarPresenter(this);
        mAvatarPresenter.attachView(this);
        mPresenter.setFront(false);

    }

    @OnClick({R.id.btn_choice_finish, R.id.choice_done, R.id.choice_back, R.id.clothes_front, R.id.clothes_back,
            R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_choice_finish:
//                if (mContainerBackBackground.getWidth() <= 0) {
//                    ToastUtil.showToast(mContext, "没有定制背面,请先定制背面", 0);
//                } else {
//                    generateBitmap();//生成衣服的图片
//                    if (imageBackPath != null && imageFrontPath != null) {
//                        startNewActivity();
//                    }
//                }
                break;
            case R.id.back:
                finish();
                break;
            case R.id.choice_back:
                switch (clotheKey.get(mCurrentPosition)) {
                    case COLOR:
                        mChoiceReturn.setVisibility(View.GONE);
                        mRecyclerDetailStyle.setVisibility(View.GONE);
                        mRecyclerStyle.setVisibility(View.VISIBLE);
                        break;
                    case PATTERN:
                        mAnyShapeView.setImageSource(null);
                        mChoiceReturn.setVisibility(View.GONE);
                        mRecyclerDetailStyle.setVisibility(View.GONE);
                        mRecyclerStyle.setVisibility(View.VISIBLE);
                        break;
                    case MASK:
                        mAnyShapeView.setImageMask(0);
                        mChoiceReturn.setVisibility(View.GONE);
                        mRecyclerDetailStyle.setVisibility(View.GONE);
                        mRecyclerStyle.setVisibility(View.VISIBLE);
                        break;
                }
                break;
            case R.id.choice_done://点击每个样式的完成保存到数据
                mChoiceReturn.setVisibility(View.GONE);
                mRecyclerDetailStyle.setVisibility(View.GONE);
                mRecyclerStyle.setVisibility(View.VISIBLE);
                break;
        }
        mBtnFinish.setVisibility(View.VISIBLE);
    }


    @Override
    public void onItemClick(View currentView, int position) {
        if (mDesBeforeView != null) {
            mDesBeforeView.setSelected(false);
        }
        switch (clotheKey.get(mCurrentPosition)) {
            case COLOR:
                setColorBg(clothes[position]);
                break;
            case PATTERN:
                imageUrl = mPatternData.get(newList.get(mCurrentPosition).getTitle()).get(position).getFile();
                setPatternUrl(imageUrl);
                break;
            case MASK:
                mAnyShapeView.setImageMask(R.mipmap.gao);
                break;

        }
        prePosition = position;
        currentView.setSelected(true);
        mDesCurrentView = currentView;
        mDesBeforeView = currentView;
    }

    private void setPatternUrl(String imageUrl) {

    }

    private void setColorBg(int image) {
        mClothes.setImageResource(image);
    }

    @Override
    public void showSuccessData(DetailStyleBean detailStyleBean) {

    }

    @Override
    public void showSuccessData(List<StyleBean> newList, List<String> clotheKey, ArrayMap<String, List<DetailColorStyle>> mColorData, ArrayMap<String, List<DetailPatterStyle>> mPatternData, ArrayMap<String, List<DetailColorStyle>> mSignatureData) {
        this.newList = newList;
        this.mPatternData = mPatternData;
        this.mColorData = mColorData;
        this.clotheKey = clotheKey;
        this.mMaskData = mSignatureData;
        mRecyclerStyle.setVisibility(View.VISIBLE);
        mBaseDesignAdapter.setItemClickListener(this);
        mBaseDesignAdapter.setData(newList);
        mRecyclerStyle.setAdapter(mBaseDesignAdapter);
        mBaseDesignAdapter.notifyDataSetChanged();
    }


    @Override
    public void showBackData(String neck, String arm, String ornament) {

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

        if (mColorData.containsKey((newList.get(position).getTitle()))) {
            ColorStyleAdapter colorStyleAdapter = new ColorStyleAdapter(this);
            colorStyleAdapter.setData(mColorData.get(newList.get(position).getTitle()));
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


}
