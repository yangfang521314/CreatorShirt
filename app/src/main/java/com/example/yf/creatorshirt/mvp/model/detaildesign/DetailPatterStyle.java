package com.example.yf.creatorshirt.mvp.model.detaildesign;

/**
 * Created by yangfang on 2017/8/21.
 */

public class DetailPatterStyle {
    protected String name;
    protected String file;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "DetailPatterStyle{" +
                "name='" + name + '\'' +
                ", file='" + file + '\'' +
                '}';
    }
}
