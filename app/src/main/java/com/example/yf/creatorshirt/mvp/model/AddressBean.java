package com.example.yf.creatorshirt.mvp.model;

/**
 * Created by yang on 29/06/2017.
 * 地址字段
 */

public class AddressBean {
    /**
     * "address": "测试内容uz41",
     * "city": "测试内容c80k",
     * "id": 35676,
     * "isDefault": false,
     * "mobile": "测试内容xs56",
     * "name": "测试内容t4a2",
     * "province": "测试内容3b6t",
     * "region": "测试内容8t4w"
     */
    private String address;
    private String city;
    private int id;
    private boolean isDefault;
    private String mobile;
    private String name;
    private String province;
    private String region;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
