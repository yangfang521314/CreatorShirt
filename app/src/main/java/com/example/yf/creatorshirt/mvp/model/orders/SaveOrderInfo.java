package com.example.yf.creatorshirt.mvp.model.orders;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by yangfang on 2017/8/31.
 * {"baseId":"baseball",
 * "picture1":"picture1",
 * "picture2":"picture2",
 * "text":"text",
 * "finishAimage":"allimage1",
 * "finishBimage":"allimage2",
 * "orderPrice":100,
 * "partner":"18958064659",
 * "address":"address",
 * "zipcode":"",
 * "mobile":"19905711354",
 * "paymode":"aliPay",
 * "payorderid":"",
 * "color":"red",
 * "maskAName":"
 * ","maskBName":"",
 * "detailList":[{"size":"s","count":30,"sex":0},{"size":"m","count":10,"sex":0},{"size":"xl","count":12,"sex":0}]}
 * <p>
 * <p>
 * <p>
 * <p>
 * {
 **/
public class SaveOrderInfo implements Parcelable  {

    protected String baseId;
    protected String picture1;
    protected String picture2;//自定义的图片
    protected String text;
    protected String backText;
    protected String finishAimage;
    protected String finishBimage;
    protected String partner;
    protected String discount;
    protected String payorderid;
    protected String color;
    protected String maskAName;//遮罩名字
    protected String maskBName;
    protected double orderPrice;
    protected int orderId;

    private String allimage1;
    private String adddate;
    private String address;
    private String zipcode;
    private String mobile;//遮罩名字
    private String status;
    private String allimage2;
    private String username;
    private String expressName;
    private String expressId;
    private String submit;

    protected List<ClothesSize> detailList;


    public SaveOrderInfo() {
    }

    protected SaveOrderInfo(Parcel in) {
        baseId = in.readString();
        picture1 = in.readString();
        picture2 = in.readString();
        text = in.readString();
        backText = in.readString();
        finishAimage = in.readString();
        finishBimage = in.readString();
        partner = in.readString();
        discount = in.readString();
        payorderid = in.readString();
        color = in.readString();
        maskAName = in.readString();
        maskBName = in.readString();
        orderPrice = in.readDouble();
        orderId = in.readInt();
        allimage1 = in.readString();
        adddate = in.readString();
        address = in.readString();
        zipcode = in.readString();
        mobile = in.readString();
        status = in.readString();
        allimage2 = in.readString();
        username = in.readString();
        expressName = in.readString();
        expressId = in.readString();
        submit = in.readString();
        detailList = in.createTypedArrayList(ClothesSize.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(baseId);
        dest.writeString(picture1);
        dest.writeString(picture2);
        dest.writeString(text);
        dest.writeString(backText);
        dest.writeString(finishAimage);
        dest.writeString(finishBimage);
        dest.writeString(partner);
        dest.writeString(discount);
        dest.writeString(payorderid);
        dest.writeString(color);
        dest.writeString(maskAName);
        dest.writeString(maskBName);
        dest.writeDouble(orderPrice);
        dest.writeInt(orderId);
        dest.writeString(allimage1);
        dest.writeString(adddate);
        dest.writeString(address);
        dest.writeString(zipcode);
        dest.writeString(mobile);
        dest.writeString(status);
        dest.writeString(allimage2);
        dest.writeString(username);
        dest.writeString(expressName);
        dest.writeString(expressId);
        dest.writeString(submit);
        dest.writeTypedList(detailList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SaveOrderInfo> CREATOR = new Creator<SaveOrderInfo>() {
        @Override
        public SaveOrderInfo createFromParcel(Parcel in) {
            return new SaveOrderInfo(in);
        }

        @Override
        public SaveOrderInfo[] newArray(int size) {
            return new SaveOrderInfo[size];
        }
    };

    public String getBaseId() {
        return baseId;
    }

    public void setBaseId(String baseId) {
        this.baseId = baseId;
    }

    public String getPicture1() {
        return picture1;
    }

    public void setPicture1(String picture1) {
        this.picture1 = picture1;
    }

    public String getPicture2() {
        return picture2;
    }

    public void setPicture2(String picture2) {
        this.picture2 = picture2;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getBackText() {
        return backText;
    }

    public void setBackText(String backText) {
        this.backText = backText;
    }

    public String getFinishAimage() {
        return finishAimage;
    }

    public void setFinishAimage(String finishAimage) {
        this.finishAimage = finishAimage;
    }

    public String getFinishBimage() {
        return finishBimage;
    }

    public void setFinishBimage(String finishBimage) {
        this.finishBimage = finishBimage;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getPayorderid() {
        return payorderid;
    }

    public void setPayorderid(String payorderid) {
        this.payorderid = payorderid;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMaskAName() {
        return maskAName;
    }

    public void setMaskAName(String maskAName) {
        this.maskAName = maskAName;
    }

    public String getMaskBName() {
        return maskBName;
    }

    public void setMaskBName(String maskBName) {
        this.maskBName = maskBName;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getAllimage1() {
        return allimage1;
    }

    public void setAllimage1(String allimage1) {
        this.allimage1 = allimage1;
    }

    public String getAdddate() {
        return adddate;
    }

    public void setAdddate(String adddate) {
        this.adddate = adddate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAllimage2() {
        return allimage2;
    }

    public void setAllimage2(String allimage2) {
        this.allimage2 = allimage2;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getExpressName() {
        return expressName;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    public String getExpressId() {
        return expressId;
    }

    public void setExpressId(String expressId) {
        this.expressId = expressId;
    }

    public String getSubmit() {
        return submit;
    }

    public void setSubmit(String submit) {
        this.submit = submit;
    }

    public List<ClothesSize> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<ClothesSize> detailList) {
        this.detailList = detailList;
    }

    @Override
    public String toString() {
        return "SaveOrderInfo{" +
                "baseId='" + baseId + '\'' +
                ", picture1='" + picture1 + '\'' +
                ", picture2='" + picture2 + '\'' +
                ", text='" + text + '\'' +
                ", backText='" + backText + '\'' +
                ", finishAimage='" + finishAimage + '\'' +
                ", finishBimage='" + finishBimage + '\'' +
                ", partner='" + partner + '\'' +
                ", discount='" + discount + '\'' +
                ", payorderid='" + payorderid + '\'' +
                ", color='" + color + '\'' +
                ", maskAName='" + maskAName + '\'' +
                ", maskBName='" + maskBName + '\'' +
                ", orderPrice=" + orderPrice +
                ", orderId=" + orderId +
                ", allimage1='" + allimage1 + '\'' +
                ", adddate='" + adddate + '\'' +
                ", address='" + address + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", mobile='" + mobile + '\'' +
                ", status='" + status + '\'' +
                ", allimage2='" + allimage2 + '\'' +
                ", username='" + username + '\'' +
                ", expressName='" + expressName + '\'' +
                ", expressId='" + expressId + '\'' +
                ", submit='" + submit + '\'' +
                ", detailList=" + detailList +
                '}';
    }
}
