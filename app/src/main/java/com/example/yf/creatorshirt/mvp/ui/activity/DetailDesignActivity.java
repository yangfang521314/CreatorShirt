package com.example.yf.creatorshirt.mvp.ui.activity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.mvp.listener.ItemClickListener;
import com.example.yf.creatorshirt.mvp.model.StyleBean;
import com.example.yf.creatorshirt.mvp.model.detaildesign.DetailCommonData;
import com.example.yf.creatorshirt.mvp.model.detaildesign.DetailOtherStyle;
import com.example.yf.creatorshirt.mvp.model.detaildesign.DetailPatterStyle;
import com.example.yf.creatorshirt.mvp.model.detaildesign.DetailColorStyle;
import com.example.yf.creatorshirt.mvp.model.detaildesign.DetailStyleBean;
import com.example.yf.creatorshirt.mvp.presenter.DetailDesignPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.DetailDesignContract;
import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;
import com.example.yf.creatorshirt.mvp.ui.adapter.design.ColorStyleAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.design.DetailStyleAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.design.PatternStyleAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.design.BaseStyleAdapter;
import com.example.yf.creatorshirt.utils.Constants;
import com.example.yf.creatorshirt.utils.DisplayUtil;
import com.example.yf.creatorshirt.utils.FileUtils;
import com.example.yf.creatorshirt.utils.LogUtil;
import com.example.yf.creatorshirt.widget.stickerview.SignatureDialog;
import com.example.yf.creatorshirt.widget.stickerview.StickerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.media.CamcorderProfile.get;
import static android.os.Build.VERSION_CODES.N;
import static com.example.yf.creatorshirt.utils.Constants.ImageUrl;

/**
 * 衣服具体设计样式界面
 */
