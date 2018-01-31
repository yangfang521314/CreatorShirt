package com.example.yf.creatorshirt.mvp.model;

/**
 * Created by yangfang on 2018/1/15.
 */

public class ClothesPrice {
    private double orderPrice;
    private double discountPrice;
    private String discountcode;

    public double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getDiscountcode() {
        return discountcode;
    }

    public void setDiscountcode(String discountcode) {
        this.discountcode = discountcode;
    }

    @Override
    public String toString() {
        return "ClothesPrice{" +
                "orderPrice=" + orderPrice +
                ", discountPrice=" + discountPrice +
                ", discountcode='" + discountcode + '\'' +
                '}';
    }
}
