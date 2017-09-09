package com.example.yf.creatorshirt.mvp.model;

/**
 * Created by yang on 31/07/2017.
 * 爆款数据
 * <p>
 * {id=106.0,
 * userId=1142,
 * Gender=A,
 * baseId=02,
 * Context=ewogICJBIiA6IHsKICAgICJhcm0iIDogIkEwMkEwMjAwMDIucG5nIiwKICAgICJuZWNrIiA6ICJBMDJBMDEwMDAxLnBuZyIsCiAgICAib3JuYW1lbnQiIDogIkEwMkEwMzAwMDIucG5nIgogIH0sCiAgIkIiIDogewoKICB9LAogICJjb2xvciIgOiAiZmZmZmZmIgp9,
 * addDate=2017-09-01T17:53:24.000Z,
 * baseName=短款直筒裤,
 * title=葛岭????????设计的短款直筒裤(2017-09-01),
 * praise=1.0,
 * height=185cm,
 * color=ffffff,
 * orderType=Check,
 * size=185cm,
 * address=浙江省杭州市西湖区景月湾,
 * zipcode=330012,
 * finishimage=http://oub3nsjgh.bkt.clouddn.com/1142_1504259603.452682A.png,
 * allImage=http://oub3nsjgh.bkt.clouddn.com/1142_1504259603.452682A.png,http://oub3nsjgh.bkt.clouddn.com/1142_1504259603.452825B.png,
 * fee=100.0,
 * styleContext={
 * "A" : {
 * "arm" : "A02A020002.png",
 * "neck" : "A02A010001.png",
 * "ornament" : "A02A030002.png"
 * },
 * "B" : {
 * },
 * "color" : "ffffff"
 * },
 * UserName=葛岭????????,
 * headerImage=https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJHM7RJLsPVYe2icSUNOJ1guH5q11B9TTsuN6MmRV9lGOkHLJ5e7pZYtg0LiaLvqF0emH9omI8lnLcA/0},
 */
public class BombStyleBean {
    private int id;
    private int userId;
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
    private String allImage;
    private int fee;
    private String styleContext;
    private String UserName;
    private String headerImage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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

    public String getAllImage() {
        return allImage;
    }

    public void setAllImage(String allImage) {
        this.allImage = allImage;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
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
        return "BombStyleBean{" +
                "id=" + id +
                ", userId=" + userId +
                ", Gender='" + Gender + '\'' +
                ", baseId='" + baseId + '\'' +
                ", Context='" + Context + '\'' +
                ", addDate='" + addDate + '\'' +
                ", baseName='" + baseName + '\'' +
                ", title='" + title + '\'' +
                ", praise=" + praise +
                ", height='" + height + '\'' +
                ", color='" + color + '\'' +
                ", orderType='" + orderType + '\'' +
                ", size='" + size + '\'' +
                ", address='" + address + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", finishimage='" + finishimage + '\'' +
                ", allImage='" + allImage + '\'' +
                ", fee=" + fee +
                ", styleContext='" + styleContext + '\'' +
                ", UserName='" + UserName + '\'' +
                ", headerImage='" + headerImage + '\'' +
                '}';
    }
}
