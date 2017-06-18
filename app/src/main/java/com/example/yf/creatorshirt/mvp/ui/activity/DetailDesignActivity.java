package com.example.yf.creatorshirt.mvp.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.model.bean.DetailStyleBean;
import com.example.yf.creatorshirt.mvp.model.bean.StyleBean;
import com.example.yf.creatorshirt.mvp.ui.adapter.StyleAdapter;
import com.example.yf.creatorshirt.mvp.ui.view.RecyclerItemClickListener;
import com.example.yf.creatorshirt.utils.Constants;
import com.example.yf.creatorshirt.utils.ToastUtil;
import com.example.yf.creatorshirt.utils.systembar.SystemUtilsBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;


public class DetailDesignActivity extends BaseActivity implements RecyclerItemClickListener.OnItemClickListener {


    @BindView(R.id.choice)
    RecyclerView mRecyclerChoice;
    @BindView(R.id.choice_style)
    RecyclerView mRecyclerStyle;
    @BindView(R.id.back)
    ImageView mActionBar;

    private List<StyleBean> list = new ArrayList<>();
    private List<DetailStyleBean> detailList = new ArrayList<>();

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
        SystemUtilsBar.with(this)
                .statusBarDarkFont(true, 0.2f)
                .init();
        mActionBar.setVisibility(View.VISIBLE);
        mRecyclerStyle.setVisibility(View.VISIBLE);
        mRecyclerStyle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        StyleAdapter adapter = new StyleAdapter(this);
        adapter.setItemClickListener(this);
        adapter.setData(list);
        mRecyclerStyle.setAdapter(adapter);
    }

    private void initData() {
        int[] image = Constants.image;
        String[] styleTitle = Constants.styleTitle;
        StyleBean styleBean;
        for (int i = 0; i < Constants.image.length; i++) {
            styleBean = new StyleBean();
            styleBean.setImageId(image[i]);
            styleBean.setTitle(styleTitle[i]);
            list.add(styleBean);
        }
        for (int i = 0; i < Constants.image.length; i++) {
        }

    }


    @Override
    public void onItemClick(View view, int position) {
        view.setPressed(true);
        mRecyclerStyle.setVisibility(View.GONE);
        clickItem();
    }

    /**
     * 点击跳转
     */
    private void clickItem() {

    }
}
