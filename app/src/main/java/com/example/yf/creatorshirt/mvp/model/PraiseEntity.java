package com.example.yf.creatorshirt.mvp.model;

/**
 * Created by panguso on 2017/9/15.
 */

public class PraiseEntity {
    private int praise;

    public int getPraise() {
        return praise;
    }

    public void setPraise(int praise) {
        this.praise = praise;
    }

    @Override
    public String toString() {
        return "PraiseEntity{" +
                "praise=" + praise +
                '}';
    }
}
