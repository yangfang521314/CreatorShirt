package com.example.yf.creatorshirt.mvp.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.model.address.City;
import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;
import com.example.yf.creatorshirt.utils.PhoneUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class EditAddressActivity extends BaseActivity {
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

    @Override
    protected void inject() {

    }

    @Override
    protected void initView() {
        String receiverName = PhoneUtils.getTextString(mReceiverName);
        String receiverPhone = PhoneUtils.getTextString(mReceiverPhone);
        String receiverEmail = mReceiverEmail.getText().toString();
        String receiverCity = mReceiverCity.getText().toString();

    }

    @OnClick({R.id.address_edit_city})
    void onClick(View view){
        switch (view.getId()){
            case R.id.address_edit_city:
                Intent intent = new Intent(this, AddressCheckActivity.class);
                startActivityForResult(intent, RETURN_CITY);
                break;
        }
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
        mReceiverCity.setText(tvAddress + "\n提交到服务器的id是：" + lastId);
    }


    @Override
    protected int getView() {
        return R.layout.activity_edit_address;
    }
}
