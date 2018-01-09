package com.example.yf.creatorshirt.common;

import com.example.yf.creatorshirt.mvp.model.orders.ClothesSize;

/**
 * create by yang 2017/9/15
 */
public class UpdateOrdersEvent {

    private boolean mFlag;
    private ClothesSize clothesSize;

    public boolean getFlag() {
        return mFlag;
    }

    public void setFlag(boolean mFlag) {
        this.mFlag = mFlag;
    }

    public UpdateOrdersEvent(boolean mFlag, ClothesSize clothesSize) {
        this.mFlag = mFlag;
        this.clothesSize = clothesSize;
    }

    public ClothesSize getClothesSize() {
        return clothesSize;
    }

    public void setClothesSize(ClothesSize clothesSize) {
        this.clothesSize = clothesSize;
    }
}
