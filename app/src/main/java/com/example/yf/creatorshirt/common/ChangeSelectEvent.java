package com.example.yf.creatorshirt.common;

/**
 * Created by yangfang on 2017/9/20.
 */

public class ChangeSelectEvent {
    private boolean mFlag;

    public boolean getFlag() {
        return mFlag;
    }

    public void setFlag(boolean mFlag) {
        this.mFlag = mFlag;
    }

    public ChangeSelectEvent(boolean flag) {
        mFlag = flag;
    }
}
