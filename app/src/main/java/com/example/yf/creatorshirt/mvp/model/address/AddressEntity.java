package com.example.yf.creatorshirt.mvp.model.address;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yangfang on 2017/9/12.
 * <p>
 * <p>
 * Parameter content type:
 * {"id":"","address":"","isDefault":"true","userName","","mobile":"","city":"","zipcode":""}
 */

public class AddressEntity implements Parcelable{
    private String id;
    private String address;
    private int isDefault;
    private String userName;
    private String mobile;
    private String city;
    private String zipcode;

    public AddressEntity() {
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.address);
        dest.writeInt(this.isDefault);
        dest.writeString(this.userName);
        dest.writeString(this.mobile);
        dest.writeString(this.city);
        dest.writeString(this.zipcode);
    }

    protected AddressEntity(Parcel in) {
        this.id = in.readString();
        this.address = in.readString();
        this.isDefault = in.readInt();
        this.userName = in.readString();
        this.mobile = in.readString();
        this.city = in.readString();
        this.zipcode = in.readString();
    }

    public static final Creator<AddressEntity> CREATOR = new Creator<AddressEntity>() {
        @Override
        public AddressEntity createFromParcel(Parcel source) {
            return new AddressEntity(source);
        }

        @Override
        public AddressEntity[] newArray(int size) {
            return new AddressEntity[size];
        }
    };
}
