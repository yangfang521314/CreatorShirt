package com.example.yf.creatorshirt.mvp.model.detaildesign.styles;

import com.example.yf.creatorshirt.mvp.model.detaildesign.DetailColorStyle;

import java.util.List;

/**
 * Created by yangfang on 2017/8/20.
 * 颜色样式
 */

public class ColorData {
    private String name;
    private List<DetailColorStyle> fileList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DetailColorStyle> getFileList() {
        return fileList;
    }

    public void setFileList(List<DetailColorStyle> fileList) {
        this.fileList = fileList;
    }

    @Override
    public String toString() {
        return "ColorData{" +
                "name='" + name + '\'' +
                ", fileList=" + fileList +
                '}';
    }
}
