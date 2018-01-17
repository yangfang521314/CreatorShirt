package com.example.yf.creatorshirt.mvp.model.orders;

import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;

/**
 * Created by yangfang on 2018/1/13.
 */

public class Size implements Parcelable {
    /**
     * id。
     */
    @JSONField(name = "name")
    private String name;

    /**
     * 名称。
     */
    @JSONField(name = "type")
    private String type;

    @JSONField(name = "size")
    private String size;

    @JSONField(name = "value")
    private String value;

    private ArrayList<Size> mClothesSize;


    protected Size(Parcel in) {
        name = in.readString();
        type = in.readString();
        size = in.readString();
        value = in.readString();
        mClothesSize = in.createTypedArrayList(Size.CREATOR);
    }

    public static final Creator<Size> CREATOR = new Creator<Size>() {
        @Override
        public Size createFromParcel(Parcel in) {
            return new Size(in);
        }

        @Override
        public Size[] newArray(int size) {
            return new Size[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(type);
        dest.writeString(size);
        dest.writeString(value);
        dest.writeTypedList(mClothesSize);
    }

    @Override
    public String toString() {
        return "Size{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", size='" + size + '\'' +
                ", value='" + value + '\'' +
                ", mClothesSize=" + mClothesSize +
                '}';
    }
}
