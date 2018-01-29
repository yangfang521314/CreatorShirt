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
 * <p>
 * {"baseId":"baseball",
 * "picture1":"picture1",
 * "picture2":"picture2",
 * "text":"text",
 * "backText":"text",
 * "finishAimage":"allimage1",
 * "finishBimage":"allimage2",
 * "orderPrice":100,
 * "partner":"18958064659",
 * "discount":"654321",
 * "payorderid":"",
 * "color":"red",
 * "maskAName":"",
 * "maskBName":"",
 * "detailList":
 * [{"size":"s","count":30,"sex":0},
 * {"size":"m","count":10,"sex":0},
 * {"size":"xl","count":12,"sex":0}]}
 * <p>
 * {"baseId":"baseball",
 * "picture1":true,
 * "picture2":true,
 * "text":true,
 * "discount":"654321",
 * "detailList":[{"size":"s","count":30},
 * {"size":"m","count":10},
 * {"size":"xl","count":12}]}
 */
public class SaveOrderInfo implements Parcelable {

    private String baseId;
    private String picture1;
    private String picture2;//自定义的图片
    private String text;
    private String backText;
    private String finishAimage;
    private String finishBimage;
    private String partner;
    private String discount;
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
        detailList = in.createTypedArrayList(ClothesSize.CREATOR);
    }

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

    public ArrayList<ClothesSize> getDetailList() {
        return detailList;
    }

    public void setDetailList(ArrayList<ClothesSize> detailList) {
        this.detailList = detailList;
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
                ", detailList=" + detailList +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
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
        dest.writeTypedList(detailList);
    }
}
