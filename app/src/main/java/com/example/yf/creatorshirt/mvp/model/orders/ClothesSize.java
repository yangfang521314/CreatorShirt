package com.example.yf.creatorshirt.mvp.model.orders;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by yangfang on 2017/9/1.
 * [{"count":"1","sex":"1","size":"M","value":"165~175cm"}]
 */

public class ClothesSize implements Parcelable, Serializable {

    private static final long serialVersionUID = 1L;
    private String size;//尺寸字母
    private String value;//具体尺寸数量
    private int count;//数量
    private int sex;//性别英文or数字代表
    private int orderid;
    private int detailId;
    private String weight;

    public ClothesSize() {
    }

    protected ClothesSize(Parcel in) {
        size = in.readString();
        value = in.readString();
        count = in.readInt();
        sex = in.readInt();
        orderid = in.readInt();
        detailId = in.readInt();
        weight = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(size);
        dest.writeString(value);
        dest.writeInt(count);
        dest.writeInt(sex);
        dest.writeInt(orderid);
        dest.writeInt(detailId);
        dest.writeString(weight);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ClothesSize> CREATOR = new Creator<ClothesSize>() {
        @Override
        public ClothesSize createFromParcel(Parcel in) {
            return new ClothesSize(in);
        }

        @Override
        public ClothesSize[] newArray(int size) {
            return new ClothesSize[size];
        }
    };

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public int getDetailId() {
        return detailId;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }
}
