package com.example.yf.creatorshirt.mvp.model.orders;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yangfang on 2017/9/1.
 */

public class OrderType implements Parcelable{
    private String orderId;
    private int fee;
    private String orderType;

    protected OrderType(Parcel in) {
        orderId = in.readString();
        fee = in.readInt();
        orderType = in.readString();
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    @Override
    public String toString() {
        return "OrderType{" +
                "orderId='" + orderId + '\'' +
                ", fee=" + fee +
                ", orderType='" + orderType + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(orderId);
        dest.writeInt(fee);
        dest.writeString(orderType);
    }
}
