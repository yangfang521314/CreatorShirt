package com.example.yf.creatorshirt.mvp.ui.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.ui.fragment.CommunityFragment;
import com.example.yf.creatorshirt.mvp.ui.fragment.HomeFragment;

import butterknife.BindView;
import butterknife.OnClick;

@TargetApi(Build.VERSION_CODES.M)
public class MainActivity extends BaseActivity {
    private static final String TYPE_SQUARE = "square";
    private static final String TYPE_DESIGN = "design";
    private static final String TYPE_MINE = "mine";
    @BindView(R.id.square_text)
    TextView mSquareContainer;
    @BindView(R.id.design_text)
    TextView mDesignContainer;
    @BindView(R.id.mine_text)
    TextView mMineContainer;
    @BindView(R.id.tool_bar)
    Toolbar mToolbar;

    private FragmentTransaction mTransaction;

    private CommunityFragment mCommunityFragment;
    private HomeFragment mHomeFragment;
    private String showFragment;
    private String hideFragment;

    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {
        mCommunityFragment = new CommunityFragment();
        mHomeFragment = new HomeFragment();
        showFragment = TYPE_SQUARE;
        hideFragment = TYPE_SQUARE;
        mTransaction = getSupportFragmentManager().beginTransaction();
        mTransaction.add(R.id.content, mCommunityFragment, "community").show(mCommunityFragment)
                .add(R.id.content, mHomeFragment, "home").hide(mHomeFragment).commit();
        mToolbar.setTitle(null);
    }

    @OnClick({R.id.mine_text, R.id.design_text, R.id.square_text})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.square_text:
                showFragment = TYPE_SQUARE;
                choiceTabState(TYPE_SQUARE);
                break;
            case R.id.design_text:
                startActivity(new Intent(this, DesignActivity.class));
                choiceTabState(TYPE_DESIGN);
                break;
            case R.id.mine_text:
                showFragment = TYPE_MINE;
                choiceTabState(TYPE_MINE);
                break;
        }
        changeFragment(getShowFragment(showFragment), getShowFragment(hideFragment));
        hideFragment = showFragment;

    }

    private void choiceTabState(String position) {
        clearSelection();
        switch (position) {
            case TYPE_SQUARE:
                Drawable drawable01 = getResources().getDrawable(R.mipmap.icon_square_hover, null);
                mSquareContainer.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable01, null, null);
                mSquareContainer.setTextColor(getResources().getColor(R.color.sand, null));
                break;
            case TYPE_DESIGN:
                Drawable drawable02 = getResources().getDrawable(R.mipmap.icon_design_hover, null);
                mDesignContainer.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable02, null, null);
                mDesignContainer.setTextColor(getResources().getColor(R.color.sand, null));
                break;
            case TYPE_MINE:
                Drawable drawable03 = getResources().getDrawable(R.mipmap.icon_mine_hover, null);
                mMineContainer.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable03, null, null);
                mMineContainer.setTextColor(getResources().getColor(R.color.sand, null));
                break;
        }
    }

    //清除上次的状态
    private void clearSelection() {
        Drawable drawable01 = getResources().getDrawable(R.mipmap.icon_square_normal, null);
        mSquareContainer.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable01, null, null);
        mSquareContainer.setTextColor(getResources().getColor(R.color.manatee_1, null));

        Drawable drawable02 = getResources().getDrawable(R.mipmap.icon_design_normal, null);
        mDesignContainer.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable02, null, null);
        mDesignContainer.setTextColor(getResources().getColor(R.color.manatee_1, null));

        Drawable drawable03 = getResources().getDrawable(R.mipmap.icon_mine_normal, null);
        mMineContainer.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable03, null, null);
        mMineContainer.setTextColor(getResources().getColor(R.color.manatee_1, null));

    }

    private void changeFragment(Fragment showFragment, Fragment hideFragment) {
        if (showFragment.equals(hideFragment))
            return;
        getSupportFragmentManager().beginTransaction().show(showFragment).hide(hideFragment).commit();
    }


    private Fragment getShowFragment(String flag) {
        switch (flag) {
            case TYPE_SQUARE:
                return mCommunityFragment;
            case TYPE_MINE:
                return mHomeFragment;
        }
        return mCommunityFragment;
    }

    @Override
    protected int getView() {
        return R.layout.activity_main;
    }

}
