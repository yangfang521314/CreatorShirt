package com.example.yf.creatorshirt.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.model.bean.StyleBean;
import com.example.yf.creatorshirt.mvp.ui.adapter.DetailStyleAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.StyleAdapter;
import com.example.yf.creatorshirt.mvp.listener.ItemClickListener;
import com.example.yf.creatorshirt.utils.Constants;
import com.example.yf.creatorshirt.utils.FileUtils;
import com.example.yf.creatorshirt.utils.LogUtil;
import com.example.yf.creatorshirt.utils.systembar.SystemUtilsBar;
import com.example.yf.creatorshirt.widget.stickerview.SignatureDialog;
import com.example.yf.creatorshirt.widget.stickerview.StickerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class DetailDesignActivity extends BaseActivity implements ItemClickListener.OnItemClickListener,
        ItemClickListener.OnClickListener {

    private static final String TAG = DetailDesignActivity.class.getSimpleName();
    public static String CLOTHES_STYLE;
    public static final String NECK = "衣领";
    public static final String ARM = "衣袖";
    public static final String COLOR = "颜色";
    public static final String PATTERN = "图案";
    public static final String SIGNATURE = "标签";
    public static final String ORNAMENT = "配饰";

    @BindView(R.id.choice)
    RecyclerView mRecyclerChoice;//具体设计的recyclerView
    @BindView(R.id.choice_style)
    RecyclerView mRecyclerStyle;//style的recyclerView
    @BindView(R.id.back)
    ImageView mBarBack;
    @BindView(R.id.app_bar_title)
    TextView mToolbarTitle;
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
        mToolbarTitle.setText(R.string.design_title_bar);
        SystemUtilsBar.with(this)
                .statusBarDarkFont(true, 0.2f)
                .init();
        mBarBack.setVisibility(View.VISIBLE);
        mRecyclerStyle.setVisibility(View.VISIBLE);
        mBtnFinish.setVisibility(View.VISIBLE);
        mRecyclerStyle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        StyleAdapter adapter = new StyleAdapter(this);
        adapter.setItemClickListener(this);
        adapter.setData(list);
        mRecyclerStyle.setAdapter(adapter);
    }

    @Override
    public void initData() {
        int[] image = Constants.styleImage;
        String[] styleTitle = Constants.styleTitle;
        StyleBean styleBean;
        for (int i = 0; i < image.length; i++) {
            styleBean = new StyleBean();
            styleBean.setImageId(image[i]);
            styleBean.setTitle(styleTitle[i]);
            list.add(styleBean);
        }
        addMap(styleTitle[0], Constants.neck, Constants.select_neck_title);
        addMap(styleTitle[1], Constants.longarm, Constants.select_neck_arm);
        addMap(styleTitle[2], Constants.clothes_color, Constants.color_name);
        addMap(styleTitle[3], Constants.select_ornament, Constants.ornament_name);
        addMap(styleTitle[4], Constants.clothes_pattern, Constants.pattern_title);
        addMap(styleTitle[5], Constants.clothes_signature, Constants.signature_name);
    }

    @OnClick({R.id.btn_choice_finish, R.id.choice_done, R.id.choice_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_choice_finish:
                generateBitmap();//生成衣服的图片
                Intent intent = new Intent();
                intent.putExtra("imageUrl", imagePath);
                intent.setClass(this, ChoiceSizeActivity.class);
                startActivity(intent);
                break;
            case R.id.back:
                finish();
                break;
            case R.id.choice_back:
                filter(mCurrentPosition);
                switch (CLOTHES_STYLE) {
                    case NECK:
                        //衣领样式，点击back，返回最初设置的样式
                        mChoiceNeck.setVisibility(View.GONE);
                        break;
                    case COLOR:
                        //不选择默认是白色
                        mContainerBackground.setBackgroundResource(R.color.white);
                        break;
                    case ORNAMENT:
                        mChoiceOrnament.setVisibility(View.GONE);
                        break;
                    case PATTERN:
                        mViews.remove(mStickerView);
                        mContainerBackground.removeView(mStickerView);
                        break;
                    case SIGNATURE:
                        mClothesSignature.setVisibility(View.GONE);
                        break;
                }
                mPatternBounds.setVisibility(View.GONE);
                mRecyclerStyle.setVisibility(View.VISIBLE);
                mRecyclerChoice.setVisibility(View.GONE);
                mBtnFinish.setVisibility(View.VISIBLE);
                mChoiceOrBack.setVisibility(View.GONE);
                break;
            case R.id.choice_done:
                filter(mCurrentPosition);
                if (mDesCurrentView != null && mDesCurrentView.isSelected()) {
                    mRecyclerStyle.setVisibility(View.VISIBLE);
                    mRecyclerChoice.setVisibility(View.GONE);
                    mBtnFinish.setVisibility(View.VISIBLE);
                    mChoiceOrBack.setVisibility(View.GONE);
                }
                if (mStickerView != null) {
                    mStickerView.setInEdit(false);
                    mPatternBounds.setVisibility(View.GONE);
                    // TODO: 22/06/2017 完成后禁止StickerView的点击事件
                    mStickerView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return true;
                        }
                    });
                }
                if (CLOTHES_STYLE.equals(SIGNATURE) && isEditSign) {
                    setSignatureText();//签名处理
                }
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
     * 每个style的具体设计数据
     *
     * @param key
     * @param images
     * @param designTitle
     */
    private void addMap(String key, int[] images, String[] designTitle) {

        if (!detailData.containsKey(key)) {
            detailData.put(key, new ArrayList<StyleBean>());
        }
        for (int i = 0; i < images.length; i++) {
            StyleBean bean = new StyleBean();
            bean.setTitle(designTitle[i]);
            bean.setImageId(images[i]);
            detailData.get(key).add(bean);
        }

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
        filter(mCurrentPosition);
        switch (CLOTHES_STYLE) {
            case NECK:
                mChoiceNeck.setVisibility(View.VISIBLE);
                mChoiceNeck.setImageResource(Constants.shirt_neck[position]);
                break;
            case ARM:
                mClothes.setImageResource(Constants.shirt_container[position]);
                break;
            case COLOR:
                mContainerBackground.setBackgroundResource(Constants.choice_color[position]);
                break;
            case ORNAMENT:
                mChoiceOrnament.setVisibility(View.VISIBLE);
                break;
            case PATTERN:
                mStickerView = new StickerView(this);
                mPatternBounds.setVisibility(View.VISIBLE);
                addStickerView(Constants.clothes_pattern[position]);
                break;
            case SIGNATURE://签名处理
                if (position == 0) {
                    mClothesSignature.setVisibility(View.GONE);
                    isEditSign = false;
                } else if (position == 1) {
                    mClothesSignature.setVisibility(View.VISIBLE);
                    isEditSign = true;
                }
                break;
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
        DetailStyleAdapter detailAdapter = new DetailStyleAdapter(this);
        if (detailData.containsKey(list.get(position).getTitle())) {
            mRecyclerStyle.setVisibility(View.GONE);
            mRecyclerChoice.setVisibility(View.VISIBLE);
            mBtnFinish.setVisibility(View.GONE);
            mChoiceOrBack.setVisibility(View.VISIBLE);
            mRecyclerChoice.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            detailAdapter.setData(detailData.get(list.get(position).getTitle()));
            detailAdapter.setOnClickListener(this);
            mRecyclerChoice.setAdapter(detailAdapter);
            detailAdapter.notifyDataSetChanged();
        }
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
                CLOTHES_STYLE = COLOR;
                break;
            case 3:
                CLOTHES_STYLE = ORNAMENT;
                break;
            case 4:
                CLOTHES_STYLE = PATTERN;
                break;
            case 5:
                CLOTHES_STYLE = SIGNATURE;
                break;
            default:
                CLOTHES_STYLE = NECK;
        }
    }

}
