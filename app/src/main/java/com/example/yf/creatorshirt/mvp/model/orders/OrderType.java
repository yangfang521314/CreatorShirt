package com.example.yf.creatorshirt.mvp.model.orders;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yangfang on 2017/9/1.
 */

public class OrderType implements Parcelable{
    private int orderId;
    private String dispContext;

    protected OrderType(Parcel in) {
        orderId = in.readInt();
        dispContext = in.readString();
    }

    public static final Creator<OrderType> CREATOR = new Creator<OrderType>() {
        @Override
        public OrderType createFromParcel(Parcel in) {
            return new OrderType(in);
        }

        @Override
        public OrderType[] newArray(int size) {
            return new OrderType[size];
        }
    };

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getDispContext() {
        return dispContext;
    }

    public void setDispContext(String dispContext) {
        this.dispContext = dispContext;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(orderId);
        dest.writeString(dispContext);
    }
}
