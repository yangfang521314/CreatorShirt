package com.example.yf.creatorshirt.mvp.ui.fragment;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.ui.activity.UserCenterActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by panguso on 2017/5/11.
 */

public class MineFragment extends BaseFragment {

    @BindView(R.id.user_avatar)
    ImageView mUserPicture;
    @Inject
    Activity mActivity;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.home_fragment;
    }

    @Override
    protected void initViews(View mView) {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.user_avatar})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_avatar:
                startCommonActivity(mActivity, UserCenterActivity.class);
                break;
        }
    }
}
