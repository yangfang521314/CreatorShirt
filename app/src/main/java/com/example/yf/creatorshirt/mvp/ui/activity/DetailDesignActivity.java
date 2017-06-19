package com.example.yf.creatorshirt.mvp.ui.activity;

import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
     * 点击style
     *
     * @param position
     */
    @Override
    public void onClick(int position) {
        clickItem(position);
        mCurrentPosition = position;
        Log.e("TAG", "DDDD" + mCurrentPosition);

    }

    /**
     * 具体design style
     *
     * @param view
     * @param position 点击的位置
     */
    @Override
    public void onItemClick(View view, int position) {
        switch (mCurrentPosition) {
            case 0:
                mChoiceNeck.setVisibility(View.VISIBLE);
                mChoiceNeck.setImageResource(Constants.shirt_neck[position]);
                break;
            case 1:
                mContainer.setBackgroundResource(Constants.shirt_container[position]);
                break;
        }
    }

    /**
     * 点击跳转
     *
     * @param position
     */
    private void clickItem(int position) {
        DetailStyleAdapter detailAdapter = new DetailStyleAdapter(this);
        if (detailData.containsKey(list.get(position).getTitle())) {
            mRecyclerStyle.setVisibility(View.GONE);
            mRecyclerChoice.setVisibility(View.VISIBLE);
            mRecyclerChoice.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            detailAdapter.setData(detailData.get(list.get(position).getTitle()));
            detailAdapter.setOnClickListener(this);
            mRecyclerChoice.setAdapter(detailAdapter);
            detailAdapter.notifyDataSetChanged();
        }
    }


}
