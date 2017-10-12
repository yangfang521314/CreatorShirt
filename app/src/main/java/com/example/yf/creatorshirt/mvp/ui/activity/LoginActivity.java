package com.example.yf.creatorshirt.mvp.ui.activity;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.common.UpdateUserInfoEvent;
import com.example.yf.creatorshirt.mvp.model.LoginBean;
import com.example.yf.creatorshirt.mvp.presenter.LoginPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.LoginContract;
import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;
import com.example.yf.creatorshirt.utils.LogUtil;
import com.example.yf.creatorshirt.utils.PhoneUtils;
import com.example.yf.creatorshirt.utils.ToastUtil;
import com.example.yf.creatorshirt.utils.WeakReferenceHandler;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.LoginView {

    @BindView(R.id.phone_number)
    EditText mEditPhone;
    @BindView(R.id.phone_code)
    EditText mEditCode;
    @BindView(R.id.next)
    Button mBtnNext;
    @BindView(R.id.weixin_login)
    Button mWeiXin;
    @BindView(R.id.send_code)
    TextView btnSendCode;
    private UMShareAPI mShareAPI = null;
    private SHARE_MEDIA platform = null;
    private int countTime;
    private Handler mHandler;

    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {
        mShareAPI = UMShareAPI.get(this);
        mPresenter.setPhoneCode(PhoneUtils.getTextString(mEditCode));
        mHandler = new RegisterHandler(this);
    }

    @OnTextChanged({R.id.phone_number})
    void afterTextChanged() {
        if (!TextUtils.isEmpty(PhoneUtils.getTextString(mEditPhone))) {
            mBtnNext.setSelected(true);
            btnSendCode.setSelected(true);
        } else {
            btnSendCode.setSelected(false);
            mBtnNext.setEnabled(false);
            mHandler.removeCallbacks(runnable);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @OnClick({R.id.next, R.id.send_code, R.id.weixin_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.next:
                if (emptyCheck()) {
                    mPresenter.phoneLogin(PhoneUtils.getTextString(mEditPhone), PhoneUtils.getTextString(mEditCode));
                }
                break;
            case R.id.weixin_login://微信login
                platform = SHARE_MEDIA.WEIXIN;

                if (mShareAPI.isInstall(LoginActivity.this, SHARE_MEDIA.WEIXIN)) {
                    platform = SHARE_MEDIA.WEIXIN;
                    mShareAPI.doOauthVerify(LoginActivity.this, platform, umAuthListener);
                } else {
                    //Umeng有提醒是否安装的Toast，不需要设置Toast。
                    Log.e("TAG", "dfuc you");
                }
                break;
            case R.id.send_code:
                if (!PhoneUtils.isPhoneNumberValid(PhoneUtils.getTextString(mEditPhone))) {
                    ToastUtil.showToast(mContext, "请输入正确的手机号码", 0);
                } else {
                    countTime = 60;
                    mPresenter.setPhoneNumber(PhoneUtils.getTextString(mEditPhone));
                    mPresenter.getVerifyCode();
                }
                break;
        }
    }


    public boolean emptyCheck() {
        if (!PhoneUtils.isPhoneNumberValid(PhoneUtils.getTextString(mEditPhone))) {
            ToastUtil.showToast(mContext, "请输入13位正确的手机号", 0);
            return false;
        }
        if (!PhoneUtils.notEmpty(mEditCode)) {
            ToastUtil.showToast(mContext, "请输入验证码", 0);
            return false;
        }

//        if (!PhoneUtils.match(PhoneUtils.getTextString(mEditCode), code)) {
//            ToastUtil.showToast(this, "验证码输入错误", 0);
//            return false;
//        }
        return true;
    }

    @Override
    protected int getView() {
        return R.layout.activity_login;
    }

    @Override
    public void showErrorMsg(String msg) {
        ToastUtil.showToast(this, msg, 0);
    }

    @Override
    public void stateError() {

    }


    @Override
    public void LoginSuccess(LoginBean loginBean) {
        if (!loginBean.getUserInfo().getNew()) {
            ToastUtil.showToast(mContext, "登录成功", 0);
            EventBus.getDefault().post(new UpdateUserInfoEvent(true));
            finish();
        } else {
            startCommonActivity(this, null, EditUserActivity.class);
            ToastUtil.showToast(mContext, "登录成功", 0);
            this.finish();
        }
    }

    private class RegisterHandler extends WeakReferenceHandler<LoginActivity> {

        public RegisterHandler(LoginActivity reference) {
            super(reference);
        }

        @Override
        protected void handleMessage(LoginActivity reference, Message msg) {

        }
    }

    @Override
    public void showSuccessCode() {
        //验证码的返回处理
        if (runnable != null) {
            mHandler.removeCallbacks(runnable);
        }
        mHandler.postDelayed(runnable, 1000);
        ToastUtil.showToast(mContext, "验证码已经发送", 0);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            btnSendCode.setText(countTime-- + getString(R.string.time_text));
            if (countTime > 0) {
                mHandler.postDelayed(this, 1000);
            } else {
                btnSendCode.setText(getString(R.string.register_get_pwd));
                btnSendCode.setClickable(true);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ToastUtil.cancel();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus()) {
            /**
             * 点击空白位置 隐藏软键盘
             */
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }

    /**
     * auth callback interface
     **/
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            Log.e("ff", "kkkkkkkkkkkkk");
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();

            mShareAPI.getPlatformInfo(LoginActivity.this, platform, umGetInfoListener);
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(getApplicationContext(), "授权 失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(getApplicationContext(), "授权 取消", Toast.LENGTH_SHORT).show();
        }
    };

    private String nickName;
    private String userId;
    private String imgUrl;
    private String openId;
    /**
     * getUserInfo
     **/
    private UMAuthListener umGetInfoListener = new UMAuthListener() {

        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {

            if (data != null) {

                if (platform == SHARE_MEDIA.WEIXIN) {
                    Set<String> keys = data.keySet();
                    for (String key : keys) {
                        if (key.equals("name")) {
                            nickName = data.get(key);
                            Log.e("TAG", "NICK" + nickName);
                        }
                        if (key.equals("uid")) {
                            userId = data.get(key);
                            Log.e("TAG", "uid" + userId);
                        }
                        if (key.equals("iconurl")) {
                            imgUrl = data.get(key);
                        }
                        if (key.equals("openid")) {
                            openId = data.get(key);
                        }
                    }

                    loginByThirdpart(openId);
                }

                LogUtil.d("auth callbacl", "getting data");
                //  Toast.makeText(getApplicationContext(), data.toString(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(getApplicationContext(), "获取用户信息失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(getApplicationContext(), "获取用户信息失败", Toast.LENGTH_SHORT).show();
        }
    };

    private void loginByThirdpart(String openId) {
        mPresenter.wenxinLogin(openId);
    }

}
