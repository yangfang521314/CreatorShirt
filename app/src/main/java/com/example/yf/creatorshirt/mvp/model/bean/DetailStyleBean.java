package com.example.yf.creatorshirt.mvp.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/18.
 */

public class DetailStyleBean {
    private String title;
    private List<StyleBean> list;

    public DetailStyleBean() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
