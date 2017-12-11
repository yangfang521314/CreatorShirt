package com.example.yf.creatorshirt.mvp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yangfang on 2017/11/29.
 */

public class VersionStyle extends BaseChoiceEntity implements Parcelable {
    private String type;
    private String color;
    private String colorName;
    private String gender;
    private String sex;



    public VersionStyle() {
    }


    protected VersionStyle(Parcel in) {
        type = in.readString();
        color = in.readString();
        colorName = in.readString();
        gender = in.readString();
        sex = in.readString();
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
    }
}
