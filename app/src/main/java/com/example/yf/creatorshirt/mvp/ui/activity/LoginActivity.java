package com.example.yf.creatorshirt.mvp.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.model.bean.LoginBean;
import com.example.yf.creatorshirt.mvp.presenter.LoginPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.LoginContract;
import com.example.yf.creatorshirt.utils.PermissionChecker;
import com.example.yf.creatorshirt.utils.PhoneUtils;
import com.example.yf.creatorshirt.utils.SharedPreferencesUtil;
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
    private static final int REQUEST_CODE = 9;

    private PermissionChecker mPermissionsChecker; // 权限检测器
    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {
        mPermissionsChecker = new PermissionChecker(this);
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


    @Override
    protected void onResume() {
        super.onResume();
        //设置到启动页面
        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            String notice = "存储空间权限用于下载和软件更新,关闭权限将关闭应用，是否放弃权限允许？";
            PermissionActivity.startActivityForResult(this, notice, REQUEST_CODE, PERMISSIONS);
        } else {
//            startOtherActivity();
        }
    }

    private void startOtherActivity() {
        boolean isFirstLaunch = SharedPreferencesUtil.getAppIsFirstLaunched();
        if (!isFirstLaunch) {
            //// TODO: 28/07/2017 启动第一次直接进入MainAvtivity
            startActivity(new Intent(this,MainActivity.class));
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

}
