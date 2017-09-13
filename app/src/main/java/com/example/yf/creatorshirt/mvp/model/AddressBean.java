package com.example.yf.creatorshirt.mvp.model;

/**
 * Created by yang on 29/06/2017.
 * 地址字段
 */

public class AddressBean {
    /**
     * address = cangyihdahdask; //
     * city = "\U6d59\U6c5f\U7701\U676d\U5dde\U5e02\U6c5f\U5e72\U533a"; //    id = 13;
     * id = 13;
     * isDefault = 0; //
     * mobile = 15868177542;
     * userId = 1119;
     * userName = Brad;
     * zipcode = 310000;
     */
    private String address;
    private String id;
    private String city;
    private int isDefault;
    private String mobile;
    private String userId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String userName;
    private String zipcode;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    @Override
    public String toString() {
        return "AddressBean{" +
                "address='" + address + '\'' +
                ", id='" + id + '\'' +
                ", city='" + city + '\'' +
                ", isDefault=" + isDefault +
                ", mobile='" + mobile + '\'' +
                ", userId='" + userId + '\'' +
                ", zipcode='" + zipcode + '\'' +
                '}';
    }
}
