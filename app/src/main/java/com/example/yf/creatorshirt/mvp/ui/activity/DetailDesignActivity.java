package com.example.yf.creatorshirt.mvp.ui.activity;

import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.model.bean.StyleBean;
import com.example.yf.creatorshirt.mvp.ui.adapter.DetailStyleAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.StyleAdapter;
import com.example.yf.creatorshirt.mvp.ui.view.ItemClickListener;
import com.example.yf.creatorshirt.utils.Constants;
import com.example.yf.creatorshirt.utils.systembar.SystemUtilsBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class DetailDesignActivity extends BaseActivity implements ItemClickListener.OnItemClickListener,
        ItemClickListener.OnClickListener {


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
    @BindView(R.id.container_background)
    RelativeLayout mContainer;
    @BindView(R.id.ll_choice_back)
    LinearLayout mChoiceOrBack;
    @BindView(R.id.btn_choice_finish)
    Button mBtnStart;
    @BindView(R.id.choice_back)
    ImageView mChoiceBack;
    @BindView(R.id.choice_done)
    ImageView mChoiceDone;


    //总的样式
    private View mBeforeView;
    private View mCurrentView;
    //每个具体的样式
    private View mDesBeforeView;
    private View mDesCurrentView;

    private int mCurrentPosition = 0;
    private List<StyleBean> list = new ArrayList<>();
    private ArrayMap<String, List<StyleBean>> detailData = new ArrayMap<>();

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
        initData();
        mToolbarTitle.setText(R.string.design_title_bar);
        SystemUtilsBar.with(this)
                .statusBarDarkFont(true, 0.2f)
                .init();
        mBarBack.setVisibility(View.VISIBLE);
        mRecyclerStyle.setVisibility(View.VISIBLE);
        mBtnStart.setVisibility(View.VISIBLE);
        mRecyclerStyle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        StyleAdapter adapter = new StyleAdapter(this);
        adapter.setItemClickListener(this);
        adapter.setData(list);
        mRecyclerStyle.setAdapter(adapter);
    }

    private void initData() {
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

    }

    @OnClick({R.id.btn_choice_finish, R.id.choice_done, R.id.choice_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_choice_finish:
                if (mCurrentView.isSelected()) {
                    clickItem(mCurrentPosition);//点击进入详情设计界面
                }
                break;
            case R.id.back:
                finish();
                break;
            case R.id.choice_back:
                //衣领样式，点击back，返回最初设置的样式
                if (mCurrentPosition == 0) {
                    mChoiceNeck.setVisibility(View.GONE);
                }
                mRecyclerStyle.setVisibility(View.VISIBLE);
                mRecyclerChoice.setVisibility(View.GONE);
                mBtnStart.setVisibility(View.VISIBLE);
                mChoiceOrBack.setVisibility(View.GONE);
                break;
            case R.id.choice_done:
                if (mDesCurrentView.isSelected()) {
                    mRecyclerStyle.setVisibility(View.VISIBLE);
                    mRecyclerChoice.setVisibility(View.GONE);
                    mBtnStart.setVisibility(View.VISIBLE);
                    mChoiceOrBack.setVisibility(View.GONE);
                }
                break;
        }
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
        Log.e("TAG", "DDDD" + mCurrentPosition);
        mBeforeView = view;

    }

    /**
     * detail a design style
     *
     * @param currentView
     * @param position    点击的位置
     */
    @Override
    public void onItemClick(View currentView, int position) {
        if (mDesBeforeView != null) {
            mDesBeforeView.setSelected(false);
        }
        switch (mCurrentPosition) {
            case 0:
                mChoiceNeck.setVisibility(View.VISIBLE);
                mChoiceNeck.setImageResource(Constants.shirt_neck[position]);
                break;
            case 1:
                mContainer.setBackgroundResource(Constants.shirt_container[position]);
                break;
        }
        currentView.setSelected(true);
        mDesCurrentView = currentView;
        mDesBeforeView = currentView;

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
            mBtnStart.setVisibility(View.GONE);
            mChoiceOrBack.setVisibility(View.VISIBLE);
            mRecyclerChoice.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            detailAdapter.setData(detailData.get(list.get(position).getTitle()));
            detailAdapter.setOnClickListener(this);
            mRecyclerChoice.setAdapter(detailAdapter);
            detailAdapter.notifyDataSetChanged();
        }
    }

}
