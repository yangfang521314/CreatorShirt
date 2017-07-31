package com.example.yf.creatorshirt.mvp.model;

/**
 * Created by yang on 31/07/2017.
 * 爆款数据
 * <p>
 * "creatTime": "1980-02-26 04:22:58",
 * "img": "https://w3.hoopchina.com.cn/a0/aa/22/a0aa228e3a05fcfec9092dfe6a46f230002.png",
 * "price": 123,
 * "priseNum": 10105,
 * "type": "eZn"
 */


public class BombStyleBean {

    private String creatTime;
    private String img;
    private int price;
    private int priseNum;
    private String type;

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPriseNum() {
        return priseNum;
    }

    public void setPriseNum(int priseNum) {
        this.priseNum = priseNum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "BombStyle{" +
                "creatTime='" + creatTime + '\'' +
                ", img='" + img + '\'' +
                ", price=" + price +
                ", priseNum=" + priseNum +
                ", type='" + type + '\'' +
                '}';
    }

}
