package com.example.yf.creatorshirt.mvp.model.detaildesign;

import com.example.yf.creatorshirt.mvp.model.detaildesign.styles.ColorData;
import com.example.yf.creatorshirt.mvp.model.detaildesign.styles.CommonData;
import com.example.yf.creatorshirt.mvp.model.detaildesign.styles.PatternData;
import com.example.yf.creatorshirt.mvp.model.detaildesign.styles.SignatureData;

/**
 * Created by yangfang on 2017/8/19.
 * 背面数据bean
 *
 */

public class DetailCommonData {

    private CommonData neck;
    private CommonData arm;
    private CommonData ornament;
    private ColorData color;
    private PatternData pattern;
    private SignatureData text;

    public CommonData getNeck() {
        return neck;
    }

    public void setNeck(CommonData neck) {
        this.neck = neck;
    }

    public CommonData getArm() {
        return arm;
    }

    public void setArm(CommonData arm) {
        this.arm = arm;
    }

    public CommonData getOrnament() {
        return ornament;
    }

    public void setOrnament(CommonData ornament) {
        this.ornament = ornament;
    }

    public ColorData getColor() {
        return color;
    }

    public void setColor(ColorData color) {
        this.color = color;
    }

    public PatternData getPattern() {
        return pattern;
    }

    public void setPattern(PatternData pattern) {
        this.pattern = pattern;
    }

    public SignatureData getText() {
        return text;
    }

    public void setText(SignatureData text) {
        this.text = text;
    }


    @Override
    public String toString() {
        return "DetailCommonData{" +
                "neck=" + neck +
                ", arm=" + arm +
                ", ornament=" + ornament +
                ", color=" + color +
                ", pattern=" + pattern +
                ", text=" + text +
                '}';
    }
}
