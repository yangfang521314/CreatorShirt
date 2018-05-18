package com.example.yf.creatorshirt.mvp.ui.activity;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.common.ChangeSelectEvent;
import com.example.yf.creatorshirt.common.UpdateUserInfoEvent;
import com.example.yf.creatorshirt.mvp.model.VersionUpdateResponse;
import com.example.yf.creatorshirt.mvp.presenter.VersionUpdatePresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.VersionUpdateContract;
import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;
import com.example.yf.creatorshirt.mvp.ui.fragment.MineFragment;
import com.example.yf.creatorshirt.mvp.ui.fragment.NewDesignFragment;
import com.example.yf.creatorshirt.mvp.ui.view.CustomUpdateDialog;
import com.example.yf.creatorshirt.utils.Constants;
import com.example.yf.creatorshirt.utils.PackageUtil;
import com.example.yf.creatorshirt.utils.SharedPreferencesUtil;
import com.example.yf.creatorshirt.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)

public class MainActivity extends BaseActivity<VersionUpdatePresenter> implements VersionUpdateContract.VersionUpdateView {
    //    private static final String TYPE_SQUARE = "square";
    private static final String TYPE_DESIGN = "design";
    private static final String TYPE_MINE = "mine";
    @BindView(R.id.square_text)
    TextView mSquareContainer;
    @BindView(R.id.design_text)
    TextView mDesignContainer;
    @BindView(R.id.mine_text)
    TextView mMineContainer;

    private FragmentTransaction mTransaction;

    //    private SquareFragment mSquareFragment;
    private MineFragment mMineFragment;
    private NewDesignFragment mDesignFragment;
    private String showFragment;
    private String hideFragment;
    private long mExitTime = 0;
    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected int getView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
//        mPermissionsChecker = new PermissionChecker(this);
//        mSquareFragment = new SquareFragment();
        mMineFragment = new MineFragment();
        mDesignFragment = new NewDesignFragment();
        showFragment = TYPE_DESIGN;
        hideFragment = TYPE_DESIGN;
        mTransaction = getSupportFragmentManager().beginTransaction();
        mTransaction
//                .add(R.id.content, mSquareFragment, "square").show(mSquareFragment)
                .add(R.id.content, mDesignFragment, "design").show(mDesignFragment)
                .add(R.id.content, mMineFragment, "mine").hide(mMineFragment).commit();
        choiceTabState(TYPE_DESIGN);
        String key = PackageUtil.getSignature(App.getInstance());
        mAppBarBack.setVisibility(View.GONE);
    }


    @OnClick({R.id.mine_text, R.id.design_text, R.id.square_text})
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.square_text:
//                showFragment = TYPE_SQUARE;
//                choiceTabState(TYPE_SQUARE);
//                mAppBar.setVisibility(View.VISIBLE);
//                mAppBarTitle.setText("广场");
//                break;
            case R.id.design_text:
                choiceTabState(TYPE_DESIGN);
                showFragment = TYPE_DESIGN;
                mAppBar.setVisibility(View.VISIBLE);
                mAppBarBack.setVisibility(View.GONE);
                mAppBarTitle.setText("设计定制");
                break;
            case R.id.mine_text:
                showFragment = TYPE_MINE;
                choiceTabState(TYPE_MINE);
                mAppBar.setVisibility(View.GONE);
                break;
        }
        changeFragment(getShowFragment(showFragment), getShowFragment(hideFragment));
        hideFragment = showFragment;

    }

    private void choiceTabState(String position) {
        clearSelection();
        switch (position) {
//            case TYPE_SQUARE:
//                Drawable drawable01 = ContextCompat.getDrawable(this, R.mipmap.icon_square_hover);
//                mSquareContainer.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable01, null, null);
//                mSquareContainer.setTextColor(ContextCompat.getColor(this, R.color.manatee_1));
//                break;
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
//        Drawable drawable01 = ContextCompat.getDrawable(this, R.mipmap.icon_square_normal);
//        mSquareContainer.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable01, null, null);
//        mSquareContainer.setTextColor(ContextCompat.getColor(this, R.color.manatee_1));

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
//            case TYPE_SQUARE:
//                return mSquareFragment;
            case TYPE_MINE:
                return mMineFragment;
            case TYPE_DESIGN:
                return mDesignFragment;
        }
        return mDesignFragment;
    }


    //login success update view
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUserViewByLogin(UpdateUserInfoEvent event) {
        if (event.getFlag()) {
            if (mMineFragment != null && mMineFragment.isAdded()) {
                mMineFragment.updateUserView();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void update(ChangeSelectEvent event) {
        if (event.getFlag()) {
            if (mMineFragment != null && mMineFragment.isAdded()) {
                mMineFragment.updateUserView();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                ToastUtil.showToast(this, getString(R.string.exit_app), Toast.LENGTH_LONG);
                mExitTime = System.currentTimeMillis();
            } else {
                ToastUtil.cancel();
                MainActivity.this.finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        Constants.IS_BACK_TOKEN = true;
        Constants.ISTOKEN = true;
        super.onResume();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        ToastUtil.cancel();
    }

    @Override
    public void showErrorMsg(String msg) {
        super.showErrorMsg(msg);
        ToastUtil.showToast(mContext, msg, 0);
    }

    @Override
    public void netChange(int type) {
        super.netChange(type);
        if (type == 1 || type == 2) {
            refresh();
        }
    }

    public void refresh() {

    }

    @Override
    public void showSuccessUpdate(VersionUpdateResponse versionUpdateResponse) {

    }

    @Override
    public void suggestVerUpdate(VersionUpdateResponse versionResponse) {
        final CustomUpdateDialog.Builder builder = new CustomUpdateDialog.Builder(mContext);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.dialog_version_update, null);
        builder.setContentView(v).setTitle(R.string.txt_update_title).setMessage(versionResponse.getUpdate_log()).setPositiveButton(R.string.txt_btn_update, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPresenter.downloadApk(versionResponse.getApk_url());
                dialog.dismiss();
            }
        }).setNegativeButton(R.string.txt_btn_cancel, (dialog, which) -> dialog.dismiss());
        final CustomUpdateDialog dialog = builder.create();
        v.findViewById(R.id.check_ignore).setVisibility(View.VISIBLE);
        ((CheckBox) v.findViewById(R.id.check_ignore)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //忽略更新
                    SharedPreferencesUtil.setLastVersionCodeFromServer(versionResponse.getNew_version());
                    dialog.dismiss();
                }
            }
        });
    }

    private ArrayList<MyTouchListener> myTouchListeners = new ArrayList<>();

    public interface MyTouchListener {
        boolean onTouchEvent(MotionEvent ev);
    }

    public void registerMyTouchListener(MyTouchListener listener) {
        myTouchListeners.add(listener);
    }

    /**
     * 提供给Fragment通过getActivity()方法来取消注册自己的触摸事件的方法
     *
     * @param listener
     */
    public void unRegisterMyTouchListener(MyTouchListener listener) {
        myTouchListeners.remove(listener);
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        for (MyTouchListener listener : myTouchListeners) {
            listener.onTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }


}
