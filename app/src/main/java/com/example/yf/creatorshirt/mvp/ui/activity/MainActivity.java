package com.example.yf.creatorshirt.mvp.ui.activity;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.ui.fragment.CommunityFragment;
import com.example.yf.creatorshirt.mvp.ui.fragment.CreateFragment;
import com.example.yf.creatorshirt.mvp.ui.fragment.HomeFragment;

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
    private CreateFragment mCreateFragment;
    private HomeFragment mHomeFragment;
    private String showFragment;
    private String hideFragment;

    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        mCommunityFragment = new CommunityFragment();
        mCreateFragment = new CreateFragment();
        mHomeFragment = new HomeFragment();
        showFragment = TYPE_COMMUNITY;
        hideFragment = TYPE_COMMUNITY;
        mTransaction = getSupportFragmentManager().beginTransaction();
        mTransaction.add(R.id.content, mCommunityFragment).show(mCommunityFragment).add(R.id.content, mCreateFragment).hide(mCreateFragment)
                .add(R.id.content, mHomeFragment).hide(mHomeFragment).commit();
        setSupportActionBar(mToolbar);
    }

    @OnClick({R.id.home_text, R.id.create_text, R.id.my_text})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_text:
                showFragment = TYPE_COMMUNITY;
                break;
            case R.id.create_text:
                showFragment = TYPE_CREATE;
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
            case TYPE_CREATE:
                return mCreateFragment;
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
