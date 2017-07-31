package com.example.yf.creatorshirt.mvp.model;

import java.util.List;

/**
 * Created by yang on 31/07/2017.
 */

public class HotDesignsBean {
    private int status;
    private List<HotDesign> result;

    /**
     * "designNum": 90,
     * "headImage": "https://w3.hoopchina.com.cn/a0/aa/22/a0aa228e3a05fcfec9092dfe6a46f230002.png",
     * "nickname": "霍霍",
     * "userId": 10000
     */
    public class HotDesign {
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

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<HotDesign> getResult() {
        return result;
    }

    public void setResult(List<HotDesign> result) {
        this.result = result;
    }
}
