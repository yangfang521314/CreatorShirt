package com.example.yf.creatorshirt.mvp.presenter;

/**
 * Created by panguso on 2017/9/5.
 * {appId=wx012a9130903a7396,
 * timeStamp=1504580305,
 * nonceStr=j4ho3c78dgf8vct,
 * signType=MD5,
 * mch_id=1459195002,
 * prepay_id=wx201709051058252c527540020821168461,
 * paySign=8DB8DD644EDDED02F3F87E291E6E639F,
 * out_trade_no=96765dd1569a4b28ad9e27bb1048529c,
 * code_url=weixin://wxpay/bizpayurl?pr=IT7dKQn}
 */

public class PayOrderEntity {
    private String appId;
    private String timeStamp;
    private String nonceStr;
    private String signType;
    private String mch_id;
    private String prepay_id;
    private String paySign;
    private String out_trade_no;
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

    public String getCode_url() {
        return code_url;
    }

    public void setCode_url(String code_url) {
        this.code_url = code_url;
    }

    @Override
    public String toString() {
        return "PayOrderEntity{" +
                "appId='" + appId + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                ", nonceStr='" + nonceStr + '\'' +
                ", signType='" + signType + '\'' +
                ", mch_id='" + mch_id + '\'' +
                ", prepay_id='" + prepay_id + '\'' +
                ", paySign='" + paySign + '\'' +
                ", out_trade_no='" + out_trade_no + '\'' +
                ", code_url='" + code_url + '\'' +
                '}';
    }
}
