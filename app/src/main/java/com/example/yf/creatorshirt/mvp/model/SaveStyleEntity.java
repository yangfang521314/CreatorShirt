package com.example.yf.creatorshirt.mvp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yangfang on 2017/8/31.
 * * {"gender":"",
 * "baseId":"",
 * "styleContext":"",
 * "height":170,
 * "color":"#FFFFFF",
 * "orderType":"Check/Share",
 * "size":"",
 * "address":"",
 * "zipCode":"",
 * "finishImage":""}
 */
public class SaveStyleEntity implements Parcelable {

    private String gender;
    private String baseId;
    private String styleContext;
    private int height;
    private String color;
    private String orderType;
    private int size;
    private String address;
    private String zipCode;
    private String finishImage;
    private int userId;

    public static final Creator<SaveStyleEntity> CREATOR = new Creator<SaveStyleEntity>() {
        @Override
        public SaveStyleEntity createFromParcel(Parcel in) {
            return new SaveStyleEntity(in);
        }

        @Override
        public SaveStyleEntity[] newArray(int size) {
            return new SaveStyleEntity[size];
        }
    };

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getBaseId() {
        return baseId;
    }

    public void setBaseId(String  baseId) {
        this.baseId = baseId;
    }

    public String getStyleContext() {
        return styleContext;
    }

    public void setStyleContext(String styleContext) {
        this.styleContext = styleContext;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getFinishImage() {
        return finishImage;
    }

    public void setFinishImage(String finishImage) {
        this.finishImage = finishImage;
    }

    public SaveStyleEntity() {
    }

    public SaveStyleEntity(Parcel in) {
        this.gender = in.readString();
        this.baseId = in.readString();
        this.styleContext = in.readString();
        this.height = in.readInt();
        this.color = in.readString();
        this.orderType = in.readString();
        this.size = in.readInt();
        this.address = in.readString();
        this.zipCode = in.readString();
        this.finishImage = in.readString();
        this.userId = in.readInt();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(gender);
        dest.writeString(baseId);
        dest.writeString(styleContext);
        dest.writeInt(height);
        dest.writeString(color);
        dest.writeString(orderType);
        dest.writeInt(size);
        dest.writeString(address);
        dest.writeString(zipCode);
        dest.writeString(finishImage);
        dest.writeInt(userId);
    }

    @Override
    public String toString() {
        return "SaveStyleEntity{" +
                "gender='" + gender + '\'' +
                ", baseId=" + baseId +
                ", styleContext='" + styleContext + '\'' +
                ", height=" + height +
                ", color='" + color + '\'' +
                ", orderType='" + orderType + '\'' +
                ", size=" + size +
                ", address='" + address + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", finishImage='" + finishImage + '\'' +
                '}';
    }
}
