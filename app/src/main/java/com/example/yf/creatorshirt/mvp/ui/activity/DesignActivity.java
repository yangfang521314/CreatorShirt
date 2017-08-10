package com.example.yf.creatorshirt.mvp.ui.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.model.design.DesignBaseBean;
import com.example.yf.creatorshirt.mvp.presenter.DesignPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.DesignBaseContract;
import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 选择衣服总体样式页面
 */
public class DesignActivity extends BaseActivity<DesignPresenter> implements DesignBaseContract.DesignBaseView {
    @BindView(R.id.choice_t_shirt)
    RelativeLayout mChoiceTShirt;
    @BindView(R.id.choice_shirts)
    RelativeLayout mChoiceShirts;
    @BindView(R.id.choice_man)
    RelativeLayout mChoiceMan;
    @BindView(R.id.choice_woman)
    RelativeLayout mChoiceWoman;
    @BindView(R.id.btn_start)
    Button mStartDesign;


    @BindView(R.id.tool_bar)
    Toolbar mToolbar;

    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {
        mAppBarTitle.setText(R.string.design);
        mAppBarBack.setVisibility(View.VISIBLE);
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter.getBaseData();
    }

    @OnClick({R.id.btn_start, R.id.choice_man, R.id.choice_woman, R.id.back, R.id.choice_shirts, R.id.choice_t_shirt})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                if ((mChoiceWoman.isSelected() || mChoiceMan.isSelected()) && ((mChoiceTShirt.isSelected()) || mChoiceShirts.isSelected()))
                    startActivity(new Intent(this, DetailDesignActivity.class));
                break;
            case R.id.back:
                finish();
                break;
            case R.id.choice_man:
                mChoiceMan.setSelected(true);
                mChoiceWoman.setSelected(false);
                break;
            case R.id.choice_woman:
                mChoiceWoman.setSelected(true);
                mChoiceMan.setSelected(false);
                break;
            case R.id.choice_shirts:
                mChoiceShirts.setSelected(true);
                mChoiceTShirt.setSelected(false);
                break;
            case R.id.choice_t_shirt:
                mChoiceShirts.setSelected(false);
                mChoiceTShirt.setSelected(true);
                break;
        }
    }

    @Override
    protected int getView() {
        return R.layout.activity_design;
    }

    @Override
    public void showBaseDesignSuccess(Map<String, List<DesignBaseBean>> designBean) {

    }
}
