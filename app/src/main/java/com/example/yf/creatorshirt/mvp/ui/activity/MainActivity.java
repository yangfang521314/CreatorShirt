package com.example.yf.creatorshirt.mvp.ui.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.ui.fragment.CommunityFragment;
import com.example.yf.creatorshirt.mvp.ui.fragment.HomeFragment;
import com.example.yf.creatorshirt.utils.systembar.SystemUtilsBar;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    private static final String TYPE_COMMUNITY = "community";
    private static final String TYPE_CREATE = "create";
    private static final String TYPE_HOME = "home";
    @BindView(R.id.home_text)
    TextView mCommunityText;
    @BindView(R.id.create_text)
    TextView mCreateText;
    @BindView(R.id.my_text)
    TextView mMyText;
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
        SystemUtilsBar.with(this)
                .statusBarDarkFont(true, 0.2f)
                .init();
        mCommunityFragment = new CommunityFragment();
        mHomeFragment = new HomeFragment();
        showFragment = TYPE_COMMUNITY;
        hideFragment = TYPE_COMMUNITY;
        mTransaction = getSupportFragmentManager().beginTransaction();
        mTransaction.add(R.id.content, mCommunityFragment, "community").show(mCommunityFragment)
                .add(R.id.content, mHomeFragment, "home").hide(mHomeFragment).commit();
        mToolbar.setTitle(null);
        setSupportActionBar(mToolbar);
    }

    @OnClick({R.id.home_text, R.id.create_text, R.id.my_text})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_text:
                showFragment = TYPE_COMMUNITY;
                break;
            case R.id.create_text:
                startActivity(new Intent(this, DesignActivity.class));
                break;
            case R.id.my_text:
                showFragment = TYPE_HOME;
                break;
        }
        changeFragment(getShowFragment(showFragment), getShowFragment(hideFragment));
        hideFragment = showFragment;

    }

    private void changeFragment(Fragment showFragment, Fragment hideFragment) {
        if (showFragment.equals(hideFragment))
            return;
        getSupportFragmentManager().beginTransaction().show(showFragment).hide(hideFragment).commit();
    }


    private Fragment getShowFragment(String flag) {
        switch (flag) {
            case TYPE_COMMUNITY:
                return mCommunityFragment;
            case TYPE_HOME:
                return mHomeFragment;
        }
        return mCommunityFragment;
    }

    @Override
    protected int getView() {
        return R.layout.activity_main;
    }

}
