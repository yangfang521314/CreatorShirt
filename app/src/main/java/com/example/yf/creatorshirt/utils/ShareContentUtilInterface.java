package com.example.yf.creatorshirt.utils;


import com.example.yf.creatorshirt.mvp.model.ShareInfoEntity;

/**
 * Created by yang on 2016-4-27.
 */
public interface ShareContentUtilInterface {
    void startShare(ShareInfoEntity shareEntity, int i);
    void onDestoryShare();
}
