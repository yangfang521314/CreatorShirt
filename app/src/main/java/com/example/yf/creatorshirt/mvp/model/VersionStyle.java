package com.example.yf.creatorshirt.mvp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yangfang on 2017/11/29.
 */

public class VersionStyle extends BaseChoiceEntity implements Parcelable {
    protected String type;//衣服总类型
    protected String color;//颜色值
    protected String colorName;//颜色name
    protected String gender;//衣服类型中文(男、女）
    protected String sex;//性别
    protected String clothesType;//衣服中文
    private String backUrl;//正面
    private String frontUrl;//背面
    private String picture1;//自定义的图片
    private String picture2;
    private String maskA;
    private String maskB;


    public VersionStyle() {
    }


    protected VersionStyle(Parcel in) {
        type = in.readString();
        color = in.readString();
        colorName = in.readString();
        gender = in.readString();
        sex = in.readString();
        clothesType = in.readString();
        backUrl = in.readString();
        frontUrl = in.readString();
        picture1 = in.readString();
        picture2 = in.readString();
        maskA = in.readString();
        maskB = in.readString();
    }

    public static final Creator<VersionStyle> CREATOR = new Creator<VersionStyle>() {
        @Override
        public VersionStyle createFromParcel(Parcel in) {
            return new VersionStyle(in);
        }

        @Override
        public VersionStyle[] newArray(int size) {
            return new VersionStyle[size];
        }
    };

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getClothesType() {
        return clothesType;
    }

    public void setClothesType(String clothesType) {
        this.clothesType = clothesType;
    }

    public String getBackUrl() {
        return backUrl;
    }

    public void setBackUrl(String backUrl) {
        this.backUrl = backUrl;
    }

    public String getFrontUrl() {
        return frontUrl;
    }

    public void setFrontUrl(String frontUrl) {
        this.frontUrl = frontUrl;
    }

    public String getMaskA() {
        return maskA;
    }

    public void setMaskA(String maskA) {
        this.maskA = maskA;
    }

    public String getMaskB() {
        return maskB;
    }

    public void setMaskB(String maskB) {
        this.maskB = maskB;
    }

    public String getPicture1() {
        return picture1;
    }

    public void setPicture1(String picture1) {
        this.picture1 = picture1;
    }

    public String getPicture2() {
        return picture2;
    }

    public void setPicture2(String picture2) {
        this.picture2 = picture2;
    }

    @Override
    public String toString() {
        return "VersionStyle{" +
                "type='" + type + '\'' +
                ", color='" + color + '\'' +
                ", colorName='" + colorName + '\'' +
                '}';
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeString(color);
        dest.writeString(colorName);
        dest.writeString(gender);
        dest.writeString(sex);
        dest.writeString(clothesType);
        dest.writeString(backUrl);
        dest.writeString(frontUrl);
        dest.writeString(picture1);
        dest.writeString(picture2);
        dest.writeString(maskA);
        dest.writeString(maskB);
    }
}
