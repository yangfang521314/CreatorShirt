package com.example.yf.creatorshirt.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.app.GlideApp;
import com.example.yf.creatorshirt.mvp.model.VersionStyle;
import com.example.yf.creatorshirt.mvp.model.orders.OrderType;
import com.example.yf.creatorshirt.mvp.model.orders.SaveOrderInfo;
import com.example.yf.creatorshirt.mvp.presenter.OrderInfoPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.OrderInfoContract;
import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;
import com.example.yf.creatorshirt.utils.Constants;
import com.example.yf.creatorshirt.utils.FileUtils;
import com.example.yf.creatorshirt.utils.RxUtils;
import com.example.yf.creatorshirt.utils.ToastUtil;
import com.example.yf.creatorshirt.utils.Utils;
import com.example.yf.creatorshirt.widget.CommonObserver;
import com.umeng.socialize.UMShareAPI;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * 选择设计尺寸大小页面
 */
public class ShowImageActivity extends BaseActivity<OrderInfoPresenter> implements OrderInfoContract.OrderInfoView {
    @BindView(R.id.clothes_front_image)
    ImageView mFrontImage;
    @BindView(R.id.clothes_back_image)
    ImageView mBackImage;
    @BindView(R.id.rl_choice_size)
    LinearLayout mRealChoiceSize;
    @BindView(R.id.btn_choice_order)
    Button mCreateOrder;
    @BindView(R.id.clothes_back)
    TextView mButtonBack;
    @BindView(R.id.clothes_front)
    TextView mButtonFront;
    @BindView(R.id.rl_front)
    RelativeLayout mRlFront;
    @BindView(R.id.rl_back)
    RelativeLayout mRlBack;

    private String mBackImageUrl;
    private String mFrontImageUrl;
    private VersionStyle mOrderBaseInfo;
    private List<String> arrayList = new ArrayList<>();
    private int backInit;
    private SaveOrderInfo saveStyleEntity;

    @Override
    public void initData() {
        super.initData();
        if (getIntent().getExtras() != null) {
            mOrderBaseInfo = getIntent().getExtras().getParcelable("clothesInfo");
            if (mOrderBaseInfo != null) {
                mFrontImageUrl = mOrderBaseInfo.getFrontUrl();
                if (mOrderBaseInfo.getBackUrl() == null) {
                    backInit = getIntent().getExtras().getInt("backInit");
                } else {
                    mBackImageUrl = mOrderBaseInfo.getBackUrl();
                }
            }
            assert mOrderBaseInfo != null;
            if (mOrderBaseInfo.getPicture1() != null) {
                arrayList.add(mOrderBaseInfo.getPicture1());
            }
            if (mOrderBaseInfo.getPicture2() != null) {
                arrayList.add(mOrderBaseInfo.getPicture2());
            }
        }
    }

    @Override
    protected int getView() {
        return R.layout.activity_choice;
    }

    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {
        mAppBarTitle.setText(R.string.design);
        mAppBarBack.setVisibility(View.VISIBLE);
        mButtonFront.setSelected(true);
        if (mFrontImageUrl != null) {
            GlideApp.with(this).load(mFrontImageUrl).into(mFrontImage);
        }
    }

    @OnClick({R.id.btn_choice_order, R.id.back, R.id.clothes_back, R.id.clothes_front})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_choice_order:
                if (App.isLogin) {
                    mPresenter.setSaveEntity(setBaseInfo());
//                    if (arrayList != null && arrayList.size() != 0) {
//                        mPresenter.saveAvatar(arrayList);
//                    }
//                    mPresenter.setBackUrl(mOrderBaseInfo.getBackUrl());
//                    mPresenter.requestSave("A", mOrderBaseInfo.getFrontUrl());
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("clothesInfo", saveStyleEntity);
                    startCommonActivity(ShowImageActivity.this, bundle, OrderEditActivity.class);
                } else {
                    startCommonActivity(this, null, LoginActivity.class);//跳转到登录界面
                }
                break;
            case R.id.back:
                finish();
                break;
            case R.id.clothes_front:
                mRlFront.setVisibility(View.VISIBLE);
                mRlBack.setVisibility(View.GONE);
                mButtonBack.setSelected(false);
                mButtonFront.setSelected(true);
                GlideApp.with(this).load(mFrontImageUrl)
                        .into(mFrontImage);
                break;
            case R.id.clothes_back:
                mRlFront.setVisibility(View.GONE);
                mRlBack.setVisibility(View.VISIBLE);
                mButtonBack.setSelected(true);
                mButtonFront.setSelected(false);
                if (mBackImageUrl != null) {
                    GlideApp.with(this).load(mBackImageUrl)
                            .into(mBackImage);
                } else {
                    Observable.create(new ObservableOnSubscribe<Bitmap>() {
                        @Override
                        public void subscribe(ObservableEmitter<Bitmap> e) throws Exception {
                            Bitmap bitmap = FileUtils.getClothesImage(Constants.WIDTH_MASK, Constants.HEIGHT_MASK, Utils.getBitmapResource(backInit));
                            e.onNext(bitmap);
                        }
                    }).compose(RxUtils.<Bitmap>rxObScheduleHelper())
                            .subscribe(new CommonObserver<Bitmap>(null) {
                                @Override
                                public void onNext(Bitmap bitmap) {
//                                    mView.showClothesBg(bitmap);
                                    GlideApp.with(mContext)
                                            .load(bitmap)
                                            .into(mBackImage);
                                }
                            });
                }
                break;
        }
    }

    private SaveOrderInfo setBaseInfo() {
        saveStyleEntity = new SaveOrderInfo();
        saveStyleEntity.setBaseId(mOrderBaseInfo.getType());
        saveStyleEntity.setFinishAimage(mOrderBaseInfo.getFrontUrl());
        saveStyleEntity.setFinishBimage(mOrderBaseInfo.getBackUrl());
        saveStyleEntity.setColor(mOrderBaseInfo.getColor());
        saveStyleEntity.setPicture1(mOrderBaseInfo.getPicture1());
        saveStyleEntity.setPicture2(mOrderBaseInfo.getPicture2());
        if (mOrderBaseInfo.getText() != null && mOrderBaseInfo.getText().size() != 0) {
            saveStyleEntity.setText(mOrderBaseInfo.getText().toString());
        } else {
            saveStyleEntity.setText("");
        }
        if (mOrderBaseInfo.getBackText() != null && mOrderBaseInfo.getBackText().size() != 0) {
            saveStyleEntity.setBackText(mOrderBaseInfo.getBackText().toString());
        } else {
            saveStyleEntity.setBackText("");
        }
        return saveStyleEntity;
    }

    @Override
    public void showErrorMsg(String msg) {
        ToastUtil.showToast(this, msg, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (App.isLogin) {
            mPresenter.getToken();
        }
    }

    @Override
    public void showOrderId(OrderType orderType) {//orderId
        Bundle bundle = new Bundle();
        bundle.putParcelable("clothesInfo", saveStyleEntity);
        startCommonActivity(ShowImageActivity.this, bundle, OrderEditActivity.class);
    }
}
