package com.example.yf.creatorshirt.mvp.model.detaildesign;

import com.example.yf.creatorshirt.mvp.model.detaildesign.styles.ArmData;
import com.example.yf.creatorshirt.mvp.model.detaildesign.styles.ColorData;
import com.example.yf.creatorshirt.mvp.model.detaildesign.styles.NeckData;
import com.example.yf.creatorshirt.mvp.model.detaildesign.styles.OrnamentData;
import com.example.yf.creatorshirt.mvp.model.detaildesign.styles.PatternData;

import java.util.List;

/**
 * Created by yangfang on 2017/8/19.
 * 背面数据bean
 *
 */

public class DetailCommonData {

    private NeckData neck;
    private ArmData arm;
    private OrnamentData ornament;
    private ColorData color;
    private PatternData pattern;

    public NeckData getNeck() {
        return neck;
    }

    public void setNeck(NeckData neck) {
        this.neck = neck;
    }

    public ArmData getArm() {
        return arm;
    }

    public void setArm(ArmData arm) {
        this.arm = arm;
    }

    public OrnamentData getOrnament() {
        return ornament;
    }

    public void setOrnament(OrnamentData ornament) {
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

    @Override
    public String toString() {
        return "DetailCommonData{" +
                "neck=" + neck +
                ", arm=" + arm +
                ", ornament=" + ornament +
                ", color=" + color +
                ", pattern=" + pattern +
                '}';
    }
}
