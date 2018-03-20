package com.example.yf.creatorshirt.mvp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by yangfang on 2018/1/30.
 * "
 * {orderId=750.0,
 * partner=15868178345,
 * baseId=cshort,
 * picture1=http://oub3nsjgh.bkt.clouddn.com/1143_20180320_1546500,
 * picture2=http://oub3nsjgh.bkt.clouddn.com/1143_20180320_1546501,
 * text=,
 * allimage1=http://oub3nsjgh.bkt.clouddn.com/1143_20180320_154650A,
 * orderPrice=0.0,
 * adddate=2018-03-20T15:46:52.000Z,
 * address=null,
 * zipcode=null,
 * mobile=null,
 * paymode=,
 * status=new,
 * payorderid=,
 * color=000000,
 * allimage2=http://oub3nsjgh.bkt.clouddn.com/1143_20180320_154650B,
 * username=null,
 * maskAName=,
 * maskBName=,
 * discount=,
 * backText=[hello],
 * expressName=,
 * expressId=,
 * submit=0.0,
 * finishAimage=http://oub3nsjgh.bkt.clouddn.com/1143_20180320_154650A,
 * finishBimage=http://oub3nsjgh.bkt.clouddn.com/1143_20180320_154650B,
 * }
 */

public class MyOrderInfo implements Parcelable {
    private int orderId;
    private String partner;
    private String baseId;//自定义的图片
    private String picture1;
    private String picture2;
    private String text;
    private String allimage1;
    private double orderPrice;
    private String adddate;
    private String address;
    private String zipcode;
    private String mobile;//遮罩名字
    private String status;
    private String payorderid;
    private String color;
    private String allimage2;
    private String username;
    private String maskAName;
    private String maskBName;
    private String discount;
    private String backText;
    private String expressName;
    private String expressId;
    private String submit;
    private String finishAimage;
    private String finishBimage;
    private ArrayList<ClotheRetureSize> detailList;

    protected MyOrderInfo(Parcel in) {
        orderId = in.readInt();
        partner = in.readString();
        baseId = in.readString();
        picture1 = in.readString();
        picture2 = in.readString();
        text = in.readString();
        allimage1 = in.readString();
        orderPrice = in.readDouble();
        adddate = in.readString();
        address = in.readString();
        zipcode = in.readString();
        mobile = in.readString();
        status = in.readString();
        payorderid = in.readString();
        color = in.readString();
        allimage2 = in.readString();
        username = in.readString();
        maskAName = in.readString();
        maskBName = in.readString();
        discount = in.readString();
        backText = in.readString();
        expressName = in.readString();
        expressId = in.readString();
        submit = in.readString();
        finishAimage = in.readString();
        finishBimage = in.readString();
        detailList = in.createTypedArrayList(ClotheRetureSize.CREATOR);
    }

    public static final Creator<MyOrderInfo> CREATOR = new Creator<MyOrderInfo>() {
        @Override
        public MyOrderInfo createFromParcel(Parcel in) {
            return new MyOrderInfo(in);
        }

        @Override
        public MyOrderInfo[] newArray(int size) {
            return new MyOrderInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(orderId);
        dest.writeString(partner);
        dest.writeString(baseId);
        dest.writeString(picture1);
        dest.writeString(picture2);
        dest.writeString(text);
        dest.writeString(allimage1);
        dest.writeDouble(orderPrice);
        dest.writeString(adddate);
        dest.writeString(address);
        dest.writeString(zipcode);
        dest.writeString(mobile);
        dest.writeString(status);
        dest.writeString(payorderid);
        dest.writeString(color);
        dest.writeString(allimage2);
        dest.writeString(username);
        dest.writeString(maskAName);
        dest.writeString(maskBName);
        dest.writeString(discount);
        dest.writeString(backText);
        dest.writeString(expressName);
        dest.writeString(expressId);
        dest.writeString(submit);
        dest.writeString(finishAimage);
        dest.writeString(finishBimage);
        dest.writeTypedList(detailList);
    }


    /**
     * "detailId": 1,
     * "size": "M",
     * "count": 15,
     * "sex": 1,
     * "value": "165~175cm"
     */
    public static class ClotheRetureSize implements Parcelable {
        private int orderid;
        private int detailId;
        private String size;
        private int count;
        private int sex;
        private String value;

        public int getOrderid() {
            return orderid;
        }

        public void setOrderid(int orderid) {
            this.orderid = orderid;
        }

        public int getDetailId() {
            return detailId;
        }

        public void setDetailId(int detailId) {
            this.detailId = detailId;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        protected ClotheRetureSize(Parcel in) {
            orderid = in.readInt();
            detailId = in.readInt();
            size = in.readString();
            count = in.readInt();
            sex = in.readInt();
            value = in.readString();
        }

        public static final Creator<ClotheRetureSize> CREATOR = new Creator<ClotheRetureSize>() {
            @Override
            public ClotheRetureSize createFromParcel(Parcel in) {
                return new ClotheRetureSize(in);
            }

            @Override
            public ClotheRetureSize[] newArray(int size) {
                return new ClotheRetureSize[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(orderid);
            dest.writeInt(detailId);
            dest.writeString(size);
            dest.writeInt(count);
            dest.writeInt(sex);
            dest.writeString(value);
        }

        @Override
        public String toString() {
            return "ClotheRetureSize{" +
                    "orderid=" + orderid +
                    ", detailId=" + detailId +
                    ", size='" + size + '\'' +
                    ", count=" + count +
                    ", sex=" + sex +
                    ", value='" + value + '\'' +
                    '}';
        }
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
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

    public String getAllimage1() {
        return allimage1;
    }

    public void setAllimage1(String allimage1) {
        this.allimage1 = allimage1;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
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

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getBackText() {
        return backText;
    }

    public void setBackText(String backText) {
        this.backText = backText;
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

    public ArrayList<ClotheRetureSize> getDetailList() {
        return detailList;
    }

    public void setDetailList(ArrayList<ClotheRetureSize> detailList) {
        this.detailList = detailList;
    }

    @Override
    public String toString() {
        return "MyOrderInfo{" +
                "orderId=" + orderId +
                ", partner='" + partner + '\'' +
                ", baseId='" + baseId + '\'' +
                ", picture1='" + picture1 + '\'' +
                ", picture2='" + picture2 + '\'' +
                ", text='" + text + '\'' +
                ", allimage1='" + allimage1 + '\'' +
                ", orderPrice=" + orderPrice +
                ", adddate='" + adddate + '\'' +
                ", address='" + address + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", mobile='" + mobile + '\'' +
                ", status='" + status + '\'' +
                ", payorderid='" + payorderid + '\'' +
                ", color='" + color + '\'' +
                ", allimage2='" + allimage2 + '\'' +
                ", username='" + username + '\'' +
                ", maskAName='" + maskAName + '\'' +
                ", maskBName='" + maskBName + '\'' +
                ", discount='" + discount + '\'' +
                ", backText='" + backText + '\'' +
                ", expressName='" + expressName + '\'' +
                ", expressId='" + expressId + '\'' +
                ", submit='" + submit + '\'' +
                ", finishAimage='" + finishAimage + '\'' +
                ", finishBimage='" + finishBimage + '\'' +
                ", detailList=" + detailList +
                '}';
    }
}
