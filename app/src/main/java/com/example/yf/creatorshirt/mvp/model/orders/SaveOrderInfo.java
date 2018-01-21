package com.example.yf.creatorshirt.mvp.model.orders;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

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
 * {"baseId":"baseball",
 * "picture1":true,
 * "picture2":true,
 * "text":true,
 * "partner":"18958064659",
 * "detailList":[{"size":"s","count":30},{"size":"m","count":10},{"size":"xl","count":12}]}
 */
public class SaveOrderInfo implements Parcelable {

    private String baseId;
    private String picture1;
    private String picture2;//自定义的图片
    private String text1;
    private String text2;
    private String finishAimage;
    private String finishBimage;
//    private double orderPrice;
    private String partner;
    private String discount;
    private String address;
    private String zipcode;
    private String mobile;
    private String paymode;
    private String payorderid;
    private String color;
    private String maskAName;//遮罩名字
    private String maskBName;
    private ArrayList<ClothesSize> detailList;


    public SaveOrderInfo() {
    }

    protected SaveOrderInfo(Parcel in) {
        baseId = in.readString();
        picture1 = in.readString();
        picture2 = in.readString();
        text1 = in.readString();
        text2 = in.readString();
        finishAimage = in.readString();
        finishBimage = in.readString();
//        orderPrice = in.readDouble();
        partner = in.readString();
        discount = in.readString();
        address = in.readString();
        zipcode = in.readString();
        mobile = in.readString();
        paymode = in.readString();
        payorderid = in.readString();
        color = in.readString();
        maskAName = in.readString();
        maskBName = in.readString();
        detailList = in.createTypedArrayList(ClothesSize.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(baseId);
        dest.writeString(picture1);
        dest.writeString(picture2);
        dest.writeString(text1);
        dest.writeString(text2);
        dest.writeString(finishAimage);
        dest.writeString(finishBimage);
//        dest.writeDouble(orderPrice);
        dest.writeString(partner);
        dest.writeString(discount);
        dest.writeString(address);
        dest.writeString(zipcode);
        dest.writeString(mobile);
        dest.writeString(paymode);
        dest.writeString(payorderid);
        dest.writeString(color);
        dest.writeString(maskAName);
        dest.writeString(maskBName);
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

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
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

//    public double getOrderPrice() {
//        return orderPrice;
//    }
//
//    public void setOrderPrice(double orderPrice) {
//        this.orderPrice = orderPrice;
//    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
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

    public String getPaymode() {
        return paymode;
    }

    public void setPaymode(String paymode) {
        this.paymode = paymode;
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

    public ArrayList<ClothesSize> getDetailList() {
        return detailList;
    }

    public void setDetailList(ArrayList<ClothesSize> detailList) {
        this.detailList = detailList;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "SaveOrderInfo{" +
                "baseId='" + baseId + '\'' +
                ", picture1='" + picture1 + '\'' +
                ", picture2='" + picture2 + '\'' +
                ", text1='" + text1 + '\'' +
                ", text2='" + text2 + '\'' +
                ", finishAimage='" + finishAimage + '\'' +
                ", finishBimage='" + finishBimage + '\'' +
//                ", orderPrice=" + orderPrice +
                ", partner='" + partner + '\'' +
                ", discount='" + discount + '\'' +
                ", address='" + address + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", mobile='" + mobile + '\'' +
                ", paymode='" + paymode + '\'' +
                ", payorderid='" + payorderid + '\'' +
                ", color='" + color + '\'' +
                ", maskAName='" + maskAName + '\'' +
                ", maskBName='" + maskBName + '\'' +
                ", detailList=" + detailList +
                '}';
    }
}
