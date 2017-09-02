package com.example.yf.creatorshirt.mvp.model.orders;

/**
 * Created by yangfang on 2017/9/1.
 */

public class ClothesSize {
    private String size;
    private String letter;

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    @Override
    public String toString() {
        return "ClothesSize{" +
                "size='" + size + '\'' +
                ", letter='" + letter + '\'' +
                '}';
    }
}
