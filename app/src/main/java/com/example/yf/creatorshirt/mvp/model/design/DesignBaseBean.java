package com.example.yf.creatorshirt.mvp.model.design;

/**
 * Created by yang on 10/08/2017.
 * "baseId": "03",
 * "baseName": "中款直筒裤",
 * "Gender": "A"
 */
public class DesignBaseBean {

    private String baseId;
    private String baseName;
    private String Gender;

    public String getBaseId() {
        return baseId;
    }

    public void setBaseId(String baseId) {
        this.baseId = baseId;
    }

    public String getBaseName() {
        return baseName;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    @Override
    public String toString() {
        return "DesignBaseBean{" +
                "baseId='" + baseId + '\'' +
                ", baseName='" + baseName + '\'' +
                ", Gender='" + Gender + '\'' +
                '}';
    }
}
