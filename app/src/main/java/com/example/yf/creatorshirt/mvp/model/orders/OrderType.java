package com.example.yf.creatorshirt.mvp.model.orders;

/**
 * Created by yangfang on 2017/9/1.
 */

public class OrderType {
    private String orderId;
    private int fee;
    private String orderType;

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
}
