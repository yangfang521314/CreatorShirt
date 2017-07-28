package com.example.yf.creatorshirt.mvp.ui.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.model.bean.LoginBean;
import com.example.yf.creatorshirt.mvp.presenter.LoginPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.LoginContract;
import com.example.yf.creatorshirt.utils.PhoneUtils;
import com.example.yf.creatorshirt.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

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
    Button btnSendCode;

    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {
        mPresenter.setPhoneNumber(PhoneUtils.getTextString(mEditPhone));
        mPresenter.setPhoneCode(PhoneUtils.getTextString(mEditCode));
        //手机登录
//        mPresenter.phoneLogin();
    }

    @OnClick({R.id.next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.next:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.weixin_login://微信login
                mPresenter.wenxinLogin();
                break;
            case R.id.send_code:
                mPresenter.getVerifyCode();
                break;
        }
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
    public void LoginSuccess(LoginBean loginBean) {
        Log.e("TAG","LO"+loginBean.getStatus());
        ToastUtil.showToast(this, "登录成功", 0);
    }

}
