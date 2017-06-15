package com.example.yf.creatorshirt.mvp.ui.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.utils.systembar.SystemUtilsBar;

import butterknife.BindView;
import butterknife.OnClick;

public class DesignActivity extends BaseActivity {
    @BindView(R.id.choice_t_shirt)
    RelativeLayout mChoiceTShirt;
    @BindView(R.id.choice_shirts)
    RelativeLayout mChoiceShirts;
    @BindView(R.id.choice_man)
    RelativeLayout mChoiceMan;
    @BindView(R.id.choice_woman)
    RelativeLayout mChoiceWoman;
    @BindView(R.id.app_bar_title)
    TextView mToolbarTitle;
    @BindView(R.id.back)
    ImageView mClickBack;
    @BindView(R.id.btn_start)
    Button mStartDesign;


    @BindView(R.id.tool_bar)
    Toolbar mToolbar;

    @Override
    protected void inject() {

    }

    @Override
    protected void initView() {
        SystemUtilsBar.with(this)
                .statusBarDarkFont(true, 0.2f)
                .init();
        mChoiceTShirt.setPressed(true);
        mChoiceMan.setPressed(true);
        mClickBack.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.btn_start})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                startActivity(new Intent(this, DetailDesignActivity.class));
                break;
        }
    }

    @Override
    protected int getView() {
        return R.layout.activity_design;
    }
}
