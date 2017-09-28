package com.example.yf.creatorshirt.mvp.model.detaildesign;

/**
 * Created by yangfang on 2017/9/27.
 */

public class TextEntity {
    private String color;
    private String text;

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
}
