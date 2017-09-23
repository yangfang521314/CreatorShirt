package com.example.yf.creatorshirt.mvp.model.detaildesign;

import com.example.yf.creatorshirt.mvp.model.BaseChoiceEntity;

/**
 * Created by yang on 2017/6/17.
 * 根据样式名生成数组，方便查找对应的样式的具体集合
 *
 */

public class StyleBean extends BaseChoiceEntity {
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
