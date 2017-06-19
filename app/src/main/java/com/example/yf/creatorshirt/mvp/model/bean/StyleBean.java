package com.example.yf.creatorshirt.mvp.model.bean;

/**
 * Created by yang on 2017/6/17.
 */

public class StyleBean {
    public StyleBean() {
    }

    private String title;
    private int imageId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    @Override
    public String toString() {
        return "StyleBean{" +
                "title='" + title + '\'' +
                ", imageId=" + imageId +
                '}';
    }
}
