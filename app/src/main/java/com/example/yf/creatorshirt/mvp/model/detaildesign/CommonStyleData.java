package com.example.yf.creatorshirt.mvp.model.detaildesign;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yangfang on 2017/8/26.
 */

public class CommonStyleData implements Parcelable {
    private static final long serialVersionUID = 2L;
    private String neckUrl;
    private String armUrl;
    private String ornametUrl;
    private int color;
    private String pattern;


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

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.neckUrl);
        dest.writeString(this.armUrl);
        dest.writeString(this.ornametUrl);
        dest.writeInt(this.color);
        dest.writeString(this.pattern);
    }

    protected CommonStyleData(Parcel in) {
        this.neckUrl = in.readString();
        this.armUrl = in.readString();
        this.ornametUrl = in.readString();
        this.color = in.readInt();
        this.pattern = in.readString();
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
