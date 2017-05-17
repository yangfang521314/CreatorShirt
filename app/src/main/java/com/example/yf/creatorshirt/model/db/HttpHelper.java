package com.example.yf.creatorshirt.model.db;

import com.example.yf.creatorshirt.model.bean.NewsSummary;

import io.reactivex.Flowable;

/**
 * Created by yang on 17/05/2017.
 */

public interface HttpHelper {
    Flowable<NewsSummary> getDataNewsSummary();
}
