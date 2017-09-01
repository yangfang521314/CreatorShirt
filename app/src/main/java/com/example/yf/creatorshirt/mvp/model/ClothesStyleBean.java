package com.example.yf.creatorshirt.mvp.model;

/**
 * Created by yangfang on 2017/9/1.
 */

public class ClothesStyleBean {

    private CommonStyleEntity A;
    private CommonStyleEntity B;

    private class CommonStyleEntity {
        private String neck;
        private String arm;
        private String ornament;
        private String color;
        private String pattern;

        public String getNeck() {
            return neck;
        }

        public void setNeck(String neck) {
            this.neck = neck;
        }

        public String getArm() {
            return arm;
        }

        public void setArm(String arm) {
            this.arm = arm;
        }

        public String getOrnament() {
            return ornament;
        }

        public void setOrnament(String ornament) {
            this.ornament = ornament;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
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
            return "CommonStyleEntity{" +
                    "neck='" + neck + '\'' +
                    ", arm='" + arm + '\'' +
                    ", ornamet='" + ornament + '\'' +
                    ", color='" + color + '\'' +
                    ", pattern='" + pattern + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ClothesStyleBean{" +
                "A=" + A +
                ", B=" + B +
                '}';
    }
}
