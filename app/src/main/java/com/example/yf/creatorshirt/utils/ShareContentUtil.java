package com.example.yf.creatorshirt.utils;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.yf.creatorshirt.mvp.listener.CommonListener;
import com.example.yf.creatorshirt.mvp.model.ShareInfoEntity;
import com.example.yf.creatorshirt.mvp.presenter.SizeOrSharePresenter;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.Log;
import com.umeng.socialize.utils.ShareBoardlistener;


/**
 */
public class ShareContentUtil extends ShareContentUtilAbstract {
    // private UMSocialService mycontroller;
    private Activity mContext;
    private String mTitle;//分享标题
    private String mDescription;//分享内容
    private String mShareUrl;//分享链接
    private UMImage mThumb;
    private UMWeb weixinContent, wbContent,qqShareContent;
    private CommonListener.CommonClickListener commonClickListener;

    public ShareContentUtil(Activity context) {
        mContext = context;
    }

    @Override
    protected void initShareContent(ShareInfoEntity shareEntity) {
        mTitle = TextUtils.isEmpty(shareEntity.getTitle()) ? "衣舍" : shareEntity.getTitle();
        mDescription = TextUtils.isEmpty(shareEntity.getContent()) ? shareEntity.getTargetUrl() : shareEntity.getContent();
        mThumb = TextUtils.isEmpty(shareEntity.getPicUrl()) ? (new UMImage(mContext, shareEntity.getDefaultImg())) : (new UMImage(mContext, shareEntity.getPicUrl()));
        mShareUrl = TextUtils.isEmpty(shareEntity.getTargetUrl()) ? "默认连接" : shareEntity.getTargetUrl();
        initSharePlatform();

    }

    protected void initSharePlatform() {
        //微信好友、朋友圈分享
        weixinContent = new UMWeb(mShareUrl);
        weixinContent.setTitle(mTitle);
        weixinContent.setDescription(mDescription);
        weixinContent.setThumb(mThumb);
        //微信好友、朋友圈分享


        // 设置QQ空间分享内容
//        qzoneShareContent = new UMWeb(myshareurl);
//        qzoneShareContent.setDescription(mycontent);
//        qzoneShareContent.setTitle(myTitle);
//        qzoneShareContent.setThumb(myshareImage);

        //QQ好友分享
        qqShareContent = new UMWeb(mShareUrl);
        qqShareContent.setDescription(mDescription);
        qqShareContent.setTitle(mTitle);
        qqShareContent.setThumb(mThumb);

//        // 设置邮件分享内容， 如果需要分享图片则只支持本地图片
//        UMImage image = new UMImage(mycontext, R.mipmap.icon);
//        mail = new UMWeb(myshareurl);
//        String mailshare = "&nbsp;&nbsp;&nbsp;&nbsp;我在中国搜索浏览新闻，想给你分享这篇文章：\r\n" + "<br>&nbsp;&nbsp;&nbsp;&nbsp;" + "【" + myTitle + "】" + "<br>" + "&nbsp;&nbsp;&nbsp;&nbsp;具体内容请戳: " + myshareurl;
//        mail.setDescription(mailshare);
//        mail.setTitle("中国搜索新闻分享");
//        mail.setThumb(image);

//        // 设置短信分享内容
//        sms = new UMWeb(myshareurl);
//        sms.setDescription(mycontent);
//        sms.setTitle(myTitle);
//        sms.setThumb(myshareImage);
//        sms.mText = "分享国搜新闻 【" + myTitle + "】" + "\r\n" + myshareurl + " (分享自#中国搜索客户端#)";

        //新浪微博分享
        wbContent = new UMWeb(mShareUrl);
        wbContent.setTitle(mTitle);
        wbContent.setDescription(mDescription);
        wbContent.setThumb(mThumb);
        wbContent.mText = "分享衣舍 【" + mTitle + "】" + " (分享自#衣舍客户端#) ";

    }

