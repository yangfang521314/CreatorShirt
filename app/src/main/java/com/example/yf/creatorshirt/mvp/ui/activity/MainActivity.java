package com.example.yf.creatorshirt.mvp.ui.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;
import com.example.yf.creatorshirt.mvp.ui.fragment.MineFragment;
import com.example.yf.creatorshirt.mvp.ui.fragment.SquareFragment;
import com.example.yf.creatorshirt.utils.PackageUtil;
import com.example.yf.creatorshirt.utils.PermissionChecker;
import com.example.yf.creatorshirt.utils.SharedPreferencesUtil;
import com.example.yf.creatorshirt.utils.ToastUtil;

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

    private FragmentTransaction mTransaction;

    private SquareFragment mSquareFragment;
    private MineFragment mMineFragment;
    private String showFragment;
    private String hideFragment;
    private long mExitTime = 0;
    private static final int REQUEST_CODE = 9;

    private PermissionChecker mPermissionsChecker; // 权限检测器
    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };


    @Override
    protected void inject() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mPermissionsChecker = new PermissionChecker(this);
        mSquareFragment = new SquareFragment();
        mMineFragment = new MineFragment();
        showFragment = TYPE_SQUARE;
        hideFragment = TYPE_SQUARE;
        mTransaction = getSupportFragmentManager().beginTransaction();
        mTransaction.add(R.id.content, mSquareFragment, "square").show(mSquareFragment)
                .add(R.id.content, mMineFragment, "mine").hide(mMineFragment).commit();
        choiceTabState(TYPE_SQUARE);
        String key = PackageUtil.getSignature(App.getInstance());
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
                showFragment = TYPE_SQUARE;
                break;
            case R.id.mine_text:
                //没有Token进行登录
                if (TextUtils.isEmpty(SharedPreferencesUtil.getUserToken())) {
                    startCommonActivity(this, null, LoginActivity.class);
                    choiceTabState(TYPE_MINE);
                    mAppBar.setVisibility(View.GONE);
                    changeFragment(getShowFragment(TYPE_MINE), getShowFragment(TYPE_SQUARE));
                    showFragment = TYPE_MINE;
                } else {
                    showFragment = TYPE_MINE;
                    choiceTabState(TYPE_MINE);
                    mAppBar.setVisibility(View.GONE);
                }
                break;
        }
        changeFragment(getShowFragment(showFragment), getShowFragment(hideFragment));
        hideFragment = showFragment;

    }

    private void choiceTabState(String position) {
        clearSelection();
        switch (position) {
            case TYPE_SQUARE:
                Drawable drawable01 = ContextCompat.getDrawable(this, R.mipmap.icon_square_hover);
                mSquareContainer.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable01, null, null);
                mSquareContainer.setTextColor(ContextCompat.getColor(this, R.color.manatee_1));
                break;
            case TYPE_DESIGN:
                Drawable drawable02 = ContextCompat.getDrawable(this, R.mipmap.icon_design_hover);
                mDesignContainer.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable02, null, null);
                mDesignContainer.setTextColor(ContextCompat.getColor(this, R.color.manatee_1));
                break;
            case TYPE_MINE:
                Drawable drawable03 = ContextCompat.getDrawable(this, R.mipmap.icon_mine_hover);
                mMineContainer.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable03, null, null);
                mMineContainer.setTextColor(ContextCompat.getColor(this, R.color.manatee_1));
                break;
        }
    }

    //清除上次的状态
    private void clearSelection() {
        Drawable drawable01 = ContextCompat.getDrawable(this, R.mipmap.icon_square_normal);
        mSquareContainer.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable01, null, null);
        mSquareContainer.setTextColor(ContextCompat.getColor(this, R.color.manatee_1));

        Drawable drawable02 = ContextCompat.getDrawable(this, R.mipmap.icon_design_normal);
        mDesignContainer.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable02, null, null);
        mDesignContainer.setTextColor(ContextCompat.getColor(this, R.color.manatee_1));

        Drawable drawable03 = ContextCompat.getDrawable(this, R.mipmap.icon_mine_normal);
        mMineContainer.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable03, null, null);
        mMineContainer.setTextColor(ContextCompat.getColor(this, R.color.manatee_1));

    }

    private void changeFragment(Fragment showFragment, Fragment hideFragment) {
        if (showFragment.equals(hideFragment))
            return;
        getSupportFragmentManager().beginTransaction().show(showFragment).hide(hideFragment).commit();
    }

    private Fragment getShowFragment(String flag) {
        switch (flag) {
            case TYPE_SQUARE:
                return mSquareFragment;
            case TYPE_MINE:
                return mMineFragment;
        }
        return mSquareFragment;
    }

    @Override
    public void onBackPressed() {
        if((System.currentTimeMillis() -mExitTime)>2000){
            ToastUtil.showToast(this,getString(R.string.exit_app),Toast.LENGTH_LONG);
            mExitTime = System.currentTimeMillis();
        }else {
            System.exit(0);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        //设置到启动页面
        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            String notice = "存储空间权限用于下载和软件更新,关闭权限将关闭应用，是否放弃权限允许？";
            PermissionActivity.startActivityForResult(this, notice, REQUEST_CODE, PERMISSIONS);
        } else {
            startOtherActivity();
        }
    }

    private void startOtherActivity() {
        if (SharedPreferencesUtil.getAppIsFirstLaunched()) {
//            startActivity(new Intent(this, MainActivity.class));
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时,缺少主要权限,
        if (requestCode == REQUEST_CODE && resultCode == PermissionActivity.PERMISSIONS_DENIED) {
            //checkPermission=true;
            finish();
        } else {
            startOtherActivity();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