public class DetailDesignActivity extends BaseActivity<DetailDesignPresenter> implements ItemClickListener.OnItemClickListener,
        ItemClickListener.OnClickListener, DetailDesignContract.DetailDesignView {

    private static final String TAG = DetailDesignActivity.class.getSimpleName();
    public static String CLOTHES_STYLE;
    public static final String NECK = "neck";
    public static final String ARM = "arm";
    public static final String COLOR = "color";
    public static final String PATTERN = "pattern";
    public static final String SIGNATURE = "标签";
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
    LinearLayout mChoiceOrBack;
    @BindView(R.id.btn_choice_finish)
    Button mBtnFinish;
    @BindView(R.id.choice_back)
    ImageView mChoiceBack;
    @BindView(R.id.choice_done)
    ImageView mChoiceDone;
    @BindView(R.id.choice_ornament)
    ImageView mChoiceOrnament;
    @BindView(R.id.rl_clothes_root)
    RelativeLayout mContainerBackground;
    @BindView(R.id.clothes_pattern_bounds)
    RelativeLayout mPatternBounds;
    @BindView(R.id.clothes_signature)
    TextView mClothesSignature;
    @BindView(R.id.clothes_back)
    TextView mClothesBack;
    @BindView(R.id.clothes_front)
    TextView mClothesFront;


    //总的样式
    private View mBeforeView;
    private View mCurrentView;
    //每个具体的样式
    private View mDesBeforeView;
    private View mDesCurrentView;
    private int mCurrentPosition = 0;

    //总样式的集合
    private List<StyleBean> list = new ArrayList<>();
    //总样式和每一个具体的样式列表形成ArrayMap;
    private ArrayMap<String, List<StyleBean>> detailData = new ArrayMap<>();
    private ArrayMap<String, List<DetailOtherStyle>> NewDetailData = new ArrayMap<>();
    private ArrayMap<String, List<DetailPatterStyle>> mPatternData = new ArrayMap<>();
    private ArrayMap<String, List<DetailColorStyle>> mColorData = new ArrayMap<>();

    String[] styleName;
    List<StyleBean> newList;

    //处于编辑的贴纸
    private StickerView mStickerView;

    //是否要编辑标签
    private boolean isEditSign = false;

    // 存储贴纸列表
    private ArrayList<View> mViews = new ArrayList<>();

    //标签名
    private String mSignatureText;

    //定制完成后图片的路径
    private String imagePath;

    private String gender;//
    private String type;
    private String mBackgroundUrl;

    private DetailCommonData mDetailStyleFrontData;//正面数据
    private DetailCommonData mDetailStyleBackData;//背面数据
    private BaseStyleAdapter mBaseDesignAdapter;

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
        DisplayUtil.calculateBGWidth(App.getInstance(), mContainerBackground);
        //默认显示正面
        String imageUrl = Constants.ImageUrl +gender+type+"A"+".png";
        Glide.with(this).load(imageUrl).into(mClothes);
        mAppBarTitle.setText(R.string.design);
        mAppBarBack.setVisibility(View.VISIBLE);
        mRecyclerStyle.setVisibility(View.VISIBLE);
        mBtnFinish.setVisibility(View.VISIBLE);
        mRecyclerStyle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mBaseDesignAdapter = new BaseStyleAdapter(this);
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
        mDetailStyleBackData = detailStyleBean.getData().getB();
        mDetailStyleFrontData = detailStyleBean.getData().getA();
    }

    private void getNameDeign(DetailCommonData mData) {
        styleName = new String[]{mData.getNeck().getName(), mData.getArm().getName(),
                mData.getOrnament().getName(), mData.getColor().getName(), mData.getPattern().getName()};
        int[] image = Constants.styleImage;
        newList = new ArrayList<>();
        for (int i = 0; i < styleName.length; i++) {
            StyleBean styleBean = new StyleBean();
            styleBean.setTitle(styleName[i]);
            styleBean.setImageId(image[i]);
            newList.add(styleBean);
        }

        addNewData(styleName[0], mData.getNeck().getFileList());
        addNewData(styleName[1], mData.getArm().getFileList());
        addNewData(styleName[2], mData.getOrnament().getFileList());
        addPatternData(styleName[4], mData.getPattern().getFileList());
        addColorData(styleName[3], mData.getColor().getFileList());

        mBaseDesignAdapter.setItemClickListener(this);
        mBaseDesignAdapter.setData(newList);
        mRecyclerStyle.setAdapter(mBaseDesignAdapter);

    }

    private void addColorData(String s, List<DetailColorStyle> fileList) {
        mColorData.put(s, fileList);
    }

    private void addPatternData(String title, List<DetailPatterStyle> fileList) {
        mPatternData.put(title, fileList);
    }

    private void addNewData(String title, List<DetailOtherStyle> fileList) {
        if (!NewDetailData.containsKey(title) && fileList != null) {
            NewDetailData.put(title, fileList);
            Log.e(TAG, "news detail" + NewDetailData.size());
            Log.e(TAG, "news detail ooo " + NewDetailData.get(styleName[0]));
        }
    }


    @OnClick({R.id.btn_choice_finish, R.id.choice_done, R.id.choice_back,R.id.clothes_front,R.id.clothes_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_choice_finish:
                generateBitmap();//生成衣服的图片
                Bundle bundle = new Bundle();
                bundle.putString("imageUrl", imagePath);
                startCommonActivity(this, bundle, ChoiceSizeActivity.class);
                break;
            case R.id.back:
                finish();
                break;
            case R.id.choice_back:
                filter(mCurrentPosition);
                switch (CLOTHES_STYLE) {
                    case ARM:
                    case NECK:
                        //衣领样式，点击back，返回最初设置的样式
                        mChoiceNeck.setVisibility(View.GONE);
                        break;
                    case COLOR:
                        //不选择默认是白色
                        mClothes.setBackgroundResource(R.color.white);
                        break;
                    case ORNAMENT:
                        mChoiceOrnament.setVisibility(View.GONE);
                        break;
                    case PATTERN:
                        mViews.remove(mStickerView);
                        mContainerBackground.removeView(mStickerView);
                        break;
//                    case SIGNATURE:
//                        mClothesSignature.setVisibility(View.GONE);
//                        break;
                }
                mPatternBounds.setVisibility(View.GONE);
                mRecyclerStyle.setVisibility(View.VISIBLE);
                mRecyclerDetailStyle.setVisibility(View.GONE);
                mBtnFinish.setVisibility(View.VISIBLE);
                mChoiceOrBack.setVisibility(View.GONE);
                break;
//            case R.id.choice_done:
//                filter(mCurrentPosition);
//                if (mDesCurrentView != null && mDesCurrentView.isSelected()) {
//                    mRecyclerStyle.setVisibility(View.VISIBLE);
//                    mRecyclerDetailStyle.setVisibility(View.GONE);
//                    mBtnFinish.setVisibility(View.VISIBLE);
//                    mChoiceOrBack.setVisibility(View.GONE);
//                }
//                if (mStickerView != null) {
//                    mStickerView.setInEdit(false);
//                    mPatternBounds.setVisibility(View.GONE);
//                    // TODO: 22/06/2017 完成后禁止StickerView的点击事件
//                    mStickerView.setOnTouchListener(new View.OnTouchListener() {
//                        @Override
//                        public boolean onTouch(View v, MotionEvent event) {
//                            return true;
//                        }
//                    });
//                }
//                if (CLOTHES_STYLE.equals(SIGNATURE) && isEditSign) {
//                    setSignatureText();//签名处理
//                }
//                break;
            case R.id.clothes_front:
                getNameDeign(mDetailStyleFrontData);
                String imageUrl = Constants.ImageUrl +gender+type+"A"+".png";
                Glide.with(this).load(imageUrl).into(mClothes);
                break;
            case R.id.clothes_back:
                getNameDeign(mDetailStyleBackData);
                String imageBackUrl = Constants.ImageUrl +gender+type+"B"+".png";
                Glide.with(this).load(imageBackUrl).into(mClothes);
                break;

        }
    }

    private void generateBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(mContainerBackground.getWidth(),
                mContainerBackground.getHeight()
                , Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        mContainerBackground.draw(canvas);
        imagePath = FileUtils.saveBitmap(bitmap, this);
        Log.e("TAG", "DDDD" + imagePath);
    }

    /**
     * 签名
     */
    private void setSignatureText() {
        final SignatureDialog dialog = new SignatureDialog(this);
        dialog.show();
        Window win = dialog.getWindow();
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        win.setAttributes(lp);
        dialog.setCompleteCallBack(new SignatureDialog.CompleteCallBack() {
            @Override
            public void onClickChoiceOrBack(View view, String s) {
                switch (view.getId()) {
                    case R.id.choice_done:
                        mSignatureText = s;
                        mClothesSignature.setVisibility(View.VISIBLE);
                        mClothesSignature.setText(mSignatureText);
                        dialog.dismiss();
                        break;
                    case R.id.choice_back:
                        mSignatureText = "";
                        dialog.dismiss();
                        break;
                }
            }
        });
    }


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
        String imageUrl = null;
        filter(mCurrentPosition);
        switch (CLOTHES_STYLE) {
            case NECK:
                judge(mCurrentPosition, position);
                imageUrl = Constants.ImageUrl +
                        NewDetailData.get(newList.get(mCurrentPosition).getTitle()).get(position).getFile();
                mChoiceNeck.setVisibility(View.VISIBLE);
                Glide.with(this).load(imageUrl).into(mChoiceNeck);
                break;
            case ARM:
                judge(mCurrentPosition, position);
                imageUrl = Constants.ImageUrl +
                        NewDetailData.get(newList.get(mCurrentPosition).getTitle()).get(position).getFile();
                mChoiceNeck.setVisibility(View.VISIBLE);
                Glide.with(this).load(imageUrl).into(mChoiceNeck);
                break;
            case ORNAMENT:
                judge(mCurrentPosition, position);
                imageUrl = Constants.ImageUrl +
                        NewDetailData.get(newList.get(mCurrentPosition).getTitle()).get(position).getFile();
                mChoiceNeck.setVisibility(View.VISIBLE);
                Glide.with(this).load(imageUrl).into(mChoiceNeck);
                mChoiceOrnament.setVisibility(View.VISIBLE);
                break;
            case COLOR:
                String color ="#"+mColorData.get(newList.get(mCurrentPosition).getTitle()).get(position).getValue();
                Log.e("Tag","colo"+color);
                int colorInt = Color.parseColor(color);
                mClothes.setBackgroundColor(colorInt);
                break;
            case PATTERN:
                mStickerView = new StickerView(this);
                mPatternBounds.setVisibility(View.VISIBLE);

                addStickerView(Constants.clothes_pattern[position]);
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

    private void judge(int currentPosition, int position) {
        if (NewDetailData.get(newList.get(currentPosition).getTitle()).get(position).getFile() == null) {
            Log.e(TAG, "image url");
            return;
        }
    }

    /**
     * 添加贴纸效果
     *
     * @param imageId
     */
    private void addStickerView(int imageId) {
        mStickerView.setImageResource(imageId);
        mStickerView.setOperationListener(new StickerView.OperationListener() {
            @Override
            public void onDeleteClick() {
                mViews.remove(mStickerView);
                mContainerBackground.removeView(mStickerView);
            }

            @Override
            public void onEdit(StickerView stickerView) {
                mStickerView.setInEdit(false);
                mStickerView.setInEdit(true);
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
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        mContainerBackground.addView(mStickerView, lp);
        mViews.add(mStickerView);
        setStickerViewEdit(mStickerView);
    }

    /**
     * 设置当前处于编辑模式的贴纸
     *
     * @param mStickerView
     */
    private void setStickerViewEdit(StickerView mStickerView) {
        if (mStickerView != null) {
            mStickerView.setInEdit(false);
        }
        mStickerView.setInEdit(true);

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
        mChoiceOrBack.setVisibility(View.VISIBLE);
    }

    /**
     * 假数据将相应的位置过滤成对应的样式名
     *
     * @param mCurrentPosition
     */
    private void filter(int mCurrentPosition) {
        switch (mCurrentPosition) {
            case 0:
                CLOTHES_STYLE = NECK;
                break;
            case 1:
                CLOTHES_STYLE = ARM;
                break;
            case 2:
                CLOTHES_STYLE = ORNAMENT;
                break;
            case 3:
                CLOTHES_STYLE = COLOR;
                break;
            case 4:
                CLOTHES_STYLE = PATTERN;
                break;
            default:
                CLOTHES_STYLE = NECK;
        }
    }

}
