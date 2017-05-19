package com.example.yf.creatorshirt.mvp.model.bean;

import java.util.List;

/**
 * Created by yang on 19/05/2017.
 */

public class GirlData {
    //参数一定要保证正确性。不然请求服务器的数据会出错

    private boolean isError;
    private List<PhotoGirl> results;

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public void setResults(List<PhotoGirl> results) {
        this.results = results;
    }

    public List<PhotoGirl> getResults() {
        return results;
    }
}