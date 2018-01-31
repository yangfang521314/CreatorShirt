package com.example.yf.creatorshirt.mvp.model;

/**
 * Created by yangfang on 2018/1/29.
 * out_trade_no=603df6ea016f421f93fc2bf243301dc5,
 * inside_no=258,
 * fee=0.01,
 * notify_url=https://style.man-kang.com:3600/api/AliPays/alnotify}
 */

public class PayTradeInfo {
    private String out_trade_no;
    private double fee;
    private String notify_url;

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    @Override
    public String toString() {
        return "PayTradeInfo{" +
                "out_trade_no='" + out_trade_no + '\'' +
                ", fee=" + fee +
                ", notify_url='" + notify_url + '\'' +
                '}';
    }
}
