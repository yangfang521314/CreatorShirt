package com.example.yf.creatorshirt.mvp.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.model.bean.StyleBean;
import com.example.yf.creatorshirt.mvp.ui.adapter.StyleAdapter;
import com.example.yf.creatorshirt.utils.systembar.SystemUtilsBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class DetailDesignActivity extends BaseActivity {


    @BindView(R.id.choice)
    RecyclerView mRecyclerChoice;
    @BindView(R.id.choice_style)
    RecyclerView mRecyclerStyle;

    private int[] image = {R.mipmap.icon_edit_neck, R.mipmap.icon_select_longarm, R.mipmap.icon_edit_color, R.mipmap.icon_edit_pattern, R.mipmap.icon_edit_text};
    private String[] styleTitle = {"衣领", "衣袖", "颜色", "图案", "标签"};
    private List<StyleBean> list = new ArrayList<>();

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
        mRecyclerStyle.setVisibility(View.VISIBLE);
        mRecyclerStyle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        StyleAdapter adapter = new StyleAdapter(this);
        adapter.setData(list);
        mRecyclerStyle.setAdapter(adapter);
    }

    private void initData() {
        StyleBean styleBean;
        for (int i = 0; i < image.length; i++) {
            styleBean = new StyleBean();
            styleBean.setImageId(image[i]);
            styleBean.setTitle(styleTitle[i]);
            list.add(styleBean);
        }
    }


}
