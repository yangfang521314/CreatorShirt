package com.example.yf.creatorshirt.mvp.model.basechoice;

import java.util.List;

/**
 * Created by yangfang on 2017/8/19.
 * 首选择裤子或者衣服页面的bean
 */

public class DesignBaseInfo {
    //M的数组对象
    private List<DesignBaseBean> M;
    private List<DesignBaseBean> W;

    public List<DesignBaseBean> getM() {
        return M;
    }

    public void setM(List<DesignBaseBean> m) {
        M = m;
    }

    public List<DesignBaseBean> getW() {
        return W;
    }

    public void setW(List<DesignBaseBean> w) {
        W = w;
    }

    @Override
    public String toString() {
        return "DesignBaseInfo{" +
                "M=" + M +
                ", W=" + W +
                '}';
    }
}
