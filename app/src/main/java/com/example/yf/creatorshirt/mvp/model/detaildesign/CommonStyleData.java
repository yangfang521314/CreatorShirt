package com.example.yf.creatorshirt.mvp.model.detaildesign;

/**
 * Created by yangfang on 2017/8/26.
 */

public class CommonStyleData  {

    private String neckUrl;
    private String armUrl;
    private String ornametUrl;
    private int color;
    private String pattern;

    public String getNeckUrl() {
        return neckUrl;
    }

    public void setNeckUrl(String neckUrl) {
        this.neckUrl = neckUrl;
    }

    public String getArmUrl() {
        return armUrl;
    }

    public void setArmUrl(String armUrl) {
        this.armUrl = armUrl;
    }

    public String getOrnametUrl() {
        return ornametUrl;
    }

    public void setOrnametUrl(String ornametUrl) {
        this.ornametUrl = ornametUrl;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String toString() {
        return "CommonStyleData{" +
                "neckUrl='" + neckUrl + '\'' +
                ", armUrl='" + armUrl + '\'' +
                ", ornametUrl='" + ornametUrl + '\'' +
                ", color=" + color +
                ", pattern='" + pattern + '\'' +
                '}';
    }
}
