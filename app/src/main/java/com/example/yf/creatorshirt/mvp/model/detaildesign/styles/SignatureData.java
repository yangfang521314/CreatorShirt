package com.example.yf.creatorshirt.mvp.model.detaildesign.styles;

import com.example.yf.creatorshirt.mvp.model.detaildesign.DetailColorStyle;

import java.util.List;

/**
 * Created by yangfang on 2017/9/25.
 */
/*
* text={name=文字, fileList=[{name=红色, value=F1CFC6}, {name=黄色, value=D3927C}, {name=绿色, value=F6D258},
* {name=黑色, value=9EB4A7}, {name=红色, value=A8C8D4}, {name=红色, value=908760}, {name=米色, value=F4F6F3}]},
* */
public class SignatureData {
    private String name;
    private List<DetailColorStyle> fileList;

    public List<DetailColorStyle> getFileList() {
        return fileList;
    }

    public void setFileList(List<DetailColorStyle> fileList) {
        this.fileList = fileList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SignatureData{" +
                "name='" + name + '\'' +
                ", fileList=" + fileList +
                '}';
    }
}
