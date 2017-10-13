package com.example.yf.creatorshirt.mvp.model.detaildesign.styles;

import com.example.yf.creatorshirt.mvp.model.detaildesign.DetailOtherStyle;

import java.util.List;

/**
 * Created by yangfang on 2017/10/13.
 */

public class CommonData {
    private String name;
    private List<DetailOtherStyle> fileList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DetailOtherStyle> getFileList() {
        return fileList;
    }

    public void setFileList(List<DetailOtherStyle> fileList) {
        this.fileList = fileList;
    }

    @Override
    public String toString() {
        return "CommonData{" +
                "name='" + name + '\'' +
                ", fileList=" + fileList +
                '}';
    }
}
