package com.example.yf.creatorshirt.common;

/**
 * Created by yangfang on 2017/9/18.
 */

public class UpdateAddressEvent {
    private boolean mFlag;

    public boolean getFlag() {
        return mFlag;
    }

    public void setFlag(boolean mFlag) {
        this.mFlag = mFlag;
    }

    public UpdateAddressEvent(boolean flag) {
        mFlag = flag;
    }

}
