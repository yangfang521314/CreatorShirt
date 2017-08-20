package com.example.yf.creatorshirt.mvp.model.detaildesign;

/**
 * Created by yangfang on 2017/8/20.
 */

public class DetailOtherStyle {
    private String name;
    private String file;
    private String positionType;

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

    public String getPositionType() {
        return positionType;
    }

    public void setPositionType(String positionType) {
        this.positionType = positionType;
    }

    @Override
    public String toString() {
        return "DetailOtherStyle{" +
                "name='" + name + '\'' +
                ", file='" + file + '\'' +
                ", positionType='" + positionType + '\'' +
                '}';
    }
}
