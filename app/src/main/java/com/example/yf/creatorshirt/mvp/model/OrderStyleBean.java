package com.example.yf.creatorshirt.mvp.model;

/**
 * Created by yangfang on 2017/8/29.
 */

/**
 * {id=95.0,
 * userId=1143,
 * Gender=A,
 * baseId=02,
 * Context=eyJBIjp7ImFybSI6IkEwMkIwMjAwMDIucG5nIiwibmVjayI6IkEwMkIwMTAwMDEucG5nIiwiY29sb3IiOiIjZmZmZjMzIn0sIkIiOnsiYXJtIjoiQTAyQTAyMDAwMi5wbmcifX0=,
 * addDate=2017-09-01T10:02:21.000Z,
 * baseName=短款直筒裤,
 * title=null设计的短款直筒裤(2017-09-01),
 * praise=0.0,
 * height=170,
 * color=FFFFFF,
 * orderType=Check,
 * size=170,
 * address=,
 * zipcode=,
 * finishimage=http://oub3nsjgh.bkt.clouddn.com/1143_20170901_100218,
 * fee=100.0,
 * styleContext={"A":{"arm":"A02B020002.png","neck":"A02B010001.png","color":"#ffff33"},
 * "B":{"arm":"A02A020002.png"}},
 * UserName=null,
 * headerImage=null}
 */
public class OrderStyleBean {
    private int id;
    private String userId;
    private String Gender;
    private String baseId;
    private String Context;
    private String addDate;
    private String baseName;
    private String title;
    private int praise;
    private String height;
    private String color;
    private String orderType;
    private String size;
    private String address;
    private String zipcode;
    private String finishimage;
    private double fee;
    private String styleContext;
    private String UserName;
    private String headerImage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getBaseId() {
        return baseId;
    }

    public void setBaseId(String baseId) {
        this.baseId = baseId;
    }

    public String getContext() {
        return Context;
    }

    public void setContext(String context) {
        Context = context;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    public String getBaseName() {
        return baseName;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPraise() {
        return praise;
    }

    public void setPraise(int praise) {
        this.praise = praise;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
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

    public String getFinishimage() {
        return finishimage;
    }

    public void setFinishimage(String finishimage) {
        this.finishimage = finishimage;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public String getStyleContext() {
        return styleContext;
    }

    public void setStyleContext(String styleContext) {
        this.styleContext = styleContext;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getHeaderImage() {
        return headerImage;
    }

    public void setHeaderImage(String headerImage) {
        this.headerImage = headerImage;
    }

    @Override
    public String toString() {
        return "OrderStyleBean{" +
                "id=" + id +
                ", userId=" + userId +
                ", Gender='" + Gender + '\'' +
                ", baseId='" + baseId + '\'' +
                ", Context='" + Context + '\'' +
                ", baseName='" + baseName + '\'' +
                ", title='" + title + '\'' +
                ", praise=" + praise +
                ", height=" + height +
                ", color='" + color + '\'' +
                ", orderType='" + orderType + '\'' +
                ", size=" + size +
                ", address='" + address + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", finishimage='" + finishimage + '\'' +
                ", fee=" + fee +
                ", styleContext=" + styleContext +
                ", UserName='" + UserName + '\'' +
                ", headerImage='" + headerImage + '\'' +
                '}';
    }
}
