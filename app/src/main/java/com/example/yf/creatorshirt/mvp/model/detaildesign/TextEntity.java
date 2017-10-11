package com.example.yf.creatorshirt.mvp.model.detaildesign;

import android.graphics.Typeface;

/**
 * Created by yangfang on 2017/9/27.
 */

public class TextEntity {
    private String color;
    private String text;
    private Typeface typeface;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "TextEntity{" +
                "color='" + color + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

    public Typeface getTypeface() {
        return typeface;
    }

    public void setTypeface(Typeface typeface) {
        this.typeface = typeface;
    }
}
