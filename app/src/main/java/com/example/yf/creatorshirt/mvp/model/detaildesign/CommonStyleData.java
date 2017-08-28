package com.example.yf.creatorshirt.mvp.model.detaildesign;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yangfang on 2017/8/26.
 */

public class CommonStyleData implements Parcelable {
    private String neckUrl;
    private String armUrl;
    private String ornametUrl;
    private String color;
    private String pattern;
    private String backUrl;
    private String frontUrl;
    private String gender;
    private String type;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getNeckUrl() {
        return neckUrl;
    }

    public void setNeckUrl(String neckUrl) {
        this.neckUrl = neckUrl;
    }

    public String getArmUrl() {
        return armUrl;
    }

    public void setArmUrl(String armUrl) {
        this.armUrl = armUrl;
    }

    public String getOrnametUrl() {
        return ornametUrl;
    }

    public void setOrnametUrl(String ornametUrl) {
        this.ornametUrl = ornametUrl;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String toString() {
        return "CommonStyleData{" +
                "neckUrl='" + neckUrl + '\'' +
                ", armUrl='" + armUrl + '\'' +
                ", ornametUrl='" + ornametUrl + '\'' +
                ", color=" + color +
                ", pattern='" + pattern + '\'' +
                ", backUrl='" + backUrl + '\'' +
                ", frontUrl='" + frontUrl + '\'' +
                ", gender='" + gender + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public CommonStyleData() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.neckUrl);
        dest.writeString(this.armUrl);
        dest.writeString(this.ornametUrl);
        dest.writeString(this.color);
        dest.writeString(this.pattern);
        dest.writeString(this.backUrl);
        dest.writeString(this.frontUrl);
        dest.writeString(this.gender);
        dest.writeString(this.type);
    }

    protected CommonStyleData(Parcel in) {
        this.neckUrl = in.readString();
        this.armUrl = in.readString();
        this.ornametUrl = in.readString();
        this.color = in.readString();
        this.pattern = in.readString();
        this.backUrl = in.readString();
        this.frontUrl = in.readString();
        this.gender = in.readString();
        this.type = in.readString();

    }

    public static final Creator<CommonStyleData> CREATOR = new Creator<CommonStyleData>() {
        @Override
        public CommonStyleData createFromParcel(Parcel source) {
            return new CommonStyleData(source);
        }

        @Override
        public CommonStyleData[] newArray(int size) {
            return new CommonStyleData[size];
        }
    };
}
