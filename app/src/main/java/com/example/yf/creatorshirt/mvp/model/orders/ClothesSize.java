package com.example.yf.creatorshirt.mvp.model.orders;

/**
 * Created by yangfang on 2017/9/1.
 */

public class ClothesSize {
    private String size;
    private String letter;
    private int numbers;
    private double prices;

    public double getPrices() {
        return prices;
    }

    public void setPrices(double prices) {
        this.prices = prices;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "ClothesSize{" +
                "size='" + size + '\'' +
                '}';
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public int getNumbers() {
        return numbers;
    }

    public void setNumbers(int numbers) {
        this.numbers = numbers;
    }
}
