package com.example.yf.creatorshirt.mvp.presenter;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.yf.creatorshirt.http.DataManager;
import com.example.yf.creatorshirt.http.HttpResponse;
import com.example.yf.creatorshirt.mvp.model.orders.OrderType;
import com.example.yf.creatorshirt.mvp.model.orders.SaveStyleEntity;
import com.example.yf.creatorshirt.mvp.model.detaildesign.CommonStyleData;
import com.example.yf.creatorshirt.mvp.presenter.base.RxPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.SizeOrShareContract;
import com.example.yf.creatorshirt.utils.RxUtils;
import com.example.yf.creatorshirt.utils.SharedPreferencesUtil;
import com.example.yf.creatorshirt.utils.Utils;
import com.example.yf.creatorshirt.widget.CommonSubscriber;
import com.google.gson.Gson;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONObject;

import javax.inject.Inject;

import okhttp3.RequestBody;

/**
 * Created by yangfang on 2017/8/28.
 */

public class SizeOrSharePresenter extends RxPresenter<SizeOrShareContract.SizeOrShareView> implements SizeOrShareContract.Presenter {

    private DataManager manager;
    private String QiniuToken;
    UploadManager uploadManager = new UploadManager();
    private String userToken = SharedPreferencesUtil.getUserToken();
    private String UserId = SharedPreferencesUtil.getUserId() + "_";
    private String imageUrl;
    private CommonStyleData mFrontData;
    private CommonStyleData mBackData;
    private String size;

    @Inject
    public SizeOrSharePresenter(DataManager manager) {
        this.manager = manager;
    }

    public void saveImage(String mFrontImageUrl, final String styleContext) {
        String key = UserId + Utils.getTime();
        uploadManager.put(mFrontImageUrl, key, QiniuToken, new UpCompletionHandler() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                if (info.isOK()) {
                    Log.e("qiniu", "UPLOAD SUCCESS");
                } else {
                    Log.e("qiniu", "UPLOAD fail");
                }
                Log.e("qiniu", "UPLOAD SUCCESS" + key + ":" + info + ":" + response);
                imageUrl = "http://oub3nsjgh.bkt.clouddn.com/" + key;
                sendOrderData(styleContext);
            }
        }, null);
    }

    /**
     * 获取七牛token
     */
    public void getToken() {
        addSubscribe(manager.getQiToken(userToken)
                .compose(RxUtils.<HttpResponse<String>>rxSchedulerHelper())
                .compose(RxUtils.<String>handleResult())
                .subscribeWith(new CommonSubscriber<String>(mView) {
                    @Override
                    public void onNext(String s) {
                        QiniuToken = s;
                    }
                })
        );
    }

    /**
     * @param styleContext
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void sendOrderData(String styleContext) {
        SaveStyleEntity saveStyleEntity = new SaveStyleEntity();
        saveStyleEntity.setGender(mBackData.getGender());
        saveStyleEntity.setBaseId(mBackData.getType());
        saveStyleEntity.setColor(mFrontData.getColor());
        saveStyleEntity.setHeight(Integer.parseInt(size));
        saveStyleEntity.setOrderType("Check");
        saveStyleEntity.setSize(Integer.parseInt(size));
        saveStyleEntity.setFinishImage(imageUrl);
        saveStyleEntity.setZipCode("");
        saveStyleEntity.setAddress("");
        saveStyleEntity.setUserId(SharedPreferencesUtil.getUserId());
        saveStyleEntity.setStyleContext(styleContext);
        Gson gson = new Gson();
        String postEntity = gson.toJson(saveStyleEntity);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), postEntity);
        addSubscribe(manager.saveOrderData(userToken, body)
                .compose(RxUtils.<HttpResponse<OrderType>>rxSchedulerHelper())
                .compose(RxUtils.<OrderType>handleResult())
                .subscribeWith(new CommonSubscriber<OrderType>(mView) {
                    @Override
                    public void onNext(OrderType s) {
                        mView.showSuccessData(s);

                    }
                }));

    }

    public void setClothesData(CommonStyleData mFrontData, CommonStyleData mBackData, String size) {
        this.mFrontData = mFrontData;
        this.mBackData = mBackData;
        this.size = size;
    }
}
