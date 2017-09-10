package com.example.yf.creatorshirt.mvp.ui.activity;

import android.widget.EditText;
import android.widget.TextView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;

import butterknife.BindView;

public class EditAddressActivity extends BaseActivity {
    @BindView(R.id.address_edit_address)
    EditText mEditAddress;
    @BindView(R.id.address_edit_name)
    EditText mReceiverName;
    @BindView(R.id.address_edit_phone)
    EditText mReceiverPhone;
    @BindView(R.id.address_edit_email)
    TextView mReceiverEmail;
    @BindView(R.id.address_edit_city)
    TextView mReceiverCity;

    @Override
    protected void inject() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getView() {
        return R.layout.activity_edit_address;
    }
}
