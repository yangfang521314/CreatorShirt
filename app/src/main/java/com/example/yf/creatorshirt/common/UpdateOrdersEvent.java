package com.example.yf.creatorshirt.common;

/**
 * create by yang 2017/9/15
 */
public class UpdateOrdersEvent {

    private boolean mFlag;

    public boolean getFlag() {
        return mFlag;
    }

    public void setFlag(boolean mFlag) {
        this.mFlag = mFlag;
    }

    public UpdateOrdersEvent(boolean flag) {
        mFlag = flag;
    }

}
