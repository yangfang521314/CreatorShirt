package com.example.yf.creatorshirt.mvp.model;

/**
 * Created by yangfang on 2017/8/29.
 */

/**
 * Gender = A;
 * addDate = "2017-08-27T18:10:10.000Z";
 * address = "";
 * baseId = 02;
 * baseName = "\U77ed\U6b3e\U76f4\U7b52\U88e4";
 * color = ffffff;
 * fee = 100;
 * finishimage = "http://oub3nsjgh.bkt.clouddn.com/0_1503828492.620139.png";
 * height = 170cm;
 * id = 51;
 * orderType = Check;
 * praise = 0;
 * size = 170cm;
 * styleContext = "{\n  \"A\" : {\n    \"arm\" : \"A02A020002.png\",\n    \"neck\" : \"A02A010002.png\",\n    \"ornament\" : \"A02A030002.png\"\n  },\n  \"B\" : {\n    \"arm\" : \"A02B020001.png\"\n  },\n  \"color\" : \"ffffff\"\n}";
 * title = "\U6731\U54e51\U8bbe\U8ba1\U7684\U77ed\U6b3e\U76f4\U7b52\U88e4(2017-08-27)";
 * userId = 1111;
 * zipcode = "";
 */
public class SaveOrderBean {
    private String Gender;
    private String addDate;
    private String address;
    private int baseId;
    private String baseName;
    private int color;
    private int fee;
    private String finishimage;
    private int height;
    private int id;
    private String orderType;
    private int praise;
    private int size;
    private String styleContext;
    private String title;
    private int userId;
    private String zipcode;

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getBaseId() {
        return baseId;
    }

    public void setBaseId(int baseId) {
        this.baseId = baseId;
    }

    public String getBaseName() {
        return baseName;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public String getFinishimage() {
        return finishimage;
    }

    public void setFinishimage(String finishimage) {
        this.finishimage = finishimage;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public int getPraise() {
        return praise;
    }

    public void setPraise(int praise) {
        this.praise = praise;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getStyleContext() {
        return styleContext;
    }

    public void setStyleContext(String styleContext) {
        this.styleContext = styleContext;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    @Override
    public String toString() {
        return "SaveOrderBean{" +
                "Gender='" + Gender + '\'' +
                ", addDate='" + addDate + '\'' +
                ", address='" + address + '\'' +
                ", baseId=" + baseId +
                ", baseName='" + baseName + '\'' +
                ", color=" + color +
                ", fee=" + fee +
                ", finishimage='" + finishimage + '\'' +
                ", height=" + height +
                ", id=" + id +
                ", orderType='" + orderType + '\'' +
                ", praise=" + praise +
                ", size=" + size +
                ", styleContext='" + styleContext + '\'' +
                ", title='" + title + '\'' +
                ", userId=" + userId +
                ", zipcode='" + zipcode + '\'' +
                '}';
    }
}
