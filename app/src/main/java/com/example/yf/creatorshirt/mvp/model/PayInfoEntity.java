package com.example.yf.creatorshirt.mvp.model;

/**
 * Created by yangfang on 2018/1/29.
 * {"orderId":1234,"paymode":"aliPay","address":"address","zipcode":"","mobile":"19905711354,username}
 */

public class PayInfoEntity {
    private String orderId;
    private String paymode;
    private String address;
    private String zipcode;
    private String mobile;
    private String userName;


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPaymode() {
        return paymode;
    }

    public void setPaymode(String paymode) {
        this.paymode = paymode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    @Override
    public String toString() {
        return "PayInfoEntity{" +
                "orderId='" + orderId + '\'' +
                ", paymode='" + paymode + '\'' +
                ", address='" + address + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", mobile='" + mobile + '\'' +
                ", username='" + userName + '\'' +
                '}';
    }
}
