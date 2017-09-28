package com.example.yf.creatorshirt.mvp.model.orders;

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
    private String allImage;
    private String texture;

    public SaveStyleEntity() {
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBaseId() {
        return baseId;
    }

    public void setBaseId(String baseId) {
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAllImage() {
        return allImage;
    }

    public void setAllImage(String allImage) {
        this.allImage = allImage;
    }

    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

    protected SaveStyleEntity(Parcel in) {
        gender = in.readString();
        baseId = in.readString();
        styleContext = in.readString();
        height = in.readInt();
        color = in.readString();
        orderType = in.readString();
        size = in.readInt();
        address = in.readString();
        zipCode = in.readString();
        finishImage = in.readString();
        userId = in.readInt();
        allImage = in.readString();
        texture = in.readString();
    }

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
        dest.writeString(allImage);
        dest.writeString(texture);
    }
}
