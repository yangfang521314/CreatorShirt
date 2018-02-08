package com.example.yf.creatorshirt.common;

/**
 * Created by yangfang on 2018/2/5.
 */

public class UpdateStateEvent {
    private boolean mFlag;

    public boolean getFlag() {
        return mFlag;
    }

    public void setFlag(boolean mFlag) {
        this.mFlag = mFlag;
    }

    public UpdateStateEvent(boolean flag) {
        mFlag = flag;
    }
}
