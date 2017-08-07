package com.example.yf.creatorshirt.mvp.model;

/**
 * Created by yang on 31/07/2017.
 */

public class HotDesignsBean {

    /**
     * "designNum": 90,
     * "headImage": "https://w3.hoopchina.com.cn/a0/aa/22/a0aa228e3a05fcfec9092dfe6a46f230002.png",
     * "nickname": "霍霍",
     * "userId": 10000
     */

    private int designNum;
    private String headImage;
    private String nickname;
    private int userId;

    public int getDesignNum() {
        return designNum;
    }

    public void setDesignNum(int designNum) {
        this.designNum = designNum;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getUserId() {
        return userId;
    }

}