    @Override
    protected void beginShare(int i) {
        switch (i) {
            case 1:
                /**分享面板增加自定义按钮,以及不同分享平台不同分享内容，不同回调监听**/
                new ShareAction(mContext).setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE,
                        SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE
//                        SHARE_MEDIA.DOUBAN, SHARE_MEDIA.EMAIL, SHARE_MEDIA.SMS //SHARE_MEDIA.TENCENT, SHARE_MEDIA.RENREN
                )

                        .setShareboardclickCallback(new ShareBoardlistener() {
                            @Override
                            public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
//                                if (share_media == SHARE_MEDIA.DOUBAN) {
//                                    new ShareAction(mContext).setPlatform(share_media).setCallback(umShareListener)
//                                            .withText("fuck you")
//                                            .withMedia(shareContent)
//                                            .share();
//                                }
                                if (share_media == SHARE_MEDIA.WEIXIN || share_media == SHARE_MEDIA.WEIXIN_CIRCLE || share_media == SHARE_MEDIA.WEIXIN_FAVORITE) {
                                    new ShareAction(mContext).setPlatform(share_media).setCallback(umShareListener)
                                            .withMedia(weixinContent)
                                            .share();
                                }
                                if (share_media == SHARE_MEDIA.QQ || share_media == SHARE_MEDIA.QZONE) {
                                    new ShareAction(mContext).setPlatform(share_media).setCallback(umShareListener)
                                            .withMedia(qqShareContent)
                                            .share();
                                }
                                if (share_media == SHARE_MEDIA.SINA) {
                                    new ShareAction(mContext).setPlatform(share_media).setCallback(umShareListener)
                                            .withText(mDescription)
                                            .withMedia(wbContent)
                                            .share();
                                }
//                                if (share_media == SHARE_MEDIA.EMAIL) {
//                                    new ShareAction(mContext).setPlatform(share_media).setCallback(umShareListener)
//                                            .withMedia(mail)
//                                            .share();
//                                }
//                                if (share_media == SHARE_MEDIA.SMS || share_media == SHARE_MEDIA.DOUBAN) {
//                                    new ShareAction(mContext).setPlatform(share_media).setCallback(umShareListener)
//                                            .withMedia(sms)
//                                            .share();
//                                }
                            }

                        }).open();
                break;
//            case 2:
//                //因为长链接问题，ShowFaces不能够实现QQ的分享
//                new ShareAction(mContext).setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE,
//                        SHARE_MEDIA.QZONE
//                )
//
//                        .setShareboardclickCallback(new ShareBoardlistener() {
//                            @Override
//                            public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
//
//                                if (share_media == SHARE_MEDIA.WEIXIN || share_media == SHARE_MEDIA.WEIXIN_CIRCLE || share_media == SHARE_MEDIA.WEIXIN_FAVORITE) {
//                                    new ShareAction(mycontext).setPlatform(share_media).setCallback(umShareListener)
//                                            .withMedia(weixinContent)
//                                            .share();
//                                }
//                                if (share_media == SHARE_MEDIA.QZONE) {
//                                    new ShareAction(mycontext).setPlatform(share_media).setCallback(umShareListener)
//                                            .withMedia(qqShareContent)
//                                            .share();
//                                }
//
//                            }
//
//                        }).open();
//                break;
            default:
                break;
        }

    }

    @Override
    public void onDestroyShare() {

    }


    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("SharePlatform", "platform成功" + platform);
            if (platform.name().equals("WEIXIN_FAVORITE")) {
                Toast.makeText(mContext, " 收藏成功啦", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mContext, " 分享成功啦", Toast.LENGTH_SHORT).show();
                if(commonClickListener != null)
                commonClickListener.onClickListener();
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(mContext, " 分享失败啦", Toast.LENGTH_SHORT).show();
            if(commonClickListener != null)
            commonClickListener.onClickListener();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(mContext, " 分享取消了", Toast.LENGTH_SHORT).show();
            if(commonClickListener != null)
            commonClickListener.onClickListener();
        }
    };

    public void setOnClickListener(SizeOrSharePresenter sizeOrSharePresenter) {
        this.commonClickListener = sizeOrSharePresenter;
    }
}
