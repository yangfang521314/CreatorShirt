package com.example.yf.creatorshirt.mvp.model.detaildesign;

/**
 * Created by Administrator on 2017/6/18.
 * 总的数据bean
 */

public class DetailStyleBean {
    private String Gender;
    private String Typeversion;
    private StyleData data;

    public class StyleData {
        private DetailStyleFrontData A;
        private DetailStyleBackData B;

        public DetailStyleFrontData getA() {
            return A;
        }

        public void setA(DetailStyleFrontData a) {
            A = a;
        }

        public DetailStyleBackData getB() {
            return B;
        }

        public void setB(DetailStyleBackData b) {
            B = b;
        }

        @Override
        public String toString() {
            return "StyleData{" +
                    "A=" + A +
                    ", B=" + B +
                    '}';
        }
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getTypeversion() {
        return Typeversion;
    }

    public void setTypeversion(String typeversion) {
        Typeversion = typeversion;
    }

    public StyleData getData() {
        return data;
    }

    public void setData(StyleData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DetailStyleBean{" +
                "Gender='" + Gender + '\'' +
                ", Typeversion='" + Typeversion + '\'' +
                ", data=" + data +
                '}';
    }
}
