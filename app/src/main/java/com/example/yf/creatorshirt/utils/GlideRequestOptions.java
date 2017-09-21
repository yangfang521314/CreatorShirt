package com.example.yf.creatorshirt.utils;

import com.bumptech.glide.request.RequestOptions;
import com.example.yf.creatorshirt.R;

/**
 * Created by yangfang on 2017/9/21.
 */

public class GlideRequestOptions {

    /**
     * 设置默认的glide配置
     * 圆形
     *
     * @return
     */
    public static RequestOptions getCircleOptions() {
        RequestOptions options = new RequestOptions()
                .circleCrop()
                .error(R.mipmap.mm);
        return options;
    }

    /**
     * 普通
     *
     * @return
     */
    public static RequestOptions getOptions() {
        RequestOptions options = new RequestOptions()
                .error(R.mipmap.mm);
        return options;
    }
}
