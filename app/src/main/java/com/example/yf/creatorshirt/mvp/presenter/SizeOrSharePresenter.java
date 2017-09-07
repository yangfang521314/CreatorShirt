package com.example.yf.creatorshirt.mvp.presenter;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.yf.creatorshirt.http.DataManager;
import com.example.yf.creatorshirt.http.HttpResponse;
import com.example.yf.creatorshirt.mvp.model.detaildesign.CommonStyleData;
import com.example.yf.creatorshirt.mvp.model.orders.OrderType;
import com.example.yf.creatorshirt.mvp.model.orders.SaveStyleEntity;
import com.example.yf.creatorshirt.mvp.presenter.base.RxPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.SizeOrShareContract;
import com.example.yf.creatorshirt.utils.Constants;
import com.example.yf.creatorshirt.utils.RxUtils;
import com.example.yf.creatorshirt.utils.SharedPreferencesUtil;
import com.example.yf.creatorshirt.utils.Utils;
import com.example.yf.creatorshirt.widget.CommonSubscriber;
import com.google.gson.Gson;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.RequestBody;

/**
 * Created by yangfang on 2017/8/28.
 */

public class SizeOrSharePresenter extends RxPresenter<SizeOrShareContract.SizeOrShareView> implements SizeOrShareContract.Presenter {

    private static final int WHAT_SUCCESS = 1;
    private static final int WHAT_SUCCESS2 = 2;
    private DataManager manager;
    private String QiniuToken;
    private UploadManager uploadManager = new UploadManager();
    private String userToken = SharedPreferencesUtil.getUserToken();
    private String UserId = SharedPreferencesUtil.getUserId() + "_";

    private CommonStyleData mFrontData;
    private CommonStyleData mBackData;
    private String imageFrontUrl;
    private String imageBackUrl;
    private String mBack;
    private String size;
    private String styleContext;
    private Map<String, String> map = new HashMap<>();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == WHAT_SUCCESS) {
                getList("B", mBack);
            }
            if (msg.what == WHAT_SUCCESS2) {
                sendOrderData();
            }
        }
    };

    @Inject
    public SizeOrSharePresenter(DataManager manager) {
        this.manager = manager;
    }

    public void getList(final String kewWord, final String imageUrl) {
        String key = UserId + Utils.getTime() + kewWord;
        uploadManager.put(imageUrl, key, QiniuToken, new UpCompletionHandler() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {

                if (info.isOK()) {
                    Log.e("qiniu_back", "UPLOAD SUCCESS" + key + ":" + info + ":" + response);
                    if (kewWord.equals("A")) {
                        try {
                            String imageFrontUrl = Constants.ImageUrl + response.get("key");
                            map.put("A", imageFrontUrl);
                            handler.sendEmptyMessage(WHAT_SUCCESS);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    if (kewWord.equals("B")) {
                        try {
                            String imageBackUrl = Constants.ImageUrl + response.get("key");
                            map.put("B", imageBackUrl);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    if (map.size() == 2) {
                        handler.sendEmptyMessage(WHAT_SUCCESS2);
                    }

                } else {
                    Log.e("qiniu_back", "UPLOAD fail");
                    mView.showErrorMsg("保存图片失败");
                }

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

    public void sendOrderData() {
        imageBackUrl = map.get("B");
        imageFrontUrl = map.get("A");
        SaveStyleEntity saveStyleEntity = new SaveStyleEntity();
        saveStyleEntity.setGender(mBackData.getGender());
        saveStyleEntity.setBaseId(mBackData.getType());
        saveStyleEntity.setColor(mFrontData.getColor());
        saveStyleEntity.setHeight(Integer.parseInt(size));
        saveStyleEntity.setOrderType("Check");
        saveStyleEntity.setSize(Integer.parseInt(size));
        saveStyleEntity.setFinishImage(imageFrontUrl);
        saveStyleEntity.setAllImage(imageFrontUrl + "," + imageBackUrl);
        saveStyleEntity.setZipCode("");
        saveStyleEntity.setAddress("");
        saveStyleEntity.setUserId(SharedPreferencesUtil.getUserId());
        saveStyleEntity.setStyleContext(styleContext);
        Gson gson = new Gson();
        String postEntity = gson.toJson(saveStyleEntity);
        Log.e("TAG", "DDDD" + postEntity.toString());
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
        if (map.size() != 0) {
            map.clear();
        }

    }

    public void setClothesData(CommonStyleData mFrontData, CommonStyleData mBackData, String size) {
        this.mFrontData = mFrontData;
        this.mBackData = mBackData;
        this.size = size;
    }

    /**
     * 设置数据的String格式
     *
     * @param s
     */
    @Override
    public void setStyleContext(String s) {
        this.styleContext = s;
    }


    public void request(final String key, final String value) {
        AsyncTask<String, Integer, Void> asyncTask = new AsyncTask<String, Integer, Void>() {
            @Override
            protected Void doInBackground(String... params) {
                getList(key, value);
                return null;
            }

            @Override
            protected void onPreExecute() {
                Log.e("TAG", "正在保存图片");
            }

            @Override
            protected void onPostExecute(Void aVoid) {
            }
        };
        asyncTask.execute();

    }

    public void setIM(String mBackImageUrl) {
        mBack = mBackImageUrl;
    }
}
