package com.example.yf.creatorshirt.mvp.model.detaildesign.styles;

import com.example.yf.creatorshirt.mvp.model.detaildesign.DetailPatterStyle;

import java.util.List;

/**
 * Created by yangfang on 2017/8/20.
 * Pattern style
 */

public class PatternData {
    private String name;
    private List<DetailPatterStyle> fileList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DetailPatterStyle> getFileList() {
        return fileList;
    }

    public void setFileList(List<DetailPatterStyle> fileList) {
        this.fileList = fileList;
    }

    @Override
    public String toString() {
        return "PatternData{" +
                "name='" + name + '\'' +
                ", fileList=" + fileList +
                '}';
    }
}
