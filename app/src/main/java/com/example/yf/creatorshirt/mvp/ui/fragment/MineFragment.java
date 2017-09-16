package com.example.yf.creatorshirt.mvp.ui.fragment;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.common.UserInfoManager;
import com.example.yf.creatorshirt.mvp.model.LoginBean;
import com.example.yf.creatorshirt.mvp.presenter.UserInfoPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.UserInfoContract;
import com.example.yf.creatorshirt.mvp.ui.activity.AddressShowActivity;
import com.example.yf.creatorshirt.mvp.ui.activity.LoginActivity;
import com.example.yf.creatorshirt.mvp.ui.activity.UserCenterActivity;
import com.example.yf.creatorshirt.mvp.ui.fragment.base.BaseFragment;
import com.example.yf.creatorshirt.mvp.ui.view.DialogLogout;
import com.example.yf.creatorshirt.utils.SharedPreferencesUtil;
import com.example.yf.creatorshirt.utils.ToastUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by panguso on 2017/5/11.
 */

public class MineFragment extends BaseFragment<UserInfoPresenter> implements UserInfoContract.UserView {

    @BindView(R.id.user_avatar)
    ImageView mUserPicture;
    @BindView(R.id.choice_address_iv)
    ImageView mChoiceAddress;
    @BindView(R.id.choice_order_iv)
    ImageView mChoiceOrder;
    @BindView(R.id.user_name)
    TextView mUserName;
    @BindView(R.id.update_user_info)
    ImageView mUpdateUserInfo;
    @BindView(R.id.rl_user_address)
    RelativeLayout mRLAddress;
    @BindView(R.id.rl_user_info)
    RelativeLayout mRLUserInfo;
    @BindView(R.id.rl_user_order)
    RelativeLayout mRLUserOrder;
    @BindView(R.id.exit_login)
    Button mExitLogin;
    private DialogLogout dialogLogout;

    @Inject
    Activity mActivity;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initViews(View mView) {

    }

    @Override
    protected void initData() {
        //打开app用户去更新信息
//        if (!TextUtils.isEmpty(UserInfoManager.getInstance().getToken())) {
        Log.e("T......./////", "DDDDD" + App.isLogin);
        if (App.isLogin) {
            mPresenter.getUserInfo(SharedPreferencesUtil.getUserToken());
//        }
        }

    }

    @OnClick({R.id.user_avatar, R.id.choice_address_iv, R.id.choice_order_iv, R.id.update_user_info,
            R.id.rl_user_address, R.id.rl_user_info, R.id.rl_user_order, R.id.user_name, R.id.exit_login})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_name:
            case R.id.user_avatar:
                if (App.isLogin) {
                    startCommonActivity(getActivity(), null, UserCenterActivity.class);
                } else {
                    startCommonActivity(getActivity(), null, LoginActivity.class);
                }
                break;
            case R.id.choice_address_iv:
            case R.id.rl_user_address:
                if (App.isLogin) {
                    startCommonActivity(mActivity, null, AddressShowActivity.class);
                } else {
                    startCommonActivity(getActivity(), null, LoginActivity.class);
                }
                break;
            case R.id.choice_order_iv:
            case R.id.rl_user_order:
                break;
            case R.id.update_user_info:
            case R.id.rl_user_info:
                if (App.isLogin) {
                    startCommonActivity(mActivity, null, UserCenterActivity.class);
                } else {
                    startCommonActivity(getActivity(), null, LoginActivity.class);
                }
                break;
            case R.id.exit_login:
                getDialog();
                break;

        }
    }

    private void getDialog() {
        dialogLogout = new DialogLogout();
        dialogLogout.setOnDialogClickListener(itemsOnclickListener);
        dialogLogout.show(getFragmentManager(), "dialog");
    }

    private View.OnClickListener itemsOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.negative:
                    dialogLogout.dismiss();
                    break;
                case R.id.positive:
                    logout();
                    dialogLogout.dismiss();
                    ToastUtil.showToast(getContext(), "注销成功", 0);
                    break;
                default:
                    break;
            }

        }
    };

    private void logout() {
        UserInfoManager.getInstance().logOut();
        updateUserView();
    }

    @Override
    public void showErrorMsg(String msg) {
        App.setIsLogin(false);
        UserInfoManager.getInstance().logOut();
        ToastUtil.showToast(mActivity, msg, 0);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showUserInfo(LoginBean userInfo) {
        updateUserView();
    }

    //更新视图
    public void updateUserView() {
        if (App.isLogin) {
            if (UserInfoManager.getInstance().getLoginResponse() != null) {
                mUserName.setText(UserInfoManager.getInstance().getLoginResponse().getUserInfo().getName());
                RequestOptions options = new RequestOptions()
                        .circleCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.mipmap.mm);
                Glide.with(mActivity).
                        load(UserInfoManager.getInstance().getLoginResponse().getUserInfo().getHeadImage()).apply(options).into(mUserPicture);
                mExitLogin.setText("退出登录");
                mExitLogin.setVisibility(View.VISIBLE);
            }
        } else {
            mExitLogin.setVisibility(View.GONE);
            mUserName.setText("未登录");
            mUserPicture.setImageResource(R.mipmap.user_default_avatar);
        }
    }
}
