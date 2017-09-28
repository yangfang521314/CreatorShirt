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
    private String text;

    protected CommonStyleData(Parcel in) {
        neckUrl = in.readString();
        armUrl = in.readString();
        ornametUrl = in.readString();
        color = in.readString();
        pattern = in.readString();
        text = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(neckUrl);
        dest.writeString(armUrl);
        dest.writeString(ornametUrl);
        dest.writeString(color);
        dest.writeString(pattern);
        dest.writeString(text);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CommonStyleData> CREATOR = new Creator<CommonStyleData>() {
        @Override
        public CommonStyleData createFromParcel(Parcel in) {
            return new CommonStyleData(in);
        }

        @Override
        public CommonStyleData[] newArray(int size) {
            return new CommonStyleData[size];
        }
    };

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
                '}';
    }

    public CommonStyleData() {
    }

}
