package com.example.yf.creatorshirt.mvp.model;

import android.support.v4.util.ArrayMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangfang on 2017/9/1.
 */

public class ClothesStyleBean {

    private List<String> mListVerName = new ArrayList<>();
    private ArrayMap<String, List<VersionStyle>> totalManMap;
    private ArrayMap<String, List<VersionStyle>> totalWomanMap;


    public List<String> getmListVerName() {
        return mListVerName;
    }

    public void setmListVerName(List<String> mListVerName) {
        this.mListVerName = mListVerName;
    }

    public ArrayMap<String, List<VersionStyle>> getTotalManMap() {
        return totalManMap;
    }

    public void setTotalManMap(ArrayMap<String, List<VersionStyle>> totalManMap) {
        this.totalManMap = totalManMap;
    }

    public ArrayMap<String, List<VersionStyle>> getTotalWomanMap() {
        return totalWomanMap;
    }

    public void setTotalWomanMap(ArrayMap<String, List<VersionStyle>> totalWomanMap) {
        this.totalWomanMap = totalWomanMap;
    }

    @Override
    public String toString() {
        return "ClothesStyleBean{" +
                "mListVerName=" + mListVerName +
                ", totalManMap=" + totalManMap +
                ", totalWomanMap=" + totalWomanMap +
                '}';
    }
}
