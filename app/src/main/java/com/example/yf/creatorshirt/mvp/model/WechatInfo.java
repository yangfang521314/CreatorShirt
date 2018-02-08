package com.example.yf.creatorshirt.mvp.model;

/**
 * Created by yangfang on 2018/1/29.

 * appId=wxdb5ce1271ea3e6d6,
 * timeStamp=1517755566,
 * nonceStr=7buperc6t2sgrlp,
 * signType=MD5,
 * mch_id=1497299502,
 * prepay_id=wx201802042246064181b606720938947864,
 * paySign=E5C7EBB2AC34BDF0BCA244C53FDC59F2,
 * out_trade_no=c32b84d856a84a4aae8e6ffe84149bfd,
 * inside_no=321,
 * notify_url=https://style.man-kang.com:3600/api/Wxpays/wxnotify,
 * code_url=weixin://wxpay/bizpayurl?pr=OSJWBYr}
 */

public class WechatInfo {
    private String appId;
    private String timeStamp;
    private String nonceStr;
    private String signType;
    private String mch_id;
    private String prepay_id;
    private String paySign;
    private String out_trade_no;
    private String inside_no;
    private String notify_url;
    private String code_url;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getPrepay_id() {
        return prepay_id;
    }

    public void setPrepay_id(String prepay_id) {
        this.prepay_id = prepay_id;
    }

    public String getPaySign() {
        return paySign;
    }

    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getInside_no() {
        return inside_no;
    }

    public void setInside_no(String inside_no) {
        this.inside_no = inside_no;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getCode_url() {
        return code_url;
    }

    public void setCode_url(String code_url) {
        this.code_url = code_url;
    }

    @Override
    public String toString() {
        return "WechatInfo{" +
                "appId='" + appId + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                ", nonceStr='" + nonceStr + '\'' +
                ", signType='" + signType + '\'' +
                ", mch_id='" + mch_id + '\'' +
                ", prepay_id='" + prepay_id + '\'' +
                ", paySign='" + paySign + '\'' +
                ", out_trade_no='" + out_trade_no + '\'' +
                ", inside_no='" + inside_no + '\'' +
                ", notify_url='" + notify_url + '\'' +
                ", code_url='" + code_url + '\'' +
                '}';
    }
}
