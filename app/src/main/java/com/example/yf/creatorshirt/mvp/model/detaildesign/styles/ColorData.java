package com.example.yf.creatorshirt.mvp.model.detaildesign.styles;

import com.example.yf.creatorshirt.mvp.model.detaildesign.DetailStyle;

import java.util.List;

/**
 * Created by yangfang on 2017/8/20.
 * 颜色样式
 */

public class ColorData {
    private String name;
    private List<DetailStyle> fileList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DetailStyle> getFileList() {
        return fileList;
    }

    public void setFileList(List<DetailStyle> fileList) {
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
