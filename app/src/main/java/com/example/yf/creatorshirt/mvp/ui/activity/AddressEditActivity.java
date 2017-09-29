package com.example.yf.creatorshirt.mvp.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.common.UpdateAddressEvent;
import com.example.yf.creatorshirt.mvp.model.AddressBean;
import com.example.yf.creatorshirt.mvp.model.address.City;
import com.example.yf.creatorshirt.mvp.presenter.AddressEditPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.AddressEditContract;
import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;
import com.example.yf.creatorshirt.utils.PhoneUtils;
import com.example.yf.creatorshirt.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 编辑地址页面
 */
public class AddressEditActivity extends BaseActivity<AddressEditPresenter> implements AddressEditContract.AddressEditView {
    private static final int RETURN_CITY = 23;
    @BindView(R.id.address_edit_address)
    EditText mEditAddress;
    @BindView(R.id.address_edit_name)
    EditText mReceiverName;
    @BindView(R.id.address_edit_phone)
    EditText mReceiverPhone;
    @BindView(R.id.address_edit_email)
    EditText mReceiverEmail;
    @BindView(R.id.address_edit_city)
    TextView mReceiverCity;
    @BindView(R.id.address_choice)
    ImageView mAddressChoice;
    private AddressBean mAddressBean;

    @Inject
    App mContext;

    @Override
    public void initData() {
        super.initData();
        if (getIntent().getExtras() != null) {
            mAddressBean = getIntent().getExtras().getParcelable("address");
        }
    }

    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {

        mAppBarBack.setVisibility(View.VISIBLE);
        mSaveAddress.setVisibility(View.VISIBLE);
        if (mAddressBean != null) {
            mAppBarTitle.setText("地址修改");
            mReceiverName.setText(mAddressBean.getUserName());
            mReceiverCity.setText(mAddressBean.getCity());
            mReceiverPhone.setText(mAddressBean.getMobile());
            mReceiverEmail.setText(mAddressBean.getZipcode());
            mEditAddress.setText(mAddressBean.getAddress());
        } else {
            mAppBarTitle.setText("地址编辑");
        }
    }

    @OnClick({R.id.address_edit_city, R.id.address_choice, R.id.save, R.id.back})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.address_choice:
            case R.id.address_edit_city:
                Intent intent = new Intent(this, AddressCheckActivity.class);
                startActivityForResult(intent, RETURN_CITY);
                break;
            case R.id.save:
                String receiverName = PhoneUtils.getTextString(mReceiverName);
                String receiverPhone = PhoneUtils.getTextString(mReceiverPhone);
                String receiverEmail = mReceiverEmail.getText().toString();
                String receiverCity = mReceiverCity.getText().toString();
                String receiverAddress = mEditAddress.getText().toString();
                if (mAddressBean == null) {
                    if (!isCheck()) {
                        mPresenter.setAddressInfo(receiverName, receiverPhone, receiverEmail, receiverCity, receiverAddress);
                        mPresenter.saveAddressData();
                    }
                } else {
                    if (!isCheck()) {
                        mPresenter.setUpdate(receiverName, receiverPhone, receiverEmail, receiverCity, receiverAddress, mAddressBean.getIsDefault(), mAddressBean.getId());
                        mPresenter.setUpdateAddress();
                    }
                }
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    private boolean isCheck() {
        if (TextUtils.isEmpty(PhoneUtils.getTextString(mReceiverName))) {
            ToastUtil.showToast(mContext, "请输入收件人姓名", 0);
            return true;
        } else if (!PhoneUtils.isPhoneNumberValid(PhoneUtils.getTextString(mReceiverPhone))) {
            ToastUtil.showToast(mContext, "请输入有效的电话号码", 0);
            return true;
        } else if (TextUtils.isEmpty(mReceiverCity.getText().toString())) {
            ToastUtil.showToast(mContext, "请输入收件人地区", 0);
            return true;
        } else if (TextUtils.isEmpty(PhoneUtils.getTextString(mEditAddress))) {
            ToastUtil.showToast(mContext, "请输入完整地址", 0);
            return true;
        }
        return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case RETURN_CITY: {
                parseAddress(data);
                break;
            }
        }
    }

    /**
     * 解析地址
     *
     * @param data
     */
    private void parseAddress(Intent data) {
        ArrayList<City> cityList = AddressCheckActivity.parse(data);

        String tvAddress = "", lastId = "";
        if (cityList != null) {
            for (int i = 0; i < cityList.size(); i++) {
                City city = cityList.get(i);
                lastId = city.getId();
                tvAddress += city.getName();
            }
        }
        mReceiverCity.setText(tvAddress);
    }


    @Override
    protected int getView() {
        return R.layout.activity_edit_address;
    }

    @Override
    public void showErrorMsg(String msg) {
        ToastUtil.showToast(this, msg, 0);
    }

    @Override
    public void SuccessSaveAddress(String message) {
        ToastUtil.showToast(App.getInstance(), message, 0);
        EventBus.getDefault().post(new UpdateAddressEvent(true));
        finish();
    }

}
