package com.example.yf.creatorshirt.mvp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yang n 31/07/2017.
 *
 *
 */

public class HotDesignsBean implements Parcelable{

    /**
     "userid": 1111,
     "mobile": "15868177543",
     "name": "  1",
     "headimage": "http://oub3nsjgh.bkt.clouddn.com/pattern_4.png", "counts": 22 //
     "counts": 22 //
     */

    private String headimage;
    private String mobile;
    private String name;
    private int userid;
    private int counts;

    protected HotDesignsBean(Parcel in) {
        headimage = in.readString();
        mobile = in.readString();
        name = in.readString();
        userid = in.readInt();
        counts = in.readInt();
    }

    public HotDesignsBean() {
    }

    public static final Creator<HotDesignsBean> CREATOR = new Creator<HotDesignsBean>() {
        @Override
        public HotDesignsBean createFromParcel(Parcel in) {
            return new HotDesignsBean(in);
        }

        @Override
        public HotDesignsBean[] newArray(int size) {
            return new HotDesignsBean[size];
        }
    };

    public String getHeadimage() {
        return headimage;
    }

    public void setHeadimage(String headimage) {
        this.headimage = headimage;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }

    @Override
    public String toString() {
        return "HotDesignsBean{" +
                "headimage='" + headimage + '\'' +
                ", mobile='" + mobile + '\'' +
                ", name='" + name + '\'' +
                ", userid=" + userid +
                ", counts=" + counts +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(headimage);
        dest.writeString(mobile);
        dest.writeString(name);
        dest.writeInt(userid);
        dest.writeInt(counts);
    }
}
