package com.example.yf.creatorshirt.mvp.model;

/**
 * Created by yang on 31/07/2017.
 *
 *
 */

public class HotDesignsBean {

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
}
