package com.example.yf.creatorshirt.common;

import com.example.yf.creatorshirt.mvp.model.AddressBean;

/**
 * Created by yangfang on 2017/9/20.
 */

public class DefaultAddressEvent {
    private AddressBean choiceAddress;

    public AddressBean getFlag() {
        return choiceAddress;
    }

    public void setFlag(AddressBean mFlag) {
        this.choiceAddress = mFlag;
    }

    public DefaultAddressEvent(AddressBean flag) {
        choiceAddress = flag;
    }

}
