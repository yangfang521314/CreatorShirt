package com.example.yf.creatorshirt.mvp.model.orders;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by yangfang on 2017/9/1.
 */

public class ClothesSize implements Parcelable,Serializable {

    private static final long serialVersionUID = 1L;
    private String size;//尺寸
    private String letter;//性别中文
    private int count;//数量
    private String sex;//性别英文or数字代表

    protected ClothesSize(Parcel in) {
        size = in.readString();
        letter = in.readString();
        count = in.readInt();
        sex = in.readString();
    }

    public ClothesSize() {
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "ClothesSize{" +
                "size='" + size + '\'' +
                ", letter='" + letter + '\'' +
                ", count=" + count +
                ", sex='" + sex + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(size);
        dest.writeString(letter);
        dest.writeInt(count);
        dest.writeString(sex);
    }
}
