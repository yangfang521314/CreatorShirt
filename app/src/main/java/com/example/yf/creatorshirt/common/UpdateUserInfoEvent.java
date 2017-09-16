package com.example.yf.creatorshirt.common;

/**
 * Created by chinaso on 2015/12/10.
 */
public class UpdateUserInfoEvent {

    private boolean mFlag;
    public boolean getFlag() {
        return mFlag;
    }

    public void setFlag(boolean mFlag) {
        this.mFlag = mFlag;
    }

    public UpdateUserInfoEvent(boolean flag){
        mFlag=flag;
    }

}
