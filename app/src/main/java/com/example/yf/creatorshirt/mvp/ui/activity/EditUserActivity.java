package com.example.yf.creatorshirt.mvp.ui.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.app.GlideApp;
import com.example.yf.creatorshirt.common.UpdateUserInfoEvent;
import com.example.yf.creatorshirt.common.manager.UserInfoManager;
import com.example.yf.creatorshirt.mvp.presenter.CommonAvatarPresenter;
import com.example.yf.creatorshirt.mvp.presenter.EditUserInfoPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.CommonAvatarContract;
import com.example.yf.creatorshirt.mvp.presenter.contract.EditUserInfoContract;
import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;
import com.example.yf.creatorshirt.mvp.ui.view.DialogPermission;
import com.example.yf.creatorshirt.mvp.ui.view.EditUserPopupWindow;
import com.example.yf.creatorshirt.utils.CircleAvatar;
import com.example.yf.creatorshirt.utils.Constants;
import com.example.yf.creatorshirt.utils.PermissionUtil;
import com.example.yf.creatorshirt.utils.PhoneUtils;
import com.example.yf.creatorshirt.utils.SharedPreferencesMark;
import com.example.yf.creatorshirt.utils.SharedPreferencesUtil;
import com.example.yf.creatorshirt.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class EditUserActivity extends BaseActivity<EditUserInfoPresenter> implements EditUserInfoContract.EditUserInfoView,
        CommonAvatarContract.CommonAvatarView {

    @BindView(R.id.user_edit_avatar)
    ImageView mEditUser;
    @BindView(R.id.user_edit_name)
    EditText mEditName;
    @BindView(R.id.save_user)
    Button mSaveUser;
    @BindView(R.id.user_tv_filter)
    TextView mTextFilter;
    @BindView(R.id.ll_edit_user)
    RelativeLayout mRLUser;

    private String mAvatarUrl;
    private String update = null;
    private CommonAvatarPresenter commonAvatarPresenter;

    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {
        if (update != null) {
            if (update.equals("update")) {
                mAppBarTitle.setText("修改资料");
                mTextFilter.setVisibility(View.GONE);
                if (App.isLogin) {
                    GlideApp.with(this)
                            .load(UserInfoManager.getInstance().getLoginResponse().getUserInfo().getHeadImage())
                            .error(R.mipmap.ic_icon)
                            .circleCrop()
                            .into(mEditUser);
                    mAvatarUrl = UserInfoManager.getInstance().getLoginResponse().getUserInfo().getHeadImage();
                    if (!TextUtils.isEmpty(UserInfoManager.getInstance().getUserName())) {
                        mEditName.setText(UserInfoManager.getInstance().getUserName());
                        mEditName.setSelection(UserInfoManager.getInstance().getUserName().length());

                    }
                }
            }
        } else {
            mAppBarTitle.setText("编辑用户");
            mTextFilter.setVisibility(View.VISIBLE);
            //微信登录时候
            if (UserInfoManager.getInstance().getLoginResponse().getUserInfo().getHeadImage() != null) {
                mAvatarUrl = UserInfoManager.getInstance().getLoginResponse().getUserInfo().getHeadImage();
                RequestOptions options = new RequestOptions()
                        .error(R.mipmap.ic_icon)
                        .transform(new CircleAvatar(this))
                        .diskCacheStrategy(DiskCacheStrategy.ALL);

                Glide.with(this).load(mAvatarUrl).apply(options).into(mEditUser);
                mEditName.setText(UserInfoManager.getInstance().getUserName());
                mPresenter.setWeixinURL(mAvatarUrl);
            }
        }
        mAppBarBack.setVisibility(View.VISIBLE);
    }

    @Override
    public void initData() {
        super.initData();
        if (getIntent().getExtras() != null) {
            update = getIntent().getExtras().getString("update");
        }

    }

    @Override
    protected int getView() {
        return R.layout.activity_edit_user;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.getToken();
        commonAvatarPresenter = new CommonAvatarPresenter(this);
        commonAvatarPresenter.attachView(this);
    }

    @OnClick({R.id.user_edit_avatar, R.id.user_tv_filter, R.id.save_user, R.id.back})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_tv_filter:
                //微信登录的
                if (UserInfoManager.getInstance().getLoginResponse().getUserInfo().getHeadImage() != null) {
                    mPresenter.setWeixinURL(UserInfoManager.getInstance().getLoginResponse().getUserInfo().getHeadImage());
                    mPresenter.setUserName(UserInfoManager.getInstance().getLoginResponse().getUserInfo().getName());
                    mPresenter.saveUserInfo();
                }
                this.finish();
                EventBus.getDefault().post(new UpdateUserInfoEvent(true));
                break;
            case R.id.user_edit_avatar:
                EditUserPopupWindow mPopupWindow = commonAvatarPresenter.initPopupWindow();
                mPopupWindow.showAtLocation(mRLUser, Gravity.CENTER | Gravity.BOTTOM, 0, 0);
                commonAvatarPresenter.setParams(Constants.CHANGE_ALPHA);
                break;
            case R.id.save_user:
                if (isCheck()) {
                    mPresenter.setUserName(PhoneUtils.getTextString(mEditName));
                    mPresenter.saveUserInfo();
                }
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    private boolean isCheck() {
        if (TextUtils.isEmpty(PhoneUtils.getTextString(mEditName))) {
            ToastUtil.showToast(mContext, "用户名为空", 0);
            return false;
        }
        if (TextUtils.isEmpty(mAvatarUrl)) {
            ToastUtil.showToast(mContext, "没有选择头像", 0);
            return false;
        }
        return true;
    }


    @Override
    public void showSuccessImage(String userAvatar) {
        mAvatarUrl = userAvatar;
        RequestOptions options = new RequestOptions()
                .error(R.mipmap.ic_icon)
                .transform(new CircleAvatar(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(this).load(userAvatar).apply(options).into(mEditUser);
    }

    @Override
    public void showSuccessSaveInfo(Integer status) {
        if (update != null) {
            if (update.equals("update")) {
                SharedPreferencesUtil.setUserName(PhoneUtils.getTextString(mEditName));
                ToastUtil.showToast(mContext, "设置信息成功", 0);
                this.finish();
            }
        } else {
            SharedPreferencesUtil.setUserName(PhoneUtils.getTextString(mEditName));
            ToastUtil.showToast(mContext, "修改资料成功", 0);
            this.finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case PermissionUtil.REQUEST_SHOWCAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    commonAvatarPresenter.uploadAvatarFromPhotoRequest();

                } else {
                    if (!SharedPreferencesMark.getHasShowCamera()) {
                        SharedPreferencesMark.setHasShowCamera(true);
                        new DialogPermission(this, "关闭摄像头权限影响扫描功能");

                    } else {
                        ToastUtil.showToast(mContext, "未获取摄像头权限", Toast.LENGTH_SHORT);
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        commonAvatarPresenter.callback(requestCode, resultCode, data);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus()) {
            /**
             * 点击空白位置 隐藏软键盘
             */
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            assert mInputMethodManager != null;
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        commonAvatarPresenter.detachView(this);
    }

    @Override
    public void showSuccessAvatar(File cover) {
        if (cover != null) {
            mPresenter.setImageFile(cover);
            mPresenter.saveUserAvatar();
        }
    }

    @Override
    public void onBackPressed() {
        if (update != null) {
            super.onBackPressed();
        }
    }
}
