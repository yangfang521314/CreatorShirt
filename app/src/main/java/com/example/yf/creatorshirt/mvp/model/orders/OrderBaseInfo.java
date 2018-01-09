package com.example.yf.creatorshirt.mvp.model.orders;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yangfang on 2017/9/11.
 */

public class OrderBaseInfo implements Parcelable{
    private String backUrl;//正面
    private String frontUrl;//背面
    private String gender;
    private String type;//衣服类型
    private String color;//颜色


    public OrderBaseInfo() {
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "OrderBaseInfo{" +
                "backUrl='" + backUrl + '\'' +
                ", frontUrl='" + frontUrl + '\'' +
                ", gender='" + gender + '\'' +
                ", type='" + type + '\'' +
                ", color='" + color + '\'' +
                '}';
    }

    protected OrderBaseInfo(Parcel in) {
        backUrl = in.readString();
        frontUrl = in.readString();
        gender = in.readString();
        type = in.readString();
        color = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(backUrl);
        dest.writeString(frontUrl);
        dest.writeString(gender);
        dest.writeString(type);
        dest.writeString(color);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderBaseInfo> CREATOR = new Creator<OrderBaseInfo>() {
        @Override
        public OrderBaseInfo createFromParcel(Parcel in) {
            return new OrderBaseInfo(in);
        }

        @Override
        public OrderBaseInfo[] newArray(int size) {
            return new OrderBaseInfo[size];
        }
    };
}
