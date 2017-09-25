package com.example.yf.creatorshirt.utils;


import com.example.yf.creatorshirt.mvp.model.ShareInfoEntity;

/**
 * Created by Administrator on 2016-4-27.
 */
public abstract class ShareContentUtilAbstract implements ShareContentUtilInterface{

    @Override
    public void startShare(ShareInfoEntity shareEntity, int i) {
      //  addSharePlatform(i);

        initShareContent(shareEntity);

        beginShare(i);
    }

    /**
     * 添加需要分享到的平台  新版分享Jar包不需要单独添加平台了
     */
    //protected abstract void addSharePlatform();
    //protected abstract void addSharePlatform(int i);
    /**
     * 开始准备分享的数据
     * <br>
     * <br>
     * title不能为空,分享内容不能为空,分享图片不能为空
     * @param shareEntity
     */
    protected abstract void initShareContent(ShareInfoEntity shareEntity);

    /**
     * 初始化第三方平台
     */
    protected abstract void initSharePlatform();

    /**
     * 开始分享
     */
    protected abstract void beginShare(int i);
}
